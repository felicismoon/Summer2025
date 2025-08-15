// finds three sigma values for Vz for Carbon. Vz lets you select between the liquid and the solid target

// timer

long st = System.currentTimeMillis(); // start time

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

import java.io.File;
import java.util.List;



// hipochain version:

ArrayList<String> fileNames = new ArrayList<String>();
fileNames.add("filteredfileC_1.hipo");
fileNames.add("filteredfileC_2.hipo");
fileNames.add("filteredfileC_3.hipo");


HipoChain reader = new HipoChain();
reader.addFiles(fileNames);
reader.open();


Event     event = new Event();
Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));

EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-");


int noOfBins = 100;
int filterCounter2 = 0;
int eventCounter2  = 0;
int filter1 = 0;
int filter2 = 0;
int filter3 = 0;

H1F    hpi1 = new H1F("	hpi1"   ,noOfBins,-20, 20);

H1F    hgamma = new H1F("hgamma"   ,noOfBins,0.05, 0.28);

H1F    htheta0 = new H1F("htheta0"   ,noOfBins,0.0, 1.5);
H1F    htheta1 = new H1F("htheta1"   ,noOfBins,0.0, 1.5);

LorentzVector vL_pi0_1 = new LorentzVector();

LorentzVector vL_omega = new LorentzVector();

LorentzVector vL_gamma0 = new LorentzVector();

LorentzVector vL_gamma1 = new LorentzVector();

LorentzVector vL_electron = new LorentzVector();

LorentzVector  vBeam   = new LorentzVector(0.0,0.0,10.6,10.6);
LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,0.938);
LorentzVector electronLV = new LorentzVector(); 
LorentzVector       vW = new LorentzVector(); 
LorentzVector      vQ2 = new LorentzVector();

double nu = 0;
double y = 0;


while(reader.hasNext()==true){
      
     reader.nextEvent(event); // read the event object
     event.read(particles);   // read particles bank from the event

     // Data manages creates a physics event with beam energy 10.6 GeV
     // and from particles bank for reconstructed particles info
     PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);



     // check if event passes the filter - at least two photons
     if(eventFilter.isValid(physEvent)==true){
         filterCounter2++;

	// selects first electron from the event

	 Particle electron = physEvent.getParticleByPid(11,0);

     	 double Vz = electron.vz();

		    
         hpi1.fill(Vz);  

     }


     eventCounter2++;



}

System.out.println("analyzed " + eventCounter2 + " events. # passed filter = " + filterCounter2);


// creates graph of Vz


TCanvas ec2 = new TCanvas("ec2",800,400);


hpi1.setTitleX("Vz");
hpi1.setTitleY("Counts");

ec2.cd(0).draw(hpi1);

// making fits for Vz: creates a gaussian, then a quadratic, then a final combination of the gaussian and the quadratic


// first gauss for the liquid target (left)
F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", -10.0, -3.0);


//set the initial values for the fit
f4.setParameter(0, 26000.0); // value for [amp]
f4.setParameter(1, -7.0);   // value for [mean]
f4.setParameter(2, 1.0);  // value for [sigma]

DataFitter.fit(f4, hpi1, "Q"); //No options uses error for sigma
f4.setLineColor(32);
f4.setLineWidth(5);
f4.setLineStyle(1);
f4.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f4,"same");   // overlay the fit on the histogram


// first gauss for the solid target (right)

F1D f7 = new F1D("f7","[amp]*gaus(x,[mean],[sigma])", -3.0, 2.0);

f7.setParameter(0, 34000.0); // value for [amp]
f7.setParameter(1, -1.0);   // value for [mean]
f7.setParameter(2, 1);  // value for [sigma]

DataFitter.fit(f7, hpi1, "Q"); //No options uses error for sigma
f7.setLineColor(37);
f7.setLineWidth(5);
f7.setLineStyle(1);
f7.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f7,"same");   // overlay the fit on the histogram


// creates limits for the second guassian for liquid target

double lowerlimit2 = f4.parameter(1).value() - 3* Math.abs(f4.parameter(2).value());

