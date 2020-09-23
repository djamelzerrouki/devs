package test_code;

import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class request_r extends ViewableAtomic {
	String msg_sysr;
	boolean requestr_agree =false;  
	boolean requestr_refuse=false ;
	boolean phase8f=false;
	boolean phase8i=false;
	String serv ;
	String decide ;
	public   String decision;
	
	
	public request_r() {
	                                        super("request reponder");
		                                        addInport("port_inr");
		                                        addOutport("port_outr");
		                                        System.out.println(" request reponder constructor");
		                                        decid();
	                                            }
	                        
	                        public void initialize() {
	                        	
	                    		holdIn("6", 5);
	                    		
	                    		
	                    	}
	                        
                            public void deltint() {
                            	
	                        	System.out.println("internal fonction reponder");
	                        	if ( (phaseIs("7"))&&(requestr_agree==true)  ) {
	                    			
                                    holdIn("8", 10);
                                    }
	                        	else if ((phaseIs("7"))&&(requestr_refuse==true)) {
	                        		holdIn("10", INFINITY);
	                        	}
	                        	else if ((phaseIs("8"))&&(phase8i==true)) {
	                        		holdIn("9", INFINITY);
	                        	}
	                        	else if ((phaseIs("8"))&&(phase8f==true)) {
	                        		holdIn("10", INFINITY);
	                        	}
	                        }
	                        public void deltext (double e , message m) { 
	                        	Continue(e);
	                        	System.out.println("externe fonction repender");
	                        	if (messageOnPort(m,"port_inr", 0)) {
	                        		msg_sysr = String.valueOf(m.getValOnPort("port_inr", 0));
	                        		
	                        		// if (msg_sysr.equals("request")) {
	        				               
	        				                holdIn("7", 5);
	        			                                //              }
	                        	}
	                        }
	                        
	                       
	                        public message out() {
	         		           message m = new message();
	         		           
	         			       if( (phaseIs("7"))&&(requestr_agree==false)&&(requestr_refuse==false)) {
                                             // ici on vas ajouter la condition des on peux choixis si on va accepte le requst ou non
	         			    	              // on vas cret un tableau qui contiens les servie que le repondeur pue les faires
	         				                     
	         				                     if (services(msg_sysr)=="agree") {
	         			                       	 m.add(makeContent("port_outr", new entity("accepte")));	
	         			                        System.out.println("Output message is  = " + m + services(msg_sysr));
	         			                        requestr_agree=true;
	         	                                  
	         		                         	 }
	         			       else if (services(msg_sysr)=="refuse") {
	         			    	  m.add(makeContent("port_outr", new entity("refuse")));
	         			    	  System.out.println("Output = " + m + services(msg_sysr));
	         			    	   requestr_refuse=true;
	         			       }
	         		                 
	         			      }
	         			       if (phaseIs("8")) {
	         			    	 
	         			    	   
	         			    	   if (decision=="faillure") {
	         			    		  m.add(makeContent("port_outr", new entity("faillure")));
	         			    		  phase8f=true;
	         			    	   }
	         			    	   else if (decision=="informe") {
	         			    		  m.add(makeContent("port_outr", new entity("informe")));
	         			    		 phase8i=true;
	         			    	   }  
	         			    	   
	         			       }
	         			       return m;
	         	                     }
	                        public String services (String service ) {
	                        	
	                        	if( (service.equals("select"))||(service.equals("update"))||(service.equals("insert"))) {
	                        		serv="agree";
	                        		System.out.println(" cretaire d acceptation est agree");
	                        		
	                        	}
	                        	else if ((!service.equals("slect"))&&(!service.equals("update"))&&(!service.equals("insert"))){
	                        		serv="refuse";
	                        		System.out.println(" cretaire d acceptation est refuse");
	                        	}
	                        	return serv;
	                        }
	                        public void decid () {
	                        	String decide ;
	                        	double randomDouble = Math.random();
	                        	randomDouble = randomDouble * 100 + 1;
	                        	int randomInt = (int) randomDouble;
	                        	
	                        	if (63<randomInt && randomInt<71) {
	                        		decide="faillure";
	                        	}
	                        	else {
	                        		decide="informe";
	                        	}
	                        	
	                        	 System.out.println(decide + randomInt);
	                        	 decision=decide;
	                      //  return decide;
	                       
	                        }
	                       
}
