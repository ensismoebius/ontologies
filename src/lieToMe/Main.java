package lieToMe;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Main {
	public static void main(String[] args) {

		try {
			new Window();
		} catch (OWLOntologyCreationException e) {
			System.out.println("Where is my ontology???!!!");
		}

	}
}
