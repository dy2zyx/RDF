package ExtraireDataSets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Dictionnaires {
	
	protected  HashMap<Integer,String> sujets;
	protected  HashMap<Integer,String> objets;
	protected final  String sujetsPath = "G:/NouvApp/RDF data Management/sujets/0_0sujets.csv";
	protected final  String objetsPath = "G:/NouvApp/RDF data Management/objets/0_0objets.csv";
	
	
	protected void createSujetDic() throws IOException {
		sujets = new HashMap<Integer,String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(sujetsPath));
		int index = 1;
		while(br.ready()){
			String sujet = br.readLine();
			sujets.put(index,sujet);
			index++;
		}
	}
	
	protected void createObjetDic() throws IOException {
		objets = new HashMap<Integer,String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(objetsPath));
		int index = 1;
		while(br.ready()){
			String objet = br.readLine();
			objets.put(index,objet);
			index++;
		}
	}

	public static void main(String[] args) throws IOException {
		Dictionnaires dic = new Dictionnaires();
		dic.createSujetDic();
		dic.createObjetDic();
	}
	
}
