package hadoopq1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopJobControl {

    //Counters for the different use cases in Hadoop Q1
    public static enum WORDCOUNT_COUNTER {
        KEY_WORDS_UNIQUE,
        KEY_WORDS_STARTING_WITH_Z,
        KEY_WORDS_LESS_THAN_4_TIMES
    };

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "COMP47470-Hadoop Q1");
        job.setJarByClass(HadoopJobControl.class);
        job.setMapperClass(HadoopMapper.class);
        job.setCombinerClass(HadoopCombiner.class);
        job.setReducerClass(HadoopReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean flag = job.waitForCompletion(true);

        // Get the counter values and display them as output
        Counters counters = job.getCounters();
        Counter cntObj = counters.findCounter(WORDCOUNT_COUNTER.
                KEY_WORDS_UNIQUE);
        System.out.println("\nNo. of unique words: " + cntObj.getValue());
        
        cntObj = counters.findCounter(WORDCOUNT_COUNTER.
                KEY_WORDS_STARTING_WITH_Z);
        System.out.println("\nNo. of words starting with z/Z: " 
                + cntObj.getValue());
       
        cntObj = counters.findCounter(WORDCOUNT_COUNTER.
                KEY_WORDS_LESS_THAN_4_TIMES);
        System.out.println("\nNo. of words appearing less than 4 times: " 
                + cntObj.getValue());
        System.out.println();

        if (flag == true) {
            System.exit(0);
        }
        else {
            System.exit(1);
        }
    }

}