package view;

import model.User;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.*;

import java.awt.*;
import java.awt.event.*;

public class EditView extends JPanel {
	/**
     *
     */
	private static final long serialVersionUID = 1L;
	private JPanel formatPanel;
	private JEditorPane textBox;
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
	private int permission;

	public EditView(User user) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(700, 600));

		permission = user.getPermission();
		// TODO: fix textBox = new JEditorPane(new
		// HTMLEditorKit().getContentType(), "");
		textBox = new JEditorPane();
		textBox.setEditorKit(new HTMLEditorKit());
		// textBox.setText("\"<html><body><p>hey</p><p></p></body></html>\"");
		textBox.setMargin(new Insets(25, 25, 25, 25));
		// If user's permissions is set to 3, can't edit.
		// TODO: This stuff
		// Change the StyleSheet of HTMLEditor to Bold ON when bold is clicked
		// and others etc..
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/text/html/StyleSheet.html
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/text/html/HTMLEditorKit.html
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/JEditorPane.html
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/text/MutableAttributeSet.html
		// //
		// http://docs.oracle.com/javase/7/docs/api/javax/swing/text/StyleConstants.html#Bold
		// //STYLES!!!

		if (permission == 3)
			textBox.setEditable(false);
		// Otherwise, user can edit.
		else
			textBox.setEditable(true);
		this.add(new JScrollPane(textBox), BorderLayout.CENTER);

		formatPanel = formatPanel();
		this.add(formatPanel, BorderLayout.WEST);

		this.add(new JLabel("No Document Selected.", SwingConstants.CENTER),
				BorderLayout.NORTH);

	}

	public void changePermission(int arg) {
		permission = arg;
	}

	private JPanel formatPanel() {
		JPanel formats = new JPanel();
		formats.setLayout(new GridLayout(12, 1));

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

	// Have not written this yet.
	/*
	 * @Override public void update(Observable o, Object arg){
	 * 
	 * // TODO Auto-generated method stub }
	 */

	// Do not need this method.
	/*
	 * public void setUpGUI(){
	 * 
	 * }
	 */

	public String getText() {
		return textBox.getText();
	}

	public void setText(final String s) {
		
		
		//textBox.setText(s);
		
		int caretPos = textBox.getCaretPosition();
		try {
			textBox.getDocument().insertString(caretPos, s, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		
		
		/*
		  CaretListener cListener = new CaretListener() {
		  
		  @Override public void caretUpdate(CaretEvent caretEvent) {
		  textBox.setCaretPosition(caretEvent.getDot()); textBox.setText(s); }
		  };
		  
		  textBox.addCaretListener(cListener);
		 */

		// textBox.setCaretPosition(caretPosition);
	}

	public class formatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bold) {

			} else if (e.getSource() == ital) {

			} else if (e.getSource() == underlined) {

			} else if (e.getSource() == colored) {

			} else if (e.getSource() == indentLeft) {

			} else if (e.getSource() == indentCenter) {

			} else if (e.getSource() == indentRight) {

			} else if (e.getSource() == bullets) {

			} else if (e.getSource() == fontType) {

			} else if (e.getSource() == fontSize) {

			} else if (e.getSource() == annotate) {

			} else if (e.getSource() == insertCode) {

			}

		}

	}

	/*
	 * private class CaretEvent implements CaretListener {
	 * 
	 * @Override public void caretUpdate(javax.swing.event.CaretEvent
	 * caretEvent) { // TODO Auto-generated method stub Object ce =
	 * caretEvent.getSource(); if (ce == textBox) { dot.setText("" +
	 * caretEvent.getDot()); mark.setText("" + caretEvent.getMark()); } } }
	 */

	/*
	 * private class documentSelect implements MouseListener{
	 * 
	 * public void actionPerformed(ActionEvent e){
	 * 
	 * }
	 * 
	 * @Override public void mouseClicked(MouseEvent e){
	 * 
	 * // TODO Auto-generated method stub }
	 * 
	 * @Override public void mousePressed(MouseEvent e){
	 * 
	 * // TODO Auto-generated method stub }
	 * 
	 * @Override public void mouseReleased(MouseEvent e){
	 * 
	 * // TODO Auto-generated method stub }
	 * 
	 * @Override public void mouseEntered(MouseEvent e){
	 * 
	 * // TODO Auto-generated method stub }
	 * 
	 * @Override public void mouseExited(MouseEvent e){
	 * 
	 * // TODO Auto-generated method stub } }
	 */

	// Main method to test its functionality.
	/*
	 * public static void main(String[] args){ JFrame frame = new JFrame();
	 * frame.setLayout(new BorderLayout()); ChatView chat = new
	 * ChatView("Blitzer"); chat.setVisible(true); frame.add(chat,
	 * BorderLayout.EAST); EditView edit = new EditView(null);
	 * edit.setVisible(true); frame.add(edit, BorderLayout.WEST);
	 * frame.setVisible(true); frame.setResizable(true); frame.pack();
	 * frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); }
	 */
}
