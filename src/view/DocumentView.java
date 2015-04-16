package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class DocumentView extends JFrame implements Observer
{
	private JTextPane documentList;
	private JButton selectDoc;

	public DocumentView()
	{
	}

	public void setUpGUI()
	{
	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
	}
	private class documentSelect implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
		}
	}
}
