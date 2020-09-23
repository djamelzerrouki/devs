package mypackage;

import model.modeling.message;
import view.modeling.ViewableAtomic;

public class evaluaor extends ViewableAtomic
{
   int solved;//처리 완료한 job의 수.
   int loss;//처리 완료하지 못한 job의 수
   int arrive; //generator가 전송한 모든 job의 수.
   
   public evaluaor()
   {
      this("evaluaor");
   }

   public evaluaor(String name)
   {
      super(name);   
    
      addInport("arrive");
      addInport("solved");
      addInport("loss");
      
      
      addOutport("out");
   }
  
   public void initialize()
   {
      arrive=0;
      solved=0;
      loss=0;
      
      holdIn("active", INFINITY);
   }

   public void deltext(double e, message x)
   {
      Continue(e);
      if (phaseIs("active"))
      //passive상태라면
      {
         for (int i = 0; i < x.getLength(); i++)
         {
            if (messageOnPort(x, "arrive", i))
            //message x가 in 포트에 존재한다면
            {
               arrive++;
            }
            else if (messageOnPort(x, "solved", i))
               //message x가 in 포트에 존재한다면
               {
               solved++;
               }
            else if (messageOnPort(x, "loss", i))
               //message x가 in 포트에 존재한다면
               {
               loss++;
               }
         }
      }
   }
  
   public void deltint()
   {
   }

   public message out()
   {
      message m = new message();
      return m;
   }

   public String getTooltipText()
   {
      return
      super.getTooltipText()
      + "\n" + "total: " + arrive
      + "\n" + "solved: " + solved
      + "\n" + "loss: " + loss;
   }

}