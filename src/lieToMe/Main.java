package lieToMe;

import java.io.File;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Main {

	public static void main(String[] args) throws OWLOntologyCreationException {
		File file = new File("ontologies/tellingLies.owl");

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

		System.out.println("Module loaded: " + ontology);

		java.util.Set<OWLEntity> entOnt = ontology.getSignature();

		for (OWLEntity a : entOnt) {
			System.out.println("Entity " + a);
		}

		Reasoner hermit = new Reasoner(ontology);

	}
}
