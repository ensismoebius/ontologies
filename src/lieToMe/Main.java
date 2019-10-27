package lieToMe;

import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Main {

	public static void main(String[] args) throws OWLOntologyCreationException {

		ArrayList<String> signal = new ArrayList<String>();
		signal.add("BreathingNormal");
		signal.add("ArmsRelaxed");
		getFelling(signal);
		
		
		ArrayList<String> teste = getSignals();
		
		for (String string : teste) {
			System.out.println(string);
		}
	}

	private static ArrayList<String> getSignals() throws OWLOntologyCreationException {
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("ontologies/tellingLies.owl");

		ArrayList<String> resultsList = new ArrayList<String>();

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX : <http://www.semanticweb.org/ensismoebius/ontologies/2019/9/tellingLies#>\n"
				+ "SELECT  DISTINCT ?name  WHERE { ?directSub rdfs:subClassOf ?super ;"
				+ "rdfs:subClassOf [ rdf:type owl:Restriction ;"
				+ "owl:onProperty :hasCaracteristic; owl:someValuesFrom ?name] ."
				+ "	FILTER( ?name != :EmotionsSignals )} order by (?name)";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		ResultSet results = qexec.execSelect();
		while (results.hasNext()) {
			QuerySolution qsol = results.nextSolution();
			resultsList.add(qsol.get("name").toString());
		}

		qexec.close();

		return resultsList;
	}

	private static void getFelling(ArrayList<String> signals) throws OWLOntologyCreationException {
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel("ontologies/tellingLies.owl");

		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX : <http://www.semanticweb.org/ensismoebius/ontologies/2019/9/tellingLies#>\n"
				+ "SELECT DISTINCT ?directSub " + "WHERE { ";

		for (String signal : signals) {
			queryString += "?directSub rdfs:subClassOf ?super ;" + "rdfs:subClassOf [" + "rdf:type owl:Restriction ;"
					+ "owl:onProperty :hasCaracteristic ;" + "owl:someValuesFrom :" + signal + "] .";
		}

		queryString += "}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution qsol = results.nextSolution();
			RDFNode name = qsol.get("directSub");
			System.out.println(name.toString());
		}

		qexec.close();
	}

}
