package view;
import controller.CEController;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import model.User;

public class PermissionView extends JPanel {
    private JList<String> userList;
    private JButton selectDoc;
    private String[] files;
    private DefaultListModel<User> users;
    private JFrame frame;
    private CEController theCaller;
    
    public PermissionView(CEController arg, ArrayList<User> theUserList){
    	
    }
}
