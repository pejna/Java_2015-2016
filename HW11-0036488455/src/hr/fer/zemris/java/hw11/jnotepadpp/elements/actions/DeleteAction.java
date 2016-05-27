package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that deletes from the current editor of the
 * {@link Notepad}.
 * <p>
 * Requires the localization of following keys: delete.name - name of the
 * action, delete.desc - tooltip of the action.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class DeleteAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "delete.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "delete.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public DeleteAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = notepad.getEditor().getDocument();
		Caret caret = notepad.getEditor().getCaret();
		int len = Math.abs(caret.getDot() - caret.getMark());
		if (len == 0)
			return;
		int offset = Math.min(caret.getDot(), caret.getMark());

		try {
			doc.remove(offset, len);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		notepad.edited();
	}


	@Override
	protected String getDescriptionKey() {
		return DESC_KEY;
	}


	@Override
	protected String getNameKey() {
		return NAME_KEY;
	}

}
