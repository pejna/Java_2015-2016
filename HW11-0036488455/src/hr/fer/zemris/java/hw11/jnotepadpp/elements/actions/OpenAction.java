package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that tries to open a file and call setEditor() and
 * setPath() methods of the {@link Notepad}.
 * 
 * <p>
 * Requires the localization of following keys: open.name - name of the action,
 * open.desc - tooltip of the action, open.error.text - text of error message,
 * open.error.name - name of error message.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class OpenAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "open.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "open.desc";

	/**
	 * Localization key of the error dialog text.
	 */
	private static final String ERROR_KEY = "open.error.text";

	/**
	 * Localization key of the error dialog name.
	 */
	private static final String ERROR_NAME_KEY = "open.error.name";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public OpenAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(provider.getTranslation(NAME_KEY));
		if (jfc.showOpenDialog(
				notepad.getFrame()) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = jfc.getSelectedFile();
		Path filePath = fileName.toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(notepad.getFrame(),
					provider.getTranslation(ERROR_KEY),
					provider.getTranslation(ERROR_NAME_KEY),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(filePath);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(notepad.getFrame(),
					provider.getTranslation(ERROR_KEY),
					provider.getTranslation(ERROR_NAME_KEY),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String tekst = new String(okteti, StandardCharsets.UTF_8);
		NotepadEditor newEditor = new NotepadEditor(tekst);
		notepad.setEditor(newEditor);
		notepad.setFilePath(filePath);
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
