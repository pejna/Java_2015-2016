package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Sort action of the Notepad that removes duplicated from the selected lines.
 * For further information see {@link SortAction} documentation.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SortAction
 */
public class UniqueAction extends SortAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "unique.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "unique.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public UniqueAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

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
		boolean[] toBeRemoved = new boolean[strings.length];
		List<String> list = new ArrayList<>();
		for (int i = 0; i < strings.length; i++) {
			if (toBeRemoved[i]) {
				continue;
			}
			list.add(strings[i]);
			for (int j = i + 1; j < strings.length; j++) {
				if (toBeRemoved[j]) {
					continue;
				}
				if (strings[i].equals(strings[j])) {
					toBeRemoved[j] = true;
				}
			}
		}
		return list.toArray(new String[0]);
	}

}
