package view;

import model.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
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
	private JComboBox fonts;
	private JComboBox fSize;
	private JComboBox colors;
	private String[] fontList;
	private Color[] col;
	private String[] colList;
	private String[] fSizes;
	private JPanel annoPopUp;
	private int permission;
	private Style bolder;
	private Style italic;
	private Style underline;
	private Style colorStyle;
	private Style fontStyle;
	private Style sizeStyle;
	private JOptionPane annotationBox;
	private String currentDoc;
	private User user;
	private JLabel currentDocLabel;
	private JList<Annotation> scrollAnnoList;
	private DefaultListModel<Annotation> userList;
	private ArrayList<Annotation> annotationList = new ArrayList<Annotation>();

	public EditView(User userArg, ArrayList<Doc> theD) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(700, 600));
		user = userArg;

		// JCOMBO BOX DATA FOR STYLES!!
		fontList = new String[] { Font.SERIF, "Century", "Font.SANS_SERIF", "Serif", "Luxi",
				"Lucida", "Typewriter", "Webdings" };
		fSizes = new String[] { "Small", "Medium", "Large" };
		colList = new String[] { "Black", "Red", "Blue", "Yellow", "Green",
				"Magenta", "Orange", "Pink" };
		col = new Color[] { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW,
				Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK };
		// WHEN COLOR IS BEING CALLED TO UPDATE COLLIST GETS COLOR FROM COL,

		
		// THEY MUST BE THE SAME

		permission = user.getPermission();
		// TODO: fix textBox = new JEditorPane(new
		// HTMLEditorKit().getContentType(), "");
		textBox = new JTextPane();
		//textBox.setContentType("text/html");
		// textBox.setEditorKit(new HTMLEditorKit());
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
		currentDocLabel = new JLabel("New Unsaved Document",
				SwingConstants.CENTER);
		this.add(currentDocLabel, BorderLayout.NORTH);

	}

	public void changeDoc(String argName) {
		currentDocLabel.setText(argName);
		repaint();
		// this.add(new JLabel("Stuff", SwingConstants.CENTER),
		// BorderLayout.NORTH);
		// repaint();
	}

	public String getDocName() {
		return currentDocLabel.getText();
	}

	public void changePermission(int arg) {
		permission = arg;
	}

	private JPanel formatPanel() {
		JPanel formats = new JPanel();
		formats.setLayout(new GridLayout(12, 1));

		ActionListener listener = new formatListener();

		bold = new JButton("Bold");
		bold.addActionListener(new StyledEditorKit.BoldAction());
		// For bold
		bolder = textBox.addStyle("bold", null);
		StyleConstants.setBold(bolder, true);

		ital = new JButton("Italics");
		ital.addActionListener(new StyledEditorKit.ItalicAction());
		// For italics
		italic = textBox.addStyle("ital", null);
		StyleConstants.setItalic(italic, true);

		underlined = new JButton("Underline");
		underlined.addActionListener(new StyledEditorKit.UnderlineAction());
		// For underline
		underline = textBox.addStyle("underlined", null);
		StyleConstants.setUnderline(underline, true);

		colored = new JButton("Color Font");
		colored.addActionListener(listener);

		fonts = new JComboBox(fontList);
		fonts.addActionListener(listener);

		
		fSize = new JComboBox(fSizes);
		fSize.addActionListener(listener);

		colors = new JComboBox(colList);
		colors.addActionListener(listener);

		// for Indent left

		// indentCenter = new JButton("Indent Center");
		// indentCenter.addActionListener(listener);

		// indentRight = new JButton("Indent Right");
		// indentRight.addActionListener(listener);
		// For indent right
		// indentR = textBox.addStyle("indentRight", null);
		// StyleConstants.setAlignment(indentR, StyleConstants.ALIGN_RIGHT);

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
		formats.add(colors);
		formats.add(fonts);
		formats.add(fSize);
		formats.add(bullets);
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
		int oldLen = textBox.getDocument().getLength();
		textBox.setText(s);
		// int caretPos = textBox.getCaretPosition();
		// try {
		// textBox.getDocument().insertString(caretPos, s, null);
		// } catch (BadLocationException ex) {
		// ex.printStackTrace();
		// }
		//int len = textBox.getDocument().getLength();

		/*
		 * CaretListener cListener = new CaretListener() {
		 * 
		 * @Override public void caretUpdate(CaretEvent caretEvent) {
		 * textBox.setCaretPosition(caretEvent.getDot()); textBox.setText(s); }
		 * };
		 * 
		 * textBox.addCaretListener(cListener);
		 */

		//textBox.setCaretPosition(s.length());
		textBox.setCaretPosition(oldLen);
	}

	public class formatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bold) {

			} else if (e.getSource() == ital) {
				// makeItal();
			} else if (e.getSource() == underlined) {
				// makeUnderline();
			} else if (e.getSource() == colors) {
				Action a =	new StyledEditorKit.ForegroundAction(col[colors.getSelectedIndex()].toString(), col[colors.getSelectedIndex()]);
				a.actionPerformed(e);
			} else if (e.getSource() == fonts) {
				Action a = new StyledEditorKit.FontFamilyAction(null, fontList[fonts.getSelectedIndex()]);
				a.actionPerformed(e);
			} else if (e.getSource() == fSize) {
				Action a = new StyledEditorKit.FontSizeAction(null, (fSize.getSelectedIndex() + 2) * 7);
				a.actionPerformed(e);
				//updateFontSize();
			} else if (e.getSource() == bullets) {

			} else if (e.getSource() == fontType) {

			} else if (e.getSource() == annotate) {
			Action a =	new StyledEditorKit.ForegroundAction("Red", Color.RED);
			a.actionPerformed(e);
			createAnnotation();
			} else if (e.getSource() == insertCode) {

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

	/*
	 * public void makeBold() {
	 * 
	 * if (textBox.getSelectionEnd() != textBox.getCaretPosition()) { int len =
	 * textBox.getSelectedText().length(); AttributeSet select =
	 * textBox.getCharacterAttributes(); boolean ifBold = false; if
	 * (select.getAttribute(StyleConstants.Bold) != null) { ifBold =
	 * (select.getAttribute(StyleConstants.Bold).toString()=="true") ? true :
	 * false; } if (ifBold != true) {
	 * textBox.getStyledDocument().setCharacterAttributes
	 * (textBox.getSelectionStart(), len, bolder, true); } else {
	 * textBox.getStyledDocument
	 * ().setCharacterAttributes(textBox.getSelectionStart(), len, bolder,
	 * false); }
	 * 
	 * } else { MutableAttributeSet attrs = textBox.getInputAttributes();
	 * StyleConstants.setBold(attrs, true); } }
	 */
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

	public void changeColor() {
		colorStyle = textBox.addStyle("color", null);
		StyleConstants
				.setForeground(colorStyle, col[colors.getSelectedIndex()]);

		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(textBox.getSelectionStart(), len, colorStyle, false);
			/*
			boolean flag = true;
			for (int i = textBox.getCaretPosition(); i < textBox.getSelectionEnd(); i++) {
				if(!textBox.getStyledDocument().getCharacterElement(i).getAttributes().containsAttribute(colorStyle, Color.RED)) {
					flag = false;
				}
			}
			if (flag == true) {
				int len = textBox.getSelectedText().length();
				textBox.getStyledDocument().setCharacterAttributes(
						textBox.getSelectionStart(), len, colorStyle, false);
			} else {
				JOptionPane.showMessageDialog(null, "You can't color over an annotation!");
			}
			*/
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setForeground(attrs, col[colors.getSelectedIndex()]);

		}
	}

	public void updateFont() {
		fontStyle = textBox.addStyle("font", null);
		StyleConstants.setFontFamily(fontStyle, fonts.getSelectedItem()
				.toString());
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, fontStyle, false);
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setFontFamily(attrs, fonts.getSelectedItem()
					.toString());
		}
	}

	public void updateFontSize() {
		sizeStyle = textBox.addStyle("size", null);
		StyleConstants.setFontSize(sizeStyle,
				(fSize.getSelectedIndex() + 2) * 7);
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, sizeStyle, false);
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setFontSize(attrs,
					(fSize.getSelectedIndex() + 2) * 7);
		}
	}

	public void makeUnderline() {
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			int len = textBox.getSelectedText().length();
			textBox.getStyledDocument().setCharacterAttributes(
					textBox.getSelectionStart(), len, underline, false);
		} else {
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setUnderline(attrs, true);
		}
	}

	/*
	 * public void makeIndentLeft() { if (textBox.getSelectionEnd() !=
	 * textBox.getCaretPosition()) { int len =
	 * textBox.getSelectedText().length();
	 * textBox.getStyledDocument().setCharacterAttributes(
	 * textBox.getSelectionStart(), len, indentL, false); } else {
	 * MutableAttributeSet attrs = textBox.getInputAttributes();
	 * StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_LEFT); } }
	 */
	/*
	 * public void makeIndentRight() { if (textBox.getSelectionEnd() !=
	 * textBox.getCaretPosition()) { int len =
	 * textBox.getSelectedText().length();
	 * textBox.getStyledDocument().setCharacterAttributes(
	 * textBox.getSelectionStart(), len, indentR, false);
	 * 
	 * } else { StyleContext context = new StyleContext(); Style style =
	 * textBox.getStyle(context.DEFAULT_STYLE);
	 * StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT); } }
	 */
	public void createAnnotation() {

		final int p0 = textBox.getSelectionStart();
		final int p1 = textBox.getSelectionEnd();

		int len = textBox.getSelectedText().length();
		
		if (textBox.getSelectionEnd() != textBox.getCaretPosition()) {
			/*
			SimpleAttributeSet as = new SimpleAttributeSet();
			StyleConstants.setBackground(as, Color.yellow);
			StyledDocument doc = (StyledDocument)textBox.getDocument();
			doc.setCharacterAttributes(p0, p1-p0, as, false);
			
			MutableAttributeSet attrs = textBox.getInputAttributes();
			StyleConstants.setForeground(attrs, col[0]);*/
			
			try {
				String annoTitle;
				annoTitle = textBox.getText(p0, len);
				String comment = JOptionPane
						.showInputDialog("What is your annotation for: "
								+ annoTitle);
				Annotation annotation = new Annotation(annoTitle, comment);
				annotationList.add(annotation);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// h.addHighlight(p0, p1, DefaultHighlighter.DefaultPainter);
			
/*
			int cp = textBox.getCaretPosition();
			try {
				while (cp <= len) {
					try {
						h.addHighlight(cp, textBox.getSelectionEnd(),
								DefaultHighlighter.DefaultPainter);
						cp++;
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				}
				
				String annoTitle = textBox.getText(p0, len);
				// h.addHighlight(p0, p1, DefaultHighlighter.DefaultPainter);
				String comment = JOptionPane
						.showInputDialog("What is your annotation for: "
								+ annoTitle);
				Annotation annotation = new Annotation(annoTitle, comment);
				annotationList.add(annotation);

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
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

}
