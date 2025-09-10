// graphs DIS variables for all of the data 

// timer

long st = System.currentTimeMillis(); // start time


// imports from the other code that actually work

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


// Loop over the events in the file and read particle bank and check for electron in the first raw, 
// if there is one we can create lorentz vector for the particle and calculate W2 and Q2, and plot it. 
// First we will declare histogram objects and canvas object:
H1F  hW = new H1F("hW" ,100, 0.5, 4.5);
H1F hQ2 = new H1F("hQ2",100, 0.1, 10.0);
hW.setTitleX("W [GeV]");
hQ2.setTitleX("Q^2 [GeV/c^2]");

H1F  hNu = new H1F("hW" ,100, 0.0, 12);
H1F hY1 = new H1F("hQ2",100, 0.1, 1.2);
H1F hY2 = new H1F("hQ2",100, 0.1, 1.2);
hW.setTitleX("W [GeV]");
hQ2.setTitleX("Q^2 [GeV/c^2]");
hNu.setTitleX("#nu");
hY1.setTitleX("Y1");
hY2.setTitleX("Y");
  

TCanvas ec = new TCanvas("ec",800,400);
 
// Loop over the events and calculate Q2 and W from the LorentzVectors 
LorentzVector  vBeam   = new LorentzVector(0.0,0.0,10.6,10.6);
LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,0.938);
LorentzVector electron = new LorentzVector(); 
LorentzVector       vW = new LorentzVector(); 
LorentzVector      vQ2 = new LorentzVector();
LorentzVector       vNu = new LorentzVector(); 
LorentzVector      vY1 = new LorentzVector();
LorentzVector      vY2 = new LorentzVector();
double nu = 0;
double y1 = 0;
double y2 = 0;
  

//reader.getEvent(event,0); // Reads the first event and resets to the begining of the file
// this ^^^^^ doesn't work with hipochain


Event     event = new Event();
Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));

EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-");


while(reader.hasNext()==true){
     reader.nextEvent(event);
     event.read(particles);

     PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);

     if(eventFilter.isValid(physEvent)==true&&particles.getRows()>0){
         int pid = particles.getInt("pid",0);
         if(pid==11){
            electron.setPxPyPzM(
                particles.getFloat("px",0), 
                particles.getFloat("py",0),
                particles.getFloat("pz",0),
                0.0005
                );
             
            vW.copy(vBeam);
            vW.add(vTarget);
	    vW.sub(electron);
             
            vQ2.copy(vBeam);
            vQ2.sub(electron);
	

	    double nu = vBeam.e() - electron.e();

	    double y2 = nu / vBeam.e();
             

// filling variables
            hW.fill(vW.mass());
            hQ2.fill(-vQ2.mass2());
            hNu.fill(nu);
            hY2.fill(y2);
         }
     }
}


ec.divide(4,1);
  
ec.cd(0).draw(hW);
ec.cd(1).draw(hQ2);
ec.cd(2).draw(hNu);
ec.cd(3).draw(hY2);


long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen

