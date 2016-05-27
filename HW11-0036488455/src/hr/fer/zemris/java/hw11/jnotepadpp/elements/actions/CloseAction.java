package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that closes the editor of the given {@link Notepad} using
 * {@link JTabbedPane} as it's content pane.
 * <p>
 * Calls save() and close() methods of {@link Notepad} to save if not saved and
 * close if saved.
 * </p>
 * <p>
 * Requires the localization of following keys: close.name - name of the action
 * close.desc - tooltip of the action close.dialog.text - message while closing.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class CloseAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "close.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "close.desc";

	private static final String PROMPT_KEY = "close.dialog.text";

	private boolean ignoreSelected;

	private int index;


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public CloseAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JTabbedPane pane = (JTabbedPane) notepad.getPane();
		if (!ignoreSelected) {
			index = pane.getSelectedIndex();
		}
		NotepadEditor editor = (NotepadEditor) pane.getComponentAt(index);
		if (!notepad.isSaved(editor)) {
			notepad.save(editor);
		}
		if (notepad.isSaved(editor)) {
			notepad.close(editor);
		} else {
			int answer = JOptionPane.showConfirmDialog((Component) notepad,
					provider.getTranslation(PROMPT_KEY));
			if (answer == JOptionPane.YES_OPTION) {
				notepad.close(editor);
			}
		}
		ignoreSelected = false;
	}


	@Override
	protected String getDescriptionKey() {
		return DESC_KEY;
	}


	@Override
	protected String getNameKey() {
		return NAME_KEY;
	}


	public void setIgnoreSelected() {
		ignoreSelected = true;
	}


	public void setIndex(int index) {
		this.index = index;
	}
}
