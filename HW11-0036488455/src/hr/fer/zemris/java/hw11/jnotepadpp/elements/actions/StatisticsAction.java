package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Statistics showing action of the {@link Notepad}. Displays statistics of the
 * current editor in a new window.
 * <p>
 * Requires the localization of following keys: statistics.name - name of the
 * action, statistics.desc - tooltip of the action, statistics.length.text -
 * length text translation, statistics.nonBlank.text - non blank translation,
 * statistic.lineNum.text - line number translation.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see ILocalizationProvider
 */
public class StatisticsAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "statistics.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "statistics.desc";

	/**
	 * Localization key of the length text.
	 */
	private static final String LENGTH_KEY = "statistics.length.text";

	/**
	 * Localization key of the non blank text.
	 */
	private static final String NON_BLANK_KEY = "statistics.nonBlank.text";

	/**
	 * Localization key of the line number text.
	 */
	private static final String LINES_NUM_KEY = "statistics.lineNum.text";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public StatisticsAction(Notepad notepad, ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String text = notepad.getEditor().getText();
		int length = text.length();
		int nonBlank = 0;
		int lineCount = 0;
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				nonBlank++;
			}
			if (text.charAt(i) == '\n') {
				lineCount++;
			}
		}

		String lengthString = provider.getTranslation(LENGTH_KEY) + " "
				+ length;
		String nonBlankString = provider.getTranslation(NON_BLANK_KEY) + " "
				+ nonBlank;
		String lineCountString = provider.getTranslation(LINES_NUM_KEY) + " "
				+ lineCount;

		JOptionPane.showMessageDialog(notepad.getFrame(),
				lengthString + "\n" + nonBlankString + "\n" + lineCountString,
				provider.getTranslation(NAME_KEY),
				JOptionPane.INFORMATION_MESSAGE,
				notepad.getIcons().getMessageIcon());
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
