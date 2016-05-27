package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Sort action of the Notepad that sorts the selected lines in descending order.
 * For further information see {@link SortAction} documentation.
 * <p>
 * Requires the localization of following keys: sortDescending.name - name of
 * the action, sortDescending.desc - tooltip of the action.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SortAction
 */
public class SortDescendingAction extends SortAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "sortDescending.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "sortDescending.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public SortDescendingAction(Notepad notepad,
			ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

	}


	@Override
	protected String getDescriptionKey() {
		return DESC_KEY;
	}


	@Override
	protected String getNameKey() {
		return NAME_KEY;
	}


	@Override
	protected String[] sort(String[] strings) {
		Arrays.sort(strings, (a, b) -> b.compareTo(a));
		return strings;
	}

}
