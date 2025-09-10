// zh for solid lead target

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

/*
for (int i = 0; i <= 9; i++) {
          
           fileNames.add("data/020014/rec_clas_020014.evio.0004" + i + ".hipo");
        }
*/


/*
public void addFiles(File folder, List<String> list) {
        File[] files = folder.listFiles();
         if (files != null) {
             for (File file : files) {
                if (file.isDirectory()) {
                    addFiles(file, list);
                 } else {
                     list.add(file.getAbsolutePath());
                 }
             }
         }
     }


//String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020017", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020018", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020019", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020021", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020022", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020023", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020024", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020026"};

String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015"};



List<String> filePaths = new ArrayList<>();
addFiles(new File(folderPath), filePaths);
for (String folderPath : folderPaths) {
	addFiles(new File(folderPath), filePaths);
}

filePaths.forEach(System.out::println);


    
//fileNames.add("/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020017");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020018");
*/

HipoChain reader = new HipoChain();
reader.addFiles(fileNames);
//reader.addFiles(filePaths);
reader.open();


Event     event = new Event();
Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));

EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-");

//reader.getEvent(event,0); // Reads the first event and resets to the begining

int noOfBins = 100;

// after this there used to be a long part commented out that finds limits for the two gammas with the two pions. However, you can also find limits for the two gammas by themselves with the task4_2 file. the limits for those are finallowerlimit and finalupperlimit. later in the code, you can use those variables, the limits you get from the stuff I'm commenting out, or the limits I got once and just plugged in the values. however, if later I find you're supposed to use the gamma limits from the pion and gamma data you can find that in 5_2 I believe....

// I'm trying to get the invariant mass of the three pions now

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

// method one
         // The particle Lorentz vector is constructed by adding
         // two photons lorentz vectors 
         // [22,0] - first photons (or 0'th)
         // [22,1] - second photons (or index 1)

        /* Particle pi1 = physEvent.getParticle("[22,0]+[22,1]+[211,0]+[-211,0]");
         hpi1.fill(pi1.mass());
         */


