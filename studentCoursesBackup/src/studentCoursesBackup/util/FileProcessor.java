//Code for Read File : https://attacomsian.com/blog/how-to-read-a-file-line-by-line-in-java
//Code for Write File : https://www.w3schools.com/java/java_files_create.asp

package studentCoursesBackup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileProcessor {
       /**
     * Takes the element of courseInfo.txt file and append it in the 2d array which is defined in driver code i.e in driver.java file
     * @param courseInfoElement //2d array to hold elements of courseInfo.txt file
     * @param courseInfoPath //represents the path of courseInfo file which is take from the args[1]
     * @param errLogFilePath //represents the path of courseInfo file which is take from the args[4]
     * @throws IOException
     * */
    public void fetchCourseInfo( String [][] courseInfoElement, String courseInfoPath, String errLogFilePath){
        //String logFilePath="/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/src/studentCoursesBackup/Log/errorLog.txt";
        String logFilePath = errLogFilePath;
        try (FileWriter logWriter = new FileWriter(logFilePath)) {
            try {

                //String courseInfoFilePath = "/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/src/studentCoursesBackup/Input_Output/courseInfo.txt";
                String courseInfoFilePath = courseInfoPath;
                
                File courseInfo = null;
                
                courseInfo = new File(courseInfoFilePath);
                
                Scanner sc = new Scanner(courseInfo);
                
                while (sc.hasNextLine()) {
                    for(int i=0; i<courseInfoElement.length; i++ ){
                        String[] courseInfoLine =sc.nextLine().trim().split(":");
                        for(int j=0; j<courseInfoLine.length; j++){
                            courseInfoElement[i][j]=String.valueOf(courseInfoLine[j]);
                        }
                    }
                }
                sc.close();

            }catch(FileNotFoundException e){
                logWriter.write("Course Info File not found!");
                System.err.println("Course Info File not found!");
            }catch(IndexOutOfBoundsException e){
                logWriter.write("File course Info does not match the criteria: "+ 
                "File must consist of 9 courses leading with corse Id in the start");
                System.err.println("File course Info does not match the criteria: "+ 
                "File must consist of 9 courses leading with corse Id in the start");

            }catch(Exception e){
                System.err.println("Error from FileProcessor.java : \n" + e.getMessage());
            }
            finally{}
        }catch(IOException ex){
            System.err.println("Error in FileProcessor from logWriter");
        }
    }
}

//time complexicity = O(n^3)