package test_code;

import model.modeling.message;
import view.modeling.ViewableAtomic;
import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class request_i extends ViewableAtomic{
	String msg_sysi;
	String msginput;
	boolean requesti;
	public  request_i() {
		
		                 // TODO Auto-generated constructor stub
		                 super("request initiator");
		                 addOutport("port_outi");
	                     addInport("port_ini");
	                     addInport("port_ini1");
		                 System.out.println(" request intiator constructor");
		                 requesti = false;
	                     }	
	   
    public void initialize(){
		
	                        	holdIn("1", 5);
		                        // reponsei = false;
		                        //System.out.println(" intiator intial ");
	                        	
	                          }
	public void deltint(){
		System.out.println("internale fonction initiator");
		if ( (phaseIs("1"))&&(requesti==true)  ) {
			
			                                     holdIn("2", 10);
		                                         }
		
	                     }
	
	public void deltext(double e ,message k) {
		                Continue(e);           
		                if (messageOnPort(k, "port_ini1", 0)) {
				    		   msginput = String.valueOf(k.getValOnPort("port_ini1", 0));   		   
				    		   
				    		   
				    	   }
		                System.out.println("externale fonction initiator");
		               
		                if (messageOnPort(k, "port_ini", 0)) {
		                	
		        			msg_sysi = String.valueOf(k.getValOnPort("port_ini", 0));
		        			
		        			               if (msg_sysi.equals("accepte")) {
		        				               
		        				                holdIn("3", 5);
		        			                                              }
		        			                else {
											if ((msg_sysi.equals("refuse")) ) {
												holdIn("4", INFINITY);
											}
											else if(msg_sysi.equals("informe")&& phaseIs("3") ) {
												
												                                  holdIn("5",INFINITY );
											                                      }
											else if ((msg_sysi.equals("faillure"))&&(phaseIs("3"))) {
												
												holdIn("4",INFINITY);
											}
										          }
		                                                      }
	                                           }
	
	
	public message out() {
		           message k = new message();
		          
			       if (phaseIs("1")) {
			    	   
			    	 
			    	   
			    	   
			    	
			    		   
			    		   
			    		   
				                      System.out.println("Output = " + k);
			                       	  k.add(makeContent("port_outi", new entity(msginput)));				
	                                  requesti= true;
		                         	 } 
		                 return k;
		                 
	                     }

}
