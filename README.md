# MapReduceFundamentals
Baby steps towards Hadoop MapReduce

Pre-Requisites:
-----------------
1) A working Hadoop environment must be set-up on the machine.
2) The java version installed on the machine must be 1.8 or above.
3) The folder structure displayed in Section 2, must be followed as such. Any modification of the folder structure will result in the failure of the program.
4) The logged in user must have enough privileges (permissions) to execute the set of commands used in the project.
5) In case, the Hadoop environment is accessed through a virtual-box, please ensure that its RAM is set to a minimum of 4GB for the smooth execution of the project.
6) All the Hadoop servers (dfs, yarn and historyserver) must be up and running, before the execution of this script.

Steps:
--------

--> Download all the relevant input files using the URLs given:
			wget -i ./otherFiles/Download_URLS.txt -P ./inputFiles/
			
--> First, start the DFS, Yarn and History servers.
			start-dfs.sh
			start-yarn.sh
			mr-jobhistory-daemon.sh start historyserver
			
--> Now, execute the below command to create a project directory inside the HDFS.
			hdfs dfs -mkdir /Project_01/
			
--> Also, create a directory inside the project directory for storing the input files.
			hdfs dfs -mkdir /Project_01/inputFiles/
			
--> Now, copy the input files present in the local machine (inside the "inputFiles" directory) to the HDFS inputFiles directory using the below command.
			hdfs dfs -copyFromLocal ../inputFiles/* /Project_01/inputFiles/
			
--> Now, create the output directory inside the project directory for storing the different output files generated as part of the execution.
			hdfs dfs -mkdir /Project_01/outputFiles/
			
--> Next, navigate to the "scriptFiles" directory and execute the following command to assign the required execution permissions for the script files.
			cd scriptFiles/
			chmod 777 ./*

--> Now execute the script "HadoopExecutionPackage.sh". 
			./HadoopExecutionPackage.sh
			
--> Please note that the other script "LoadHadoopProperties.sh" is used to set and define all the relevant property variables required for the execution of the main script. All the properties are already defined in the file and hence, this file need not be modified.

--> The script "HadoopExecutionPackage.sh" assumes that all the above steps are completed before its execution. Because the HDFS environment works little slower, several validations are avoided in the script to reduce the execution time.

--> If any of the folders does not exist and if any file is not present at the required location, errors would be encountered, the program would be terminated.

--> The script is developed as an interactive Unix application. The instructions, user-prompts and menu options have been made as much user-friendly as possible. As you can see, a menu is displayed with an option corresponding to each use-case (question). Select any option of your choice by entering 1/2/3/4.

--> The execution of the script continues till the user chooses option 4 or enters any invalid option i.e., any character other than 1/2/3/4. This avoids the overhead of executing the script multiple times to get the outputs for different questions.

--> If the execution of any jar fails, the corresponding output directory (q1Output /q2Output/q3Output) in HDFS must be deleted and re-created before the next execution, which is created by the master bash script. In case it needs to be done manually, use the following command for the same.
			hdfs dfs -rm -r /Project_01/outputFiles/q1Output/
			
--> The alternate way is to modify the values of the property parameters HDFS_Q1_OUTPUT_DIR, HDFS_Q2_OUTPUT_DIR and HDFS_Q3_OUTPUT_DIR in the script file LoadHadoopProperties.sh present inside the scriptFiles directory.

--> Upon selection of option 1, the script executes the Hadoop job for question 1 i.e., the three different cases find the count of unique words, the no. of words starting with z/Z and the no. of words appearing less than 4 times. The output for this use case will be displayed on the console. A corresponding output file is generated by the Hadoop job, which would be present inside the q1Output folder in HDFS.

--> Upon selection of option 2, the script executes the Hadoop job for question 2 i.e., find the number of words that are unique to a specific file. The output for this use case will be displayed on the console. A corresponding output file is generated by the Hadoop job, which would be present inside the q2Output folder in HDFS. 

--> Upon selection of option 3, the script executes the Hadoop job to compute the five words that appear the most after a specific stop-word, as mentioned in question 3. Here, the user would be prompted to enter a stop-word of his/her choice and based on the input, the five most frequent words after the stop-word and are displayed from the output file. In this case, there is no output on the console. A corresponding output file is generated by the Hadoop job, which would be present inside the q3Output folder in HDFS.

--> Upon selection of option 4, the script execution would be terminated.


