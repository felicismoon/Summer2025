// creates final multiplicity ratio graphs. uses the sidesub and line shape fitting of the different targets in pT2 to create graphs of the multiplicity ratio

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


// !!! these numbers are the number of omegas in the omega peak for the different bins of the pT2 data. ex: 92 is the number of omegas from the liquid (deut) carbon target the bin with the lowest values of pT2 using the sideband subtraction method of extracting omegas from the peak. Then 0 is the number of omegas from the liquid (deut) carbon target the bin with the middle values of pT2 using the sideband subtraction method of extracting omegas from the peak.SB = sideband method, LF = line fitting method

double[] omegaSBLC = {92,0,47};

double[] omegaSBSC = {89,72,58};

double[] omegaSBLPb = {168,62,146};

double[] omegaSBSPb = {19,56,47};

double[] omegaLFLC = {77,24,45};

double[] omegaLFSC = {109,36,52};

double[] omegaLFLPb = {203, 85, 113};

double[] omegaLFSPb = {45,42,34};

double[] omegaSC;

omegaSC = new double[3];

double[] omegaLC;

omegaLC = new double[3];

double[] omegaSPb;

omegaSPb = new double[3];

double[] omegaLPb;

omegaLPb = new double[3];


// averaging the number of omegas using each method. 

for (int i = 0; i < omegaSBLC.length; i++) {

	omegaLC[i] = (omegaSBLC[i] + omegaLFLC[i])/2;

	omegaSC[i] = (omegaSBSC[i] + omegaLFSC[i])/2;

	omegaLPb[i] = (omegaSBLPb[i] + omegaLFLPb[i])/2;

	omegaSPb[i] = (omegaSBSPb[i] + omegaLFSPb[i])/2;
}



double [] multC;

multC = new double[3];

double [] multPb;

multPb = new double[3];


// calculating the multiplicity ratio. the numbers are the number of electrons that went to each target

for (int i = 0; i < multC.length; i++) {

	multC[i] = (omegaSC[i] / 1563520)/ (omegaLC[i] / 1956587);

	multPb[i] = (omegaSPb[i] / 837570) / (omegaLPb[i]/ 1956587);

}
 

// next part is for determining the x error bars 

double[] deltaNSC;

deltaNSC = new double[3];


double[] deltaNLC;

deltaNLC = new double[3];



double[] deltaNSPb;

deltaNSPb = new double[3];


double[] deltaNLPb;

deltaNLPb = new double[3];


// this is for determining the error from averaging the sideband subtraction values and line fitting values to get the number of omegas in the peak. using summing and quadrature, the final value for the error in the number of omega is .5 * square root of (number of omegas from sideband method + number of omegas from line fitting method)

for (int i = 0; i < deltaNSC.length; i++) {

	deltaNSC[i] = .5 * Math.sqrt(omegaSBSC[i]+omegaLFSC[i]);
	deltaNLC[i] = .5 * Math.sqrt(omegaSBLC[i]+omegaLFLC[i]);
	deltaNSPb[i] = .5 * Math.sqrt(omegaSBSPb[i]+omegaLFSPb[i]);
	deltaNLPb[i] = .5 * Math.sqrt(omegaSBLPb[i]+omegaLFLPb[i]);
	

}



double[] errorXC;

errorXC = new double[3];

double[] errorXPb;

errorXPb = new double[3];


// this is for determining the error from dividing values to get the multiplicity ratio

for (int i = 0; i < errorXC.length; i++) {

	errorXC[i] = multC[i] * Math.sqrt((Math.pow(deltaNSC[i]/omegaSC[i],2))+(Math.pow(deltaNLC[i]/omegaLC[i],2)));

	errorXPb[i] = multPb[i] * Math.sqrt((Math.pow(deltaNSPb[i]/omegaSPb[i],2))+(Math.pow(deltaNLPb[i]/omegaLPb[i],2)));
	

}


// these values are the average values of pT2 from each bin (lowest, middle, highest pT2). you figure out the values of pT2 that give equal statistics in each bin in pT2.jsh, then you use the range of the variable for the lowest and highest bin. 

double[] pT2ValuesC = {.0263,.099,.823};

double[] pT2ValuesPb = {0.264,.102,.826};


TCanvas c1 = new TCanvas("pT2C",800,800);


// graph of pT2 vs multiplicity ratio for carbon data

GraphErrors graph = new GraphErrors();
graph.addPoint(pT2ValuesC[0],multC[0],0,errorXC[0]);
graph.addPoint(pT2ValuesC[1],multC[1],0,errorXC[1]);
graph.addPoint(pT2ValuesC[2],multC[2],0,errorXC[2]);
graph.setTitle("Multiplicity Ratio for Carbon target vs. pT2");
graph.setTitleY("Multiplicity Ratio (C/D)");
graph.setTitleX("pT2");

c1.draw(graph);


// line fit

F1D func1 = new F1D("func1","[a]+[b]*x+[c]*x*x", 0, pT2ValuesC[2]);
DataFitter.fit(func1, graph, "Q");
func1.setLineColor(7);
func1.setLineWidth(3);

c1.draw(func1, "same");


// graph of pT2 vs multiplicity ratio for lead data

TCanvas c2 = new TCanvas("pT2Pb",800,800);

GraphErrors graph2 = new GraphErrors();
graph2.addPoint(pT2ValuesPb[0],multPb[0],0,errorXPb[0]);
graph2.addPoint(pT2ValuesPb[1],multPb[1],0,errorXPb[1]);
graph2.addPoint(pT2ValuesPb[2],multPb[2],0,errorXPb[2]);
graph2.setTitle("Multiplicity Ratio for Lead target vs. pT2");
graph2.setTitleY("Multiplicity Ratio (Pb/D)");
graph2.setTitleX("pT2");

c2.draw(graph2);


// line fit

F1D func2 = new F1D("func2","[a]+[b]*x+[c]*x*x", 0, pT2ValuesPb[2]);
DataFitter.fit(func2, graph2, "Q");
func2.setLineColor(7);
func2.setLineWidth(3);


c2.draw(func2, "same");

