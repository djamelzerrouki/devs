package mypackage;

import view.modeling.ViewableDigraph;

public class Coupled extends ViewableDigraph {
	public Coupled() {
	super("request_model");
	Request_initiator bayer = new Request_initiator();
	Agent_saller saller = new Agent_saller();	
    add(bayer);
	add(saller);	
	//add coupling
	addCoupling(bayer, "envoi_request", saller, "recoi_request");
	addCoupling(saller,"envoi_inform" , bayer ,"envoi_inform");
	addCoupling(saller,"envoi_agree",bayer,"recoi_agree");
	addCoupling(saller,"envoi_refuse",bayer,"recoi_refuse");
	addCoupling(saller,"envoi_faillure",bayer,"recoi_faillure");
	

	}
	

}
