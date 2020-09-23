/*     
 *    
 *  Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */
package facade.simulation;

/**
 * FCoupled Simulator : extended from SimViewCoordinator
 * @modified Sungung Kim 05/29/2008
 */

import model.modeling.content;
import model.modeling.coupledDevs;
import model.modeling.devs;
import model.modeling.digraph;
import model.simulation.realTime.TunableCoordinator.Listener;
import controller.Governor;
import controller.Stopwatch;
import controller.simulation.*;
import util.Logging;
import util.Util;
import view.View;
import view.modeling.ViewableDigraph;
import view.simView.*;
import facade.modeling.FCoupledModel;
import facade.modeling.FModel;
import facade.simulation.hooks.SimulatorHookListener;

public class FCoupledSimulator implements FSimulator
{
    private FCoupledSimulator.FRTCentralCoordX simulator;
    private FModel rootModel;
    private short currentState;
    private short modelType;
    private SimulatorHookListener Flistener;
    
    public FCoupledSimulator(ViewableDigraph model, Listener listener, short modelType)
    {
        simulator = new FCoupledSimulator.FRTCentralCoordX(model, listener);
        rootModel = new FCoupledModel(model,this);
        this.modelType = modelType;
    }
      
    public void step()                          {simulator.simulate(1);}
    public void step(int n)                     {simulator.simulate(n);}
    public void run()                           {simulator.simulate(Integer.MAX_VALUE);}
    public void requestPause()                    {simulator.requestPause();}  
    public void reset()                         {simulator.reset();}
    public void setRTMultiplier(double factor)  {simulator.setRTMultiplier(factor);}
    public double getRTMultiplier()             {return simulator.getRTMultiplier();}
    public double getTimeOfLastEvent()          {return simulator.getTL();}
    public double getTimeOfNextEvent()          {return simulator.getTN();}
    public short  getCurrentState()             {return currentState;}
    public FModel getRootModel()                {return rootModel;}
    
    public void setSimulatorHookListener(SimulatorHookListener Flistener) 
    {
            this.Flistener = Flistener;
    }
    
    public void setCurrentState(short newState)
    {
        currentState = newState;
        if (Flistener != null)
            Flistener.simulatorStateChangeHook();
    }
    
    //----------------------------------------------------------------------
    public class FRTCentralCoordX extends SimViewCoordinator
    {
        /**
        * How many milliseconds this coordinator should wait to simulate one
        * unit of simulation time.
        */
        private int rtMultiplier; 
               
        public FRTCentralCoordX(ViewableDigraph coupledModel, Listener listener)
        {
            super(coupledModel, listener);
            rtMultiplier = 1000;
            setCurrentState(STATE_INITIAL);
            reset();
        }
        
        //----------------------------------------------------------------------
        //		THREAD LOGIC
        //----------------------------------------------------------------------
        private boolean stopRequested;
        private boolean suspendRequested;

        private void requestStop()          {stopRequested = true;}
        private void requestSuspend()       {suspendRequested = true;}
        
        private void requestResume()
        {
            suspendRequested = false;
            try
            {
                notifyAll();
            }
            catch(Exception e){}
        }
        
        private synchronized void checkSuspended()
        {
            while(suspendRequested)
            {
                try {wait();}
                catch (InterruptedException e){}
            } 
        }
        
        private synchronized void sleep(long time)
        {
            try
            {
                wait(time);
            }
            catch (InterruptedException e){}
        }
        //----------------------------------------------------------------------
        
        public double getTL()           {return tL;}
        public double getTN()           {return tN;}
        
        /**
         * Sets how many seconds this coordinator should wait to simulate one
         * unit of simulation time.
         *
         * @param   realTimeFactor      How many seconds to wait per one unit
         *                              of simulation time.
         */
        public synchronized void setRTMultiplier (double realTimeFactor)
        {
            // convert the given time factor to milliseconds
            rtMultiplier = (int)Math.floor(1000 * realTimeFactor);            
           
            //If the current thread is not suspended and is not stopped, interrupt it
            //so that the multiplier can take effect right away.
            if (currentState == STATE_SIMULATING)
                notifyAll();
        }

        public double getRTMultiplier()
        {
            return ((double)rtMultiplier)/1000;
        }
        
