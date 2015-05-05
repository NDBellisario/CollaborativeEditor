package view;

import model.Doc;
import model.DocumentAssistant;
import model.User;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.html.*;

import java.awt.*;
import java.awt.event.*;
<<<<<<< HEAD
=======
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
import java.util.ArrayList;

public class EditView extends JPanel {
	/**
     *
     */
	private static final long serialVersionUID = 1L;
	private JPanel formatPanel;
	private JTextPane textBox;
	private JButton bold;
	private JButton ital;
	private JButton underlined;
	private JButton colored;
	private JButton indentCenter;
	private JButton indentRight;
	private JButton bullets;
	private JButton fontType;
	private JButton fontSize;
	private JButton annotate;
	private JButton insertCode;
	private JButton showAnnotations;
	private JPanel annoPopUp;
	private int permission;
	private Style bolder;
	private Style italic;
	private Style underline;
	private Style indentL;
	private Style indentR;
	private Style indentC;
<<<<<<< HEAD
	private String currentDoc;
	private User user;
	private JLabel currentDocLabel;
=======
	private JOptionPane annotationBox;
	private JList<Annotation> scrollAnnoList;
	private DefaultListModel<Annotation> userList;
	private ArrayList<Annotation> annotationList = new ArrayList<Annotation>();
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267

	public EditView(User userArg, ArrayList<Doc> theD) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(700, 600));
		user = userArg;
		permission = user.getPermission();
		// TODO: fix textBox = new JEditorPane(new
		// HTMLEditorKit().getContentType(), "");
		textBox = new JTextPane();
		textBox.setContentType("text/html");
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

		int docIndex = user.getSelectedDoc();

		for (int i = 0; i < theD.size(); i++) {
			if (theD.get(i).getDocIdentification() == docIndex) {
				currentDoc = theD.get(i).getDocName();
			}
		}
		currentDocLabel = new JLabel("TEST",  SwingConstants.CENTER);
		this.add(currentDocLabel, BorderLayout.NORTH);

<<<<<<< HEAD
	}
	public void changeDoc(String argName) {
		currentDocLabel.setText(argName);
		repaint();
		//this.add(new JLabel("Stuff", SwingConstants.CENTER), BorderLayout.NORTH);
		//repaint();
=======
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
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
		// For bold
		bolder = textBox.addStyle("bold", null);
		StyleConstants.setBold(bolder, true);

		ital = new JButton("Italics");
		ital.addActionListener(listener);
		// For italics
		italic = textBox.addStyle("ital", null);
		StyleConstants.setItalic(italic, true);

		underlined = new JButton("Underline");
		underlined.addActionListener(listener);
		// For underline
		underline = textBox.addStyle("underlined", null);
		StyleConstants.setUnderline(underline, true);

		colored = new JButton("Color Font");
		colored.addActionListener(listener);

<<<<<<< HEAD
		indentLeft = new JButton("Align Left");
		indentLeft.addActionListener(listener);
		// for Indent left
		indentL = textBox.addStyle("indentLeft", null);
		StyleConstants.setAlignment(indentL, StyleConstants.ALIGN_LEFT);
=======
		// for Indent left
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267

		indentCenter = new JButton("Indent Center");
		indentCenter.addActionListener(listener);

		indentRight = new JButton("Indent Right");
		indentRight.addActionListener(listener);
		// For indent right
		indentR = textBox.addStyle("indentRight", null);
<<<<<<< HEAD
		StyleConstants.setAlignment(indentL, StyleConstants.ALIGN_RIGHT);
=======
		StyleConstants.setAlignment(indentR, StyleConstants.ALIGN_RIGHT);
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267

		bullets = new JButton("Bullet Points");
		bullets.addActionListener(listener);

		fontType = new JButton("Font Type");
		fontType.addActionListener(listener);

		fontSize = new JButton("Font Size");
		fontSize.addActionListener(listener);

		annotate = new JButton("Annotate");
		annotate.addActionListener(listener);
		// for Annotation

		insertCode = new JButton("Insert Code");
		insertCode.addActionListener(listener);
		
		showAnnotations = new JButton("Show Annotations");
		showAnnotations.addActionListener(listener);

		formats.add(bold);
		formats.add(ital);
		formats.add(underlined);
		formats.add(colored);
		formats.add(indentCenter);
		formats.add(indentRight);
		formats.add(bullets);
		formats.add(fontType);
		formats.add(fontSize);
		formats.add(annotate);
		formats.add(insertCode);
		formats.add(showAnnotations);

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

		// textBox.setText(s);

		int caretPos = textBox.getCaretPosition();
		try {
			textBox.getDocument().insertString(caretPos, s, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}

		/*
		 * CaretListener cListener = new CaretListener() {
		 * 
		 * @Override public void caretUpdate(CaretEvent caretEvent) {
		 * textBox.setCaretPosition(caretEvent.getDot()); textBox.setText(s); }
		 * };
		 * 
		 * textBox.addCaretListener(cListener);
		 */

		// textBox.setCaretPosition(caretPosition);
	}

	public class formatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bold) {
				makeBold();
			} else if (e.getSource() == ital) {
				makeItal();
			} else if (e.getSource() == underlined) {
				makeUnderline();
			} else if (e.getSource() == colored) {

<<<<<<< HEAD
			} else if (e.getSource() == indentLeft) {
				makeIndentLeft();
=======
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
			} else if (e.getSource() == indentCenter) {

			} else if (e.getSource() == indentRight) {
				makeIndentRight();
			} else if (e.getSource() == bullets) {

			} else if (e.getSource() == fontType) {

			} else if (e.getSource() == fontSize) {

			} else if (e.getSource() == annotate) {
				createAnnotation();
			} else if (e.getSource() == insertCode) {
				System.out.println(textBox.getText());
			} else if (e.getSource() == showAnnotations) {
				userList = new DefaultListModel<Annotation>();
				for (Annotation anno : annotationList) {
					userList.addElement(anno);
				}
				scrollAnnoList = new JList<Annotation>(userList);
		        scrollAnnoList.setFont(new Font("Arial", Font.BOLD, 20));
		        JScrollPane currentAnnos = new JScrollPane(scrollAnnoList);	
		        JOptionPane.showMessageDialog(null, currentAnnos);
			}
		}
	}


	public void makeBold() {

		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
<<<<<<< HEAD
			textBox.getStyledDocument().setCharacterAttributes(textBox.getSelectionStart(), textBox.getSelectedText().length(), bolder, false);
=======
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(),
					textBox.getSelectedText().length(), bolder, false);
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setBold(attrs, true);
		}
	}

	public void makeItal() {
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, italic, false);
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setItalic(attrs, true);
		}

	}

	public void makeUnderline() {
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(textBox.getSelectionStart(), len, underline, false);
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setUnderline(attrs, true);
		}
	}

	// Needs work
	public void makeIndentLeft() {
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
<<<<<<< HEAD
			textBox.getStyledDocument().setCharacterAttributes(textBox.getSelectionStart(), len, indentL, false);
=======
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, indentL, false);
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_LEFT);
		}
	}
