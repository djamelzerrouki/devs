package test_code;

import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class CFP_R2 extends ViewableAtomic {
	String article;
	boolean disp;
	String prix;
	String msg1;
	boolean send=false;
	boolean testinf1=false;
	boolean testfail1=false;
public  CFP_R2() {
		
        // TODO Auto-generated constructor stub
        super("CFP_R2");
       // addOutport("2cfpr_out");
        addOutport("2cfpr1_out");
        addInport("2cfpr1_in");
       // addInport("2cfpr2_in");
        System.out.println(" cfp intiator constructor");
       
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
if (phaseIs("4")&&testfail1==true) {
	holdIn("6", 10);
	
}
if (phaseIs("4")&&testinf1==true) {
	holdIn("5", 10);
	
}
}





public void deltext(double e , message msg) {
	System.out.println(" externe R2");
	Continue(e);
	
if( (messageOnPort(msg,"2cfpr1_in", 0))	){
	if (phaseIs("1")) {
	holdIn("2", 11);
	msg1 =String.valueOf(msg.getValOnPort("2cfpr1_in", 0));
	disponible(msg1);
}}
if(phaseIs("3")) {
	msg1 =String.valueOf(msg.getValOnPort("2cfpr1_in", 0));
	if (msg1=="accept") {
		holdIn("4", 10);
	}
	else if(msg1=="reject") {
		holdIn("5", 10);
	}
}
if (phaseIs("4")) {
	msg1 =String.valueOf(msg.getValOnPort("2cfpr1_in", 0));
	if (msg1=="cancel") {
		holdIn("5", 10);
	}}
}
public message out() {
	message m = new message();
	if (phaseIs("2")&&(disp==true)&&(send==false)){
		 m.add(makeContent("2cfpr1_out", new entity(prix)));
		 send=true;}
		 if (phaseIs("4")&&testinf1==false&&testfail1==false) {
				double x = Math.random()*10+1;
				
				if(x>2&&x<=10) {
					m.add(makeContent("2cfpr1_out",new entity("informe")));
					testinf1=true;
				}
				else if (x<=2&&x>10) {
					m.add(makeContent("2cfpr1_out",new entity("faillure")));
				testfail1=true;}
			}
	
	return m;
}
public boolean disponible (String article ) {
	
	if( (article.equals("pc"))||(article.equals("roter"))||(article.equals("smartphone"))||(article.equals("headphone"))) {
		
		 if (article.equals("headphone")) {
			prix="120";
			
		}
		 else if (article.equals("pc")) {
			prix="2500";
			
		}
		else if (article.equals("smartphone")) {;
			prix="500";
		}
		else if (article.equals("roter")) {
			prix="90";
		}
		disp=true;
		System.out.println(" article diponible chez vendeur 2");
		
	}
	else if ((!article.equals("pc"))&&(!article.equals("modem"))&&(!article.equals("adapter"))){
		disp=false;
		System.out.println("article indiponible chez vendeur 2");
	}
	return disp;
}
}
