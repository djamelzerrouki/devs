package test_code;

import GenCol.entity;
import view.modeling.ViewableDigraph;

public class copled_request extends ViewableDigraph {
	public copled_request() {
		
		super("request_model");
		
		request_i bayer = new request_i();
		request_r saller = new request_r();	
		 addInport("I");
		 addOutport("O");
	    add(bayer);
		add(saller);	
		
		addTestInput("I",new entity("jobx"));
		addTestInput("I",new entity("joby"));
		addTestInput("I",new entity("update"));
		addTestInput("I",new entity("insert"),20);
		addTestInput("I",new entity("select"));
		//add coupling
		
		addCoupling(this,"I",bayer,"port_ini1");
		addCoupling(this,"O",saller,"port_outr");
		addCoupling(bayer, "port_outi", saller, "port_inr");
		addCoupling(saller, "port_outr", bayer, "port_ini" );
		}

}
