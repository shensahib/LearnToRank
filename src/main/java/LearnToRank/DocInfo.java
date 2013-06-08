package LearnToRank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Photeinis
 * Date: 6/8/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocInfo {

    private int rank;
    private int id;
    private float idf;
    private float tf;
    private float fieldNorm;

    public static int parseID(String input){
        Pattern p = Pattern.compile("doc=\\d*");
        // get a matcher object
        Matcher m = p.matcher(input);

        if (m.find())
        {
            String str = m.group();
            str = str.replace("doc=","");
            return Integer.parseInt(str);
        } else {
            return -1;
        }
    }

    public static float parseIDF(String input){
        Pattern p = Pattern.compile("\\d.* = idf");
        // get a matcher object
        Matcher m = p.matcher(input);

        if (m.find())
        {
            String str = m.group();
            str = str.replace(" = idf","");
            return Float.valueOf(str);
        } else {
            return -1;
        }
    }

    public static float parseTF(String input){
        Pattern p = Pattern.compile("\\d.* = tf");
        // get a matcher object
        Matcher m = p.matcher(input);

        if (m.find())
        {
            String str = m.group();
            str = str.replace(" = tf","");
            return Float.valueOf(str);
        } else {
            return -1;
        }
    }

    public static float parseFieldNorm(String input){
        Pattern p = Pattern.compile("\\d.* = fieldNorm");
        // get a matcher object
        Matcher m = p.matcher(input);

        if (m.find())
        {
            String str = m.group();
            str = str.replace(" = fieldNorm","");
            return Float.valueOf(str);
        } else {
            return -1;
        }
    }

    private int Rank() {
        float score = score();
        if (score >= 0.15){
            rank = 4;
        } else if (score >= 0.11) {
            rank = 3;
        } else if (score >= 0.08){
            rank = 2;
        } else if (score >= 0.05){
            rank = 1;
        } else {
            rank = 0;
        }
        return rank;
    }

    public  DocInfo (String info){
        this.id = parseID(info);
        this.idf = parseIDF(info);
        this.tf = parseTF(info);
        this.fieldNorm = parseFieldNorm(info);
        this.rank = Rank();
    }

    public DocInfo (int Id, float Idf, float Tf, float FieldNorm){
        this.id = Id;
        this.idf = Idf;
        this.tf = Tf;
        this.fieldNorm = FieldNorm;
    }

    public int getId(){
        return id;
    }

    public float fieldWeight() {
        return tf * idf * fieldNorm;
    }

    public float score() {
        return fieldWeight();
    }

    public String toString() {
        String line = Integer.toString(rank) + " qid:1" +
                " 1:" + String.valueOf(idf) +
                " 2:" + String.valueOf(tf) +
                " 3:" + String.valueOf(fieldNorm) +
                " #" + String.valueOf(id) + "\n";

        return line;
    }

}