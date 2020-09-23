package test_code;



import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class CFP_I extends ViewableAtomic {
	String msginput;
	boolean inputtest;
	boolean testphase9;
	boolean testphase10;
	boolean thereismsg ;
	boolean decid;
	boolean offre1=false;
	boolean offre2=false;
	boolean offre3=false;
	boolean offre4=false;
	boolean cancel=false;
	String msg1;
	String msg2;
	String msg3;
	String msg4;
	String port;
	String portref;
	String portref1;
	String portref2;
	String portref3;
	int prix;
	int prix1;
	int prix2;
	int prix3;
	int prix4;
	
	
	
	public  CFP_I() {
		
        // TODO Auto-generated constructor stub
        super("CFP_I");
        addOutport("cfpI_out1");
        addOutport("cfpI_out2");
        addOutport("cfpI_out3");
        addOutport("cfpI_out4");
        
        addInport("cfpI_in");
        addInport("cfpI_in1");
        addInport("cfpI_in2");
        addInport("cfpI_in3");
        addInport("cfpI_in4");
        System.out.println(" cfp intiator constructor");
        inputtest=false;
        testphase9=false;
        testphase10=false;
        thereismsg=false;
        decid=false;
        }	

public void initialize(){

           	holdIn("7", 5);
               // reponsei = false;
               //System.out.println(" intiator intial ");
           	
             }
public void deltint(){
	Continue(sigma);
	if( phaseIs("7")&&(inputtest=true)) {
		holdIn("8", 20);
		//System.out.println("ta is "+ta());
	}
		/*if (phaseIs("8")&&(thereismsg==false)) {
			
			holdIn("11", INFINITY);
		}*/
		 if (phaseIs("9")&&(decid==true)) {
			
			holdIn("10", 10);
			
		}
		
	

	
}




public void deltext(double e , message msg) {
	Continue(e);
	
	System.out.println(" externel fonction");
	if (messageOnPort(msg, "cfpI_in", 0)) {
		
		 msginput = String.valueOf(msg.getValOnPort("cfpI_in", 0));   	
	
		
		
	}
if (messageOnPort(msg,"cfpI_in1",0)) {
	System.out.println("message1");
	System.out.println(" externel fonction first if in the firdt port");
		msg1=String.valueOf(msg.getValOnPort("cfpI_in1", 0));
		if( (phaseIs("8")) ){
			offre1=true;
			System.out.println(msg1);
		prix1=Integer.parseInt(msg1);
		
		testphase9=true;
		
		}
		 if( (phaseIs("10")&&(testphase10==false)&&(msg1=="faillure")) ){
			holdIn("11", INFINITY);
			testphase10=true;
			
		}
		 if ((phaseIs("10")&&(testphase10==false)&&(msg1=="informe"))) {
			holdIn("12", INFINITY);
			testphase10=true;
		}}
	
if (messageOnPort(msg,"cfpI_in2",0)) {
	System.out.println(" externel fonction second if in the second  port");
	System.out.println("message2");
		msg2=String.valueOf(msg.getValOnPort("cfpI_in2", 0));
		if( (phaseIs("8")) ){
			offre2=true;
			System.out.println(msg2);
			prix2=Integer.parseInt(msg2);
		//holdIn("9", 30);
		testphase9=true;
		
		}
		 if( (phaseIs("10")&&(testphase10==false)&&(msg2=="faillure")) ){
			holdIn("11", INFINITY);
			testphase10=true;
			
		}
		 if ((phaseIs("10")&&(testphase10==false)&&(msg2=="informe"))) {
			holdIn("12", INFINITY);
			testphase10=true;
		}}
	
if (messageOnPort(msg,"cfpI_in3",0)) {
	System.out.println("message3");
	msg3=String.valueOf(msg.getValOnPort("cfpI_in3", 0));
	if( (phaseIs("8")) ){
		offre3=true;
		System.out.println(msg3);
		prix3=Integer.parseInt(msg3);
	//holdIn("9", 30);
	testphase9=true;
	
	}
	 if( phaseIs("10")&&testphase10==false&&msg3=="faillure" ){
		holdIn("11", INFINITY);
		testphase10=true;
		
	}
	if ((phaseIs("10")&&(testphase10==false)&&(msg3=="informe"))) {
		holdIn("12", INFINITY);
		testphase10=true;
	}
}
if (messageOnPort(msg,"cfpI_in4",0)) {
	System.out.println("message4");
	msg4=String.valueOf(msg.getValOnPort("cfpI_in4", 0));
	if( (phaseIs("8")) ){
		System.out.println(msg4);
		offre4=true;
		prix4=Integer.parseInt(msg4);
	//holdIn("9", 30);
	testphase9=true;
	
	}
	 if( (phaseIs("10")&&(testphase10==false)&&(msg4=="faillure")) ){
		holdIn("11", INFINITY);
		testphase10=true;
		
	}
	 if ((phaseIs("10")&&(testphase10==false)&&(msg4=="informe"))) {
		holdIn("12", INFINITY);
		testphase10=true;
	}
}
	if (phaseIs("8")&& offre1==true&&offre2==true&&offre3==true&&offre4==true) {
		
		holdIn("9", 7);
	}
}
public message out() {
	message m = new message();
	if (phaseIs("7")) {
		 m.add(makeContent("cfpI_out1", new entity(msginput)));
		 m.add(makeContent("cfpI_out2", new entity(msginput)));
		 m.add(makeContent("cfpI_out3", new entity(msginput)));
		 m.add(makeContent("cfpI_out4", new entity(msginput)));
		 
		inputtest=true;
	} 
	if(phaseIs("9")&&decid==false) {
		System.out.println(prix1+"/"+prix2+"/"+prix3+"/"+prix4);
		 m.add(makeContent(comparaison(prix1, prix2, prix3, prix4), new entity("accept")));
		 m.add(makeContent(portref1, new entity("reject")));
		 m.add(makeContent(portref2, new entity("reject")));
		 m.add(makeContent(portref3, new entity("reject")));
		// comparer les prix et envoyer accept ou refuse
		decid=true;
	}
	if (phaseIs("10")&&cancel==false) {
		double y = Math.random()*100+1;
		if (y==1) {
			 m.add(makeContent(port, new entity("cancel")));
			 cancel=true;
		}
		
	}
	return m;
}
	public String comparaison (int a,int b ,int c,int d) {
		if ((a<=b)&&(a<=c)&&(a<=d) ){
			port="cfpI_out1";
			
			portref1="cfpI_out2";
			portref2="cfpI_out3";
			portref3="cfpI_out4";
		}
		if((b<=a)&&(b<=c)&&(b<=d)){
			
			port="cfpI_out2";
			portref1="cfpI_out1";
			portref2="cfpI_out3";
			portref3="cfpI_out4";
		}
		if((c<=a)&&(c<=b)&&(c<=d)) {
			port="cfpI_out3";
			portref1="cfpI_out1";
			portref2="cfpI_out2";
			portref3="cfpI_out4";
			
		}
		if((d<=a)&&(d<=b)&&(d<=c)) {
			port="cfpI_out4";
			portref1="cfpI_out1";
			portref2="cfpI_out2";
			portref3="cfpI_out3";
		}
		
		return port;
	}
}
