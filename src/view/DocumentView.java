package view;
import javax.swing.*;

import model.Doc;
import model.DocumentAssistant;
import controller.CEController;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * @class DocumentView
 * @author Nicholas,Omri,Cameron,Taylor,Eric Team Amphetamine Salts 
 * Holds and displays current iteration of document from server and allows for interation between document
 */
public class DocumentView extends JPanel {
	private JList<String> documentList;
	private JButton selectDoc;
	private String[] files;
	private DefaultListModel<String> listDocuments;
	private JFrame frame;
	private CEController theCaller;
	ArrayList<Doc> ourDocs;
/**
 * Constructor for DocumentView 
 * @param arg - CEController
 */
	public DocumentView(CEController arg) {
		frame = new JFrame();
		theCaller = arg;
		this.setLayout(new BorderLayout());
		selectDoc = new JButton("Current Documents");
		listDocuments = new DefaultListModel<String>();
		ArrayList<Doc> temp = new ArrayList<Doc>();
		updateList(temp);
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
/**
 * Update List of documents that are available to edit 
 * @param theLists
 */
	public void updateList(ArrayList<Doc> theLists) {


				ourDocs = theLists;
//				System.out.println("Update List In DocView.  Contents Are:");
//				for(Doc temp : theLists)
//				{
//					System.out.println( temp.getDocName()+ " ");
//				}

				listDocuments.clear();
				for (int i = 0; i < ourDocs.size(); i++) {
					listDocuments.addElement(ourDocs.get(i).getDocName());
				}

			
				//repaint();
				//System.out.println("REPAINT CALLLED!");



	}
	/**
	 * Returns an ArrayList of all Documents 
	 * @return ArrayList - [Documents] 
	 */
	public ArrayList<Doc> getDocs(){
		return ourDocs;
	}
/**
 * @class SelectDocumentListener
 * @author NDBellisario
 * Selects current document in view for editing 
 */
	private class SelectDocumentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

					if (documentList.getSelectedValue() != null) {
						String fileName = documentList.getSelectedValue();
						for (int i = 0; i < ourDocs.size(); i++) {
							if (ourDocs.get(i).getDocName().equals(fileName)) {
								int careID = documentList.getSelectedIndex() + 1;
								theCaller.setCurrentSelectedDoc(careID);
								theCaller.updateDocName();
								
							}
						}
					} else {
						JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(frame), "Please Select A Document!");
					}
				}


		}
	

}
