package LearnToRank;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.core.SimpleAnalyzer;

/**
 * Hello world!
 *
 */
public class Explainer {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: Explainer <index dir>");
            System.exit(1);
        }
        String indexDir = args[0];
        ArrayList<String> queryExpressions = new ArrayList<String>();
        String[] queryArray = new String[]{
                "computer science",                "santa barbara",                "database",                "wireless",                "distributed",
                "cryptography",                "dynamics",                "defense",                "visualization",                "robotics",
                "computational neuroscience",                "natural language processing",                "software engineering",                "computer vision",                "grid computing",
                "animation",                "virtual reality",                "knowledge representation",                "medical image",                "cognitive science",
                "set theory",                "graph theory",                "san diego",                "cambridge university",                "microsoft",
                "combinatorics",                "game theory",                "numerical analysis",                "Lie theory",                "gaussian elimination",
                "strong interaction",                  "Electromagnetism",                "quantum optics",                "acoustics",                "Relativity",
                "particle physics",                "nonlinear",                 "sub-linear",                "asteroid",                "solar"

        };

        String[] queryArray2 = new String[]{
            "stanford",            "harvard",                "biological",                "virus",                "catalyst"
        };

        for (int i = 0; i < queryArray2.length; i++ ){
            queryExpressions.add(queryArray2[i]);
        }

        String content = "";
        String content0 = "";

        File file = new File("/users/Photeinis/Downloads/CS290n/f_features.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        Directory directory = FSDirectory.open(new File(indexDir));
        QueryParser parser = new QueryParser(Version.LUCENE_43,
                "contents", new SimpleAnalyzer(Version.LUCENE_43));

        int qid = 0;

        for (String queryExpression: queryExpressions){

            qid++;
            Query query = parser.parse(queryExpression);
            System.out.println("Query: " + queryExpression);
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc match : topDocs.scoreDocs) {
                Explanation explanation
                        = searcher.explain(query, match.doc);
                System.out.println("----------");
                Document doc = searcher.doc(match.doc);
                content = explanation.toHtml();
                DocInfo docInfo = new DocInfo(content, qid);
                content0 += docInfo.toString();
                System.out.println(content);
            }
        }

        bw.write(content0);
        bw.close();
        directory.close();
    }
}
