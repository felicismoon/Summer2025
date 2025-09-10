// cleaned up task 5 with the gamma stuff added in

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

 
/*
// Create the reader and load in the file
HipoReader reader = new HipoReader(); // Create a reader obejct
reader.open("Downloads/SimOuts.hipo"); // open a file
//reader.open("data/020014/rec_clas_020014.evio.00040.hipo"); // open a file
*/


// hipochain version:

ArrayList<String> fileNames = new ArrayList<String>();
//fileNames.add("data/020014/rec_clas_020014.evio*.hipo");
//fileNames.add("data/020014/rec_clas_020014.evio.00041.hipo");

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

//String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015"};


String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020017", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020018", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020019", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020021", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020022", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020023", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020024", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020026", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020027", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020029", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020030", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020031", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020032", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020033", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020036", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020038", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020039", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020041", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020042", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020043", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020044", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020045", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020046", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020047", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020048"};


List<String> filePaths = new ArrayList<>();
addFiles(new File(folderPath), filePaths);
for (String folderPath : folderPaths) {
	addFiles(new File(folderPath), filePaths);
}

filePaths.forEach(System.out::println);

*/

// use this following code here if you want to use every file from the recon directory: 




    public void addFiles(File folder, List<String> list) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    addFiles(file, list); // Recursive for subfolders
                } else {
                    list.add(file.getAbsolutePath()); // Collect absolute path
                }
            }
        }
    }




        String directoryPath = "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/"; // Update this to your desired parent directory
        List<String> filePaths = new ArrayList<>();
        addFiles(new File(directoryPath), filePaths);
        filePaths.forEach(System.out::println);
    

    
//fileNames.add("/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020017");
//fileNames.add("nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020018");

HipoChain reader = new HipoChain();
//reader.addFiles(fileNames);
reader.addFiles(filePaths);
reader.open();


Event     event = new Event();
Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));

EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-");


int noOfBins = 100;

// after this there used to be a long part commented out that finds limits for the two gammas with the two pions. However, you can also find limits for the two gammas by themselves with the task4_2 file. the limits for those are finallowerlimit and finalupperlimit. later in the code, you can use those variables, the limits you get from the stuff I'm commenting out, or the limits I got once and just plugged in the values. however, if later I find you're supposed to use the gamma limits from the pion and gamma data you can find that in 5_2 I believe....

// I'm trying to get the invariant mass of the three pions now

int filterCounter2 = 0;
int eventCounter2  = 0;
int filter1 = 0;
int filter2 = 0;
int filter3 = 0;

H1F    hpi1 = new H1F("	hpi1"   ,noOfBins,0, 1.5);

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

 
	 vL_pi0_1.copy(gamma0.vector());
         vL_pi0_1.add(gamma1.vector()); // lorentz vectors

	 double pi0_mass = vL_pi0_1.mass();

	 hgamma.fill(vL_pi0_1.mass());
	

// fill histo with this stuff ^^^^^^


// getting the angles: 

	 if(pi0_mass>=0.08687435847577243&&pi0_mass<=0.17457583593553674){

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
	   if(theta0>=0.165&&theta1>=0.105){

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


System.out.println("all events = " + eventCounter2 + ". through first filter = " + filterCounter2 + ". through second filter (pi0 mass) = " + filter1 + ". through second filter (theta) = " + filter2 + ". through third filter (W & Q2 & Nu) = " + filter3);


TCanvas ec2 = new TCanvas("ec2",800,400);


hpi1.setTitleX("M(#gamma#gamma #pi+ #pi-) [GeV]");
hpi1.setTitleY("Counts");

ec2.cd(0).draw(hpi1);

//

TCanvas ec3 = new TCanvas("ec3",800,400);


hgamma.setTitleX("M(#gamma#gamma) [GeV]");
hgamma.setTitleY("Counts");

ec3.cd(0).draw(hgamma);


//


////

TCanvas ec4 = new TCanvas("ec4 theta0",800,400);


htheta0.setTitleX("#theta(e-#gamma) ");
htheta0.setTitleY("Counts");

ec4.cd(0).draw(htheta0);


DataLine line = new DataLine(0.165,0.0,.165,400.0);
line.setLineColor(4); 
line.setLineWidth(2);
ec4.draw(line);


////

TCanvas ec5 = new TCanvas("ec5 theta1",800,400);


htheta1.setTitleX("#theta(e-#gamma) ");
htheta1.setTitleY("Counts");

ec5.cd(0).draw(htheta1);


DataLine line2 = new DataLine(0.105,0.0,.105,300.0);
line2.setLineColor(4); 
line2.setLineWidth(2);
ec5.draw(line2);



F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .75, .81);
//F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", .72, .84);



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

F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", .72, .84);

//F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

// automated from 4_2
//f5.setParameter(0, 200); // value for [B0]

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


long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen


