package hadoopq3;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HadoopReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();
    public static Map<String, Integer> valueMap = new HashMap<>();

    // Method to sort the contents of the map based on the values.
    public static Map<String, Integer> returnSortedMap(Map<String, Integer> tempMap) {

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        tempMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return sortedMap;
    }

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int sum = 0;

        for (IntWritable val : values) {
            sum += val.get();
        }
        
        valueMap.put(key.toString(), sum);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        Map<String, Integer> sortedMap = returnSortedMap(valueMap);
        int c = 0;

        /* Get the map containing the values given by the reducer. Sort the map
           using the method defined for reverse-sorting the obtained map based 
           on its values. Once sorted, display the first five key-value pairs. */
        for (String key : sortedMap.keySet()) {
     
            if (c < 5) {
                result.set(sortedMap.get(key));
                context.write(new Text(key), result);
            } else {
                break;
            }
            c++;
        }   
    }   
}
