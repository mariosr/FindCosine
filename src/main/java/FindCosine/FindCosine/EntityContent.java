package FindCosine.FindCosine;

import java.util.ArrayList;
import java.util.List;

public class EntityContent {

	private List<String> paragraphsWords = new ArrayList<String>();
	private List<String> tags = new ArrayList<String>();
	private String author;
	private String local;

	public List<String> getParagraphs() {
		return paragraphsWords;
	}

	public void setParagraphs(List<String> paragraphsWords) {
		this.paragraphsWords = paragraphsWords;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

}
