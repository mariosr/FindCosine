package FindCosine.FindCosine;

public class Main {
	
    public static void main( String[] args ){
 
    	Process p = new Process();
    	// esse método é para ler as palavras consideradas stopWords, arquivo txt obtido do link passado pelo juliano
    	p.readTxt();
    	//esse metodo verifica o nome de todos os arquivos json contidos em uma pasta
    	p.readAllFiles();
    	//esse metodo ler todos os json das noticias e armazena em o objetos locais
    	p.readJson();
 
    	for (EntityContent e : p.getEntitiesContent()) {
			System.out.println("\n author: " +e.getAuthor() +"\n local: " +e.getLocal()
					+"\n tags size: " +e.getTags().size()
					+"\n paragraphsWords size: " +e.getParagraphs().size());
		}
    	
    	
    }
}
