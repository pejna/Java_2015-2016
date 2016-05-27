package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Case editing action for use with {@link Notepad} implementing classes.
 * <p>
 * Requires the child classes to implement changeCase() method returning value
 * the changed string in the argument.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationListener
 * @see ILocalizationProvider
 */
public abstract class ChangeCaseAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public ChangeCaseAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = notepad.getEditor().getDocument();
		Caret caret = notepad.getEditor().getCaret();
		int len = Math.abs(caret.getDot() - caret.getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(caret.getMark(), caret.getDot());
		} else {
			len = doc.getLength();
		}

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}

		notepad.edited();
	}


	/**
	 * Changes the case of the given string.
	 * 
	 * @param text
	 *            text to be changed
	 * @return the result of change
	 */
	protected abstract String changeCase(String text);

}
