package lieToMe;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Main {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		try {
			new Window();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
