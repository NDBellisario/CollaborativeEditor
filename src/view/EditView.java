package view;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class EditView extends JFrame implements Observer
{
	private JPanel editPanel;
	private JTextPane textBox;
	private JButton bold;
	private JButton ital;
	private JButton colored;
	private JButton fonts;
	private JButton sizes;

	public EditView()
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
	private class documentSelect implements MouseListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub
		}
	}
}
