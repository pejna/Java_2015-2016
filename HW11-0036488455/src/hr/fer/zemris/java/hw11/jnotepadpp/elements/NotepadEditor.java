package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;

/**
 * Text editor for use with {@link Notepad} implementing classes.
 * <p>
 * Provides a scrollable expanding text area for user to write to. Provides
 * basic text editing and listener implementing methods.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 */
public class NotepadEditor extends JScrollPane {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = -7110433077407995858L;

	/**
	 * Text area to write to.
	 */
	private JTextArea editor;


	/**
	 * Creates an empty editor.
	 */
	public NotepadEditor() {
		super(new JTextArea());
		editor = (JTextArea) getViewport().getView();
	}


	/**
	 * Creates an editor with given text.
	 * 
	 * @param text
	 *            text of the editor
	 */
	public NotepadEditor(String text) {
		super(new JTextArea(text));
		editor = (JTextArea) getViewport().getView();
	}


	/**
	 * Gets the text of the editor.
	 * 
	 * @return returns the text
	 */
	public String getText() {
		return editor.getText();
	}


	/**
	 * Gets the caret of the editor.
	 * 
	 * @return gets the caret
	 */
	public Caret getCaret() {
		return editor.getCaret();
	}


	/**
	 * Gets the caret position.
	 * 
	 * @return returns the caret position
	 */
	public int getCaretPosition() {
		return editor.getCaretPosition();
	}


	/**
	 * Adds a caret Listener.
	 * 
	 * @param caretListener listener for the caret changes
	 */
	public void addCaretListener(CaretListener caretListener) {
		if (editor == null) {
			return;
		}
		editor.addCaretListener(caretListener);
	}


	@Override
	public synchronized void addKeyListener(KeyListener l) {
		editor.addKeyListener(l);
	}


	/**
	 * Copies the text between Caret.getDot() and Caret.getMark().
	 */
	public void copy() {
		editor.copy();
	}


	/**
	 * Cuts the text between Caret.getDon() and Caret.getMark().
	 */
	public void cut() {
		editor.cut();
	}


	/**
	 * Gets the document containing the text.
	 * 
	 * @return returns the document
	 */
	public Document getDocument() {
		return editor.getDocument();
	}


	/**
	 * Pastes thet from clipboard to position of the Caret.getDot().
	 */
	public void paste() {
		editor.paste();
	}


	/**
	 * Gets the line number of given offset.
	 * 
	 * @param offset
	 *            offset in the text
	 * @return returns the line number
	 * @throws BadLocationException
	 *             thrown if given offset is outside the text
	 */
	public int getLineOfOffset(int offset) throws BadLocationException {
		return editor.getLineOfOffset(offset);
	}


	/**
	 * Gets the offset of the starting character of the given line.
	 * 
	 * @param line
	 *            line number
	 * @return returns the offset of the starting character
	 * @throws BadLocationException
	 *             thrown if given line number is not in the text
	 */
	public int getLineStartOffset(int line) throws BadLocationException {
		return editor.getLineStartOffset(line);
	}


	/**
	 * Gets the offset of the ending character of the given line.
	 * 
	 * @param line
	 *            line number
	 * @return returns the offset of the ending character
	 * @throws BadLocationException
	 *             thrown if given line number is not in the text
	 */
	public int getLineEndOffset(int line) throws BadLocationException {
		return editor.getLineEndOffset(line);
	}


	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (editor != null) {
			editor.setFont(font);
		}
	}
}
