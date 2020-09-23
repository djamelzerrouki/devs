package mypackage;

import model.modeling.message;
import view.modeling.ViewableAtomic;

public class testt extends ViewableAtomic{

	
	
		double period = 10;
	
		public void initialize(){
		holdIn("active", period);
		}
		public void deltext(double e,message x){
		Continue(e);
		}
		public void deltint( ){
		holdIn("1", 5);
		}
		public message out( ){
		message m = new message();
		return m;
		}
		}
	
	
	

