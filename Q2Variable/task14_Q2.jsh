// for figuring out three equal bins of Q2. you change the parameters of Q2 for each target (LC, SC, LPb, SPb), until the .getIntegral() for each bin/histogram is about the same

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
 


// hipochain version:


ArrayList<String> fileNames = new ArrayList<String>();

/*
fileNames.add("filteredfileC_1.hipo");
fileNames.add("filteredfileC_2.hipo");
fileNames.add("filteredfileC_3.hipo");
*/


fileNames.add("filteredfilePb_1.hipo");
fileNames.add("filteredfilePb_2.hipo");
fileNames.add("filteredfilePb_3.hipo");
fileNames.add("filteredfilePb_4.hipo");
fileNames.add("filteredfilePb_5.hipo");
fileNames.add("filteredfilePb_6.hipo");


/*
fileNames.add("filteredfileX_1.hipo");
fileNames.add("filteredfileX_2.hipo");
fileNames.add("filteredfileX_3.hipo");
*/


HipoChain reader = new HipoChain();
reader.addFiles(fileNames);
//reader.addFiles(filePaths);
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

H1F    hz1 = new H1F("hz1"   ,noOfBins,0.0, 1.5);
H1F    hz2 = new H1F("hz2"   ,noOfBins,0.0, 1.5);
H1F    hz3 = new H1F("hz3"   ,noOfBins,0.0, 1.5);




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


double piPlusP = 0;
double piMinusP = 0;
double piZeroP = 0;
double piPlusPz = 0;
double piMinusPz = 0;
double piZeroPz = 0;
double totalP = 0;
double totalE = 0;
double totalPz = 0;
double omegaMass = .782;
double theZh = 0;
LorentzVector piPlusV = new LorentzVector(); 
LorentzVector piMinusV = new LorentzVector(); 
LorentzVector piZeroV = new LorentzVector(); 
LorentzVector gamma0V = new LorentzVector(); 
LorentzVector gamma1V = new LorentzVector(); 


while(reader.hasNext()==true){
      
     reader.nextEvent(event); // read the event object
     event.read(particles);   // read particles bank from the event

     // Data manages creates a physics event with beam energy 10.6 GeV
     // and from particles bank for reconstructed particles info
     PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);



     // check if event passes the filter - at least two photons
     if(eventFilter.isValid(physEvent)==true){
         filterCounter2++;


// for invariant mass of the omega

	 Particle gamma0 = physEvent.getParticleByPid(22,0);
         Particle gamma1 = physEvent.getParticleByPid(22,1);

	 Particle electron = physEvent.getParticleByPid(11,0);

 	 Particle piPlus = physEvent.getParticleByPid(211,0);
         Particle piMinus = physEvent.getParticleByPid(-211,0);

	 double Vz = electron.vz();


	 vL_pi0_1.copy(gamma0.vector());
         vL_pi0_1.add(gamma1.vector()); // lorentz vectors

	 double pi0_mass = vL_pi0_1.mass();

	 hgamma.fill(vL_pi0_1.mass());


// calculates delta beta cut

	 double beta_pdgM = piMinus.p()/Math.sqrt(Math.pow(piMinus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaM = particles.getFloat("beta",physEvent.getParticleIndex(-211,0))-beta_pdgM;


	 double beta_pdgP = piPlus.p()/Math.sqrt(Math.pow(piPlus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaP = particles.getFloat("beta",physEvent.getParticleIndex(211,0))-beta_pdgP;
            
	

// following pi0 values from 4_3. using two sigma here. beta values from beta_piMinus and beta_piPlus. Vz from 11_Vz.jsh and 11_VzPb.jsh


 /*
// for all of the data
if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026){
*/

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


// for solid target LEAD
	 if(pi0_mass>=0.09738220586799731&&pi0_mass<=0.16368863413716286
&&dBetaM>-0.009610088863792025&&dBetaM<0.009930203872220556&&dBetaP>-0.011682417283575339
&&dBetaP<0.012765139660253026&&Vz >= -2.893378674913232&&Vz<=0.39807400423051953){


	 filter1++;

// calculating theta using angle between two vectors formula

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

		
// values from visual inspection. can test with thetatest


	  //  if(theta0>=0.165&&theta1>=0.105){
	 //  if(theta0>=0.105&&theta1>=0.105){
	   if(theta0>=0.2&&theta1>=0.2){
	  //  if(theta0>=0.3&&theta1>=0.3){
		//if(theta0>=0.4&&theta1>=0.4){

	   filter2++;


	   // DIS cuts

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



// change values of Q2 here until each histogram (hz1, hz2, hz3) all have about the same amount of data in them (.getIntegral() is about the same)

 		 if(W>2.0&&Q2>1.0&&y<0.85&&Q2<2.02){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hz1.fill(vL_omega.mass());
		}


 		if(W>2.0&&y<0.85&&Q2>=2.02&&Q2<3.0){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hz2.fill(vL_omega.mass());
		}

		if(W>2.0&&y<0.85&&Q2>=3.0&&Q2<10){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hz3.fill(vL_omega.mass());
		}

            }
   	 }
       }
     }


     eventCounter2++;



}

System.out.println("analyzed " + eventCounter2 + " events. # passed filter = " + filterCounter2);


System.out.println("all events = " + eventCounter2 + ". through first filter = " + filterCounter2 + ". through second filter (pi0 mass) = " + filter1 + ". through second filter (theta) = " + filter2 + ". through third filter (W & Q2) = " + filter3);


// creates histo of invariant mass for lowest bin of Q2

TCanvas ec10 = new TCanvas("ec10",800,400);


hz1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz1.setTitleY("Counts");
hz1.setTitle("zh1");

ec10.cd(0).draw(hz1);


// creates histo of invariant mass for middle bin of Q2

TCanvas ec11 = new TCanvas("ec11",800,400);


hz2.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz2.setTitleY("Counts");
hz2.setTitle("zh2");

ec11.cd(0).draw(hz2);



// creates histo of invariant mass for highest bin of Q2

TCanvas ec12 = new TCanvas("ec12",800,400);


hz3.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz3.setTitleY("Counts");
hz3.setTitle("zh3");

ec12.cd(0).draw(hz3);


// pi0 from gammas invariant mass

TCanvas ec3 = new TCanvas("ec3",800,400);


hgamma.setTitleX("M(#gamma#gamma) [GeV]");
hgamma.setTitleY("Counts");

ec3.cd(0).draw(hgamma);


//// theta from gamma0 and electron 

TCanvas ec4 = new TCanvas("ec4 theta0",800,400);


htheta0.setTitleX("#theta(e-#gamma) ");
htheta0.setTitleY("Counts");

ec4.cd(0).draw(htheta0);


DataLine line = new DataLine(0.105,0.0,.105,5000.0);
line.setLineColor(4); 
line.setLineWidth(2);
ec4.draw(line);


//// theta from gamma1 and electron

TCanvas ec5 = new TCanvas("ec5 theta1",800,400);


htheta1.setTitleX("#theta(e-#gamma) ");
htheta1.setTitleY("Counts");

ec5.cd(0).draw(htheta1);


//DataLine line2 = new DataLine(0.105,0.0,.105,300.0);
DataLine line2 = new DataLine(0.105,0.0,.105,5000.0);
line2.setLineColor(4); 
line2.setLineWidth(2);
ec5.draw(line2);


long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen




