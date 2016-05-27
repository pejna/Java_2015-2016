package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * To lower case action of the {@link Notepad}. Lowers the case of the letters
 * between Caret.getDot() and Caret.getMark() in the {@link Notepad}'s editor's
 * {@link Caret}.
 * <p>
 * Requires the localization of following keys: toLowerCase.name - name of the
 * action, toLowerCase.desc - tooltip of the action.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 * @see Caret
 */
public class ToLowerCaseAction extends ChangeCaseAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "toLowerCase.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "toLowerCase.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public ToLowerCaseAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

	}


	@Override
	protected String changeCase(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (Character.isUpperCase(c)) {
				c = Character.toLowerCase(c);
			}
			sb.append(c);
		}
		return sb.toString();
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
