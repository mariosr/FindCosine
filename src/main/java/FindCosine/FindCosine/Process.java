package FindCosine.FindCosine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Process {
	
	private List<String> stopWords = new ArrayList<String>();
	private List<String> filesName = new ArrayList<String>();
	private List<EntityContent> entitiesContent = new ArrayList<EntityContent>();
	private int[][] ocurrences = new int[5][3174];
	
	public Process(){
		initializeMatrix();
	}
	
	public void initializeMatrix(){
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3174; j++) {
				ocurrences[i][j] = 0;
			}
		}
		
	}
	
	public List<String> getStopWords() {
		return stopWords;
	}

	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}

	public List<String> getFilesName() {
		return filesName;
	}

	public int[][] getOcurrences() {
		return ocurrences;
	}

	public void setOcurrences(int[][] ocurrences) {
		this.ocurrences = ocurrences;
	}

	public void setFilesName(List<String> filesName) {
		this.filesName = filesName;
	}

	public List<EntityContent> getEntitiesContent() {
		return entitiesContent;
	}

	public void setEntitiesContent(List<EntityContent> entitiesContent) {
		this.entitiesContent = entitiesContent;
	}

	public String preProcessingWord(String text){
		
		String result = text.toLowerCase();
		
		result = result.replace( " " , ""); 
		result = result.replace( "." , ""); 
		result = result.replace( "/" , ""); 
		result = result.replace( "-" , ""); 
		result = result.replace( "|" , "");
		result = result.replace( "," , "");
		result = result.replace( ")" , "");
		result = result.replace( "(" , "");
		result = result.replace( ":" , "");
		result = result.replace( "*" , "");
		
		if(stopWords.contains(result) == true) result = null;
		
		return result;
	}
	
	public String preProcessingWordWhiteSpace(String text){
		
		String result = text.toLowerCase();
		
		result = result.replace( "." , ""); 
		result = result.replace( "*" , "");
		result = result.replace( "/" , ""); 
		result = result.replace( "-" , ""); 
		result = result.replace( "|" , "");
		result = result.replace( "," , "");
		result = result.replace( ")" , "");
		result = result.replace( "(" , "");
		result = result.replace( ":" , "");
		
		if(stopWords.contains(result) == true) result = null;
		
		return result;
	}
	
	public void countOcurrenceWord(String word, int numDoc){
		
		// 0 dilma
		// 1 rio
		// 2 olimpiadas
		// 3 brasilia
		
		if(word != null){
	    	Pattern re = Pattern.compile(
							"\\brio\\b|\\bdilma\\b|\\bolimpiada\\b|\\bbrasilia\\b",
							Pattern.CASE_INSENSITIVE);

			Matcher m = re.matcher(word);

			String stringFound = "";
			
			while (m.find()) {
				stringFound = m.group();
			}
			
			if(stringFound.equalsIgnoreCase("dilma")){
				ocurrences[0][numDoc] += 1;	
			}else if(stringFound.equalsIgnoreCase("rio")){
				ocurrences[1][numDoc] += 1;
			}else if(stringFound.equalsIgnoreCase("olimpiadas")){
				ocurrences[2][numDoc] += 1;
			}else if(stringFound.equalsIgnoreCase("brasilia")){
				ocurrences[3][numDoc] += 1;
			}
	
		}
				
	}
	
	public void readTxt(){

		BufferedReader br = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(getProjectPath() +"/src/main/resources/stop.txt"));
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] s = sCurrentLine.split(" ");
				for (int i = 0; i < s.length; i++) {
					if(s[i] != " " && s[i] != "+" && s[i] != " " && s[i] != "| " && s[i].trim().length() > 1 )
						if(preProcessingWord(s[i]) != null) stopWords.add(preProcessingWord(s[i]));
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
	
	public String getProjectPath(){
		File currDir = new File("");
	    String path = currDir.getAbsolutePath();
	    return path;
	}
	
	public void readJson(){
	
	   JSONParser parser = new JSONParser();
	   int numDoc = 0;
   	   	for (String fileName : filesName) {

   	   	   List<String> paragraphsAux = new ArrayList<String>();
   	   	   List<String> tagsAux = new ArrayList<String>();
   	   	   
   	   		try {
   	   			Object obj = parser.parse(new FileReader(getProjectPath()+"/src/main/resources/news/"+fileName));
   	   			JSONObject jsonObject = (JSONObject) obj;
     
                String author = preProcessingWordWhiteSpace((String) jsonObject.get("author"));
                String local = preProcessingWordWhiteSpace((String) jsonObject.get("local"));
                
                countOcurrenceWord(local, numDoc);
                countOcurrenceWord(author, numDoc);
                
                JSONArray tags = (JSONArray) jsonObject.get("tags");
     
                @SuppressWarnings("unchecked")
				Iterator<String> iteratorTags = tags.iterator();
                while (iteratorTags.hasNext()) {
                	 String phrase = iteratorTags.next();
                 	  String[] parts = phrase.split(" ");
                 	  	for (int i = 0; i < parts.length; i++) {
                 	  		String word = preProcessingWord(parts[i]);
                 	  		if (word != null) {
                 	  			countOcurrenceWord(word, numDoc);
                 	  			tagsAux.add(word);
                 	  		}
                 	  	}	
                }
                
                JSONArray paragraphs = (JSONArray) jsonObject.get("paragraphs");
                @SuppressWarnings("unchecked")
                Iterator<String> iteratorParagraphs = paragraphs.iterator();
                while (iteratorParagraphs.hasNext()) {
              	  String phrase = iteratorParagraphs.next();
              	  String[] parts = phrase.split(" ");
              	  	for (int i = 0; i < parts.length; i++) {
              	  		String word = preProcessingWord(parts[i]);
              	  		if (word != null){
              	  			countOcurrenceWord(word, numDoc);
              	  			paragraphsAux.add(word);
              	  		} 
              	  	}	
                }
                
                EntityContent entity = new EntityContent();
                entity.setAuthor(author);
                entity.setLocal(local);
                entity.setTags(tagsAux);
                entity.setParagraphs(paragraphsAux);
                
                entitiesContent.add(entity);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
   	   	numDoc++;
   	   	}
          	
	}
	
	public void readAllFiles(){
		
		File folder = new File(getProjectPath() +"/src/main/resources/news/");
		File[] listOfFiles = folder.listFiles();

		 for (int i = 0; i < listOfFiles.length; i++) {
			 if (listOfFiles[i].isFile()) {
		    	filesName.add(listOfFiles[i].getName());
		     } 
		}
	}
	
	public double findCosine(int[] vectorA, int[] vectorB){
	 
		double result = 0.0;
		double x = 0.0;
		double y = 0.0;
		    for (int i = 0; i < vectorA.length; i++) {
		    	result += vectorA[i] * vectorB[i];
		        x += Math.pow(vectorA[i], 2);
		        y += Math.pow(vectorB[i], 2);
		    }   
		    
		return result / (Math.sqrt(x) * Math.sqrt(y));
	}
	
}
