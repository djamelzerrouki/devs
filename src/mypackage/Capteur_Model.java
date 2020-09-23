package mypackage;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class Capteur_Model extends ViewableAtomic
{
  boolean bool = true;
  
  public Capteur_Model()
  {
    super("détecteur de mouvement");
    
    addOutport("situation");
    addInport("In");
    
    holdIn("pre", 0.0D);
  }
  


  public void deltint() {}
  


  public void deltexet(double e, message m) {}
  


  public message out()
  {
    message m = new message();
    if ((phaseIs("pre")) && (bool))
    {
      content cont = makeContent("situation", new entity("1"));
      m.add(cont);
      bool = false;
    }
    
    return m;
  }
}
