/*
//Code for Read File : https://attacomsian.com/blog/how-to-read-a-file-line-by-line-in-java
//Code for Write File : https://www.w3schools.com/java/java_files_create.asp
//Code to remove all non-alphanumeric character: https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
*/

package studentCoursesBackup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class fileCheck{
    /**
     * check method will check if file exits, can Read/Write to file.
     * @param errlogFilePath
     * @param logWriter
     * @param regResultsFilePath
     * @param regConflictsFilePath
     * @param coursePrefFilePath
     * @param courseInfoFilePath
     * @return //will return true if all condition satisfies else will return false
     */
    public boolean check(String errlogFilePath, FileWriter logWriter, String courseInfoFilePath, String coursePrefFilePath, String regConflictsFilePath, String regResultsFilePath) {
        List<String> fileNameList = new ArrayList<>(Arrays.asList(errlogFilePath, coursePrefFilePath, regResultsFilePath, regConflictsFilePath ));
        try {
            for(int iterate=0; iterate<fileNameList.size(); iterate++){
                File file= new File(fileNameList.get(iterate));
                    if(file.exists()==false){
                        System.out.println(fileNameList.get(iterate) + " does not exists\n");
                        logWriter.write(fileNameList.get(iterate) + " does not exists\n");
                        return false;
                    }else if(file.canRead() == false){
                        System.out.println("Cant read from:\n" + fileNameList.get(iterate));
                        logWriter.write("Cant read from:\n" + fileNameList.get(iterate));
                        return false; 
                    }else if((file.getName().compareTo("regConflicts.txt")==0 || file.getName().compareTo("regResults.txt")==0 ) && file.canWrite()== false){
                        System.out.println("Cant write to:\n" + fileNameList.get(iterate));
                        logWriter.write("Cant write to:\n" + fileNameList.get(iterate));
                        return false;
                    }
            }
        }catch(Exception e){
            System.out.println("Exception occured while checking file");
        }finally{}
        return true;
    }
}

public class Results implements FileDisplayInterface, StdoutDisplayInterface {
    
    /* 
     * fetchCoursePref method will read courses line by line and write results to the destination file.
     */
    public void fetchCoursePref(String[][] courseInfoElement, String coursePrefFilePath, String courseInfoFilePath, String regConflictsFilePath, String regResultsFilePath, String errLogFilePath, int rowNumber){
        
        FileWriter logWriter = null;
        try{logWriter = new FileWriter(errLogFilePath);}
        catch( FileNotFoundException e){System.out.println("Log File not found Exception!");}
        catch(IOException e){System.out.println("Log File IO Exception!");}
        catch(Exception e){System.out.println("Log File Exception!");}
        finally{}
        
        fileCheck fc = new fileCheck();
        boolean checkResults = fc.check(errLogFilePath,logWriter, courseInfoFilePath,coursePrefFilePath,regConflictsFilePath,regResultsFilePath); 

        //if file exists and we can read, write those files then only program will run
        //System.out.println(checkResults);
        if(checkResults){
            

            try {
                
                
                File coursePref = new File(coursePrefFilePath);
                FileWriter outpuWriter = new FileWriter(regResultsFilePath);
                FileWriter conflictsWriter = new FileWriter(regConflictsFilePath);
            
                Scanner sc = new Scanner(coursePref);
                String [] coursePrefLine = new String[10];
                float totalSatisfaction=0f;
                int lineCounter =0;
                int courseInfoLength = rowNumber;

                while (sc.hasNextLine()) {
                    String[] line =sc.nextLine().replaceAll("[^A-Za-z0-9]"," ").split(" ");
                    
                    //write student id to regresults file
                    outpuWriter.write(line[0] +" : ");
                    int courseCounter=1;
                    int maxSatisfaction = 10;
                    int countSatisfaction=0;
                    float satisfactionRate;
                    
                    // create an array list
                    ArrayList<Integer> timeCheck = new ArrayList<Integer>(3);
                    ArrayList<String> registeredCourses = new ArrayList<String>(3);
                    
                    
                    if(line.length-1!=courseInfoLength){
                        outpuWriter.write("\n");
                        logWriter.write(line[0]+" must contain "+courseInfoLength+" courses!");
                        System.out.println(line[0]+" must contain "+courseInfoLength+" courses!");
                        continue;
                    }
                    for(int i=1; i<line.length; i++){
                        coursePrefLine[i]=String.valueOf(line[i]);
                        for(int j=0;j<courseInfoElement.length;j++){
                            if(line[i].equals(courseInfoElement[j][0])){
                                if(Integer.parseInt(courseInfoElement[j][1])>0 && courseCounter<=3){
                                    if(timeCheck.contains(Integer.parseInt(courseInfoElement[j][2]))==true){
                                        conflictsWriter.write(line[0] + " : "+ line[i]+ " conflicts with "+registeredCourses.get(timeCheck.indexOf(Integer.parseInt(courseInfoElement[j][2])))+"\n");
                                    }else{
                                        courseCounter +=1;
                                        timeCheck.add(Integer.parseInt(courseInfoElement[j][2]));
                                        registeredCourses.add(line[i]);
                                        countSatisfaction=countSatisfaction+maxSatisfaction-i;
                                        outpuWriter.write(line[i]);
                                        courseInfoElement[j][1] = String.valueOf(Integer.parseInt(courseInfoElement[j][1])-1);
                                    }
                                }
                            }
                        }
                    }
                
                    if(courseCounter>=2 && courseCounter<=4){
                        lineCounter=lineCounter+1;
                        satisfactionRate=(float)countSatisfaction/3;
                        totalSatisfaction=totalSatisfaction+satisfactionRate;
                        outpuWriter.write(" :: Satisfaction Rating = "+ String.format("%.2f", satisfactionRate));
                    }else if(courseCounter<=1){
                        lineCounter=lineCounter+1;
                        satisfactionRate=(float)0;
                        totalSatisfaction=totalSatisfaction+satisfactionRate;
                        outpuWriter.write(" :: Satisfaction Rating = "+ String.format("%.2f", satisfactionRate));
                    }
                    outpuWriter.write("\n");
                }
                sc.close();
                outpuWriter.write("__________________________________________\nAverage Satisfaction Rating: " + String.format("%.2f",totalSatisfaction/(lineCounter))+"%");
                outpuWriter.close();
                logWriter.close();
                conflictsWriter.close();

            }catch(FileNotFoundException e){
                try {
                    logWriter.write("File not Found"+ e.getMessage());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("File not Found!");
            }catch(IOException e){
                try {
                    logWriter.write("IO exception Error in Results.java:\n"+ e.getMessage());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.err.println("IO exception Error in Results.java:\n" + e.getMessage());
            }catch(Exception e){
                try {
                    logWriter.write("Exception occurred in Results.java:\n"+ e.getMessage());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.err.println("Exception occurred in Results.java:\n" + e.getMessage());
                e.printStackTrace();
            }
            finally{}
       }
    }
}

//time complexicity = O(n^3) 