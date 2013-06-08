package LearnToRank;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        if (args.length != 2) {
            System.err.println("Usage: Explainer <index dir> <query>");
            System.exit(1);
        }
        String indexDir = args[0];
        String queryExpression = args[1];
        Directory directory = FSDirectory.open(new File(indexDir));
        QueryParser parser = new QueryParser(Version.LUCENE_43,
                "contents", new SimpleAnalyzer(Version.LUCENE_43));
        Query query = parser.parse(queryExpression);
        System.out.println("Query: " + queryExpression);
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query, 100);

        try {

            String content = "";
            String content0 = "";

            File file = new File("/users/Photeinis/Documents/features.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (ScoreDoc match : topDocs.scoreDocs) {
                Explanation explanation
                        = searcher.explain(query, match.doc);
                System.out.println("----------");
                Document doc = searcher.doc(match.doc);
                System.out.println(explanation.toHtml());
                content = explanation.toHtml();
                DocInfo docInfo = new DocInfo(content);
                content0 += docInfo.toString();
            }

            bw.write(content0);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        directory.close();
    }
}
