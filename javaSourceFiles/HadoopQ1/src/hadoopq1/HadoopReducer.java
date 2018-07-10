package hadoopq1;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HadoopReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        context.getCounter(HadoopJobControl.WORDCOUNT_COUNTER.KEY_WORDS_UNIQUE)
                .increment(1);

        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }

        if (key.toString().startsWith("z")) {
            context.getCounter(HadoopJobControl.WORDCOUNT_COUNTER
                    .KEY_WORDS_STARTING_WITH_Z).increment(1);
        }

        if (sum < 4) {
            context.getCounter(HadoopJobControl.WORDCOUNT_COUNTER
                    .KEY_WORDS_LESS_THAN_4_TIMES).increment(1);
        }

        result.set(sum);
        context.write(key, result);
    }
}
