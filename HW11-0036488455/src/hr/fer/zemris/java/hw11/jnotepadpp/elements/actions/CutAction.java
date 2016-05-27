package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that cuts from the current editor of the {@link Notepad}
 * into the clipboar.
 * <p>
 * Requires the localization of following keys: cut.name - name of the action,
 * cut.desc - tooltip of the action.
 * </p>
 * 
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class CutAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "cut.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "cut.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public CutAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		notepad.getEditor().cut();
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
