// Dalitz plot


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


import org.jlab.clas.pdg.PhysicsConstants;

PhysicsConstants PhyConsts= new PhysicsConstants();
 
/*
// Create the reader and load in the file
HipoReader reader = new HipoReader(); // Create a reader obejct
//reader.open("Downloads/SimOuts.hipo"); // open a file
//reader.open("/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014/rec_clas_020014.evio.00041.hipo"); // open a file
reader.open("filteredfileC_2.hipo"); // open a file
*/



// hipochain version:


ArrayList<String> fileNames = new ArrayList<String>();


fileNames.add("filteredfileC_1.hipo");
fileNames.add("filteredfileC_2.hipo");
fileNames.add("filteredfileC_3.hipo");



fileNames.add("filteredfilePb_1.hipo");
fileNames.add("filteredfilePb_2.hipo");
fileNames.add("filteredfilePb_3.hipo");
fileNames.add("filteredfilePb_4.hipo");
fileNames.add("filteredfilePb_5.hipo");
fileNames.add("filteredfilePb_6.hipo");



fileNames.add("filteredfileX_1.hipo");
fileNames.add("filteredfileX_2.hipo");
fileNames.add("filteredfileX_3.hipo");




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

H1F    hpi1 = new H1F("	hpi1"   ,noOfBins,0.0, 1.5);

H1F    hgamma = new H1F("hgamma"   ,noOfBins,0.05, 0.28);

H1F    htheta0 = new H1F("htheta0"   ,noOfBins,0.0, 1.5);
H1F    htheta1 = new H1F("htheta1"   ,noOfBins,0.0, 1.5);

LorentzVector vL_pi0_1 = new LorentzVector();

LorentzVector vL_omega = new LorentzVector();

LorentzVector vL_gamma0 = new LorentzVector();

LorentzVector vL_gamma1 = new LorentzVector();

LorentzVector vL_electron = new LorentzVector();

LorentzVector vL_piPlus = new LorentzVector();

LorentzVector vL_piMinus = new LorentzVector();


LorentzVector  vBeam   = new LorentzVector(0.0,0.0,10.6,10.6);
LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,0.938);
LorentzVector electronLV = new LorentzVector(); 
LorentzVector       vW = new LorentzVector(); 
LorentzVector      vQ2 = new LorentzVector();

LorentzVector      piPlusMinusLV = new LorentzVector();
LorentzVector      piPlusZeroLV = new LorentzVector();
LorentzVector      piMinusZeroLV = new LorentzVector();


double nu = 0;
double y = 0;

double pi0_mass_2 = 0;
double piPlus_mass_2 = 0;
double piMinus_mass_2 = 0;

H2F hDalitzPlot1 = new H2F("hDalitzPlot1", 100, 0, 1.0, 100, 0, 1.0);
hDalitzPlot1.setTitleX("pi+ and pi0");
hDalitzPlot1.setTitleY("pi- and pi0");

H2F hDalitzPlot2 = new H2F("hDalitzPlot2", 100, 0.0, 1.0, 100, 0, 1.0);
hDalitzPlot2.setTitleX("pi+ and pi-");
hDalitzPlot2.setTitleY("pi+ and pi0");


