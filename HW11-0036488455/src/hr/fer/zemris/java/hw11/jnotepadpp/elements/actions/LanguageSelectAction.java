package hr.fer.zemris.java.hw11.jnotepadpp.elements.actions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProviderBridge;

/**
 * Language select action of the implementing {@link Notepad}. Requires the used
 * {@link LocalizationProvider} to implement localization for german (de),
 * english (en), croatian (hr) and serbian (srb) languages.
 * <p>
 * Requires the localization of following keys: language.name - name of the
 * action, language.desc - description of the action, language.dialog.name -
 * name of the poppup window, english.trans - translation of english language
 * name in the localized language, german.trans - translation of german language
 * name in the localized language, croatian.trans - translation of croatian
 * language name in the localized language, serbian.trans - translation of
 * serbian language name in the localized language.
 * </p>
 * <p>
 * Upon activation popps up a language selection window.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationProvider
 * @see Notepad
 */
public class LanguageSelectAction extends LocalizableAction {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization key of the action name.
	 */
	private static final String NAME_KEY = "language.name";

	/**
	 * Localization key of the action description tooltip.
	 */
	private static final String DESC_KEY = "language.desc";

	/**
	 * Localization key of the poppup dialog name.
	 */
	private static final String DIALOG_KEY = "language.dialog.name";

	/**
	 * Localization key of the english translation name.
	 */
	private static final String ENGLISH_TRANS = "english.trans";

	/**
	 * Localization mark of english localization.
	 */
	private static final String ENGLISH_LOCALIZATION = "en";

	/**
	 * Localization key of the serbian translation name.
	 */
	private static final String SERBIAN_TRANS = "serbian.trans";

	/**
	 * Localization mark of serbian localization.
	 */
	private static final String SERBIAN_LOCALIZATION = "srb";

	/**
	 * Localization key of the croatian translation name.
	 */
	private static final String CROATIAN_TRANS = "croatian.trans";

	/**
	 * Localization mark of croatian localization.
	 */
	private static final String CROATIAN_LOCALIZATION = "hr";

	/**
	 * Localization key of the german translation name.
	 */
	private static final String GERMAN_TRANS = "german.trans";

	/**
	 * Localization mark of the german localization.
	 */
	private static final String GERMAN_LOCALIZATION = "de";


	/**
	 * Creates the action.
	 * 
	 * @param notepad
	 *            notepad using this action
	 * @param provider
	 *            localization provider
	 */
	public LanguageSelectAction(Notepad notepad,
			ILocalizationProvider provider) {
		super(notepad, provider);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G,
				InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProviderBridge bridge = new LocalizationProviderBridge(
				provider);
		bridge.connect();
		JDialog dialog = new JDialog(notepad.getFrame(),
				provider.getTranslation(DIALOG_KEY), true);

		JPanel dialogPanel = new JPanel();
		dialog.add(dialogPanel);
		dialogPanel.setLayout(new GridLayout(0, 1));

		dialogPanel.add(
				createButton(CROATIAN_TRANS, CROATIAN_LOCALIZATION, bridge));
		dialogPanel
				.add(createButton(ENGLISH_TRANS, ENGLISH_LOCALIZATION, bridge));
		dialogPanel
				.add(createButton(SERBIAN_TRANS, SERBIAN_LOCALIZATION, bridge));
		dialogPanel
				.add(createButton(GERMAN_TRANS, GERMAN_LOCALIZATION, bridge));
		dialog.setContentPane(dialogPanel);
		dialog.setSize(dialog.getPreferredSize().width * 4,
				dialog.getPreferredSize().height * 2);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				bridge.disconnect();
				dialog.dispose();
			}
		});
		bridge.disconnect();
	}


	/**
	 * Creates a button with the translated name.
	 * 
	 * @param trans
	 *            localization key
	 * @param lang
	 *            language to activate upon action
	 * @return returns the created button
	 */
	private JButton createButton(String trans, String lang,
			ILocalizationProvider bridge) {
		LocalizableLabel label = new LocalizableLabel(trans, bridge);
		label.setHorizontalAlignment(JLabel.CENTER);
		JButton temp = new JButton();
		temp.add(label);
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((LocalizationProvider) provider).setLanguage(lang);
			}
		});
		return temp;
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