        /**
         * Initializes this coordinator to starting state.
         */
        public synchronized void reset()
        {
            if (currentState == STATE_INITIAL ||  currentState == STATE_PAUSE ||
                currentState == STATE_END)
            {
                simulators.tellAll("initialize");
                tL = 0;
                stopRequested    = false;
                suspendRequested = false;
                setCurrentState(STATE_INITIAL);
            }
            else
            {
                throw new FIllegalSimulatorStateException(
                 formatErrorMsg("Can only [Reset] from state: {Initial} | {Pause} | {End}."));
            }
        }

        /**
         * Tells this coordinator to execute the specified number of iterations
         * of the simulation.
         *
         * @param   numIterations       The number of iterations to execute.
         */
        public synchronized void simulate(int numIterations)
        {
            if (numIterations > 0)
            {
                if (currentState == STATE_INITIAL || currentState == STATE_PAUSE)
                {
                    setCurrentState(STATE_SIMULATING);
                    numIter = numIterations;
                    if (!myThread.isAlive())
                    {
                        myThread = null; //Ensure garbage collection
                        myThread = new Thread(this); //Create new Thread 
                        myThread.start(); //Start the thread
                    }
                    else
                    {   
                        requestResume();
                    }
                }
                else
                    throw new FIllegalSimulatorStateException(
                     formatErrorMsg("Can only [Simulate] from state: {Initial} | {Pause}."));
            }
            else
                throw new FIllegalSimulatorParameterException(
                 "Cannot [Simulate], number of iterations must be > 0: " + numIterations+".");
        }

        public synchronized void requestPause()
        {
            if (currentState == STATE_SIMULATING || currentState == STATE_PAUSE)
            {
                numIter = 0;
            }
            else
                throw new FIllegalSimulatorStateException(
                 formatErrorMsg("Can only [Pause] from state: {Simulating} | {Pause}."));
        }
        
        /**
         * Executes the actual iterations of the simulation. This method
         * should not be called from any method outside this class.
         */        
        public void run()
        {
        	// keep doing this
        	Stopwatch.start();
        	
            while (!stopRequested) {
            	checkSuspended(); 
                // for each iteration this coordinator was told to do,
                // stopping early if there are no more next events to process
                int i = 1;
                tN = nextTN();
                while (tN < INFINITY && i <= numIter) {
                    
                    // detm how long to sleep before performing this iteration,
                    // such that it will be performed at the actual time of next
                    // event
                    timeToSleep = (long)((tN - tL) * rtMultiplier);
                    tL = tN;
                    // if the simulation is restarted, there may be a brief period
                    // of time where tL is greater than tN (which is now zero), so
                    // make sure we don't have a negative time to sleep
                    timeToSleep = Math.max(timeToSleep, 0);

                    // sleep for that amount
                    Util.sleep(myThread, timeToSleep);
                    
                 		//sync graphs
                    Governor.syncGraphs();

                    // perform the actual simulation iteration
                    computeInputOutput(tN);
                    System.out.println("computIO");
                    
                    /**
                     * this method used for the tracking environment 
                     * cause the slowness of simulation engine
                     * so disable when we select only the simview.
                     */
                    if (Flistener != null)
                	   Flistener.postComputeInputOutputHook();
                                        
                    wrapDeltFunc(tN);
                    //tL = tN;
                    tN = nextTN();
                    i++; 
                    
                    System.out.println("done computeIO");
                }

               
                // the iterations have now been completed
                System.out.println("Terminated Normally before ITERATION " + i +
                    " ,time: "+ getTimeOfLastEvent());
                shouldIterate = false;                
                          
                if (!(tN < INFINITY))
                {
                	if(modelType == FModel.ATOMIC){
                		requestSuspend();
                        setCurrentState(STATE_PAUSE);
                	}
                	else{
                		requestStop();
                		setCurrentState(STATE_END);
                	}
                }
                else
                {
                    requestSuspend();
                    setCurrentState(STATE_PAUSE);
                }
            }
            //System.out.println("              S TIME: "+Stopwatch.lap()+" sec");
        }
        
             
        private String formatErrorMsg(String msg)
        {
            switch (currentState)
            {
                case STATE_INITIAL:
                    msg += " {Current State = Ready}";
                    break;
                case STATE_SIMULATING:
                    msg += " {Current State = Simulating}";
                    break;
                case STATE_PAUSE:
                    msg += " {Current State = Pause}";
                    break;
                case STATE_END:
                    msg += " {Current State = End}";
                    break;
                default:
                    msg += " {Current State = Unknown}";
                    break;
            }
            return msg;
        }
    }
}