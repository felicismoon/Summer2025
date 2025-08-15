// trying to create filtered data files


//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//import org.jlab.physics.io.DataManager;
//import org.jlab.clas.physics.*;

long st = System.currentTimeMillis(); // start time



/*
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

*/


 
// Create a HipoReader object and load in a file.  In this case, myInput.hipo is a placeholder that really does not exist.



// This gets all of the paths to the files in a folder

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


// to get these folders, need to use the google sheet. 

// Carbon folders 1
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020017", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020018", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020019", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020131", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020132", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020133", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020134", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020135", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020136", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020137"};


// Carbon folders 2
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020138", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020139", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020140", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020141", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020142", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020143", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020144", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020145", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020148", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020149"};


// Carbon folders 3
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020150", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020151", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020152", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020153", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020154", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020155", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020156", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020157"};


// Lead folders 1
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020041", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020042", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020044", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020045", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020046", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020047", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020048", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020056", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020059", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020060"};




// Lead folders 2
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020075", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020077", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020078", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020079", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020080", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020081", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020082", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020083", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020084", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020085"};

// Lead folders 3
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020086", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020089", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020090", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020091", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020094", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020095", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020096", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020097", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020098", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020100"};


// Lead folders 4
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020101", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020104", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020105", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020107", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020108", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020111", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020112", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020113", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020114", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020116"};

// Lead folders 5
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020117", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020118", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020119", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020120", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020121", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020123", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020124", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020125", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020126", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020127"};

// Lead folders 6
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020128", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020129"};



// Targetless folders 1
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020014", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020015", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020021", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020022", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020023", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020024", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020026", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020027", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020029", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020030"};


// Targetless folders 2
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020031", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020032", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020033", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020036", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020038", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020039", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020043", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020051", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020055", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020057"};


// Targetless folders 3
String[] folderPaths = {"/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020072", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020076", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020093", "/nfs/data02/clas12/rg-e/production/pass0.7/mon/recon/020106"};


// adds the file paths to an Array List
List<String> filePaths = new ArrayList<>();
addFiles(new File(folderPath), filePaths);
for (String folderPath : folderPaths) {
	addFiles(new File(folderPath), filePaths);
}

filePaths.forEach(System.out::println);


HipoChain reader = new HipoChain();
reader.addFiles(filePaths);
reader.open();


 SchemaFactory factory = reader.getSchemaFactory();
 factory.show();
 Bank  particles = new Bank(factory.getSchema("REC::Particle"));
 Event     event = new Event();

//EventFilter  eventFilter = new EventFilter("11:22:22:211:-211:Xn:X+:X-"); 

// Loop through events and write into a new file.
 


// for writing new hipo files from the chosen files
HipoWriter writer = new HipoWriter(factory);
writer.open("filteredfileX_3.hipo"); // name of the new file you are writing
  
int  readCounter = 0;
int writeCounter = 0;
  
while(reader.hasNext()==true){
     reader.nextEvent(event);
     event.read(particles);
      
     readCounter++;
      
     int gamma_count = 0;
     int electron_count = 0;
	 int piPlus_count = 0;
	 int piMinus_count = 0;


    //PhysicsEvent physEvent = DataManager.getPhysicsEvent(10.6,particles);

// finds events with the omega particles (pi+, pi-, 2 gammas)
      
     if(particles.getRows()>0){

         for(int row = 0; row < particles.getRows(); row++){

			int pid1 = particles.getInt("pid", row);
			if(pid1==11){

				electron_count++;
			}
			
			int pid2 = particles.getInt("pid", row);
			if(pid2==22){

				gamma_count++;
			}

			int pid3 = particles.getInt("pid", row);
			if(pid3==211){

				piPlus_count++;
			}


			int pid4 = particles.getInt("pid", row);
			if(pid4==-211){

				piMinus_count++;
			}
  
         }
         if(electron_count>=1&&gamma_count>=2&&piPlus_count>=1&&piMinus_count>=1){

             writer.addEvent(event);
             writeCounter++;
         }
     }
}
writer.close();
System.out.printf("Read # %9d , Write # %9d %n",readCounter,writeCounter);

long et = System.currentTimeMillis(); // end time
long time = et-st; // time to run the script
System.out.println(" time = " + (time/1000.0) + "s"); // print run time to the screen
