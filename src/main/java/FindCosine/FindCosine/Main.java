package FindCosine.FindCosine;

import java.util.List;

public class Main {
	
	public static void printMatrix(int[][] matrix){
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3174; j++) {
				if(matrix[i][j] > 0) System.out.println("matrix["+i+"]["+j+"] = " +matrix[i][j] +"\n");
			}
		}
	}
	
	public static void printEntitiesContent(List<EntityContent> list){
		for (EntityContent e : list) {
			System.out.println("\n author: " +e.getAuthor() +"\n local: " +e.getLocal()
					+"\n tags size: " +e.getTags().size()
					+"\n paragraphsWords size: " +e.getParagraphs().size());
		}

	}
	
    public static void main( String[] args ){
 
    	Process p = new Process();
    	// esse método é para ler as palavras consideradas stopWords, arquivo txt obtido do link passado pelo juliano
    	p.readTxt();
    	//esse metodo verifica o nome de todos os arquivos json contidos em uma pasta
    	p.readAllFiles();
    	//esse metodo ler todos os json das noticias e armazena em o objetos locais
    	p.readJson();
 
    	//printEntitiesContent(p.getEntitiesContent());	
    	//printMatrix(p.getOcurrences());
    	
    	double cosine = p.findCosine(p.getVectorWord(3), p.getVectorWord(1));
    	
    	System.out.println("cosine value " +cosine);
    	
    }
    
}
