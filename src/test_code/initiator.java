package test_code;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class initiator extends ViewableAtomic {
	String msg_sysi;
	boolean reponsei;

	public initiator() {
		super("initiator");

		addOutport("porti_out");
		addInport("porti_in");
		System.out.println(" intiator constructor");
	

		
		
		
		
	}	
	
	
	
	
	
	

	public void initialize() {
		holdIn("1", 10);
		reponsei = false;
		System.out.println(" intiator intial ");
	}

	

	public void deltext(double e ,message s) {
		Continue(e);
		System.out.println(" intiator externel fcn ");

	
		if (messageOnPort(s, "porti_in", 0)) {
			
			System.out.println(" intiator externel first if");
			msg_sysi = String.valueOf(s.getValOnPort("porti_in", 0));
			

			if ((msg_sysi.equals("inform")))

			{
				holdIn("3", 10);
				
				
              
			}
			else if ((msg_sysi.equals("refuse"))||(msg_sysi.equals("faillure"))) {
				holdIn("4", 10);
			}
		}

	}
public void deltint() {
		

		System.out.println("intiator internel  " + reponsei);

		if((phaseIs("1"))&& (reponsei == true)) {
			holdIn("2",10);
			System.out.println(" intiator internel  first if");
		}
	}

	public message out() {
		message m = new message();
		System.out.println("out intiator ");
		if (phaseIs("1")) {

			System.out.println("Output = " + m);
			m.add(makeContent("porti_out", new entity("query")));
			System.out.println("out intiator - if   ");
            reponsei= true;
		}
		return m;
	}

}