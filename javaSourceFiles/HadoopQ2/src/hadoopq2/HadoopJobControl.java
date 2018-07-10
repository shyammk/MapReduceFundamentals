package hadoopq2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopJobControl {

    //Counters for the use case in Hadoop Q2
    public static enum WORDCOUNT_COUNTER {
        KEY_WORDS_UNIQUE_TO_A_FILE,
    };

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "COMP47470-Hadoop Q2");
        job.setJarByClass(HadoopJobControl.class);
        job.setMapperClass(HadoopMapper.class);
        // job.setCombinerClass(HadoopReducer.class);
        job.setReducerClass(HadoopReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean flag = job.waitForCompletion(true);

        Counters jobCounters = job.getCounters();
        Counter counter = jobCounters.findCounter(
                WORDCOUNT_COUNTER.KEY_WORDS_UNIQUE_TO_A_FILE);
        System.out.println("No. of Words Unique to a specific File: " +
                counter.getValue());

        if (flag == true) {
            System.exit(0);
        }
    }

}
