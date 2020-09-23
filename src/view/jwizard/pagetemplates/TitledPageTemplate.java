
package view.jwizard.pagetemplates;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import view.jwizard.WizardPage;


public class TitledPageTemplate extends PageTemplate {

   /**
    * Generated version uid.
    */
   private static final long serialVersionUID = -2282167921679786408L;

   /**
    * The label to display the current page description.
    */
   private final JLabel _title = new JLabel();
   
   /**
    * We'll use the DefaultPageTemplate to take advantage of it's page switching
    * logic, since it doesn't include any additional decorations it is ideal
    * for nesting.
    */
   private final PageTemplate _innerTemplate = new DefaultPageTemplate();
   
   /**
    * Constructor.  Sets up the inner template and the title label.
    */
   public TitledPageTemplate(){
      // Create a simple empty border to impose a bit of space around the title:
      Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
      // use an Matte border to add an underline:
      Border innerBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
      
      // combine the two borders to get the desired look:
      _title.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
      
      this.setLayout(new BorderLayout());
      this.add(_title, BorderLayout.NORTH);
      this.add(_innerTemplate, BorderLayout.CENTER);
   }
   
  
   @Override
   public void setPage(final WizardPage page) {
      // Since we're using a nested DefaultPageTemplate, we just need to
      // delegate to that object, and then do whatever is necessary to update
      // the additional widgets introduced by this PageTemplate.
      
      
      SwingUtilities.invokeLater(new Runnable(){
         @Override
         public void run() {
            // delegate to the DefaultPageTemplate:
            _innerTemplate.setPage(page);
            
            // Set the new title text:
            _title.setText(page.getDescription());
         }
      });
   }

}
