package hadoopq3;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HadoopMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private String stopWord;
    private final String splitDelim = " ";
    private final String punctTokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@~]";

    @Override
    public void setup(Context context) throws InterruptedException, IOException {

        super.setup(context);

        // Get the respective stop-word received as input from the user
        Configuration conf = context.getConfiguration();
        stopWord = conf.get("inputStopWord").toLowerCase();
    }

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        /* Removing all the punctuations & special characters from the file 
           contents and converting it to the lower case. */
        String valueString = value.toString().replaceAll("\r\n", "\n")
                .replaceAll("--", " ")
                .replaceAll("[-—‘’”“•´]", "")
                .replaceAll(punctTokens, " ")
                .replaceAll("[\\W]+", " ")
                .toLowerCase();

        /* Split the valueString, get one word array and compare it against the
           stop-word received as input. If they match, then fetch the next word
           and set it as the key. Set the value as one. */
        String[] valueArray = valueString.trim().split(splitDelim);
        String currentToken;
        String nextToken;
        int j;       
        for (int i = 0; i < valueArray.length; i++) {          
            currentToken = valueArray[i].trim();
            j = i + 1;
            nextToken = (j < valueArray.length) ? valueArray[j].trim() : "";          
            if (currentToken.equalsIgnoreCase(stopWord)) {
                if (nextToken != null && !nextToken.isEmpty()) {
                    word.set(nextToken);
                    context.write(word, one);
                }
            }
        }
    }
}
