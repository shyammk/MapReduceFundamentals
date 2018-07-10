package hadoopq1;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HadoopMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private final String splitDelim = " ";
    private final String punctTokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@~]";

    @Override
    public void map(Object key, Text value, Context context) throws
            IOException, InterruptedException {

        /* Removing all the punctuations & special characters from the file 
           contents and converting it to the lower case. */
        String valueString = value.toString().replaceAll("\r\n", "\n")
                .replaceAll("--", " ")
                .replaceAll("[-—‘’”“•´\uFEFF]", "")
                .replaceAll(punctTokens, " ")
                .replaceAll("[\\s]+", " ")
                .toLowerCase();
        
        /* Split the valueString, iterate through the different tokens and
           map the unique words.    */
        String[] valueArray = valueString.trim().split(splitDelim);
        for (String element : valueArray) {
            if (element!=null && !element.trim().isEmpty()) {
                word.set(element.trim());
                context.write(word, one);
            }
        }
    }
}
