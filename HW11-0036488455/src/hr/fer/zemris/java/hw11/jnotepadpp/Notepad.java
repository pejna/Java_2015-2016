package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Component;
import java.awt.Frame;
import java.nio.file.Path;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.IconPack;

/**
 * Interface that enables implementing classes to perform duties of a notepad.
 * <p>
 * Methods save and close should not do any checking of variables or editor
 * states, for that use isSaved and isEdited methods.
 * </p>
 * <p>
 * Methods saved() and edited() should be used to send messages to the
 * {@link Notepad} if external saving or editing has been done or if methods
 * save or getEditor with following editing done.
 * </p>
 * <p>
 * GetEditor method can be used for getting current editor if more editors are
 * used concurrently.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see NotepadEditor
 * @see IconPack
 */
public interface Notepad {

	/**
	 * Gets the file path of the editor.
	 * 
	 * @return returns the file path
	 */
	Path getFilePath();


	/**
	 * Returns the frame of the notepad. Used for centering dialog windows, and
	 * fathering comands.
	 * 
	 * @return returns the frame of the notepad
	 */
	Frame getFrame();


	/**
	 * Sets the path of the current editor.
	 * 
	 * @param path
	 *            new path of the editor
	 */
	void setFilePath(Path path);


	/**
	 * Gets the current editor.
	 * 
	 * @return returns the current editor
	 */
	NotepadEditor getEditor();


	/**
	 * Gets the icon pack used by the notepad. It provides basic icons to be
	 * with the notepad.
	 * 
	 * @return returns the icon pack
	 */
	IconPack getIcons();


	/**
	 * Creates the new editor.
	 */
	void newEditor();


	/**
	 * Sets the editor to new editor or add a new one.
	 * 
	 * @param editor
	 *            editor to be set to
	 */
	void setEditor(NotepadEditor editor);


	/**
	 * Tells the {@link Notepad} that the editor state has been saved.
	 */
	void saved();


	/**
	 * Tells the {@link Notepad} that the editor state has been edited.
	 */
	void edited();


	/**
	 * Checks if the given editor has been saved. Should be used with
	 * getEditor() method.
	 * 
	 * @param editor
	 *            editor to be checked
	 * @return returns true if saved
	 */
	boolean isSaved(NotepadEditor editor);


	/**
	 * Checks if the given editor has been edited. Should be used with
	 * getEditor() method.
	 * 
	 * @param editor
	 *            editor to be checked
	 * @return returs true if saved
	 */
	boolean isEdited(NotepadEditor editor);


	/**
	 * Tells the notepad to save the given editor. Should be used with
	 * getEditor().
	 * 
	 * @param editor
	 *            editor to save
	 */
	void save(NotepadEditor editor);


	/**
	 * Tells the notepad to close the editor. Should be used with getEditor().
	 * 
	 * @param editor
	 *            editor to close
	 */
	void close(NotepadEditor editor);


	/**
	 * Tells the notepad to exit application and tidy up after itself.
	 */
	void exit();


	/**
	 * Gets the pane of the containing editor. Can be {@link JPanel} or
	 * {@link JTabbedPane} or any other type of container.
	 * 
	 * @return gets the containing pane
	 */
	Component getPane();
}
