package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class EditView extends JPanel implements Observer{
	
	private JPanel formatPanel;
	private JTextPane textBox;
	private JButton bold;
	private JButton ital;
	private JButton underlined;
	private JButton colored;
	private JButton indentLeft;
	private JButton indentCenter;
	private JButton indentRight;
	private JButton bullets;
	private JButton fontType;
	private JButton fontSize;
	private JButton annotate;
	private JButton insertCode;
	
	private JPanel formatPanel(){
		JPanel formats = new JPanel();
		formats.setLayout(new GridLayout(12,1));
		bold = new JButton("Bold");
		ital = new JButton("Italics");
		underlined = new JButton("Underline");
		colored = new JButton("Color Font");
		indentLeft = new JButton("Indent Left");
		indentCenter = new JButton("Indent Center");
		indentRight = new JButton("Indent Right");
		bullets = new JButton("Bullet Points");
		fontType = new JButton("Font Type");
		fontSize = new JButton("Font Size");
		annotate = new JButton("Annotate");
		insertCode = new JButton("Insert Code");
		
		
		formats.add(bold);
		formats.add(ital);
		formats.add(underlined);
		formats.add(colored);
		formats.add(indentLeft);
		formats.add(indentCenter);
		formats.add(indentRight);
		formats.add(bullets);
		formats.add(fontType);
		formats.add(fontSize);
		formats.add(annotate);
		formats.add(insertCode);
		return formats;
	}

	public EditView(){
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(700, 600));
		
		textBox = new JTextPane();
		textBox.setEditable(true);
		this.add(new JScrollPane(textBox), BorderLayout.CENTER);
		
		formatPanel = formatPanel();
        this.add(formatPanel,BorderLayout.WEST);
	}
	
	//Have not written this yet.
	@Override
	public void update(Observable o, Object arg){
		
		// TODO Auto-generated method stub
	}

	//Do not need this method.
	/*public void setUpGUI(){
		
	}*/

	private class formatListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
            if(e.getSource() == bold){
				
			}
			else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == ital){
				
			}
			
		}
		
	}

	
	/*private class documentSelect implements MouseListener{
		
		public void actionPerformed(ActionEvent e){
			
		}

		@Override
		public void mouseClicked(MouseEvent e){
			
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e){
			
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e){
			
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e){
			
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e){
			
			// TODO Auto-generated method stub
		}
	}*/
	
	//Main method to test its functionality.
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		ChatView chat = new ChatView();
		chat.setVisible(true);
		frame.add(chat, BorderLayout.EAST);
		EditView edit = new EditView();
		edit.setVisible(true);
		frame.add(edit, BorderLayout.WEST);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.pack();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}
