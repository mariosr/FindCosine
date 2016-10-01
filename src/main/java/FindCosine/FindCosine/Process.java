package FindCosine.FindCosine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Process {
	
	private List<String> stopWords = new ArrayList<String>();
	private List<String> filesName = new ArrayList<String>();
	private List<EntityContent> entitiesContent = new ArrayList<EntityContent>();
	
	public List<String> getStopWords() {
		return stopWords;
	}

	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}

	public List<String> getFilesName() {
		return filesName;
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
   	   	for (String fileName : filesName) {

   	   	   List<String> paragraphsAux = new ArrayList<String>();
   	   	   List<String> tagsAux = new ArrayList<String>();
   	   	   
   	   		try {
   	   			Object obj = parser.parse(new FileReader(getProjectPath()+"/src/main/resources/news/"+fileName));
   	   			JSONObject jsonObject = (JSONObject) obj;
     
                String author = preProcessingWordWhiteSpace((String) jsonObject.get("author"));
                String local = preProcessingWordWhiteSpace((String) jsonObject.get("local"));
                
                JSONArray tags = (JSONArray) jsonObject.get("tags");
     
                @SuppressWarnings("unchecked")
				Iterator<String> iteratorTags = tags.iterator();
                while (iteratorTags.hasNext()) {
                	 String phrase = iteratorTags.next();
                 	  String[] parts = phrase.split(" ");
                 	  	for (int i = 0; i < parts.length; i++) {
                 	  		if (preProcessingWord(parts[i]) != null) tagsAux.add(preProcessingWord(parts[i]));
                 	  	}	
                }
                
                JSONArray paragraphs = (JSONArray) jsonObject.get("paragraphs");
                @SuppressWarnings("unchecked")
                Iterator<String> iteratorParagraphs = paragraphs.iterator();
                while (iteratorParagraphs.hasNext()) {
              	  String phrase = iteratorParagraphs.next();
              	  String[] parts = phrase.split(" ");
              	  	for (int i = 0; i < parts.length; i++) {
              	  		if (preProcessingWord(parts[i]) != null) paragraphsAux.add(preProcessingWord(parts[i]));
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
	
	public void findCosine(String firstWord, String secondWord){
	
	}
	
}
