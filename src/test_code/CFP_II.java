package test_code;

import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class CFP_II extends ViewableAtomic{
	boolean inputtest1;
	String msginput1;
	public  CFP_II() {
		
        // TODO Auto-generated constructor stub
        super("CFP_II");
        addOutport("cfpII_out1");
        addOutport("cfpII_out2");
        addOutport("cfpII_out3");
        addOutport("cfpII_out4");
        
        addInport("cfpII_in");
        addInport("cfpII_in1");
        addInport("cfpII_in2");
        addInport("cfpII_in3");
        addInport("cfpII_in4");
        System.out.println(" cfp intiator constructor");
        inputtest1=false;
       
        }	

public void initialize(){

           	holdIn("1", 5);
               // reponsei = false;
               //System.out.println(" intiator intial ");
           	
             }
public void deltint(){}




public void deltext(double e , message msg) {
	Continue(e);
	if (messageOnPort(msg, "cfpII_in", 0)) {
		
		 msginput1 = String.valueOf(msg.getValOnPort("cfpII_in", 0));   	
		inputtest1=true;
		
	}
	
	
}
public message out() {
	message m = new message();
	
	if (phaseIs("1")&&(inputtest1==true)) {
		 m.add(makeContent("cfpII_out1", new entity(msginput1)));
		 m.add(makeContent("cfpII_out2", new entity(msginput1)));
		 m.add(makeContent("cfpII_out3", new entity(msginput1)));
		 m.add(makeContent("cfpII_out4", new entity(msginput1)));
		inputtest1=false;
	} 

	return m;
}
	
	
}

