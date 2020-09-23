package test_code;

import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class CFP_R3 extends ViewableAtomic{
String msg1;
	
	boolean send=false;
	String article;
	boolean disp;
	String prix ;
	boolean testinf2=false;
	boolean testfail2=false;
	public  CFP_R3() {
		
        // TODO Auto-generated constructor stub
        super("CFP_R3");
       // addOutport("1cfpr_out");
       addOutport("3cfpr1_out");
        addInport("3cfpr1_in");
       // addInport("1cfpr2_in");
       System.out.println(" cfpR3 intiator constructor");
       
        }	

public void initialize(){

           	holdIn("1", 5);
               // reponsei = false;
               //System.out.println(" intiator intial ");
           	
             }
public void deltint(){
if (phaseIs("2")&&(send==true)) {
		
		holdIn("3", 10);
	}
if (phaseIs("4")&&testfail2==true) {
	holdIn("6", 10);
	
}
if (phaseIs("4")&&testinf2==true) {
	holdIn("5", 10);
	
}
}




public void deltext(double e , message msg) {
	
	System.out.println(" externe R1");
	Continue(e);
	
if( (messageOnPort(msg,"3cfpr1_in", 0))	){
	if (phaseIs("1")) {
	holdIn("2", 12);
	msg1 =String.valueOf(msg.getValOnPort("3cfpr1_in", 0));
	disponible(msg1);

	}
	if(phaseIs("3")) {
		msg1 =String.valueOf(msg.getValOnPort("3cfpr1_in", 0));
		if (msg1=="accept") {
			holdIn("4", 10);
		}
		else if(msg1=="reject") {
			holdIn("5", 10);
		}
		
	}
}
if (phaseIs("4")) {
	msg1 =String.valueOf(msg.getValOnPort("3cfpr1_in", 0));
	if (msg1=="cancel") {
		holdIn("5", 10);
	}}	
}
public message out() { 
	message m = new message();
	if (phaseIs("2")&&(disp==true)&&(send==false)){
		 m.add(makeContent("3cfpr1_out", new entity(prix)));
		send=true;}
		if (phaseIs("4")&&testinf2==false&&testfail2==false) {
			double x = Math.random()*10+1;
			if(x>2&&x<=10) {
				m.add(makeContent("3cfpr1_out",new entity("informe")));
				testinf2=true;
			}
			else if (x<=2&&x>10) {
				m.add(makeContent("3cfpr1_out",new entity("faillure")));
			testfail2=true;}
		
		
	}
	return m;
}
public boolean disponible (String article ) {
	
	if( (article.equals("pc"))||(article.equals("roter"))||(article.equals("smartphone"))||(article.equals("headphone"))) {
		
		 if (article.equals("headphone")) {
			prix="90";
		 }
		 else if (article.equals("pc")) {
			prix="1300";
			
		}
		else if (article.equals("roter")) {
			prix="500";
		}
		else if (article.equals("smartphone")) {
			prix="995";
		}
		disp=true;
		System.out.println(" article diponible chez vendeur 3");
		
	}
	else if ((!article.equals("pc"))&&(!article.equals("voiture"))&&(!article.equals("smartphone"))){
		disp=false;
		System.out.println("article indiponible chez vendeur 3");
	}
	return disp;
}
}
