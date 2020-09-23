package mypackage.f;

import GenCol.entity;
import model.modeling.message;

public class SysCont_Model extends view.modeling.ViewableAtomic
{
  String msg_capt;
  String msg_chaud;
  float msg_pmv;
  float msg_vent;
  float chaud_temp;
  
  public SysCont_Model()
  {
    super("Systeme de Contrôle");
    

    addInport("In_Capt");
    addInport("In_PMV");
    addInport("In_Chaud");
    addInport("In_Vent");
    

    addOutport("déclencheur_PMV");
    addOutport("Out_Chaud");
    addOutport("Out_Vent");
    
    holdIn("Inactif", 0.0D);
  }
  


  public void deltint() {}
  

  public void deltext(double e, message m)
  {
    if (messageOnPort(m, "In_Capt", 0))
    {
      msg_capt = String.valueOf(m.getValOnPort("In_Capt", 0));
      
      if (msg_capt.equals("1")) {
        holdIn("Actif", 0.0D);
      } else {
        holdIn("Inactif", INFINITY);
      }
    }
    if (messageOnPort(m, "In_PMV", 0))
    {
      msg_pmv = Float.parseFloat(String.valueOf(m.getValOnPort("In_PMV", 0)));
      
      if ((msg_pmv < 3.0F) && (msg_pmv > -3.0F))
      {
        if (msg_pmv == 0.0F) {
          holdIn("Stable", INFINITY);
        } else if (msg_pmv < 0.0F) {
          holdIn("test_vent", 0.0D);
        } else {
          holdIn("test_chaud", 0.0D);
        }
      } else {
        holdIn("Inactif", INFINITY);
      }
    }
    if (messageOnPort(m, "In_Vent", 0))
    {
      msg_vent = Float.parseFloat(String.valueOf(m.getValOnPort("In_Vent", 0)));
      
      if (msg_vent > 0.0F)
      {
        if (msg_pmv < 0.0F) {
          holdIn("dim_vent", 0.0D);
        } else {
          holdIn("aug_vent", e);
        }
      } else if (msg_vent == 0.0F)
      {
        if (msg_pmv > 0.0F) {
          holdIn("aug_vent", 0.0D);
        } else {
          holdIn("test_chaud", 0.0D);
        }
      }
    }
    if (messageOnPort(m, "In_Chaud", 0))
    {
      msg_chaud = String.valueOf(m.getValOnPort("In_Chaud", 0));
      if (!msg_chaud.equals("Chaud_Off")) {
        chaud_temp = Float.parseFloat(msg_chaud);
      }
      if (chaud_temp >= 30.0F)
      {
        if (msg_pmv < 0.0F) {
          holdIn("aug_chaud", 0.0D);
        } else {
          holdIn("dim_chaud", 0.0D);
        }
      } else if (msg_chaud.equals("Chaud_Off"))
      {
        if (msg_pmv < 0.0F) {
          holdIn("allum_chaud", 0.0D);
        } else {
          holdIn("test_vent", 0.0D);
        }
      }
    }
  }
  
  public message out() {
    message m = new message();
    
    if ((phaseIs("Actif")) && (msg_capt.equals("1")))
    {
      model.modeling.content cont = makeContent("déclencheur_PMV", new entity("calculer"));
      m.add(cont);
      holdIn("Att", 0.0D);

    }
    else if (phaseIs("test_vent"))
    {
      model.modeling.content cont = makeContent("Out_Vent", new entity("tester"));
      m.add(cont);
      holdIn("Att", 0.0D);

    }
    else if (phaseIs("test_chaud"))
    {
      model.modeling.content cont = makeContent("Out_Chaud", new entity("tester"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    else if (phaseIs("aug_chaud"))
    {
      model.modeling.content cont = makeContent("Out_Chaud", new entity("aug_temp"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    else if (phaseIs("dim_chaud"))
    {
      model.modeling.content cont = makeContent("Out_Chaud", new entity("dim_temp"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    else if (phaseIs("allum_chaud"))
    {
      model.modeling.content cont = makeContent("Out_Chaud", new entity("allum_chaud"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    else if (phaseIs("aug_vent"))
    {
      model.modeling.content cont = makeContent("Out_Vent", new entity("aug_vitesse"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    else if (phaseIs("dim_vent"))
    {
      model.modeling.content cont = makeContent("Out_Vent", new entity("dim_vitesse"));
      m.add(cont);
      holdIn("Att", 0.0D);
    }
    
    return m;
  }
}
