package mypackage;

import view.modeling.ViewableAtomic;

public class Request_initiator extends ViewableAtomic{
	public Request_initiator ()
	{
		super("Atomic1");
		// les entres
		addInport("recoi_agree");
		addInport("recoi_refuse");
		addInport("recoi_faillure");
		addInport("recoi_inform");
		// les soorties
		addOutport("envoi_request");
	}

}
