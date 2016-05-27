package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that closes all editors of the given {@link Notepad} using
 * {@link JTabbedPane} as it's content pane.
 * <p>
 * Calls save() and close() methods of {@link Notepad} to save if not saved and
 * close if saved.
 * </p>
 * <p>
 * Requires the localization of following keys: closeAll.name - name of the
 * action, closeAll.desc - tooltip of the action
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class CloseAllAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "closeAll.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "closeAll.desc";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public CloseAllAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JTabbedPane pane = (JTabbedPane) notepad.getPane();
		NotepadEditor editor;
		for (int i = 0; i < pane.getTabCount(); i++) {
			editor = (NotepadEditor) pane.getComponentAt(i);
			notepad.save(editor);
			if (!notepad.isSaved(editor)) {
				return;
			}
		}
		for (int i = 0; i < pane.getTabCount(); i++) {
			editor = (NotepadEditor) pane.getComponentAt(i);
			notepad.close(editor);
		}
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
