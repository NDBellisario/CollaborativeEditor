package view;
import javax.swing.*;

import model.Doc;
import model.DocumentAssistant;
import controller.CEController;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DocumentView extends JPanel {
	private JList<String> documentList;
	private JButton selectDoc;
	private String[] files;
	private DefaultListModel<String> listDocuments;
	private JFrame frame;
	private CEController theCaller;
	ArrayList<Doc> ourDocs;

	public DocumentView(CEController arg) {
		frame = new JFrame();
		theCaller = arg;
		this.setLayout(new BorderLayout());
		selectDoc = new JButton("Current Documents");
		listDocuments = new DefaultListModel<String>();
		updateList(new ArrayList<Doc>());
		documentList = new JList<String>(listDocuments);
		frame.add(new JScrollPane(documentList), BorderLayout.CENTER);
		frame.add(new JLabel("Your Documents"), BorderLayout.NORTH);
		JButton docSelect = new JButton("Open Document");
		frame.add(docSelect, BorderLayout.SOUTH);
		docSelect.addActionListener(new SelectDocumentListener());
		Dimension curdim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (theCaller.getWidth()),
                (int) 0); 

		frame.setVisible(true);
		frame.setResizable(true);
		frame.pack();
	}

	public void updateList(ArrayList<Doc> theLists) {


				ourDocs = theLists;

				listDocuments.clear();
				for (int i = 0; i < ourDocs.size(); i++) {
					listDocuments.addElement(ourDocs.get(i).getDocName());
				}
				repaint();



	}

	private class SelectDocumentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

					if (documentList.getSelectedValue() != null) {
						String fileName = documentList.getSelectedValue();
						for (int i = 0; i < ourDocs.size(); i++) {
							if (ourDocs.get(i).getDocName().equals(fileName)) {
								int careID = documentList.getSelectedIndex() + 1;
								theCaller.setCurrentSelectedDoc(careID);
								
							}
						}
					} else {
						JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(frame), "Please Select A Document!");
					}
				}


		}
	

}
