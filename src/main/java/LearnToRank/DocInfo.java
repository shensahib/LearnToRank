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

    private int id;
    private float idf;
    private float queryNorm;
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

    public static float parseQueryNorm(String input){
        Pattern p = Pattern.compile("\\d.* = queryNorm");
        // get a matcher object
        Matcher m = p.matcher(input);

        if (m.find())
        {
            String str = m.group();
            str = str.replace(" = queryNorm","");
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

    public  DocInfo (String info){
        this.id = parseID(info);
        this.idf = parseIDF(info);
        this.queryNorm = parseQueryNorm(info);
        this.tf = parseTF(info);
        this.fieldNorm = parseFieldNorm(info);
    }

    public DocInfo (int Id, float Idf, float QueryNorm, float Tf, float FieldNorm){
        this.id = Id;
        this.idf = Idf;
        this.queryNorm = QueryNorm;
        this.tf = Tf;
        this.fieldNorm = FieldNorm;
    }

    public int getId(){
        return id;
    }

    public float queryWeight() {
        return idf * queryNorm;
    }

    public float fieldWeight() {
        return tf * idf * fieldNorm;
    }

    public float score() {
        return queryWeight() * fieldWeight();
    }

    public String toString() {
        String line = String.valueOf(id) + " qid:1" +
                " 1:" + String.valueOf(idf) +
                " 2:" + String.valueOf(queryNorm) +
                " 3:" + String.valueOf(tf) +
                " 4:" + String.valueOf(fieldNorm) + "\n";

        return line;
    }

}