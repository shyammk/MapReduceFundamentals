#############################
##	Name: Shyam Mohan Kizhakekara    ##
##	Topic: Hadoop MapReduce			  	 ##
############################

#!/bin/bash

: '
	Function to check the execution status of the command and take appropriate steps. It accepts three parameters - the exit code of a command, the corresponding success message and corresponding failure message.
'
getCommandExecStatus() {
	if [ $1 -ne 0 ]
	then
		echo $2
		exit
	else
	echo $3
	fi
}

# Loading the properties file required for the execution of the script.
. LoadHadoopProperties.sh
getCommandExecStatus $? "$PROPERTY_FILE_LOAD_ERROR" "$PROPERTY_FILE_LOAD_INFO"

# Checking if the jar directory exists and contains the jars
if [ ! -d $LOCAL_JAR_DIR_NAME ]
then
	echo "$JAR_FOLDER_CHECK_ERROR"
	exit
elif [ -z "$(ls -A $LOCAL_JAR_DIR_NAME)" ]
then
	echo "$JAR_FOLDER_EMPTY_ERROR"
	exit
else
	chmod 777 $LOCAL_JAR_DIR_NAME/*
	echo "$JAR_FOLDER_CHECK_INFO"
fi

# All pre-requisite checks done.
echo "$HDFS_PROJECT_DIR_INFO"
echo "$HDFS_INPUT_DIR_INFO"
echo "$HDFS_OUTPUT_DIR_INFO"
echo "$PRE_REQUISITE_CHECK_INFO"

# Function to format output structure.
formatOutputStructure() {
cat<< EOF
-------------------------------------------------------
EOF
}

#Use Case 01
: ' 
This job handles three cases:
	a) What is the number of distinct words in the corpus? 
	b) How many words start with the letter Z/z? 
	c) How many words appear less than 4 times?	
'
hadoopUseCaseOne() {
	hdfs dfs -rm -r "$HDFS_Q1_OUTPUT_DIR"
	hadoop jar "$HADOOP_JAR_Q1_NAME" "$HDFS_INPUT_COMMAND_PARAM_STRING" "$HDFS_Q1_OUTPUT_DIR"
}

#Use Case 02
: ' 
This job handles one case:
	How many terms appear in only one single document? Such words may appear multiple times in one document, but they have to appear in only one document in the corpus.
'
hadoopUseCaseTwo() {
	hdfs dfs -rm -r "$HDFS_Q2_OUTPUT_DIR"
	hadoop jar "$HADOOP_JAR_Q2_NAME" "$HDFS_INPUT_COMMAND_PARAM_STRING" "$HDFS_Q2_OUTPUT_DIR"
}

#Use Case 03
: ' 
This job handles one case:
	a) Take one stopword (e.g., the, and) and compute the five words that appear the most after it. 
	E.g. "the cat belongs to the old lady from the hamlet" ! "cat ", "old" and "hamlet" would be candidates. The output should contains 5 lines with the words and their frequency.	
'
hadoopUseCaseThree() {
	
	echo "Enter any stopword of your choice: "
	read stopWord	
	
	hdfs dfs -rm -r "$HDFS_Q3_OUTPUT_DIR"
	hadoop jar "$HADOOP_JAR_Q3_NAME" "$HDFS_INPUT_COMMAND_PARAM_STRING" "$HDFS_Q3_OUTPUT_DIR" ${stopWord}
	hdfs dfs -cat "$HDFS_Q3_OUTPUT_DIR"/part-r-00000
}

: '
	The user gets to choose the specific operation he/she wishes to perform through the script.
	A user menu for the script would be displayed.
	The user has to choose an option from the list (single character).
	The corresponding operation would be performed and the output(s) would be displayed.
	The entry of an invalid input character would exit the program.
	There is an option for the user to exit the program as well.
'
while :
do
cat<< EOF
=======================================================
COMP47470 - PART 02 - HADOOP
-------------------------------------------------------
Please enter your choice from the below menu (1/2/3/4):

(1) Job to execute HADOOP-Use Case 01
(2) Job to execute HADOOP-Use Case 02
(3) Job to execute HADOOP-Use Case 03
(4) Exit	
-------------------------------------------------------
EOF
    read -n1 -s
    case "$REPLY" in
    "1")  echo "You chose Option 1: Hadoop Job to execute Use Case 01" 
		formatOutputStructure
		hadoopUseCaseOne ;;
    "2")  echo "You chose Option 2: Hadoop Job to execute Use Case 02" 
		formatOutputStructure
		hadoopUseCaseTwo ;;
    "3")  echo "You chose Option 3: Hadoop Job to execute Use Case 03" 
		formatOutputStructure
		hadoopUseCaseThree ;;
	"4")  echo "You chose Option 4: Exit the Program!" 
		formatOutputStructure
		exit ;;
     *)  echo "Invalid Option. Exitting the program!"
		formatOutputStructure
		exit ;;
    esac
    sleep 1
done
