package test_code;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class repondeur extends ViewableAtomic {
   
	String msg_sys;
	boolean reponse;
    boolean  envoisinf=false;
    boolean envoisref=false;
    boolean envoisfail=false;
    boolean envoimssg=false;
	public repondeur() {
		super("repondeur");

		addInport("port_in");
		addOutport("port_out");

	
	
	}

	public void initialize() {
	
			
		
		holdIn("5", 10);
		reponse = false;
		
		
		System.out.println(" intiator intial ");
		
	}
	
	public void deltint() { 
if ((phaseIs("6") & (envoisref=true))){
	
	holdIn("7", 10);
			
	
	envoisref=false;
	
	
}
if(phaseIs("6")&&(envoisinf==true)) {
	holdIn("7", 10); 
	
	envoisinf=false;
}
		if (phaseIs("6")&&((envoisfail=true))){
			
			
			holdIn("8", 10);
			envoisfail=false;
			
		}
		
		
		
	}

	public void deltext(double e, message m) {
		Continue(e);
		System.out.println("exterenel fonction");
		if (messageOnPort(m, "port_in", 0)) {
			msg_sys = String.valueOf(m.getValOnPort("port_in", 0));

			if (msg_sys.equals("query")) {
				reponse = true;
				holdIn("6", 10);
				System.out.println("etat et reponse repondeur" + phase + "   " + reponse);
			}
		}
		/*
		 * else if (msg_sys.equals("aug_temp")) { if (temp < 30) { temp = 30;
		 * holdIn(temp, 0.0D); } else if (temp < 75) { temp += 1; holdIn(temp, 0.0D); }
		 * 
		 * } else if (msg_sys.equals("dim_temp")) { if (temp > 30) { temp -= 1;
		 * holdIn(temp, 0.0D); } else { holdIn("Off", e); } } else if
		 * (msg_sys.equals("allum_chaud")) { temp = 30; holdIn(temp, 0.0D); } }
		 */
	}
	
	 public void deltcon(double e, message x) 
	   {
	      deltint();
	      deltext(0, x);
	   }
	public message out() {
		message m = new message();
		
		System.out.println("out mssg repondeur   reponse eest :" + reponse);
		if ((phaseIs("6"))&&(reponse = true)&&(envoimssg==false)) {
			double randomDouble = Math.random()*10+1;
			if (randomDouble<=4 && envoimssg==false) {
			content cont = makeContent("port_out", new entity("inform"));
			m.add(cont);
           envoisinf=true;
           envoimssg=true;
          
			reponse = false;}
			if (randomDouble>4 && randomDouble<7 && envoimssg==false) {
				content cont = makeContent("port_out", new entity("refuse"));
				m.add(cont);
	           envoisref=true;
	          
				reponse = false;
				envoimssg=true;
			}
			if (randomDouble>=7 && randomDouble<=10 && envoimssg== false) {
				content cont = makeContent("port_out", new entity("faillure"));
				m.add(cont);
	           envoisfail=true;
	          
				reponse = false;
				envoimssg=true;
			}
			
		} 
		

		return m;
		
		
	
	

		
		
		
		
	}
}
