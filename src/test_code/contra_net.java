package test_code;

import view.modeling.ViewableDigraph;
import GenCol.entity;


public class contra_net extends ViewableDigraph {
public contra_net() {
	
	super("contat_net");
	
	 addInport("I");
	// addInport("II");
	 addOutport("O");
	 addTestInput("I", new entity("pc"));
	
	 addTestInput("I", new entity("smartphone"));
	 
	 addTestInput("I", new entity("roter"));
	 
	 addTestInput("I", new entity("headphone"));
	
	
	/* addTestInput("II", new entity("acheter chemise"));
	 addTestInput("II", new entity("acheter livre"));
	 addTestInput("II", new entity("acheter voiture"));
	 addTestInput("II", new entity("acheter smartphone"));
	 addTestInput("II", new entity("acheter pc"));*/
	 
	 CFP_I acheteur1 = new CFP_I();
	// CFP_II acheteur2 = new CFP_II();
	 CFP_R1 vendeur1 =new CFP_R1();
	 CFP_R2 vendeur2 = new CFP_R2();
	 CFP_R3 vendeur3 = new CFP_R3();
	 CFP_R4 vendeur4 = new CFP_R4();
	 add(acheteur1);
	// add(acheteur2);
	 add(vendeur1);
	 add(vendeur2);
	 add(vendeur3);
	 add(vendeur4);
	 addCoupling(this,"I",acheteur1,"cfpI_in");
	// addCoupling(this,"II",acheteur2,"cfpII_in");
		addCoupling(acheteur1, "cfpI_out1", vendeur1, "1cfpr1_in");
		addCoupling(acheteur1, "cfpI_out2", vendeur2, "2cfpr1_in" );
		addCoupling(acheteur1, "cfpI_out3", vendeur3, "3cfpr1_in" );
		addCoupling(acheteur1, "cfpI_out4", vendeur4, "4cfpr1_in" );
		
	/*	addCoupling(acheteur2, "cfpII_out1", vendeur1, "1cfpr2_in");
		addCoupling(acheteur2, "cfpII_out2", vendeur2, "2cfpr2_in" );
		addCoupling(acheteur2, "cfpII_out3", vendeur3, "3cfpr2_in" );
		addCoupling(acheteur2, "cfpII_out4", vendeur4, "4cfpr2_in" );*/
		
		
		/*addCoupling( vendeur1, "1cfpr_out",acheteur2, "cfpII_in1");
		addCoupling(vendeur2, "2cfpr_out",acheteur2, "cfpII_in2" );
		addCoupling(vendeur3, "3cfpr_out",acheteur2, "cfpII_in3" );
		addCoupling(vendeur4, "4cfpr_out",acheteur2, "cfpII_in4" );*/
		
		addCoupling(vendeur1, "1cfpr1_out",acheteur1, "cfpI_in1");
		addCoupling(vendeur2, "2cfpr1_out",acheteur1, "cfpI_in2"  );
		addCoupling(vendeur3, "3cfpr1_out",acheteur1, "cfpI_in3" );
		addCoupling(vendeur4, "4cfpr1_out",acheteur1, "cfpI_in4" );
		 addCoupling(vendeur1, "1cfpr1_out",this, "O");
			addCoupling(vendeur2, "2cfpr1_out",this, "O"  );
			addCoupling(vendeur3, "3cfpr1_out",this, "O" );
			addCoupling(vendeur4, "4cfpr1_out",this, "O" );

}
	
}
