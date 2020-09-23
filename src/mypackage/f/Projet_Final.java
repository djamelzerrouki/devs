package mypackage.f;

import java.awt.Point;
import view.modeling.ViewableDigraph;

public class Projet_Final extends ViewableDigraph
{
  public Projet_Final()
  {
    super("Projet Final");
    

    PMV_Model pmv = new PMV_Model();
    Capteur_Model capteur = new Capteur_Model();
    SysCont_Model system = new SysCont_Model();
    Chaud_Model chaudiere = new Chaud_Model();
    Vent_Model ventilateur = new Vent_Model();
    
    add(pmv);
    add(capteur);
    add(system);
    add(chaudiere);
    add(ventilateur);
    
    addCoupling(pmv, "PMV", system, "In_PMV");
    addCoupling(capteur, "situation", system, "In_Capt");
    addCoupling(chaudiere, "Out_Sys", system, "In_Chaud");
    addCoupling(ventilateur, "Out_Sys", system, "In_Vent");
    addCoupling(system, "déclencheur_PMV", pmv, "Sys");
    addCoupling(system, "Out_Chaud", chaudiere, "In_Sys");
    addCoupling(system, "Out_Vent", ventilateur, "In_Sys");
    
    capteur.setPreferredLocation(new Point(0, 10));
    pmv.setPreferredLocation(new Point(0, 210));
    system.setPreferredLocation(new Point(150, 110));
    chaudiere.setPreferredLocation(new Point(370, 10));
    ventilateur.setPreferredLocation(new Point(370, 250));
  }
}
