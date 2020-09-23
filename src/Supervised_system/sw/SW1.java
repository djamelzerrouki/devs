package 	Supervised_system.sw;



import model.modeling.message;
import view.modeling.ViewableAtomic;

public class SW1 extends ViewableAtomic {
	String msginput;
	boolean testinf1=false;
    boolean send=false;
	boolean down=false;
	public  SW1() {
		
        // TODO Auto-generated constructor stub
        super("SW1");
        addOutport("cn12");
        addOutport("cn31");
        addOutport("cs1");
        addOutport("observations");
        

        addInport("cn12");
        addInport("cn31");
        addInport("cs1");
        addInport("SW1observations");


        System.out.println("Sw1");
        }	

public void initialize(){

           	holdIn("normale", sigma);
           	
           	
             }
public void deltint(){
	 
	if ((phaseIs("normale") & (down=true))){
		
		holdIn("Sw1brk", 10);
				
		
		down=false;
		
		
	}
	if(phaseIs("6")&&(envoisinf==true)) {
		holdIn("7", 10); 
		
		envoisinf=false;
	}
			if (phaseIs("6")&&((envoisfail=true))){
				
				
				holdIn("8", 10);
				envoisfail=false;
				
			}
			
			
			
		
	

if (phaseIs("normale")&&(send==true)) {
		
		holdIn("enattent", 10);
	}
if (phaseIs("4")&&(testinf1==true)) {
	
	//holdIn("5", 10);
}
if (phaseIs("4")&&testinf1==true) {
	holdIn("6", 10);
	
}
if (phaseIs("4")&&testinf1==true) {
	holdIn("5", 10);
	
}

}




public void deltext(double e, message m) {
	Continue(e);
	System.out.println("fonction");
	// bach nraj3oha en attent
	if (messageOnPort(m, "cn12", 0)||messageOnPort(m, "cn31", 0) ) {
		String msg_sys1 = String.valueOf(m.getValOnPort("cn12", 0));
		String msg_sys2 = String.valueOf(m.getValOnPort("cn31", 0));

		if (msg_sys1.equals("cutCn12")||msg_sys2.equals("cutCn31")) {
			send = true;
			holdIn("en attent", 10);
			System.out.println("Etat en attent" );
		}
	}
	// bach nraj3oha normale 

	if (messageOnPort(m, "SW1observations", 0) ) {
		String msg_sys = String.valueOf(m.getValOnPort("SW1observations", 0));
 
		if (msg_sys.equals("workCn12")||msg_sys.equals("workCn31")) {
			send = true;
			holdIn("normale", 10);
			System.out.println("Etat normale" );
		}
	}

	
}}
