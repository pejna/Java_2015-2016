package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.CloseAction;

/**
 * Used for insertion into JTabbedPane with JTabbedPane.setTabComponentAt()
 * method.
 * <p>
 * Enables calling {@link CloseAction} from the button placed in the tab. Uses
 * {@link NotepadButton} to represent buttons so image passed in the constructor
 * cannot be null.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CloseAction
 * @see JTabbedPane
 */
public class ExitableTab extends JPanel {

	/**
	 * Serial version of the component.
	 */
	private static final long serialVersionUID = 28444570949681639L;

	/**
	 * Button representing edited state of the editor.
	 */
	private JButton edited;

	/**
	 * Name of the editor.
	 */
	private JLabel name;

	/**
	 * button used for closing.
	 */
	private JButton close;

	/**
	 * Close action.
	 */
	private CloseAction action;


	/**
	 * Creates the exitable tab with the given elements.
	 * 
	 * @param pane
	 *            pane into which to embed the tab
	 * @param name
	 *            name of the tab
	 * @param exitIcon
	 *            icon for the exit button
	 * @param exitHoverIcon
	 *            icon for hovering over exit button
	 * @param editedIcon
	 *            icon for edited representing image
	 * @param closeAction
	 *            action that closes the tab
	 * @param isEdited
	 *            is the editor been edited
	 */
	public ExitableTab(JTabbedPane pane, String name, ImageIcon exitIcon,
			ImageIcon exitHoverIcon, ImageIcon editedIcon,
			CloseAction closeAction, boolean isEdited) {
		Objects.requireNonNull(pane);
		Objects.requireNonNull(exitIcon);
		Objects.requireNonNull(editedIcon);
		Objects.requireNonNull(name);
		Objects.requireNonNull(closeAction);

		this.setBorder(BorderFactory.createEmptyBorder());
		this.setOpaque(false);
		this.setBackground(Color.WHITE);
		this.setFocusable(false);

		edited = new NotepadButton(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		edited.setIcon(editedIcon);
		edited.setBorder(BorderFactory.createEmptyBorder());
		edited.setEnabled(isEdited);

		this.add(edited);

		this.name = new JLabel(name);
		this.name.enableInputMethods(false);
		this.name.setBorder(BorderFactory.createEmptyBorder());
		this.add(this.name);

		this.action = closeAction;
		close = new NotepadButton(new AbstractAction() {

			/**
			 * Serial version ID of the class.
			 */
			private static final long serialVersionUID = -8346987114587373555L;


			@Override
			public void actionPerformed(ActionEvent e) {
				action.setIgnoreSelected();
				action.setIndex(pane.indexOfTabComponent(ExitableTab.this));
				action.actionPerformed(null);
			}
		});
		close.setIcon(exitIcon);
		close.setRolloverIcon(exitHoverIcon);
		close.setBorder(BorderFactory.createEmptyBorder());

		this.add(close);
	}
}