while(reader.hasNext()==true){
      
     reader.nextEvent(event); // read the event object
     event.read(particles);   // read particles bank from the event

     // Data manages creates a physics event with beam energy 10.6 GeV
     // and from particles bank for reconstructed particles info
     PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);



     // check if event passes the filter - at least two photons
     if(eventFilter.isValid(physEvent)==true){
         filterCounter2++;



	 Particle gamma0 = physEvent.getParticleByPid(22,0);
         Particle gamma1 = physEvent.getParticleByPid(22,1);

	 Particle electron = physEvent.getParticleByPid(11,0);

 	 Particle piPlus = physEvent.getParticleByPid(211,0);
         Particle piMinus = physEvent.getParticleByPid(-211,0);


	 vL_piPlus.copy(piPlus.vector());
	 vL_piMinus.copy(piMinus.vector());



	 double Vz = electron.vz();


	 vL_pi0_1.copy(gamma0.vector());
         vL_pi0_1.add(gamma1.vector()); // lorentz vectors

	 double pi0_mass = vL_pi0_1.mass();

	 hgamma.fill(vL_pi0_1.mass());

	 double beta_pdgM = piMinus.p()/Math.sqrt(Math.pow(piMinus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaM = particles.getFloat("beta",physEvent.getParticleIndex(-211,0))-beta_pdgM;


	 double beta_pdgP = piPlus.p()/Math.sqrt(Math.pow(piPlus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaP = particles.getFloat("beta",physEvent.getParticleIndex(211,0))-beta_pdgP;
            
	


 
if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026){


/*
// for liquid target CARBON
	 if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026&&Vz>=-9.34312331643451&&Vz<=-3.1874807563985703){
*/

/*
// for solid target CARBON
	 if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026&&Vz >= -2.8088366963172513&&Vz<=0.39146702410361933){
*/


/*
// for liquid target LEAD
	 if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026&&Vz>=-9.286797342560995&&Vz<=-3.2131364969370733){
*/

/*
// for solid target LEAD
	 if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026&&Vz >= -2.893378674913232&&Vz<=0.39807400423051953){
*/

	 filter1++;



// Dalitz Vectors

	 piPlusMinusLV.copy(piPlus.vector());
	 piPlusMinusLV.add(piMinus.vector());
	 piPlusZeroLV.copy(piPlus.vector()); 
	 piPlusZeroLV.add(vL_pi0_1);
	 piMinusZeroLV.copy(piMinus.vector());
	 piMinusZeroLV.add(vL_pi0_1);

	 double piPlusMinus = piPlusMinusLV.mass2();
	 double piPlusZero = piPlusZeroLV.mass2();
	 double piMinusZero = piMinusZeroLV.mass2();


hDalitzPlot1.fill(piPlusZero, piMinusZero);	 




//double piPlusMinus = piPlus_mass_2 * piMinus_mass_2;

hDalitzPlot2.fill(piPlusMinus, piPlusZero);	 

////

	 vL_gamma0.copy(gamma0.vector());
	 vL_electron.copy(electron.vector());


double dotTotal0 = vL_electron.vect().dot(vL_gamma0.vect());

double AB0 = electron.vector().p()*gamma0.vector().p();

double cosTheta0 = dotTotal0 / AB0;

double theta0 = Math.acos(cosTheta0);

htheta0.fill(theta0);


	 vL_gamma1.copy(gamma1.vector());


double dotTotal1 = vL_electron.vect().dot(vL_gamma1.vect());

double AB1 = electron.vector().p()*gamma1.vector().p();

double cosTheta1 = dotTotal1 / AB1;

double theta1 = Math.acos(cosTheta1);

htheta1.fill(theta1);

		


// different ways of getting these limits
	// if(pi0_mass>=lowerlimit1&&pi0_mass<=upperlimit1){
	   //if(pi0_mass>=finallowerlimit&&pi0_mass<=finalupperlimit){
	   //if(pi0_mass>=0.08687435847577243&&pi0_mass<=0.17457583593553674){
	   //if(pi0_mass>=0.08687435847577243&&pi0_mass<=0.17457583593553674&&theta0>=0.165&&theta1>=0.105){

	  //  if(theta0>=0.165&&theta1>=0.105){
	 //  if(theta0>=0.105&&theta1>=0.105){
	   if(theta0>=0.2&&theta1>=0.2){
	  //  if(theta0>=0.3&&theta1>=0.3){
		//if(theta0>=0.4&&theta1>=0.4){

	   filter2++;

 		int pid = particles.getInt("pid",0);
        		 if(pid==11){
           		 electronLV.setPxPyPzM(
               		 particles.getFloat("px",0), 
               		 particles.getFloat("py",0),
                		particles.getFloat("pz",0),
               		 0.0005
               		 );
             
          		 vW.copy(vBeam);
           		 vW.add(vTarget);
	   		 vW.sub(electronLV);
             
           		 vQ2.copy(vBeam);
           		 vQ2.sub(electronLV);


	  		  double W = vW.mass();
	  	          double Q2 = -vQ2.mass2();

     			  double nu = vBeam.e() - electron.e();

		          double y = nu / vBeam.e();


 		 if(W>2.0&&Q2>1.0&&y<0.85){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hpi1.fill(vL_omega.mass());
		}
            }
   	 }
       }
     
     }

     eventCounter2++;



}

System.out.println("analyzed " + eventCounter2 + " events. # passed filter = " + filterCounter2);


System.out.println("all events = " + eventCounter2 + ". through first filter = " + filterCounter2 + ". through second filter (pi0 mass) = " + filter1 + ". through second filter (theta) = " + filter2 + ". through third filter (W & Q2) = " + filter3);


TCanvas ec2 = new TCanvas("ec2",800,400);


hpi1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi1.setTitleY("Counts");

ec2.cd(0).draw(hpi1);

//

TCanvas ec3 = new TCanvas("ec3",800,400);


hgamma.setTitleX("M(#gamma#gamma) [GeV]");
hgamma.setTitleY("Counts");

ec3.cd(0).draw(hgamma);


// how to take integral: hpi1.integral(lowbin,highbin)


// how to find the bins: hpi1.getDataX(binnumber) until it gives you the number you want


TCanvas ec20 = new TCanvas("ec20",800,400);

ec20.cd(0).draw(hDalitzPlot1);


TCanvas ec21 = new TCanvas("ec21",800,400);

ec21.cd(0).draw(hDalitzPlot2);




////

TCanvas ec4 = new TCanvas("ec4 theta0",800,400);


htheta0.setTitleX("#theta(e-#gamma) ");
htheta0.setTitleY("Counts");

ec4.cd(0).draw(htheta0);


DataLine line = new DataLine(0.105,0.0,.105,5000.0);
line.setLineColor(4); 
line.setLineWidth(2);
ec4.draw(line);


////

TCanvas ec5 = new TCanvas("ec5 theta1",800,400);


htheta1.setTitleX("#theta(e-#gamma) ");
htheta1.setTitleY("Counts");

ec5.cd(0).draw(htheta1);


//DataLine line2 = new DataLine(0.105,0.0,.105,300.0);
DataLine line2 = new DataLine(0.105,0.0,.105,5000.0);
line2.setLineColor(4); 
line2.setLineWidth(2);
ec5.draw(line2);



//F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .75, .81);


F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .71, .81);



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


// new: creating a fit function with a quadratic fit

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .7, .85);

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .72, .84);

