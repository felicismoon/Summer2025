// creates final multiplicity ratio graphs. uses the sidesub and line shape fitting of the different targets in zh to create graphs of the multiplicity ratio

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


// !!! these numbers are the number of omegas in the omega peak for the different bins of the zh data. ex: 25 is the number of omegas from the liquid (deut) carbon target the bin with the lowest values of zh using the sideband subtraction method of extracting omegas from the peak. Then 56 is the number of omegas from the liquid (deut) carbon target the bin with the middle values of zh using the sideband subtraction method of extracting omegas from the peak.SB = sideband method, LF = line fitting method

double[] omegaSBLC = {25,56,56};

double[] omegaSBSC = {81,65,73};

double[] omegaSBLPb = {40,97,239};

double[] omegaSBSPb = {25,34,63};

double[] omegaLFLC = {32,39,71};

double[] omegaLFSC = {78,47,55};

double[] omegaLFLPb = {130, 47, 170};

double[] omegaLFSPb = {29,28,45};

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


// these values are the average values of zh from each bin (lowest, middle, highest zh). you figure out the values of zh that give equal statistics in each bin in zh.jsh, then you use the range of the variable for the lowest and highest bin. 

double[] zhValuesC = {.088,.21,.723};

double[] zhValuesPb = {.085,.205,.72};


TCanvas c1 = new TCanvas("zhC",800,800);


// graph of zh vs multiplicity ratio for carbon data

GraphErrors graph = new GraphErrors();
graph.addPoint(zhValuesC[0],multC[0],0,errorXC[0]);
graph.addPoint(zhValuesC[1],multC[1],0,errorXC[1]);
graph.addPoint(zhValuesC[2],multC[2],0,errorXC[2]);
graph.setTitle("Multiplicity Ratio for Carbon target vs. zh");
graph.setTitleY("Multiplicity Ratio (C/D)");
graph.setTitleX("zh");

c1.draw(graph);


// line fit

F1D func1 = new F1D("func1","[a]+[b]*x+[c]*x*x", 0, zhValuesC[2]);
DataFitter.fit(func1, graph, "Q");
func1.setLineColor(7);
func1.setLineWidth(3);

c1.draw(func1, "same");


// graph of zh vs multiplicity ratio for lead data

TCanvas c2 = new TCanvas("zhPb",800,800);

GraphErrors graph2 = new GraphErrors();
graph2.addPoint(zhValuesPb[0],multPb[0],0,errorXPb[0]);
graph2.addPoint(zhValuesPb[1],multPb[1],0,errorXPb[1]);
graph2.addPoint(zhValuesPb[2],multPb[2],0,errorXPb[2]);
graph2.setTitle("Multiplicity Ratio for Lead target vs. zh");
graph2.setTitleY("Multiplicity Ratio (Pb/D)");
graph2.setTitleX("zh");

c2.draw(graph2);


// line fit

F1D func2 = new F1D("func2","[a]+[b]*x+[c]*x*x", 0, zhValuesPb[2]);
DataFitter.fit(func2, graph2, "Q");
func2.setLineColor(7);
func2.setLineWidth(3);


c2.draw(func2, "same");

