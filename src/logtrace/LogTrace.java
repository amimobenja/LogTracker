/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logtrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class LogTrace {

    /**
     * @param from
     * @param to
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void copyFile(File from, File to ) throws IOException {
       Files.copy(from.toPath(), to.toPath() );    
    } 
    
    public static void main(String[] args) {        
        String pathE = "", path = "";
        
        System.out.println(" ");
        System.out.println("****************************** START ******************************************");
        System.out.println(" ");
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        
        do {          
            Scanner inOne = new Scanner(System.in); 
            System.out.println(" ");
            System.out.println("PATH OF SEARCH LOGS? a || Enter - [DEFAULT] : b - [NEW]");            
            path = inOne.nextLine();
            
            if (path.equals("a") || path.equals("")) {
                pathE = System.getProperty("user.dir")+File.separator+"Search Logs"+File.separator;
            } else if (path.equals("b")) {
                do {
                    System.out.println("NEW PATH OF LOGS");
                    pathE = inOne.nextLine();
                } while (pathE.equals(""));
            } else {
                System.out.println("Unknown Path.");
            }
            
        } while (!(path.equals("a") || path.equals("b") || path.equals("")) );
        
        
        System.out.println("Path - "+pathE);
        String line = null, searchValue = null; // This will reference one line at a time
        
        do {          
            Scanner in = new Scanner(System.in); 
            System.out.println("SEARCH (ID, REFERENCE etc)?");        
            searchValue = in.nextLine();
            
        } while (searchValue.equals(""));
        
        
        System.out.println(" ");
        System.out.println("Searching for - "+searchValue);
        System.out.println(" ");
        
//        //Search for files withing the provided reference and copy them to Search Log        
//        String dateChar = searchValue.substring(0, Math.min(searchValue.length(), 8));
//        System.out.println("Date - "+dateChar);
//        
//        //Loop through the working directory and select all files that contain - dateChar        
//        File workDir = new File(System.getProperty("user.dir"));
//        File[] workDirListing = workDir.listFiles();
//        int x = 0;
//        long fileCTime = 0;
//        
//        if (workDirListing != null) {
//            for (File logFiles : workDirListing) {
//                if (logFiles.isFile()) {
//                    fileCTime = System.currentTimeMillis();
//                    if (logFiles.getName().contains(dateChar)) {
//                        File dirTo = new File(workDir+File.separator+"Search Logs"+File.separator+logFiles.getName());
//                        
//                        System.out.println(dirTo);
//
//                        try {
//                            copyFile(logFiles, dirTo);
//                            long endCTime = System.currentTimeMillis();
//                            long runCTime = endCTime-fileCTime;
//                            System.out.println(logFiles.getName()+" Copied In: " + runCTime/((double)1000) + " seconds");
//                            x++;
//                        } catch (IOException ex) {
//                            Logger.getLogger(LogTrace.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    } else {
//                        System.out.println(logFiles.getName()+" Does not contain - "+dateChar);
//                    } 
//                
//                }
//                           
//            }
//        
//        } else {
//            System.out.println("Not File");
//        }
//        long fileCETime = System.currentTimeMillis();
//        long fileCERunTime = fileCTime-fileCETime;
//        System.out.println(x+" Files Read In: " + fileCERunTime/((double)1000) + " seconds");  
//        
        
        
        
        File dir = new File(pathE);
        File[] directoryListing = dir.listFiles();        
        
        
        if (directoryListing != null) {
          PrintWriter writer = null;
            try {
                writer = new PrintWriter("Results"+File.separator+searchValue+".txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LogTrace.class.getName()).log(Level.SEVERE, null, ex);
            } 
            writer.println("SEARCHED FOR:     "+searchValue);
            writer.println(" ");
            int fileNo = 0;
            long fileTime = System.currentTimeMillis();
          for (File child : directoryListing) {
//             Do something with child                 
              if (child.isFile()) {
                  try {
//                         FileReader reads text files in the default encoding.
                        FileReader fileReader = new FileReader(child);
                        long startTime = System.currentTimeMillis();

//                         Always wrap FileReader in BufferedReader.
                        BufferedReader bufferedReader = 
                            new BufferedReader(fileReader);
                        

                        while((line = bufferedReader.readLine()) != null) {
                            if (line.contains(searchValue)) {                                
                                System.out.println(line);  
                                
                                writer.println(line.toString());
                                
                            }
                        }    

//                         Always close files.
                        bufferedReader.close(); 
                        long endTime = System.currentTimeMillis();
                        long runTime = endTime-startTime;
                        System.out.println(child.getName()+" Read In: " + runTime/((double)1000) + " seconds");
                        System.out.println(" ");
                    }
                    catch(FileNotFoundException ex) {
                        System.out.println(
                            "Unable to open file '" + 
                            child + "'");                
                    }
                    catch(IOException ex) {
                        System.out.println(
                            "Error reading file '" 
                            + child + "'");                   
//                         Or we could just do this: 
//                         ex.printStackTrace();
                    }
              
              } else {
//                  System.out.println(child.getName()+" - Not File");
              }
              
             fileNo++; 
          }
//           Always close files.
          writer.close();
          long fileEndTime = System.currentTimeMillis();
          long fileRunTime = fileEndTime-fileTime;
          System.out.println(fileNo+" Files Read In: " + fileRunTime/((double)1000) + " seconds");
          System.out.println(" "); 
          
          System.out.println("Results in " +
              System.getProperty("user.dir")+File.separator+"Results"+File.separator);
          System.out.println("File Name - "+searchValue+".txt");
          System.out.println(" ");
          
          //Delete files from that directory
//          for(File file: dir.listFiles()) file.delete();
          System.out.println("****************************** THE END ****************************************");
          
          
        } else {
//           Handle the case where dir is not really a directory.
//           Checking dir.isDirectory() above would not be sufficient
//           to avoid race conditions with another process that deletes
//           directories.            
        }
    }
    
}
