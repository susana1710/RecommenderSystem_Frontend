// http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
//http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
package logica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.LowerCaseFilter;
//port org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;


public class ReadCSV {
// Arreglo de 254 (numero de personas en el archivo csv) que contendra
// la concatenacion de todos los comentarios de una misma persona
// Cada posicion representa una persona distina
static String[] usrComments = new String[254];
public static String[] usrCommentsSeparated = new String[254];
static Set<String> stopWords;

  public void run() throws IOException  {


	this.readFile();
	File file;
	for (int i = 0; i < 254; i++) {
		// 254 archivos de comentarios (un archivo por persona)
		// Contiene los comentarios sin stopwords y con el algoritmo porterstem aplicado
		file = new File ("/home/susana/Miniproyecto/recommender_system/src/main/java/logica/CommentsTagged/removeStopWordsAndStem"+i+".txt");

		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			String separatedInWordsComment = removeStopWordsAndStem(usrComments[i]);
			usrCommentsSeparated[i]= separatedInWordsComment;
			writer.println(usrCommentsSeparated[i]);		
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//System.out.println(usrComments[0]);
	//System.out.println(removeStopWordsAndStem(usrComments[0]));

  }
  //Metodo que agrega las stopwords de archivo "/home/susana/Miniproyecto/Logica/stopwords.txt"
  //al hashset stopWords

  public static void addStopwordsToSet(){
  	String stopwordsFile = "/home/susana/Miniproyecto/recommender_system/src/main/java/logica/stopwords.txt";
  	BufferedReader br = null;
	String line = "";
  	stopWords= new HashSet<String>();

  	try {
		br = new BufferedReader(new FileReader(stopwordsFile));
		//line = br.readLine();
		while ((line = br.readLine()) != null) {
			stopWords.add(line);
			//System.out.println(line);
		}
	} 
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//stopWords.forEach(System.out::println);

  }
  public static String removeStopWordsAndStem(String input) throws IOException {

  	addStopwordsToSet();

    TokenStream tokenStream = new StandardTokenizer(
            Version.LUCENE_30, new StringReader(input));
    tokenStream = new LowerCaseFilter(tokenStream);
    tokenStream = new StopFilter(true, tokenStream, stopWords);
    tokenStream = new PorterStemFilter(tokenStream);

    StringBuilder sb = new StringBuilder();
    StringBuilder sb2;
    TermAttribute termAttr = tokenStream.getAttribute(TermAttribute.class);
    
    while (tokenStream.incrementToken()) {
    	sb2 = new StringBuilder();
        if (sb.length() > 0) {
            sb.append(", ");
        }
        sb2.append("\'");
        sb2.append(termAttr.term());
       	sb2.append("\'");
        sb.append(sb2);
    }
    return sb.toString();
	}


  public void readFile() {
  	// Archivo csv que se va a leer
	String csvFile = "/home/susana/Miniproyecto/recommender_system/src/main/java/logica/DataSetPersonality.csv";
	BufferedReader br = null;
	String line = "";
	//String cvsSplitBy = ",";
	// Archivo donde se imprimiran los comentarios por usuario
	File file = new File ("/home/susana/Miniproyecto/recommender_system/src/main/java/logica/comments.txt");


	try {
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		br = new BufferedReader(new FileReader(csvFile));
		line = br.readLine();
		String usr = "";
		//int usrCount= 0;


		int i = -1;
		// Procesamiento del archivo csv
		// str[1] contiene el id del autor
		// str[2] contiene los status
		while ((line = br.readLine()) != null) {

		        // use comma as separator
			//split on the comma only if that comma has zero, or an even number of quotes ahead of it.
			String otherThanQuote = " [^\"] ";
        	String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        	String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                ",                         "+ // match a comma
                "(?=                       "+ // start positive look ahead
                "  (                       "+ //   start group 1
                "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                "    %s                    "+ //     match 'quotedString'
                "  )*                      "+ //   end group 1 and repeat it zero or more times
                "  %s*                     "+ //   match 'otherThanQuote'
                "  $                       "+ // match the end of the string
                ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);

			String[] str = line.split(regex, -1);
			// Son 254 usuarios. Cada posicion del arreglo tene una concatenacion de sus comentarios.
			// Si entra en el if es porque es una nueva persona
			if (!usr.equals(str[1])){
				// Esto es solo para imprimir en el archivo
				if (i!=-1){
					writer.println(usrComments[i]);
				}
				i++;
				// Se inicializa la casilla de esa persona				
				usrComments[i] = "";
				usr = str[1];
				//usrCount++;
				// Para imprimir en el archivo
				writer.println("User: " + usr + "\n");
			}
			// Concatenacion del nuevo comentario a los anteriores
			usrComments[i] = usrComments[i].concat(" ");
			usrComments[i] = usrComments[i].concat(str[2].replace("\"", ""));

			

			//System.out.println("User: " + str[1] 
            //                     + " , comment=" + str[2] + "]");

		}
		writer.println(usrComments[i]);
		writer.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//System.out.println(usrCount);
	System.out.println("Done");
  }

}
