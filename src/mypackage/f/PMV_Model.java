package mypackage.f;

import GenCol.entity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class PMV_Model
  extends ViewableAtomic
{
  int Ta = 34; int Tr = 20; int Ha = 8;
  float Va = 0.1F; float icl = 0.5F; float w = 0.0F; float M = 70.2F; float Tclinit = 25.68F;
  







  float Icl = icl * 0.155F; float W = w * 58.15F;
  boolean bool;
  
  public PMV_Model() {
    super("PMV");
    

    addInport("Sys");
    addInport("Ta");
    addInport("Tr");
    addInport("Ha");
    addInport("Va");
    addInport("Icl");
    addInport("M");
    

    addOutport("PMV");
  }
  


  public void deltint() {}
  

  public void deltext(double e, message m)
  {
    if (messageOnPort(m, "Sys", 0))
    {
      if (String.valueOf(m.getValOnPort("Sys", 0)).equals("calculer"))
      {
        File f = new File("resultat.txt");
        
        PrintWriter writer = null;
        try
        {
          if ((f.exists()) && (!f.isDirectory()))
          {

            writer = new PrintWriter(new FileOutputStream(new File("resultat.txt"), true));
            
            writer.println("#### Variables Initiales ####");
            writer.println("Ta = " + Ta);
            writer.println("Tr = " + Tr);
            writer.println("Ha = " + Ha);
            writer.println("Va = " + Va);
            writer.println("icl = " + icl);
            writer.println("w = " + w);
            writer.println("M = " + M);
            writer.println("Tclinit = " + Tclinit);
            writer.println("#### Resultats ####");
            writer.println("TCL = " + tcl());
            writer.println("Pa = " + pa());
            writer.println("FCL = " + fcl());
            writer.println("HC = " + hc());
            writer.println("PMV = " + pmv());
            writer.println("PPD = " + ppd() + "%");
            writer.println("*********************************************************************");
          }
          else
          {
            writer = new PrintWriter("resultat.txt");
            
            writer.println("#### Variables Initiales ####");
            writer.println("Ta = " + Ta);
            writer.println("Tr = " + Tr);
            writer.println("Ha = " + Ha);
            writer.println("Va = " + Va);
            writer.println("icl = " + icl);
            writer.println("w = " + w);
            writer.println("M = " + M);
            writer.println("Tclinit = " + Tclinit);
            writer.println("#### Resultats ####");
            writer.println("TCL = " + tcl());
            writer.println("Pa = " + pa());
            writer.println("FCL = " + fcl());
            writer.println("HC = " + hc());
            writer.println("PMV = " + pmv());
            writer.println("PPD = " + ppd() + "%");
            writer.println("*********************************************************************");
          }
        }
        catch (Exception localException) {}
        
        writer.close();
        
        System.out.print("TCL = " + tcl());
        System.out.print(" | Pa = " + pa());
        System.out.print(" | FCL = " + fcl());
        System.out.println(" | HC = " + hc());
        System.out.println("PMV = " + pmv());
        System.out.println("PPD = " + ppd() + " %");
        holdIn("Finish", 0.0D);
        bool = true;
      }
    }
  }
  
  public message out()
  {
    message m = new message();
    
    if (bool)
    {
      content cont = makeContent("PMV", new entity("pmv())"));
      m.add(cont);
      bool = false;
    }
    
    return m;
  }
  
  public float hc()
  {
    float _Hc = (float)(2.38D * Math.pow(Math.abs(Tclinit - Ta), 0.25D));
    
    if (_Hc > 12.1D * Math.sqrt(Va)) {
      return _Hc;
    }
    return (float)(12.1D * Math.sqrt(Va));
  }
  


  public float fcl()
  {
    if (icl <= 0.078D) {
      return (float)(1.0D + 1.29D * Icl);
    }
    return (float)(1.05D + 0.645D * Icl);
  }
  



  public float tcl()
  {
    return (float)(35.7D - 0.028D * (M - W) - Icl * (3.96D * Math.pow(10.0D, -8.0D) * fcl() * (Math.pow(Tclinit + 273.0F, 4.0D) - Math.pow(Tr + 273, 4.0D)) + fcl() * hc() * (Tclinit - Ta)));
  }
  
  public float pa()
  {
    return (float)(10 * Ha * Math.exp(16.6536D - 4030.183D / (Ta + 235)));
  }
  

  public float pmv()
  {
    return (float)((0.303D * Math.exp(-0.036D * M) + 0.028D) * (M - W - 0.00305D * (5733.0D - 6.99D * (M - W) - pa()) - 
      0.42D * (M - W - 58.15D) - 1.7E-5D * M * (5867.0F - pa()) - 0.0014D * M * (34 - Ta) - 
      3.96E-8D * fcl() * (Math.pow(Tclinit + 273.0F, 4.0D) - Math.pow(Tr + 273, 4.0D)) - 
      fcl() * hc() * (tcl() - Ta)));
  }
  

  public float ppd()
  {
    return (float)(100.0D - 95.0D * Math.exp(-0.03353D * Math.pow(pmv(), 4.0D) - 0.2179D * Math.pow(pmv(), 2.0D)));
  }
}
