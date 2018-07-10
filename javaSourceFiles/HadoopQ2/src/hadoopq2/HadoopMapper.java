package hadoopq2;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class HadoopMapper extends Mapper<Object, Text, Text, Text> {
    
    private Text word = new Text();
    private Text value = new Text();
    private final String splitDelim = " ";
    private final String punctTokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@~]";
    private String localname;

    @Override
    public void setup(Context context) throws InterruptedException, IOException {
        
        super.setup(context);
        
        /* Fetching the source of the contents i.e., the file from which the 
           content is obtained */
        localname = ((FileSplit) context.getInputSplit()).getPath().getName();
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
        
        /* Split the valueString, get the elements in an array and push the 
           corresponding key and value pairs into the map.   */
        String[] valueArray = valueString.trim().split(splitDelim);
        for (String element : valueArray) {
            if (element!=null && !element.trim().isEmpty()) {
                word.set(element.trim());
                value.set(localname);
                context.write(word, value);
            }
        }
    }
}
