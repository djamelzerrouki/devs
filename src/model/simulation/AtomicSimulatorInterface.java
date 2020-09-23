/*  
 * Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */ 

package model.simulation;

import java.util.*;

import GenCol.*;


import model.modeling.*;


public interface AtomicSimulatorInterface extends coreSimulatorInterface{
public void initialize();
public void initialize(Double d);
public void initialize(double time);
public double nextTN();
double getTL();
double getTN();
public void wrapDeltfunc(double t);
public void wrapDeltfunc(double t,MessageInterface x);
public void showModelState();
public void computeInputOutput(double t);
public void showOutput();
public MessageInterface makeMessage();
public void simInject(double e,MessageInterface m);
public void simInject(double e,String portName,entity value);
}