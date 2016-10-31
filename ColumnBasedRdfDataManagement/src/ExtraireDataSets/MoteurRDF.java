package ExtraireDataSets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoteurRDF {
	protected static final String inputPath = "input.csv";
	protected BufferedReader br;
	protected static List<String> inputs; 
	
	public void initialInput() throws IOException{
		 inputs = new ArrayList<String>();
		 br = new BufferedReader(new FileReader(inputPath)); 
		 while(br.ready()){
			 String line = br.readLine();
			 inputs.add(line);
		 }
	}
	
	public static boolean hasType() throws FileNotFoundException{
		for(int i=0;i<inputs.size();i++){
			String[] lineArray = inputs.get(i).split(" ");
			if(lineArray[1].equals("rdf:type") || lineArray[1].equals("a") || lineArray[1].equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected Set<String> ordrePrioritaireInput() throws FileNotFoundException{
		Set newOrdre = new HashSet<String>();
		Set ordre = new HashSet<String>();

		for(int i=0;i<inputs.size();i++){
			String[] lineArray = inputs.get(i).split(" ");
			if(lineArray[1].equals("rdf:type") || lineArray[1].equals("a") || lineArray[1].equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				ordre.add(inputs.get(i));
			}
		}
	}
	
	
	
	
	
	
	
	
}







