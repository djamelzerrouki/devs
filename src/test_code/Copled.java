package test_code;



import view.modeling.ViewableDigraph;

public class Copled extends ViewableDigraph {
	public Copled() {
		
	super("query_model");
	 addInport("I");
	 addOutport("O");
	
	initiator bayer = new initiator();
	repondeur saller = new repondeur();	
	
    add(bayer);
	add(saller);	
	
	//add coupling
	addCoupling(this,"I",bayer,"porti_in");
	addCoupling(this,"O",saller,"port_out");
	addCoupling(bayer, "porti_out", saller, "port_in");
	addCoupling(saller, "port_out", bayer, "porti_in" );
	}
}
