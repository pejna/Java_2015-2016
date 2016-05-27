package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * {@link JButton} implementation that hides the text al all times.
 * <p>
 * Set the icon of the button with the setIcon() method of {@link JButton}. For
 * all other information see the {@link JButton} documentation.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see JButton
 */
public class NotepadButton extends JButton {

	/**
	 * Seral version ID of the class.
	 */
	private static final long serialVersionUID = -10167782620574887L;


	/**
	 * Creates the button with the given action.
	 * 
	 * @param action
	 *            action for the button
	 */
	public NotepadButton(Action action) {
		super(action);
		action.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				NotepadButton.this.setText("");
			}
		});
		setText("");
		setFocusable(false);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setSize(30, 30);
		setMargin(new Insets(0, 0, 0, 0));

	}
}