// method two

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

	 double beta_pdgM = piMinus.p()/Math.sqrt(Math.pow(piMinus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaM = particles.getFloat("beta",physEvent.getParticleIndex(-211,0))-beta_pdgM;


	 double beta_pdgP = piPlus.p()/Math.sqrt(Math.pow(piPlus.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
 	 double dBetaP = particles.getFloat("beta",physEvent.getParticleIndex(211,0))-beta_pdgP;
            
	

// fill histo with this stuff ^^^^^^


// getting the angles: 

	// if(pi0_mass>=0.08687435847577243&&pi0_mass<=0.17457583593553674){
	 //if(pi0_mass>=0.1&&pi0_mass<=0.16){

 /*
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



	    if(pid==211){
            piPlusV.setPxPyPzM(
                particles.getFloat("px",0), 
                particles.getFloat("py",0),
                particles.getFloat("pz",0),
                0.1396
                );
		}


  	    if(pid==-211){
            piMinusV.setPxPyPzM(
                particles.getFloat("px",0), 
                particles.getFloat("py",0),
                particles.getFloat("pz",0),
                0.1396
                );
		}

	// Particle gamma0 = physEvent.getParticleByPid(22,0);
      //   Particle gamma1 = physEvent.getParticleByPid(22,1);


	 gamma0V.copy(gamma0.vector());
         gamma0V.add(gamma1.vector()); 


	 double piPlusP = piPlusV.p();
	 double piMinusP = piMinusV.p();
 	 double piZeroP = gamma0V.p(); 

	 double totalP = piPlusP + piMinusP + piZeroP;

	 double totalE = Math.sqrt(Math.pow(totalP,2) + Math.pow(omegaMass,2));


	 double piPlusPz = piPlusV.pz();
	 double piMinusPz = piMinusV.pz();
 	 double piZeroPz = gamma0V.pz(); 


	// double totalPz = piPlusPz + piMinusPz + piZeroPz;


	// double theZ = (totalE + totalPz) / (omegaMass + 2*nu);


	 double theZh = totalE / nu;


 		 if(W>2.0&&Q2>1.0&&y<0.85&&theZh<0.16){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hz1.fill(vL_omega.mass());
		}


 		if(W>2.0&&Q2>1.0&&y<0.85&&theZh>=.16&&theZh<.23){
			filter3++;
			vL_omega.copy(vL_pi0_1);
			vL_omega.add(piPlus.vector());
			vL_omega.add(piMinus.vector());
			hz2.fill(vL_omega.mass());
		}

		if(W>2.0&&Q2>1.0&&y<0.85&&theZh>=.23){
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

/*
TCanvas ec2 = new TCanvas("ec2",800,400);


hpi1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi1.setTitleY("Counts");

ec2.cd(0).draw(hpi1);
*/



TCanvas ec10 = new TCanvas("ec10",800,400);


hz1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz1.setTitleY("Counts");
hz1.setTitle("z1");

ec10.cd(0).draw(hz1);

////

TCanvas ec11 = new TCanvas("ec11",800,400);


hz2.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz2.setTitleY("Counts");
hz2.setTitle("z2");

ec11.cd(0).draw(hz2);

/////



TCanvas ec12 = new TCanvas("ec12",800,400);


hz3.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hz3.setTitleY("Counts");
hz3.setTitle("z3");

ec12.cd(0).draw(hz3);


/////////////////

TCanvas ec3 = new TCanvas("ec3",800,400);


hgamma.setTitleX("M(#gamma#gamma) [GeV]");
hgamma.setTitleY("Counts");

ec3.cd(0).draw(hgamma);


// how to take integral: hpi1.integral(lowbin,highbin)


// how to find the bins: hpi1.getDataX(binnumber) until it gives you the number you want




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

//////////////////////////////////////

// this is the fit that isn't actually fit to the graph but actually is the shape I want


/*

F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .71, .81);



//set the initial values for the fit
f4.setParameter(0, 17); // value for [amp]
f4.setParameter(1, 0.78);   // value for [mean]
f4.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f4, hpi1, "Q"); //No options uses error for sigma
f4.setLineColor(32);
f4.setLineWidth(5);
f4.setLineStyle(1);
f4.setOptStat(1111);  // draw the statistics box

ec10.cd(0).draw(f4,"same");   // overlay the fit on the histogram


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

//double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

f5.setParameter(0, -11); // value for [B0]
f5.setParameter(1, 50); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, hpi1, "Q"); //No options uses error for sigma
f5.setLineColor(33);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
ec10.cd(0).draw(f5,"same");   // overlay the fit on the histogram

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

ec10.cd(0).draw(f6,"same");   // overlay the fit on the histogram

*/


///////////////////////////////////

// this is the fit that's actually to the graph even though it doesn't look good:



//F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .71, .81);

// for right peak
//F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .71, .81);

// for left peak
//F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .7, .84);

F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .68, .86);


//set the initial values for the fit
f4.setParameter(0, 17); // value for [amp]
f4.setParameter(1, 0.78);   // value for [mean]
f4.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f4, hz1, "Q"); //No options uses error for sigma
f4.setLineColor(32);
f4.setLineWidth(5);
f4.setLineStyle(1);
f4.setOptStat(1111);  // draw the statistics box

ec10.cd(0).draw(f4,"same");   // overlay the fit on the histogram


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

//double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

f5.setParameter(0, -11); // value for [B0]
f5.setParameter(1, 50); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, hz1, "Q"); //No options uses error for sigma
f5.setLineColor(33);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
ec10.cd(0).draw(f5,"same");   // overlay the fit on the histogram

// new: creating a fit function with both the hist and the linear fit


// gives a broad curved orange line

// works
F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .68, .86);

//F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .75, .81);

F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .67, .84);


f6.setParameter(0, f4.parameter(0).value()); // value for [amp]
f6.setParameter(1, f4.parameter(1).value()); // value for [mean]
f6.setParameter(2, f4.parameter(2).value()); // value for [sigma]
f6.setParameter(3, f5.parameter(0).value()); // value for [B0]
f6.setParameter(4, f5.parameter(1).value()); // value for [B1]
f6.setParameter(5, f5.parameter(2).value()); // value for [B2]


DataFitter.fit(f6, hz1, "Q"); //No options uses error for sigma
f6.setLineColor(35);
f6.setLineWidth(5);
f6.setLineStyle(1);
f6.setOptStat(1111);  // draw the statistics box

ec10.cd(0).draw(f6,"same");   // overlay the fit on the histogram








////////////////////////

DataLine line3 = new DataLine(0.7,0.0,.7,800.0);
line3.setLineColor(4); 
line3.setLineWidth(2);
ec10.draw(line3);

DataLine line4 = new DataLine(0.84,0.0,.84,800.0);
line4.setLineColor(4); 
line4.setLineWidth(2);
ec10.draw(line4);



// print the values of the fit parameters
System.out.println("f4 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f5 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f6 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());

System.out.println("lower limit2 (range of first gauss f4)  =  " + lowerlimit2);
System.out.println("upper limit2 (range of first gauss f4) =  " + upperlimit2);



//////////////////////// for second graph



// this isn't very good but it at least covers the peak a bit




// F1D f8 = new F1D("f8","[amp]*gaus(x,[mean],[sigma])", .71, .81);

F1D f8 = new F1D("f8","[amp]*gaus(x,[mean],[sigma])", .71, .84);

//set the initial values for the fit
f8.setParameter(0, 17); // value for [amp]
f8.setParameter(1, 0.78);   // value for [mean]
f8.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f8, hz2, "Q"); //No options uses error for sigma
f8.setLineColor(32);
f8.setLineWidth(5);
f8.setLineStyle(1);
f8.setOptStat(1111);  // draw the statistics box

