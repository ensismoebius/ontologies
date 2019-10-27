package lieToMe;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1677291891868774520L;
	private DefaultListModel<String> avaiableSignalsListModel;
	private Queries queries;
	private DefaultListModel<String> selectedSignalsListModel;
	private JList<String> availableSignalsList;
	private JList<String> selectedSignalsList;
	private JButton add;
	private JButton remove;
	private DefaultListModel<String> resultListModel;
	private JList<String> resultList;

	public Window() throws OWLOntologyCreationException {
		super("Lie to me");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		queries = new Queries();

		avaiableSignalsListModel = new DefaultListModel<String>();
		for (String string : queries.getSignals()) {
			avaiableSignalsListModel.addElement(string);
		}

		availableSignalsList = new JList<String>(avaiableSignalsListModel);
		availableSignalsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableSignalsList.setLayoutOrientation(JList.VERTICAL);
		availableSignalsList.setSelectedIndex(0);
		availableSignalsList.setVisibleRowCount(10);
		JScrollPane availableSignalsListScrollPane = new JScrollPane(availableSignalsList);

		selectedSignalsListModel = new DefaultListModel<String>();
		selectedSignalsList = new JList<String>(selectedSignalsListModel);
		selectedSignalsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedSignalsList.setLayoutOrientation(JList.VERTICAL);
		selectedSignalsList.setSelectedIndex(0);
		selectedSignalsList.setVisibleRowCount(10);
		JScrollPane selectedSignalsListScrollPane = new JScrollPane(selectedSignalsList);

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, availableSignalsListScrollPane,
				selectedSignalsListScrollPane);
		jsp.setOneTouchExpandable(true);
		jsp.setDividerLocation(370);

		// Provide minimum sizes for the two components in the split pane.
		Dimension minimumSize = new Dimension(740, 500);
		availableSignalsList.setMinimumSize(minimumSize);
		selectedSignalsList.setMinimumSize(minimumSize);

		resultListModel = new DefaultListModel<String>();
		resultList = new JList<String>(resultListModel);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.setLayoutOrientation(JList.VERTICAL_WRAP);
		resultList.setSelectedIndex(0);
		resultList.setVisibleRowCount(-1);
		JScrollPane resultListScrollPane = new JScrollPane(resultList);

		add = new JButton("Add ->");
		add.addActionListener(this);

		remove = new JButton("<- Remove");
		remove.addActionListener(this);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints coords = new GridBagConstraints();

		this.setLayout(layout);

		coords.gridx = 0;
		coords.gridy = 0;
		coords.gridwidth = 2;
		coords.weightx = 10;
		coords.weighty = 5;
		coords.fill = GridBagConstraints.BOTH;
		layout.setConstraints(jsp, coords);
		this.getContentPane().add(jsp);

		coords.gridx = 0;
		coords.gridy = 1;
		coords.gridwidth = 1;
		coords.weightx = 5;
		coords.weighty = 1;
		layout.setConstraints(add, coords);
		this.getContentPane().add(add);

		coords.gridx = 1;
		coords.gridy = 1;
		coords.gridwidth = 1;
		coords.weightx = 5;
		coords.weighty = 1;
		layout.setConstraints(remove, coords);
		this.getContentPane().add(remove);

		coords.gridx = 0;
		coords.gridy = 2;
		coords.gridwidth = 2;
		coords.weightx = 10;
		coords.weighty = 3;
		layout.setConstraints(resultListScrollPane, coords);
		this.getContentPane().add(resultListScrollPane);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == add) {
			selectSignals();
		} else {
			removeSignals();
		}

		ArrayList<String> signals = new ArrayList<String>();

		for (int i = 0; i < selectedSignalsListModel.getSize(); i++) {
			signals.add(selectedSignalsListModel.getElementAt(i));
		}

		try {
			resultListModel.clear();

			ArrayList<String> res = new ArrayList<String>();
			res = queries.getFelling(signals);
			for (String string : res) {
				resultListModel.addElement(string);
			}
		} catch (OWLOntologyCreationException e1) {
			resultListModel.clear();
		}

	}

	private void selectSignals() {
		int index = availableSignalsList.getSelectedIndex();

		selectedSignalsListModel.addElement(avaiableSignalsListModel.get(index));

		avaiableSignalsListModel.remove(index);

		int size = avaiableSignalsListModel.getSize();

		if (size == 0) { // Nobody's left, disable firing.
			add.setEnabled(false);

		} else { // Select an index.
			remove.setEnabled(true);
			if (index == avaiableSignalsListModel.getSize()) {
				// removed item in last position
				index--;
			}

			availableSignalsList.setSelectedIndex(index);
			availableSignalsList.ensureIndexIsVisible(index);
		}
	}

	private void removeSignals() {
		int index = selectedSignalsList.getSelectedIndex();

		avaiableSignalsListModel.addElement(selectedSignalsListModel.get(index));

		selectedSignalsListModel.remove(index);

		int size = selectedSignalsListModel.getSize();

		if (size == 0) { // Nobody's left, disable firing.
			remove.setEnabled(false);
		} else { // Select an index.
			add.setEnabled(true);
			if (index == selectedSignalsListModel.getSize()) {
				// removed item in last position
				index--;
			}

			selectedSignalsList.setSelectedIndex(index);
			selectedSignalsList.ensureIndexIsVisible(index);
		}
	}
}
