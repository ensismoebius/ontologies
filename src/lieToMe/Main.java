package lieToMe;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class Main {
	public static void main(String[] args) {

		try {
			new Window();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
