package ExtraireDataSets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionnaires {

	protected static HashMap<Integer,Set<Integer>> types_ts = new HashMap<Integer,Set<Integer>>();
	protected static HashMap<Integer,Set<Integer>> types_st = new HashMap<Integer,Set<Integer>>();
	protected static HashMap<Integer,String> dictionnary;
	protected static HashMap<Integer,HashMap<Integer,HashMap<Integer,Set<Integer>>>> indexationsTriplets;
	protected static BufferedReader br;
	protected static final String sposPath = "0_0spos.csv";
	//protected static final String sposPath = "test.csv";
	protected static final String dicPath = "dic.csv";
	
	protected static String subject;
	protected static String predicate;
	protected static String object;
	
	protected static int subject_id;
	protected static int predicate_id;
	protected static int object_id;
	
	protected static List<String> triplets = new ArrayList<String>();
	/**
	 * La fonction de la cr¨¦ation du dictionnaire.
	 * 
	 * @throws IOException
	 */
	protected  static void createDictionnary() throws IOException {
		dictionnary = new HashMap<Integer,String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(dicPath));
		int index = 1;
		while(br.ready()){
			String terme = br.readLine();
			dictionnary.put(index,terme);
			index++;
		}
	}
	
	protected static void ParserTriplet() throws IOException{
		br = new BufferedReader(new FileReader(sposPath));
		while(br.ready()){
			String triplet = br.readLine();
			triplets.add(triplet);	
		}
	}
	
	protected static Object SearchInDictionary(String terme){
		for(int key : dictionnary.keySet()){
			if(dictionnary.get(key).equals(terme)){
				return key;
			}
		}
		return null;
	}	
	
	protected static void TSIndexation(){
		
		for(int i=0;i<triplets.size();i++){
			String[] tripletsArray = triplets.get(i).split(",");
			subject = tripletsArray[0];
			predicate = tripletsArray[1];
			object = tripletsArray[2];

			if(predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				
				int terme_id = (int) SearchInDictionary(subject);
				int type_id = (int) SearchInDictionary(object);
				if(!types_ts.containsKey(type_id)){
					Set<Integer> termesOfType = new HashSet<Integer>();
					termesOfType.add(terme_id);
					types_ts.put(type_id,termesOfType);
				}
				types_ts.get(type_id).add(terme_id);
			}
		}
		//System.out.println(types_ts);
	}
	
	protected static void STIndexation(){
		for(int i=0;i<triplets.size();i++){
			String[] tripletsArray = triplets.get(i).split(",");
			subject = tripletsArray[0];
			predicate = tripletsArray[1];
			object = tripletsArray[2];
			
			if(predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				int terme_id = (int) SearchInDictionary(subject);
				int type_id = (int) SearchInDictionary(object);
				if(!types_st.containsKey(terme_id)){
					Set<Integer> typesOfTerme = new HashSet<Integer>();
					typesOfTerme.add(type_id);
					types_st.put(terme_id, typesOfTerme);
				}
				types_st.get(terme_id).add(type_id);
			}
		}
		//System.out.println(types_st);
	}
	
	protected static Set<Integer> getTypesOfSuject(int sujet_id){
		return types_st.get(sujet_id);
	}
	
	/*protected static void TripletIndexation() throws FileNotFoundException {
		for(int i=0;i<triplets.size();i++){
			String[] tripletsArray = triplets.get(i).split(",");
			subject = tripletsArray[0];
			predicate = tripletsArray[1];
			object = tripletsArray[2];
			int subject_id = (int) SearchInDictionary(subject);
			int predicate_id = (int) SearchInDictionary(predicate);
			int object_id = (int) SearchInDictionary(object);
			indexationsTriplets = new HashMap<Integer,HashMap<Integer,HashMap<Integer,Set<Integer>>>>();
			if(!predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				if(!indexationsTriplets.containsKey(predicate_id)){
					HashMap<Integer,HashMap<Integer,Set<Integer>>> type_sujet_objet = new HashMap<Integer,HashMap<Integer,Set<Integer>>>();
					Set<Integer> types = new HashSet<Integer>();
					types = getTypesOfSuject(subject_id);
					for(int type : types){
						if(!type_sujet_objet.containsKey(type)){
							HashMap<Integer,Set<Integer>> sujet_objet = new HashMap<Integer,Set<Integer>>();
							Set<Integer> objets = new HashSet<Integer>();
							objets.add(object_id);
							sujet_objet.put(subject_id, objets);
							type_sujet_objet.put(type, sujet_objet);
							indexationsTriplets.put(predicate_id,type_sujet_objet);
						}else{
							type_sujet_objet.get(type).get(subject_id).add(object_id);
						}
					}
				}else{
					Set<Integer> types = new HashSet<Integer>();
					types = getTypesOfSuject(subject_id);
					for(int type : types){
						indexationsTriplets.get(predicate_id).get(type).get(subject_id).add(object_id);
					}
				}
			}
		}
	}*/
	
	protected static void TripletIndexations() throws FileNotFoundException {
		indexationsTriplets = new HashMap<Integer,HashMap<Integer,HashMap<Integer,Set<Integer>>>>();
		
		for(int i=0;i<triplets.size();i++){
			String[] tripletsArray = triplets.get(i).split(",");
			subject = tripletsArray[0];
			predicate = tripletsArray[1];
			object = tripletsArray[2];
			int subject_id = (int) SearchInDictionary(subject);
			int predicate_id = (int) SearchInDictionary(predicate);
			int object_id = (int) SearchInDictionary(object);
			Set<Integer> types = getTypesOfSuject(subject_id);
			
			if(!predicate.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")){
				if(!indexationsTriplets.containsKey(predicate_id)){
					HashMap<Integer,HashMap<Integer,Set<Integer>>> tso = new HashMap<Integer,HashMap<Integer,Set<Integer>>>();
					
					for(int type : types){
						if(!tso.containsKey(type)){
							HashMap<Integer,Set<Integer>> so = new HashMap<Integer,Set<Integer>>();
							if(!so.containsKey(subject_id)){
								Set<Integer> objets = new HashSet<Integer>();
								objets.add(object_id);
								so.put(subject_id, objets);
							}else{
								so.get(subject_id).add(object_id);
							}
							tso.put(type, so);
						}else{
							tso.get(type).get(subject_id).add(object_id);
						}
						indexationsTriplets.put(predicate_id, tso);
						
					}
				}
				else if(indexationsTriplets.containsKey(predicate_id)){
					
					HashMap<Integer,HashMap<Integer,Set<Integer>>> tso = indexationsTriplets.get(predicate_id);
					

					for(int type : types){
						
						if(tso.containsKey(type)){
							if(tso.get(type).containsKey(subject_id)){
								//System.out.println("duplicate line in line" + i + " ");
							}else{
								Set<Integer> objets = new HashSet<Integer>();
								objets.add(object_id);
								tso.get(type).put(subject_id, objets);
							}
							tso.get(type).get(subject_id).add(object_id);
						}else{
							HashMap<Integer,Set<Integer>> so = new HashMap<Integer,Set<Integer>>();
							tso.put(type, so);
						}
						HashMap<Integer,Set<Integer>> so = tso.get(type);
						if(so.containsKey(subject_id)){
							so.get(subject_id).add(object_id);
						}else{
							Set<Integer> objets = new HashSet<Integer>();
							objets.add(object_id);
							so.put(subject_id, objets);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		
		
		Dictionnaires dic = new Dictionnaires();
		dic.ParserTriplet();
		dic.createDictionnary();
		dic.TSIndexation();
		dic.STIndexation();
		dic.TripletIndexations();
		
		System.out.println(indexationsTriplets);

	}
	
}
