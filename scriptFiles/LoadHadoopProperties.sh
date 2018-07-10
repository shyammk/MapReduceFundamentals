#############################
##	Name: Shyam Mohan Kizhakekara    ##
##	Topic: Hadoop MapReduce			  	 ##
############################

#!/bin/bash

# Property Variables
LOCAL_JAR_DIR_NAME=../jarFiles
HADOOP_JAR_Q1_NAME=$LOCAL_JAR_DIR_NAME/HadoopQ1.jar
HADOOP_JAR_Q2_NAME=$LOCAL_JAR_DIR_NAME/HadoopQ2.jar
HADOOP_JAR_Q3_NAME=$LOCAL_JAR_DIR_NAME/HadoopQ3.jar
HDFS_PROJECT_DIR=/Project_01
HDFS_INPUT_DIR=$HDFS_PROJECT_DIR/inputFiles
HDFS_OUTPUT_DIR=$HDFS_PROJECT_DIR/outputFiles
HDFS_Q1_OUTPUT_DIR=$HDFS_OUTPUT_DIR/q1Output
HDFS_Q2_OUTPUT_DIR=$HDFS_OUTPUT_DIR/q2Output
HDFS_Q3_OUTPUT_DIR=$HDFS_OUTPUT_DIR/q3Output
HDFS_INPUT_COMMAND_PARAM_STRING=$HDFS_INPUT_DIR/*.txt

#INFO Messages
PROPERTY_FILE_LOAD_INFO="INFO: Property file loaded successfully!"
HDFS_PROJECT_DIR_INFO="INFO: The project directory 17200802_Project01 must be created in the HDFS, before the execution."
HDFS_INPUT_DIR_INFO="INFO: The directory inputFiles must be created in the HDFS and all the relevant input files must be copied, before the execution."
HDFS_OUTPUT_DIR_INFO="INFO: The directory outputFiles must be created in the HDFS, before the execution."
JAR_FOLDER_CHECK_INFO="INFO: The directory jarFiles exists and contains the required jars!"
PRE_REQUISITE_CHECK_INFO="INFO: If all the above-mentioned pre-requisites are in place, the program execution is good to go!"

#ERROR Messages
JAR_FOLDER_CHECK_ERROR="ERR: The directory jarFiles does not exist. Exitting the program!"
JAR_FOLDER_EMPTY_ERROR="ERR: The directory jarFiles does not contain any jar files. Exitting the program!"