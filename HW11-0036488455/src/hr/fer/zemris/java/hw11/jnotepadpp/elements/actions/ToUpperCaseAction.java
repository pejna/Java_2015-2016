package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * To upper case action of the {@link Notepad}. Raises the case of the letters
 * between Caret.getDot() and Caret.getMark() in the {@link Notepad}'s editor's
 * {@link Caret}.
 * <p>
 * Requires the localization of following keys: toUpperCase.name - name of the
 * action, toUpperCase.desc - tooltip of the action.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 * @see Caret
 */
public class ToUpperCaseAction extends ChangeCaseAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "toUpperCase.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "toUpperCase.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public ToUpperCaseAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

	}


	@Override
	protected String changeCase(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (Character.isLowerCase(c)) {
				c = Character.toUpperCase(c);
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
