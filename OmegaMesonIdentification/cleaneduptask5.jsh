// fixed up version of everything: got rid of earlier stuff from task5 so this program just does the omega (and the two gammas invariant mass as a check) and you use other programs to add the values for the cuts instead of trying to have those all in this program too

import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.Bank;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
import org.jlab.groot.ui.*;
//---- imports for PHYSICS library
import org.jlab.clas.physics.*;


// I added:
import org.jlab.physics.io.DataManager;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.math.*;
import org.jlab.groot.fitter.DataFitter;
import java.util.ArrayList;
 

// for using one file, not hipo chain
/*
// Create the reader and load in the file
HipoReader reader = new HipoReader(); // Create a reader obejct
reader.open("Downloads/SimOuts.hipo"); // open a file
*/


// hipochain version:

ArrayList<String> fileNames = new ArrayList<String>();


for (int i = 0; i <= 9; i++) {
          
           fileNames.add("data/020014/rec_clas_020014.evio.0004" + i + ".hipo");
        }
        

HipoChain reader = new HipoChain();
reader.addFiles(fileNames);
reader.open();


Event     event = new Event();
Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));

EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-");


int noOfBins = 100;

// after this there used to be a long part commented out that finds limits for the two gammas with the two pions. However, you can also find limits for the two gammas by themselves with the task4_2 file. the limits for those are finallowerlimit and finalupperlimit. later in the code, you can use those variables, the limits you get from the stuff I'm commenting out, or the limits I got once and just plugged in the values. however, if later I find you're supposed to use the gamma limits from the pion and gamma data you can find that in 5_2 I believe....

// I'm trying to get the invariant mass of the three pions now

int filterCounter2 = 0;
int eventCounter2  = 0;

H1F    hpi1 = new H1F("	hpi1"   ,noOfBins,0, 1.5);

H1F    hgamma = new H1F("hgamma"   ,noOfBins,0.05, 0.28);

LorentzVector vL_pi0_1 = new LorentzVector();

LorentzVector vL_omega = new LorentzVector();

 
while(reader.hasNext()==true){
      
     reader.nextEvent(event); // read the event object
     event.read(particles);   // read particles bank from the event
      
     // Data manages creates a physics event with beam energy 10.6 GeV
     // and from particles bank for reconstructed particles info
     PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);
      
     // check if event passes the filter - at least two photons
     if(eventFilter.isValid(physEvent)==true){
         filterCounter2++;


// getting invariant mass of omega meson

	 Particle gamma0 = physEvent.getParticleByPid(22,0);
         Particle gamma1 = physEvent.getParticleByPid(22,1);


 	 Particle piPlus = physEvent.getParticleByPid(211,0);
         Particle piMinus = physEvent.getParticleByPid(-211,0);

 
	 vL_pi0_1.copy(gamma0.vector());
         vL_pi0_1.add(gamma1.vector()); // lorentz vectors

	 double pi0_mass = vL_pi0_1.mass();

	 hgamma.fill(vL_pi0_1.mass());




// get these limits from task4_3

	   if(pi0_mass>=0.08687435847577243&&pi0_mass<=0.17457583593553674){
		vL_omega.copy(vL_pi0_1);
		vL_omega.add(piPlus.vector());
		vL_omega.add(piMinus.vector());
		hpi1.fill(vL_omega.mass());
		}

    

     }


     eventCounter2++;



}

System.out.println("analyzed " + eventCounter2 + " events. # passed filter = " + filterCounter2);



TCanvas ec2 = new TCanvas("ec2",800,400);


hpi1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi1.setTitleY("Counts");

ec2.cd(0).draw(hpi1);

//

TCanvas ec3 = new TCanvas("ec3",800,400);


hgamma.setTitleX("M(#gamma#gamma) [GeV]");
hgamma.setTitleY("Counts");

ec3.cd(0).draw(hgamma);


F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .75, .81);


//set the initial values for the fit
f4.setParameter(0, 10); // value for [amp]
f4.setParameter(1, 0.78);   // value for [mean]
f4.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f4, hpi1, "Q"); //No options uses error for sigma
f4.setLineColor(32);
f4.setLineWidth(5);
f4.setLineStyle(1);
f4.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f4,"same");   // overlay the fit on the histogram


//  this second one only covers the mean +/- three sigma

double lowerlimit2 = f4.parameter(1).value() - 3* Math.abs(f4.parameter(2).value());

double upperlimit2 = f4.parameter(1).value() + 3* Math.abs(f4.parameter(2).value());


// creating a fit function with a quadratic fit


F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .72, .84);


double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

f5.setParameter(0, averageValue); // value for [B0]
f5.setParameter(1, 1); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, hpi1, "Q"); //No options uses error for sigma
f5.setLineColor(3);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
ec2.cd(0).draw(f5,"same");   // overlay the fit on the histogram



// new: creating a fit function with both the hist and the linear fit - gives a broad curved orange line
F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);


f6.setParameter(0, f4.parameter(0).value()); // value for [amp]
f6.setParameter(1, f4.parameter(1).value()); // value for [mean]
f6.setParameter(2, f4.parameter(2).value()); // value for [sigma]
f6.setParameter(3, f5.parameter(0).value()); // value for [B0]
f6.setParameter(4, f5.parameter(1).value()); // value for [B1]
f6.setParameter(5, f5.parameter(2).value()); // value for [B2]


DataFitter.fit(f6, hpi1, "Q"); //No options uses error for sigma
f6.setLineColor(5);
f6.setLineWidth(5);
f6.setLineStyle(1);
f6.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f6,"same");   // overlay the fit on the histogram


// print the values of the fit parameters
System.out.println("f4 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f5 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f6 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());

System.out.println("lower limit2 (range of first gauss f4)  =  " + lowerlimit2);
System.out.println("upper limit2 (range of first gauss f4) =  " + upperlimit2);


