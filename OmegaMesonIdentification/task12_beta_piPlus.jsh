// gets three sigma beta cut for piPlus

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

// from PID

import org.jlab.groot.fitter.ParallelSliceFitter;
import org.jlab.groot.data.GraphErrors;

// from electronAna

import org.jlab.clas.pdg.PhysicsConstants;
//---- imports for the file io library
import java.nio.file.Files;
import java.nio.file.Paths;



double BEAM_ENERGY = 10.5473; // units in GeV fro RG-E run
PhysicsConstants PhyConsts= new PhysicsConstants();



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


int disCounter = 0;
int filterCounter = 0;
int eventCounter  = 0;


H1F    hpi1 = new H1F("	hpi1"   ,noOfBins,-20, 22);

H1F hCount_pion = new H1F("hCount_pions","Number of pions per Event","Counts",10,-0.5,9.5);


H2F hBeta_Vs_P = new H2F("hBeta_Vs_P",100,0.0,8.0,110,0.0,1.1);
hBeta_Vs_P.setTitleX("P (GeV)");
hBeta_Vs_P.setTitleY("#beta");

H2F hDBeta_Vs_P = new H2F("hDBeta_Vs_P",100,0.0,8.0,100,-0.1,0.1);
hDBeta_Vs_P.setTitleX("P (GeV)");
hDBeta_Vs_P.setTitleY("#Delta #beta");


Particle  virtPhotonPart = new Particle();
Particle  virtPhotonTargetPart = new Particle();
Particle frame = new Particle();

List<Particle> pionList;

LorentzVector electronLV = new LorentzVector(); 

      // 2D histogram: DeltaBeta vs Momentum for protons
        H2F DBvM = new H2F("DBvM", 25, 0.60, 3.0, 100, -0.012, 0.012);
        DBvM.setTitleX("P [GeV]");
        DBvM.setTitleY("D#beta");

	Vector3 v3Parts = new Vector3(0.0, 0.0, 0.0);



float piPlusMass = .1349766f;

while(reader.hasNext()==true){
      
     reader.nextEvent(event); // read the event object
     event.read(particles);   // read particles bank from the event

     // Data manages creates a physics event with beam energy 10.6 GeV
     // and from particles bank for reconstructed particles info
     PhysicsEvent physEvent = DataManager.getPhysicsEvent(BEAM_ENERGY,particles);



     // check if event passes the filter - at least two photons
     if(eventFilter.isValid(physEvent)==true){
         filterCounter2++;


 Particle  beamPart = physEvent.getParticle("[b]");  // set the beam particle
        Particle  targetPart = physEvent.getParticle("[t]"); // set the target particle
        Particle  beamtargetPart = physEvent.getParticle("[b]+[t]"); // set the target particle
        
        pionList = physEvent.getParticlesByPid(211);
        
        int nPions = physEvent.countByPid(211);


  if(pionList.size()!=nPions) System.out.println("N(Pions) " + nPions + " " + pionList.size());
        hCount_pion.fill(nPions); // number of electrons per event
        disCounter = 0; // initialize the DIS counter
        int iEm = 0; // electron particle counter

 for(Particle pionPart : pionList){
            boolean cuts_DIS = false;  // initialize the DIS cut

// calculating delta beta
 	    double beta_pdg = pionPart.p()/Math.sqrt(Math.pow(pionPart.p(),2) + Math.pow(PhysicsConstants.massPionCharged(),2));
            double dBeta = particles.getFloat("beta",physEvent.getParticleIndex(211,iEm))-beta_pdg;
            hDBeta_Vs_P.fill(pionPart.p(),dBeta);

 		}
            iEm++;
        }
       
     }
     eventCounter++;
}





TCanvas can5 = new TCanvas("can5",1000,600);
can5.cd(1).draw(hDBeta_Vs_P);