ec11.cd(0).draw(f8,"same");   // overlay the fit on the histogram


//  this second one only covers the mean +/- three sigma

double lowerlimit5 = f8.parameter(1).value() - 3* Math.abs(f8.parameter(2).value());

double upperlimit5 = f8.parameter(1).value() + 3* Math.abs(f8.parameter(2).value());


// new: creating a fit function with a quadratic fit

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .7, .85);

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .72, .84);

//F1D f9 = new F1D("f9","[B0]+[B1]*x+[B2]*x*x", .4, 1.0);

F1D f9 = new F1D("f9","[B0]+[B1]*x+[B2]*x*x", .5, 1.0);

//F1D f9 = new F1D("f9","[B0]+[B1]*x+[B2]*x*x", .55, .95);

//F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

// automated from 4_2
//f5.setParameter(0, 200); // value for [B0]

//double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

//f9.setParameter(0, 0); // value for [B0]
f9.setParameter(0, -50); // value for [B0]
f9.setParameter(1, 30); // value for [B1]
f9.setParameter(2, 1); // value for [B2]

DataFitter.fit(f9, hz2, "Q"); //No options uses error for sigma
f9.setLineColor(33);
f9.setLineWidth(5);
f9.setLineStyle(1);
f9.setOptStat(1111);  // draw the statistics box
 
ec11.cd(0).draw(f9,"same");   // overlay the fit on the histogram

// new: creating a fit function with both the hist and the linear fit


// gives a broad curved orange line
//F1D f10 = new F1D("f10","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);


// works
//F1D f10 = new F1D("f10","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .71, .85);

F1D f10 = new F1D("f10","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .7, .85);


f10.setParameter(0, f8.parameter(0).value()); // value for [amp]
f10.setParameter(1, f8.parameter(1).value()); // value for [mean]
f10.setParameter(2, f8.parameter(2).value()); // value for [sigma]
f10.setParameter(3, f9.parameter(0).value()); // value for [B0]
f10.setParameter(4, f9.parameter(1).value()); // value for [B1]
f10.setParameter(5, f9.parameter(2).value()); // value for [B2]


DataFitter.fit(f10, hz2, "Q"); //No options uses error for sigma
f10.setLineColor(35);
f10.setLineWidth(5);
f10.setLineStyle(1);
f10.setOptStat(1111);  // draw the statistics box

ec11.cd(0).draw(f10,"same");   // overlay the fit on the histogram


DataLine line5 = new DataLine(0.7,0.0,.7,800.0);
line3.setLineColor(4); 
line3.setLineWidth(2);
ec11.draw(line3);

DataLine line6 = new DataLine(0.84,0.0,.84,800.0);
line4.setLineColor(4); 
line4.setLineWidth(2);
ec11.draw(line4);



/////////////////// for third graph






// F1D f11 = new F1D("f11","[amp]*gaus(x,[mean],[sigma])", .71, .81);

F1D f11 = new F1D("f11","[amp]*gaus(x,[mean],[sigma])", .7, .84);



//set the initial values for the fit
f11.setParameter(0, 17); // value for [amp]
f11.setParameter(1, 0.78);   // value for [mean]
f11.setParameter(2, 0.02);  // value for [sigma]

DataFitter.fit(f11, hz3, "Q"); //No options uses error for sigma
f11.setLineColor(32);
f11.setLineWidth(5);
f11.setLineStyle(1);
f11.setOptStat(1111);  // draw the statistics box

ec12.cd(0).draw(f11,"same");   // overlay the fit on the histogram


//  this second one only covers the mean +/- three sigma

double lowerlimit5 = f11.parameter(1).value() - 3* Math.abs(f11.parameter(2).value());

double upperlimit5 = f11.parameter(1).value() + 3* Math.abs(f11.parameter(2).value());


// new: creating a fit function with a quadratic fit

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .7, .85);

// F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .72, .84);

 F1D f12 = new F1D("f12","[B0]+[B1]*x+[B2]*x*x", .4, 1.0);

// F1D f12 = new F1D("f12","[B0]+[B1]*x+[B2]*x*x", .4, .9);

//F1D f9 = new F1D("f9","[B0]+[B1]*x+[B2]*x*x", .5, 1.0);

//F1D f9 = new F1D("f9","[B0]+[B1]*x+[B2]*x*x", .55, .95);

//F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

// automated from 4_2
//f5.setParameter(0, 200); // value for [B0]

//double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

//f9.setParameter(0, 0); // value for [B0]
f12.setParameter(0, -50); // value for [B0]
f12.setParameter(1, 30); // value for [B1]
f12.setParameter(2, 1); // value for [B2]

DataFitter.fit(f12, hz3, "Q"); //No options uses error for sigma
f12.setLineColor(33);
f12.setLineWidth(5);
f12.setLineStyle(1);
f12.setOptStat(1111);  // draw the statistics box
 
ec12.cd(0).draw(f12,"same");   // overlay the fit on the histogram

// new: creating a fit function with both the hist and the linear fit


// gives a broad curved orange line
//F1D f10 = new F1D("f10","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);
F1D f13 = new F1D("f13","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .5, 1);



f13.setParameter(0, f11.parameter(0).value()); // value for [amp]
f13.setParameter(1, f11.parameter(1).value()); // value for [mean]
f13.setParameter(2, f11.parameter(2).value()); // value for [sigma]
f13.setParameter(3, f12.parameter(0).value()); // value for [B0]
f13.setParameter(4, f12.parameter(1).value()); // value for [B1]
f13.setParameter(5, f12.parameter(2).value()); // value for [B2]


DataFitter.fit(f13, hz3, "Q"); //No options uses error for sigma
f13.setLineColor(35);
f13.setLineWidth(5);
f13.setLineStyle(1);
f13.setOptStat(1111);  // draw the statistics box

ec12.cd(0).draw(f13,"same");   // overlay the fit on the histogram


DataLine line7 = new DataLine(0.7,0.0,.7,800.0);
line7.setLineColor(4); 
line7.setLineWidth(2);
ec12.draw(line7);

DataLine line8 = new DataLine(0.84,0.0,.84,800.0);
line8.setLineColor(4); 
line8.setLineWidth(2);
ec12.draw(line8);




///////////////////// first line shape stuff





// getting the other histogram...?


H1F    hpi2 = new H1F("	hpi2"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz1.getDataX(i);
	double dog = f6.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi2.setBinContent(i, dog);
}
	



TCanvas ec20 = new TCanvas("ec20",800,400);


hpi2.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi2.setTitleY("Counts");

ec20.cd(0).draw(hpi2);
	


////




H1F    hpi3 = new H1F("	hpi3"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz1.getDataX(i);
	double dog = f5.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi3.setBinContent(i, dog);
}
	



TCanvas ec21 = new TCanvas("ec21",800,400);


hpi3.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi3.setTitleY("Counts");

ec21.cd(0).draw(hpi3);
	

////


H1F hSub = hSub.sub(hpi2,hpi3)

TCanvas ec22 = new TCanvas("ec22",800,400);

ec22.cd(0).draw(hSub);





//////////////////////////// second line shape stuff




H1F    hpi4 = new H1F("	hpi4"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz2.getDataX(i);
	double dog = f10.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi4.setBinContent(i, dog);
}
	



TCanvas ec23 = new TCanvas("ec23",800,400);


hpi4.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi4.setTitleY("Counts");

ec23.cd(0).draw(hpi4);
	


////




H1F    hpi5 = new H1F("	hpi5"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz2.getDataX(i);
	double dog = f9.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi5.setBinContent(i, dog);
}
	



TCanvas ec24 = new TCanvas("ec24",800,400);


hpi5.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi5.setTitleY("Counts");

ec24.cd(0).draw(hpi5);
	

////


H1F hSub1 = hSub1.sub(hpi4,hpi5)

TCanvas ec25 = new TCanvas("ec25",800,400);

ec25.cd(0).draw(hSub1);





///////////////////////////// third line shape stuff





H1F    hpi6 = new H1F("	hpi6"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz3.getDataX(i);
	double dog = f13.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi6.setBinContent(i, dog);
}
	



TCanvas ec26 = new TCanvas("ec26",800,400);


hpi6.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi6.setTitleY("Counts");

ec26.cd(0).draw(hpi6);
	


////




H1F    hpi7 = new H1F("	hpi7"   ,noOfBins,0.0, 1.5);


for (int i = 0; i < noOfBins; i++) {
	
	double cat = hz3.getDataX(i);
	double dog = f12.evaluate(cat);
	//System.out.println(cat + " " + dog);
	hpi7.setBinContent(i, dog);
}
	



TCanvas ec27 = new TCanvas("ec27",800,400);


hpi7.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi7.setTitleY("Counts");

ec27.cd(0).draw(hpi7);
	

////


H1F hSub2 = hSub2.sub(hpi6,hpi7)

TCanvas ec28 = new TCanvas("ec28",800,400);

ec28.cd(0).draw(hSub2);










////////////////////////////

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

