package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class ChatView extends JFrame implements Observer
{
 private JPanel chatPanel;
 private JPanel typePanel;
 private JButton sendButton;
 private JTextPane groupText, personalTest;




 public ChatView()
 {
   //Test for sourcetree
 }


 public void setUpGui()
 {
 }

 @Override
 public void update(Observable o, Object arg)
 {
  // TODO Auto-generated method stub
 }
 private class chatSendListener implements ActionListener
 {
  public void actionPerformed(ActionEvent arg0)
  {
  }
 }
 private class documentSelect implements ActionListener
 {
  public void actionPerformed(ActionEvent arg0)
  {
  }
 }
}
