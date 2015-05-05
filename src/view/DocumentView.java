package view;
import javax.swing.*;

import model.Doc;
import model.DocumentAssistant;
import controller.CEController;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DocumentView extends JPanel{
    private JList<String> documentList;
    private JButton selectDoc;
    private String[] files;
    private DefaultListModel<String> listDocuments;
    private JFrame frame;
    private CEController theCaller;
    

    public DocumentView(CEController arg, ArrayList<Doc> theLists){
       frame = new JFrame();
     theCaller = arg;
    	this.setLayout(new BorderLayout());
        selectDoc = new JButton("Current Documents");

         listDocuments = new DefaultListModel<String>();

     	ArrayList<Doc> ourDocs = theLists;
     	listDocuments.clear();
     	for(int i = 0; i < ourDocs.size(); i++){
     		listDocuments.addElement(ourDocs.get(i).getDocName());
     	}


        documentList = new JList<String>(listDocuments);

        frame.add(new JScrollPane(documentList), BorderLayout.CENTER);

        //this.add(selectDoc, BorderLayout.SOUTH);

       frame.add(new JLabel("Your Documents"), BorderLayout.NORTH);

        JButton docSelect = new JButton("Open Document");
        frame.add(docSelect, BorderLayout.SOUTH);
        docSelect.addActionListener(new newDocument());
        frame.setVisible(true);
        frame.setResizable(true);
        frame.pack();
    }
    



<<<<<<< HEAD
    public void updateList(ArrayList<Doc> theLists){
    	ArrayList<Doc> ourDocs = theLists;
    	listDocuments.clear();
    	for(int i = 0; i < ourDocs.size(); i++){
    		listDocuments.addElement(ourDocs.get(i).getDocName());
    	}

    	repaint();

=======
    //Haven't filled this in yet.
    @Override
    public void update(Observable o, Object arg) {
    	
        // TODO Auto-generated method stub
>>>>>>> db5fe6deb9394a0815bb990515b3b7f45ce4da14
    }
    


    private class newDocument implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectDoc) {
            	System.out.println("You Clicked The Button!");

            	theCaller.NewDocument();
            	
                //enter (docformat)documentList.getSelectedValue();
            }
        }
        
       

    }
}
