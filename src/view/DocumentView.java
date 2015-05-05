package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DocumentView extends JPanel implements Observer {
    private JList<String> documentList;
    private JButton selectDoc;
    private String[] files;

    public DocumentView(String[] files) {
        this.setLayout(new BorderLayout());
        selectDoc = new JButton("Select Doc");

        DefaultListModel<String> listDocuments = new DefaultListModel<String>();
        this.files = files;

        for (int i = 0; i < files.length; i++) {
            listDocuments.add(i, files[i]);
        }

        documentList = new JList<String>(listDocuments);

        this.add(new JScrollPane(documentList), BorderLayout.CENTER);

        this.add(selectDoc, BorderLayout.SOUTH);

        this.add(new JLabel("Welcome <username>, Select a " + "Doc to Edit!"), BorderLayout.NORTH);
    }

    //No need for this method.
    /*public void setUpGUI(){

	}*/

    public static void main(String[] args) {
        String[] arr = new String[7];
        for (int i = 0; i < 7; i++) {
            arr[i] = "String" + i;
        }
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        DocumentView doc = new DocumentView(arr);
        frame.add(doc, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Haven't filled this in yet.
    @Override
    public void update(Observable o, Object arg) {

        // TODO Auto-generated method stub
    }

    private class documentSelect implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectDoc) {
                //enter (docformat)documentList.getSelectedValue();
            }
        }

    }
}
