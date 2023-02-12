## Name: Rohit Karnawat

-----------------------------------------------------------------------
-----------------------------------------------------------------------


Following are the commands and the instructions to run ANT on the project.
#### Note: build.xml is present in studentCoursesBackup/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

####Command: ant -buildfile studentCoursesBackup/src/build.xml clean

Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

####Command: ant -buildfile studentCoursesBackup/src/build.xml all

Description: Compiles the code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

####Command: ant -buildfile studentCoursesBackup/src/build.xml run -Darg0=coursePrefs.txt -Darg1=courseInfo.txt -Darg2=regResults.txt -Darg3=regConflicts.txt -Darg4=errorLog.txt

-----------------------------------------------------------------------
## Time Complexicity: O(n^3)
-----------------------------------------------------------------------
## Description:
1. Processed the courseInfo file in the FileProcessor.java file and the results are stored in the 2d array list, so as to work on Course Info data effectively.
2. In Results.java file before processing the file it is been checked if file exists and we can read/write to these files though check method if it returns true then only move forward else stop.
3. Now, read the coursePref file line by line using scanner function, while reading checked for the condition that the course info element has length equals to number of courses.
4. In this step, we create nested for loop outer loop will run for each line of coursePref file i.e for each element in coursePref and the inner loop will loop till it reaches the length of coursePref.
5. Then we check for conditions, like:
   - Using if condition to check if the course name we got from the coursePref file is equal to the course name from CourseInfo file if thats false we continue the loop else  
   - We check if the capacity for the given if greater than zero or not and we havent alloted more than 3 courses  
   - Finally check if the course we are trying to alot does not overlap any previously alloted course timing (we keep a track of priveously alloted course time and alloted course name using array list); if it is trying to overlap then we add the couse in conflict with then name of the course it is trying to allocate.  
   - If it does not overlap the timing, then:  
       - Increase the alloted courses counter   
       - Add the time in the time check array list   
       - Add courses in registered courses array list  
       - Count the satisfaction score  
       - Write the alloted course in the regResults  
       - Decrease the capacity of that course  
6. Now, If we alloted courses between 1 to 3, then:  
   - Increment the line counter (line counter we used to get total number of rows in coursePref file which will help to calculate average satisfaction.)  
   - Calculate the satisfaction score for that student id/ line  
   - Increase total satisfaction  
   - Write the satisfaction rating for that student id/ line in the regResults file  
7. Finally write the average satisfaction rating at the end of the regResults file and close all file writer and scanner functions.
8. For any exception handling, all the code is written under try_catch_finally block.  
