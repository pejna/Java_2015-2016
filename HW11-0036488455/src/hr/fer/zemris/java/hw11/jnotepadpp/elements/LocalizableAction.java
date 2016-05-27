package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * {@link AbstractAction} intended for use with {@link ILocalizationProvider}
 * implementing classes.
 * <p>
 * Reads from them using getTranslation() method. Requires the children classes
 * to implement getDescriptionKey() and getNameKey() to work properly. If
 * localization is not used, it is better to use {@link AbstractAction} since
 * this has no other benefits other than localization adaptation.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ILocalizationProvider
 * @see ILocalizationListener
 * @see AbstractAction
 * @see Notepad
 */
public abstract class LocalizableAction extends AbstractAction
		implements ILocalizationListener {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = -7346328479382011714L;

	/**
	 * Notepad this action is used with.
	 */
	protected Notepad notepad;

	/**
	 * Localization provider.
	 */
	protected ILocalizationProvider provider;


	/**
	 * Creates the action that serves the given notepad with words from the
	 * given provider.
	 * 
	 * @param notepad
	 *            notepad requireing services
	 * @param provider
	 *            localization provider
	 */
	public LocalizableAction(Notepad notepad, ILocalizationProvider provider) {
		Objects.requireNonNull(notepad);
		Objects.requireNonNull(provider);
		this.notepad = notepad;
		this.provider = provider;
		provider.AddLocalizationListener(this);
	}


	@Override
	public abstract void actionPerformed(ActionEvent e);


	@Override
	public void localizationChanged() {
		putValue(Action.NAME, provider.getTranslation(getNameKey()));
		putValue(Action.SHORT_DESCRIPTION,
				provider.getTranslation(getDescriptionKey()));
	}


	/**
	 * Gets the description key of the class.
	 * 
	 * @return returns the description key
	 */
	protected abstract String getDescriptionKey();


	/**
	 * Gets the name key of the class.
	 * 
	 * @return returns the description key
	 */
	protected abstract String getNameKey();
}