double upperlimit2 = f4.parameter(1).value() + 3* Math.abs(f4.parameter(2).value());


// F5 is a function with a quadratic fit for the liquid target

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .7, .85);

F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", -9, -3);


f5.setParameter(0, 2500); // value for [B0]
f5.setParameter(1, 1); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, hpi1, "Q"); //No options uses error for sigma
f5.setLineColor(33);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
ec2.cd(0).draw(f5,"same");   // overlay the fit on the histogram



// creating a fit function with both the hist and the linear -- orange line. for the liquid target
F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

//F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .75, .81);

f6.setParameter(0, f4.parameter(0).value()); // value for [amp]
f6.setParameter(1, f4.parameter(1).value()); // value for [mean]
f6.setParameter(2, f4.parameter(2).value()); // value for [sigma]
f6.setParameter(3, f5.parameter(0).value()); // value for [B0]
f6.setParameter(4, f5.parameter(1).value()); // value for [B1]
f6.setParameter(5, f5.parameter(2).value()); // value for [B2]


DataFitter.fit(f6, hpi1, "Q"); //No options uses error for sigma
f6.setLineColor(35);
f6.setLineWidth(5);
f6.setLineStyle(1);
f6.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f6,"same");   // overlay the fit on the histogram


double lowerlimit3 = f6.parameter(1).value() - 3* Math.abs(f6.parameter(2).value());

double upperlimit3 = f6.parameter(1).value() + 3* Math.abs(f6.parameter(2).value());



// print the values of the fit parameters
System.out.println("f4 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f5 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f6 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());


// this is the three sigma limits of Vz to choose liquid target
System.out.println("lower limit3 (range of second gauss f6)  =  " + lowerlimit3);
System.out.println("upper limit3 (range of second gauss f6) =  " + upperlimit3);

///////////////////////////


// repeating the process for the other peak (solid target)

double lowerlimit4 = f7.parameter(1).value() - 3* Math.abs(f7.parameter(2).value());

double upperlimit4 = f7.parameter(1).value() + 3* Math.abs(f7.parameter(2).value());



// creates a fit function with a quadratic fit for the solid target


F1D f8 = new F1D("f8","[B0]+[B1]*x+[B2]*x*x", -3, 4);


f8.setParameter(0, 2000); // value for [B0]
f8.setParameter(1, 1); // value for [B1]
f8.setParameter(2, 1); // value for [B2]

DataFitter.fit(f8, hpi1, "Q"); //No options uses error for sigma
f8.setLineColor(38);
f8.setLineWidth(5);
f8.setLineStyle(1);
f8.setOptStat(1111);  // draw the statistics box
 
ec2.cd(0).draw(f8,"same");   // overlay the fit on the histogram



// creates a fit function with both the hist and the linear fit for the solid target

F1D f9 = new F1D("f9","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit4, upperlimit4);


f9.setParameter(0, f7.parameter(0).value()); // value for [amp]
f9.setParameter(1, f7.parameter(1).value()); // value for [mean]
f9.setParameter(2, f7.parameter(2).value()); // value for [sigma]
f9.setParameter(3, f8.parameter(0).value()); // value for [B0]
f9.setParameter(4, f8.parameter(1).value()); // value for [B1]
f9.setParameter(5, f8.parameter(2).value()); // value for [B2]


DataFitter.fit(f9, hpi1, "Q"); //No options uses error for sigma
f9.setLineColor(39);
f9.setLineWidth(5);
f9.setLineStyle(1);
f9.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f9,"same");   // overlay the fit on the histogram


double lowerlimit5 = f9.parameter(1).value() - 3* Math.abs(f9.parameter(2).value());

double upperlimit5 = f9.parameter(1).value() + 3* Math.abs(f9.parameter(2).value());


// print the values of the fit parameters
System.out.println("f7 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f8 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f9 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());



// this is the three sigma limits of Vz to choose solid target

System.out.println("lower limit5 (range of second gauss f9)  =  " + lowerlimit5);
System.out.println("upper limit5 (range of second gauss f9) =  " + upperlimit5);



///////////////////////

long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen


