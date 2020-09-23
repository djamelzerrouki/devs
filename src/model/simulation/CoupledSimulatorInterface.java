/* 
 * Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */ 




package model.simulation;


import java.util.*;

import GenCol.*;


import model.modeling.*;


public interface CoupledSimulatorInterface extends
            coreSimulatorInterface
            ,ActivityProtocolInterface
            ,CouplingProtocolInterface
            ,HierParent
            {}


//public
interface ActivityProtocolInterface {
public void startActivity(ActivityInterface a);
public void returnResultFromActivity(EntityInterface  result);
}


