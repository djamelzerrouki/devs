package mypackage.f;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class Vent_Model extends ViewableAtomic
{
  int vitesse = 0;
  String msg_sys;
  boolean reponse;
  
  public Vent_Model()
  {
    super("Ventilateur");
    
    addInport("In_Sys");
    addOutport("Out_Sys");
    
    if (vitesse <= 0) {
      holdIn("off", 0.0D);
    } else {
      holdIn("vitesse", 0.0D);
    }
  }
  


  public void deltint() {}
  

  public void deltext(double e, message m)
  {
    if (messageOnPort(m, "In_Sys", 0))
    {
      msg_sys = String.valueOf(m.getValOnPort("In_Sys", 0));
      
      if (msg_sys.equals("tester"))
      {
        reponse = true;
      }
      else if (msg_sys.equals("aug_vitesse"))
      {
        if (vitesse < 3) {
          holdIn("vitesse + 1", 0.0D);
        }
      }
      else if (msg_sys.equals("dim_vitesse"))
      {
        if (vitesse > 1) {
          holdIn("vitesse - 1", 0.0D);
        } else {
          holdIn("off", 0.0D);
        }
      }
    }
  }
  
  public message out()
  {
    message m = new message();
    
    if (reponse)
    {
      content cont = makeContent("Out_Sys", new entity("vitesse"));
      m.add(cont);
      reponse = false;
    }
    
    return m;
  }
}
