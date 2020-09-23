package mypackage;

import view.modeling.ViewableAtomic;

public class Agent_saller extends ViewableAtomic {
	public Agent_saller()
	{
		super("Atomic2");
		// les entrees
		addInport("recoi_request");
		// les sorties
		addOutport("envoi_agree");
		addOutport("envoi_refuse");
		addOutport("envoi_faillure");
		addOutport("envoi_inform");
	}

}
