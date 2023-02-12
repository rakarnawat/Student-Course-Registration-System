//Code for Write File : https://www.w3schools.com/java/java_files_create.asp
package studentCoursesBackup.driver;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import studentCoursesBackup.util.FileDisplayInterface;
import studentCoursesBackup.util.FileProcessor;
import studentCoursesBackup.util.Results;

public class Driver {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * As the build.xml specifies the arguments as argX, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */

		
		if (args.length != 5 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}")
				|| args[3].equals("${arg3}") || args[4].equals("${arg4}")) {

			System.err.println("Error: Incorrect number of arguments. Program accepts 5 argumnets.");
			System.exit(0);
		}
		else{
			
			String coursePrefFilePath = args[0];
			String courseInfoFilePath = args[1];
			String regResultsFilePath = args[2];
			String regConflictsFilePath = args[3];
			String errLogFilePath = args[4];
			
			//All files path:
						
			 //String coursePrefFilePath = "/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/coursePref.txt"; 
			 //String courseInfoFilePath = "/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/courseInfo.txt";
    		 //String regResultsFilePath = "/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/regResults.txt";
    		 //String regConflictsFilePath = "/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/regConflicts.txt";
			 //String errLogFilePath="/home/rkarnaw1/cs542-fall-22-assign1-rakarnawat-1/studentCoursesBackup/errorLog.txt";
			
			String errorMsg = "Error!, File course Info does not match the criteria: File must consist of 9 courses leading with corse Id in the start";
			try{
				// number of rows and colums in course info file
				int rowNumber=9;
				int columnNumber=3; 
				String [][] courseInfoElement = new String[rowNumber][columnNumber];
				
				FileProcessor FP = new FileProcessor();
				FP.fetchCourseInfo(courseInfoElement, courseInfoFilePath, errLogFilePath);

				//program to Interface
				FileDisplayInterface FDI = new Results();
				FDI.fetchCoursePref(courseInfoElement, coursePrefFilePath,courseInfoFilePath,regConflictsFilePath,regResultsFilePath,errLogFilePath,rowNumber);

			}catch(IndexOutOfBoundsException e){
				try (FileWriter logWriter = new FileWriter(errLogFilePath)) {
					logWriter.write(errorMsg + "\n");
					System.err.println(errorMsg);
						
				}catch(FileNotFoundException ex){
					System.err.println("Log File not found");
				}
				catch(IOException ex){
					System.err.println("IO exception : "+ ex);
				}
				catch (Exception ex) {
					System.err.println("Error from Driver code: " + ex);
				}
				finally{}
			}finally{}
		}
	}
}

//total time complexicity = O(n^3)