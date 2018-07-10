package hadoopq3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopJobControl {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("inputStopWord", args[2]);
        Job job = Job.getInstance(conf, "COMP47470-Hadoop Q3");
        job.setJarByClass(HadoopJobControl.class);
        job.setMapperClass(HadoopMapper.class);
        job.setCombinerClass(HadoopCombiner.class);
        job.setReducerClass(HadoopReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        boolean flag = job.waitForCompletion(true);
        
        System.out.println("Five words that appear the most after the stopword '"
        +args[2].toLowerCase()+"': ");
        
        if (flag == true) {
            System.exit(0);
        }
        else {
            System.exit(1);
        }
    }

}