//////


        H1F projectionY = hDBeta_Vs_P.projectionY();
 TCanvas canvasProjection = new TCanvas("Projection of Y", 600, 600);
        projectionY.setTitleX("#Delta#beta");
        projectionY.setFillColor(4);
        projectionY.setLineColor(2);
        projectionY.setTitleY("Counts");
        canvasProjection.draw(projectionY);


// first gaussian fit

F1D f4 = new F1D("f4","[amp]*gaus(x,[mean],[sigma])", -0.02, 0.03);


//set the initial values for the fit
f4.setParameter(0, 130000.0); // value for [amp]
f4.setParameter(1, 0.0);   // value for [mean]
f4.setParameter(2, .005);  // value for [sigma]

DataFitter.fit(f4, projectionY, "Q"); //No options uses error for sigma
f4.setLineColor(32);
f4.setLineWidth(5);
f4.setLineStyle(1);
f4.setOptStat(1111);  // draw the statistics box

canvasProjection.cd(0).draw(f4,"same");   // overlay the fit on the histogram

////////////////////////



double lowerlimit2 = f4.parameter(1).value() - 3* Math.abs(f4.parameter(2).value());

double upperlimit2 = f4.parameter(1).value() + 3* Math.abs(f4.parameter(2).value());


// quadratic fit

F1D f5 = new F1D("f5","[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);


double averageValue = hpi1.getIntegral()/noOfBins; // getting average value of hist

f5.setParameter(0, 5000); // value for [B0]
f5.setParameter(1, 1); // value for [B1]
f5.setParameter(2, 1); // value for [B2]

DataFitter.fit(f5, projectionY, "Q"); //No options uses error for sigma
f5.setLineColor(33);
f5.setLineWidth(5);
f5.setLineStyle(1);
f5.setOptStat(1111);  // draw the statistics box
 
canvasProjection.cd(0).draw(f5,"same");   // overlay the fit on the histogram

// second gaussian: a fit function with both the hist and the linear fit

F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", lowerlimit2, upperlimit2);

//F1D f6 = new F1D("f6","[amp]*gaus(x,[mean],[sigma])+[B0]+[B1]*x+[B2]*x*x", .75, .81);

f6.setParameter(0, f4.parameter(0).value()); // value for [amp]
f6.setParameter(1, f4.parameter(1).value()); // value for [mean]
f6.setParameter(2, f4.parameter(2).value()); // value for [sigma]
f6.setParameter(3, f5.parameter(0).value()); // value for [B0]
f6.setParameter(4, f5.parameter(1).value()); // value for [B1]
f6.setParameter(5, f5.parameter(2).value()); // value for [B2]


DataFitter.fit(f6, projectionY, "Q"); //No options uses error for sigma
f6.setLineColor(35);
f6.setLineWidth(5);
f6.setLineStyle(1);
f6.setOptStat(1111);  // draw the statistics box

canvasProjection.cd(0).draw(f6,"same");   // overlay the fit on the histogram



double lowerlimit3 = f6.parameter(1).value() - 3* Math.abs(f6.parameter(2).value());

double upperlimit3 = f6.parameter(1).value() + 3* Math.abs(f6.parameter(2).value());


// print the values of the fit parameters
System.out.println("f4 (first gauss)");
for(int j=0; j<f4.getNPars(); j++) System.out.println(" par = " + f4.parameter(j).value() + " error = " + f4.parameter(j).error());

System.out.println("f5 (linear)");
for(int j=0; j<f5.getNPars(); j++) System.out.println(" par = " + f5.parameter(j).value() + " error = " + f5.parameter(j).error());

System.out.println("f6 (final gauss)");
for(int j=0; j<f6.getNPars(); j++) System.out.println(" par = " + f6.parameter(j).value() + " error = " + f6.parameter(j).error());


// three sigma values of beta
System.out.println("lower limit3 (range of second gauss f6)  =  " + lowerlimit3);
System.out.println("upper limit3 (range of second gauss f6) =  " + upperlimit3);

///////

long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen


