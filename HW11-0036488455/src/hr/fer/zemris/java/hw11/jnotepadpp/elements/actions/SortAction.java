package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Parent action of {@link Notepad} actions used for sorting editor lines.
 * <p>
 * Each child should provide it's sort() method, with argument being the
 * selected lines, and the return value being the sorted lines. Selects all
 * lines with at least one character in between Caret.getDot() and
 * Caret.getMark().
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public abstract class SortAction extends LocalizableAction {

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
	public SortAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Caret caret = notepad.getEditor().getCaret();
		Document document = notepad.getEditor().getDocument();
		try {
			int lower = Math.min(caret.getDot(), caret.getMark());
			int higher = Math.max(caret.getDot(), caret.getMark());
			int line = notepad.getEditor().getLineOfOffset(lower);
			lower = notepad.getEditor().getLineStartOffset(line);
			line = notepad.getEditor().getLineOfOffset(higher);
			higher = notepad.getEditor().getLineEndOffset(line);
			String text = document.getText(lower, higher - lower);
			document.remove(lower, higher - lower);
			String[] strings = text.split("\n");
			strings = sort(strings);
			StringBuilder sb = new StringBuilder();
			for (String s : strings) {
				sb.append(s).append("\n");
			}
			document.insertString(lower, sb.toString(), null);

			notepad.edited();
		} catch (BadLocationException ignorable) {
		}
	}


	/**
	 * Sorts the given string with the method described in the class
	 * documentation.
	 * 
	 * @param strings strings to sort
	 * @return returns the sorted string
	 */
	protected abstract String[] sort(String[] strings);
}
