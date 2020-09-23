/*
 *  Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */ 


package model.modeling;


import java.util.*;

import GenCol.*;


import model.simulation.*;

interface basicDevs {

public void deltext(double e,MessageInterface x);
public void deltcon(double e,MessageInterface x);
public void deltint();
public MessageInterface Out();  //to allow out:message for usual devs
public double ta();
public void initialize();
public void  showState();
}

public interface IOBasicDevs extends basicDevs,IODevs{
public ActivityInterface getActivity();
public void setSimulator(CoupledSimulatorInterface sim);
/* removed these from here and digraph */
//public  void setSigma(double sigma);
//public double getSigma();

}
