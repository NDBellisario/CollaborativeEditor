package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
		
		ActionListener listener = new formatListener();
		
		bold = new JButton("Bold");
		bold.addActionListener(listener);
		
		ital = new JButton("Italics");
		ital.addActionListener(listener);
		
		underlined = new JButton("Underline");
		underlined.addActionListener(listener);
		
		colored = new JButton("Color Font");
		colored.addActionListener(listener);
		
		indentLeft = new JButton("Indent Left");
		indentLeft.addActionListener(listener);
		
		indentCenter = new JButton("Indent Center");
		indentCenter.addActionListener(listener);
		
		indentRight = new JButton("Indent Right");
		indentRight.addActionListener(listener);
		
		bullets = new JButton("Bullet Points");
		bullets.addActionListener(listener);
		
		fontType = new JButton("Font Type");
		fontType.addActionListener(listener);
		
		fontSize = new JButton("Font Size");
		fontSize.addActionListener(listener);
		
		annotate = new JButton("Annotate");
		annotate.addActionListener(listener);
		
		insertCode = new JButton("Insert Code");
		insertCode.addActionListener(listener);
		
		
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
        
        this.add(new JLabel("No Document Selected.", SwingConstants.CENTER),BorderLayout.NORTH);
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
               // textBox.setFont(new Font(name, Font.BOLD, alignmentX));
			}
			else if(e.getSource() == ital){
				
			}
            else if(e.getSource() == underlined){
				
			}
            else if(e.getSource() == colored){
				
			}
            else if(e.getSource() == indentLeft){
				
			}
            else if(e.getSource() == indentCenter){
				
			}
            else if(e.getSource() == indentRight){
				
			}
            else if(e.getSource() == bullets){
				
			}
            else if(e.getSource() == fontType){
				
			}
            else if(e.getSource() == fontSize){
				
			}
            else if(e.getSource() == annotate){
				
			}
            else if(e.getSource() == insertCode){
				
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
		ChatView chat = new ChatView("Blitzer");
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
