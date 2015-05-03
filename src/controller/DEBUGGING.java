package controller;
import server.CEServer;

import javax.swing.*;

public class DEBUGGING {

    public static void main(String[] args) {
        new CEServer();
        String toParse = JOptionPane.showInputDialog(null, "Enter Number Of Clients To Start");
        int num = Integer.parseInt(toParse);
    for(int i = 0; i < num; i++)
        new CEController(null, null, null);
    }
}