F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .4, 1.0);

//F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

// automated from 4_2
//f5.setParameter(0, 200); // value for [B0]

double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

f5.setParameter(0, averageValue); // value for [B0]
f5.setParameter(1, 1); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, hpi1, "Q"); //No options uses error for sigma
f5.setLineColor(33);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
ec2.cd(0).draw(f5,"same");   // overlay the fit on the histogram

// new: creating a fit function with both the hist and the linear fit


// gives a broad curved orange line
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


DataLine line3 = new DataLine(0.7,0.0,.7,800.0);
line3.setLineColor(4); 
line3.setLineWidth(2);
ec2.draw(line3);

DataLine line4 = new DataLine(0.84,0.0,.84,800.0);
line4.setLineColor(4); 
line4.setLineWidth(2);
ec2.draw(line4);



// print the values of the fit parameters
System.out.println("f4 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f5 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f6 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());

System.out.println("lower limit2 (range of first gauss f4)  =  " + lowerlimit2);
System.out.println("upper limit2 (range of first gauss f4) =  " + upperlimit2);


/////////

// getting the other histogram...?

/*
H1F    hpi2 = new H1F("	hpi2"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hpi1.getDataX(i);
	double dog = f6.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi2.setBinContent(i, dog);
}
	



TCanvas ec6 = new TCanvas("ec6",800,400);


hpi2.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi2.setTitleY("Counts");

ec6.cd(0).draw(hpi2);
	


////




H1F    hpi3 = new H1F("	hpi3"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hpi1.getDataX(i);
	double dog = f5.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi3.setBinContent(i, dog);
}
	



TCanvas ec7 = new TCanvas("ec7",800,400);


hpi3.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi3.setTitleY("Counts");

ec7.cd(0).draw(hpi3);
	

////


H1F hSub = hSub.sub(hpi2,hpi3)

TCanvas ec8 = new TCanvas("ec8",800,400);

ec8.cd(0).draw(hSub);


// for Deut Carbon
//hSub.integral(47,54);


*/



long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen


/*

F1D f7 = new F1D("f7","[amp]*gaus(x,[mean],[sigma])", .4, .5);



//set the initial values for the fit
f7.setParameter(0, 10); // value for [amp]
f7.setParameter(1, 0.45);   // value for [mean]
f7.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f7, hpi1, "Q"); //No options uses error for sigma
f7.setLineColor(32);
f7.setLineWidth(5);
f7.setLineStyle(1);
f7.setOptStat(1111);  // draw the statistics box

ec2.cd(0).draw(f7,"same");   // overlay the fit on the histogram

*/

