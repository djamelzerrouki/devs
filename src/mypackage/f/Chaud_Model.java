package mypackage.f;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class Chaud_Model extends ViewableAtomic
{
  int temp = 29;
  String msg_sys;
  boolean reponse;
  
  public Chaud_Model()
  {
    super("Chaudiere");
    
    addInport("In_Sys");
    addOutport("Out_Sys");
    
    if (temp < 30)
      holdIn("off", 0.0D); else {
      holdIn("temp", 0.0D);
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
      else if (msg_sys.equals("aug_temp"))
      {
        if (temp < 30)
        {
          temp = 30;
          holdIn("temp", 0.0D);
        }
        else if (temp < 75)
        {
          temp += 1;
          holdIn("temp", 0.0D);
        }
        
      }
      else if (msg_sys.equals("dim_temp"))
      {
        if (temp > 30)
        {
          temp -= 1;
          holdIn("temp", 0.0D);
        }
        else {
          holdIn("Off", e);
        }
      } else if (msg_sys.equals("allum_chaud"))
      {
        temp = 30;
        holdIn("temp", 0.0D);
      }
    }
  }
  
  public message out()
  {
    message m = new message();
    
    if (reponse)
    {
      if (temp < 30)
      {
        content cont = makeContent("Out_Sys", new entity("Chaud_Off"));
        m.add(cont);
        reponse = false;
      }
      else
      {
        content cont = makeContent("Out_Sys", new entity("temp"));
        m.add(cont);
        reponse = false;
      }
    }
    

    return m;
  }
}
