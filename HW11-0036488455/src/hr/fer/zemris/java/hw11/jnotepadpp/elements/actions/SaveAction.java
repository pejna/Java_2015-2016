package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Localizable action that tries to save a file and call setFilePath() method of
 * the {@link Notepad}. Calls saved() method when done if all went good.
 * 
 * <p>
 * Requires the localization of following keys: save.name - name of the action,
 * save.desc - tooltip of the action, save.error.text - text of error message,
 * save.error.name - name of error message, save.approved.text - message of the
 * finished information message.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class SaveAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "save.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "save.desc";

	/**
	 * Localization key of the message text.
	 */
	private static final String APPROVED_KEY = "save.approved.text";

	/**
	 * Localization key of the error message text.
	 */
	private static final String ERROR_KEY = "save.error.text";

	/**
	 * Localization key of the error message name.
	 */
	private static final String ERROR_NAME_KEY = "save.error.name";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public SaveAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (notepad.getFilePath() == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(provider.getTranslation(NAME_KEY));
			if (jfc.showSaveDialog(
					notepad.getFrame()) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(notepad.getFrame(),
						provider.getTranslation(ERROR_KEY),
						provider.getTranslation(ERROR_NAME_KEY),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			notepad.setFilePath(jfc.getSelectedFile().toPath());
		}

		byte[] data = notepad.getEditor().getText()
				.getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(notepad.getFilePath(), data);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(notepad.getFrame(),
					provider.getTranslation(ERROR_KEY),
					provider.getTranslation(ERROR_NAME_KEY),
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(notepad.getFrame(),
				provider.getTranslation(APPROVED_KEY),
				provider.getTranslation(NAME_KEY),
				JOptionPane.INFORMATION_MESSAGE,
				notepad.getIcons().getMessageIcon());

		notepad.saved();
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