<<<<<<< HEAD
=======

>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267
	// Needs work
	public void makeIndentRight() {
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
<<<<<<< HEAD
			textBox.getStyledDocument().setCharacterAttributes(textBox.getSelectionStart(), len, indentR, false);
=======
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, indentR, false);
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267

		} else {
			StyleContext context = new StyleContext();
			Style style = textBox.getStyle(context.DEFAULT_STYLE);
			StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
		}
	}
<<<<<<< HEAD
=======

	public void createAnnotation() {

		final int p0 = textBox.getSelectionStart();
		final int p1 = textBox.getSelectionEnd();
			
		int len = textBox.getSelectedText().length();
		
		Highlighter h = textBox.getHighlighter();

		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			try {
				String annoTitle = textBox.getText(p0, len);
				h.addHighlight(p0, p1, DefaultHighlighter.DefaultPainter);
				String comment = JOptionPane.showInputDialog("What is your annotation for: " + annoTitle);
				Annotation annotation = new Annotation(annoTitle, comment);
				annotationList.add(annotation);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
		 * 
		 * try { h.addHighlight(p0, p1, DefaultHighlighter.DefaultPainter);
		 * MouseInputAdapter mouseHandler = new MouseInputAdapter() {
		 * 
		 * public void mouseEntered(MouseEvent event) { checkForHover(event); }
		 * 
		 * 
		 * }; } catch (BadLocationException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } }
		 */
	}

	public class Annotation {
		private String annotation;
		private String title;

		public Annotation(String highlighted, String comment) {
			annotation = comment;
			title = highlighted;
		}
		public String toString() {
			String toReturn = "Your annotation for " + title + " is ";
			toReturn += annotation;
			return toReturn;
			
			
		}
	}

	/*
	 * public void checkForHover(MouseEvent event) {
	 * 
	 * final int p0 = textBox.getSelectionStart(); final int p1 =
	 * textBox.getSelectionEnd();
	 * 
	 * String text; try { text = textBox.getText(p0, p1-p0);
	 * 
	 * FontMetrics metrics = getFontMetrics(textBox.getFont());
	 * 
	 * Graphics g = getGraphics(); Rectangle textBounds =
	 * metrics.getStringBounds(text, g).getBounds(); g.dispose();
	 * 
	 * if (textBounds.contains(event.getPoint())) { System.out.println("fuck");
	 * } else { System.out.println("fuckme"); } repaint(textBounds);
	 * 
	 * } catch (BadLocationException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
>>>>>>> 12bc3c1d48e063913528493f76dfcb9943acd267

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
