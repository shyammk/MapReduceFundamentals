package hadoopq2;

import java.io.IOException;
import java.util.HashSet;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HadoopReducer extends Reducer<Text, Text, Text, Text> {

    private Text result = new Text();

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {
        
        HashSet<Text> tempSet = new HashSet<>();

        for (Text val : values) {
            tempSet.add(val);
        }

        if (tempSet.size() == 1) {
            
            context.getCounter(HadoopJobControl.WORDCOUNT_COUNTER.
                    KEY_WORDS_UNIQUE_TO_A_FILE).increment(1);
            result.set(tempSet.iterator().next());
            context.write(key, result);
        }
    }
}
