package FindCosine.FindCosine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class App {
	
	private static List<String> stopWords = new ArrayList<String>();
	
	public static String preProcessingWord(String text){
		
		String result = text.toLowerCase();
		
		result = result.replace( " " , ""); //tira espaço em branco
		result = result.replace( "." , ""); //tira ponto
		result = result.replace( "/" , ""); //tira barra
		result = result.replace( "-" , ""); //tira hífen
		result = result.replace( "|" , "");
		result = result.replace( "," , "");
		result = result.replace( ")" , "");
		result = result.replace( "(" , "");
		result = result.replace( ":" , "");
		
		if(stopWords.contains(result) == true) result = "";
		
		return result;
	}
	
	public static void readTxt(){

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("D:/UECE 2016.1/research/stop.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] s = sCurrentLine.split(" ");
				for (int i = 0; i < s.length; i++) {
					if(s[i] != "" && s[i] != "+" && s[i] != " " && s[i] != "| ")
						stopWords.add(preProcessingWord(s[i]));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	public static void readJson(){
	
	   JSONParser parser = new JSONParser();
   	   List<String> paragraphsAux = new ArrayList<String>();
   	   List<String> tagsAux = new ArrayList<String>();
   	   
          try {
   
              Object obj = parser.parse(new FileReader("D:/UECE 2016.1/research/news/culturanoticia201608abldecretalutooficialdetresdiasedestacaparticipacaodepitanguy.json"));
   
              JSONObject jsonObject = (JSONObject) obj;
   
              String author = preProcessingWord((String) jsonObject.get("author"));
              String local = preProcessingWord((String) jsonObject.get("local"));
              
              JSONArray tags = (JSONArray) jsonObject.get("tags");
   
              Iterator<String> iteratorTags = tags.iterator();
              while (iteratorTags.hasNext()) {
           	   tagsAux.add(preProcessingWord(iteratorTags.next()));
              }
              
              JSONArray paragraphs = (JSONArray) jsonObject.get("paragraphs");
              Iterator<String> iteratorParagraphs = paragraphs.iterator();
              while (iteratorParagraphs.hasNext()) {
           	   paragraphsAux.add(preProcessingWord(iteratorParagraphs.next()));
              }
   
          } catch (Exception e) {
              e.printStackTrace();
          }	
	}
	
    public static void main( String[] args ){
    	   
    	readTxt();
    	
    	
    }
}
