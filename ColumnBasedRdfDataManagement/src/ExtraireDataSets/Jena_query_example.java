package ExtraireDataSets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;



public class Jena_query_example {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

	


		/**
		 * 
		 * Test query
		 * 
		 * 
		 */

		String q = " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ " PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ " PREFIX  owl: <http://www.w3.org/2002/07/owl#>"
				+ " PREFIX univ-bench: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX xml: <http://www.w3.org/XML/1998/namespace>"
				+ " SELECT distinct ?s"
				+ " WHERE { ?s ?p ?o }";


		/**
		 * 
		 * Create a data model and load file
		 * 
		 */

		Model model = ModelFactory.createDefaultModel();

		//String pathToOntology = "G:/NouvApp/RDF data Management/materiel pour TP/EUGen/output/University0_0.rdf";
		String pathToOntology = "G:/NouvApp/RDF data Management/test.owl";

		InputStream in = FileManager.get().open(pathToOntology);

		Long start = System.currentTimeMillis();

		model.read(in, null);

		System.out.println("Import time : " + (System.currentTimeMillis() - start));

		/**
		 * 
		 * Create a query object
		 * 
		 */

		Query query = QueryFactory.create(q);

		start = System.currentTimeMillis();

		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		System.out.println("Query pre-processing time : " + (System.currentTimeMillis() - start));

		/**
		 * 
		 * Execute Query and print result
		 * 
		 */
		start = System.currentTimeMillis();

		try {

			ResultSet rs = qexec.execSelect();
			FileOutputStream fos = new FileOutputStream(new File("text.csv")); 

			ResultSetFormatter.outputAsCSV(fos, rs);

		} finally {
			
			qexec.close();
		}

		System.out.println("Query + Display time : " + (System.currentTimeMillis() - start));

	
	}
}
