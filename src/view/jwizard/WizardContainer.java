
package view.jwizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


import view.jwizard.pagetemplates.DefaultPageTemplate;
import view.jwizard.pagetemplates.PageTemplate;
//import org.utilities.ExceptionUtilities;

/**
 * This is the primary "Wizard" class.  It must be instantiated with a
 * PageFactory and then treated as a JPanel.
 * 
 * 
 *
 */
public class WizardContainer extends JPanel implements WizardController {

   
   /**
    * Storage for all the collected information.
    */
   private final WizardSettings _settings = new WizardSettings();

   /**
    * The path from the start of the dialog to the current location.
    */
   private final List<WizardPage> _path = new LinkedList<WizardPage>();
   
   /**
    * List of listeners to update on wizard events.
    */
   private final List<WizardListener> _listeners =
      new LinkedList<WizardListener>();

   /**
    * The template to surround the wizard pages of this dialog.
    */
   private PageTemplate _template = null;

   /**
    * The factory that generates pages for this wizard.
    */
   private final PageFactory _factory;
   
   private final AbstractAction _prevAction = new AbstractAction("< Prev"){
      {
         setEnabled(false);
      }
      @Override
      public void actionPerformed(ActionEvent e) {
         prev();
      }
   };
   
   private final AbstractAction _nextAction = new AbstractAction("Next >"){
      @Override
      public void actionPerformed(ActionEvent e) {
         next();
      }
   };

   private final AbstractAction _finishAction = new AbstractAction("Finish"){
      {
         setEnabled(false);
      }
      @Override
      public void actionPerformed(ActionEvent e) {
         finish();
      }
   };
   
   private final AbstractAction _cancelAction = new AbstractAction("Cancel"){
      @Override
      public void actionPerformed(ActionEvent e) {
         cancel();
      }
   };
   
   /**
    * Constructor, uses default PageTemplate.
    */
   public WizardContainer(PageFactory factory){
      this(factory, new DefaultPageTemplate());
   }
   
   /**
    * Constructor.
    */
   public WizardContainer(PageFactory factory, PageTemplate template){
      _factory = factory;
      _template = template;
     
      initComponents();
      _template.registerController(this);
      
      // get the first page:
      next();
   }

   /**
    * 
    */
   private void initComponents() {
      final JButton prevBtn = new JButton(_prevAction);
      final JButton nextBtn = new JButton(_nextAction);
      final JButton finishBtn = new JButton(_finishAction);
      final JButton cancelBtn = new JButton(_cancelAction);
           
      final JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
      buttonPanel.add(Box.createHorizontalGlue());
      buttonPanel.add(prevBtn);
      buttonPanel.add(Box.createHorizontalStrut(5));
      buttonPanel.add(nextBtn);
      buttonPanel.add(Box.createHorizontalStrut(10));
      buttonPanel.add(finishBtn);
      buttonPanel.add(Box.createHorizontalStrut(10));
      buttonPanel.add(cancelBtn);
      
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
      this.setLayout(new BorderLayout());
      
      this.add(_template, BorderLayout.CENTER);
      this.add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
    * The PageFactory is not queried for pages when moving *backwards*.
    */
   public void prev() {
     
      _path.remove(_path.size() - 1);
      // roll-back the settings:
      getSettings().rollBack();
      
      assert 1 <= _path.size() : "Invalid path size! "+_path.size();
      if (_path.size() <= 1){
         setPrevEnabled(false);
      }
      
      WizardPage curPage =  _path.get(_path.size() - 1);
      
      setNextEnabled(true);
      // tell the page that it is about to be rendered:
      curPage.rendering(getPath(), getSettings());
      _template.setPage(curPage);

      firePageChanged(curPage, getPath());
   }
   
   /**
    * 
    */
   public void next() {
     
      if (0 != _path.size()){
         // get the settings from the page that is going away:
         WizardPage lastPage = _path.get(_path.size()-1);
         getSettings().newPage(lastPage.getId());
         lastPage.updateSettings(getSettings());
      }
      
      WizardPage curPage = _factory.createPage(getPath(), getSettings());
      curPage.registerController(this);
     
      _path.add(curPage);
      if (_path.size() > 1){
         setPrevEnabled(true);
      }
      
      // tell the page that it is about to be rendered:
      curPage.rendering(getPath(), getSettings());
      _template.setPage(curPage);

      firePageChanged(curPage, getPath());
   }

   public void visitPage(WizardPage page){
      int idx = _path.indexOf(page);
      
      if (-1 == idx){
         // new page
         if (0 != _path.size()){
            // get the settings from the page that is going away:
            WizardPage lastPage = _path.get(_path.size()-1);
            getSettings().newPage(lastPage.getId());
            lastPage.updateSettings(getSettings());
         }
         
         getPath().add(page);
      } else {
         // page is in the path at idx.
         
         // first, roll back the settings and trim the path:
         for (int i=_path.size()-1; i > idx; i--){
            getSettings().rollBack();
            _path.remove(i);
         }
      }
      
      if (_path.size() > 1){
         setPrevEnabled(true);
      }

      setNextEnabled(true);
      page.rendering(_path, getSettings());
      _template.setPage(page);
      firePageChanged(page, _path);
   }
   
   /**
    * @param nextPage
    * @param path
    */
   private void firePageChanged(WizardPage curPage, List<WizardPage> path) {
      for (WizardListener l : _listeners) {
         l.onPageChanged(curPage, getPath());
      }
   }

   /**
    * 
    */
   public void finish() {
      
      
      for (WizardListener l : _listeners) {
         l.onFinished(getPath(), getSettings());
      }
   }
   
   
   public void cancel() {
     
      
      for (WizardListener l : _listeners) {
         l.onCanceled(getPath(), getSettings());
      }
   }
   
   
   public void addWizardListener(WizardListener listener){
     // ExceptionUtilities.checkNull(listener, "listener");
      if (!_listeners.contains(listener)){
         _listeners.add(listener);
         WizardPage curPage = _path.get(_path.size()-1);
         listener.onPageChanged(curPage, getPath());
      }
   }
   
   
   public void removeWizardListener(WizardListener listener){
    //  ExceptionUtilities.checkNull(listener, "listener");
      _listeners.remove(listener);
   }
   
   
   public WizardSettings getSettings(){
      return _settings;
   }
   
   
   public List<WizardPage> getPath() {
      return _path;
   }

   /* (non-Javadoc)
    * @see org.ciscavate.cjwizard.WizardController#setNextEnabled(boolean)
    */
   public void setNextEnabled(boolean enabled) {
      _nextAction.setEnabled(enabled);
   }
   
   
   public void setPrevEnabled(boolean enabled) {
      _prevAction.setEnabled(enabled);
   }

   
   public void setFinishEnabled(boolean enabled) {
      _finishAction.setEnabled(enabled);
   }
}
