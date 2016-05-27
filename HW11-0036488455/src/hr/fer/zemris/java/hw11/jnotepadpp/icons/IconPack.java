package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import javax.swing.ImageIcon;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;

/**
 * Icon pack of usual icons found in a {@link Notepad} implementing classes.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 */
public interface IconPack {

	/**
	 * Gets the main frame and app icon.
	 * @return returns the icon
	 */
	ImageIcon getFrameIcon();

	/**
	 * Gets the save icon.
	 * @return returns the icon
	 */
	ImageIcon getSaveIcon();


	/**
	 * Gets the icon depicing saved state, usually found in a tab.
	 * @return returns the icon
	 */
	ImageIcon getSaveMiniIcon();


	/**
	 * Gets the icon for exiting a tab.
	 * @return returns the icon
	 */
	ImageIcon getExitIcon();


	/**
	 * Gets the icon for hover over exit icon.
	 * @return returns the icon
	 */
	ImageIcon getExitHoverIcon();


	/**
	 * Gets the icon for closing a tab.
	 * @return returns the icon
	 */
	ImageIcon getCloseIcon();

	/**
	 * Gets the icon for closing all tabs.
	 * @return returns the icon
	 */
	ImageIcon getCloseAllIcon();

	
	/**
	 * Gets the icon for opening new file.
	 * @return returns the icon
	 */
	ImageIcon getNewFileIcon();


	/**
	 * Gets the icon for opening existing file.
	 * @return returns the icon
	 */
	ImageIcon getOpenFileIcon();


	/**
	 * Gets the icon for saving under different name.
	 * @return returns the icon
	 */
	ImageIcon getSaveAsIcon();

	
	/**
	 * Gets the icon for cutting out of text.
	 * @return returns the icon
	 */
	ImageIcon getCutIcon();


	/**
	 * Gets the icon for copying from text.
	 * @return returns the icon
	 */
	ImageIcon getCopyIcon();


	/**
	 * Gets the icon for pasting to text.
	 * @return returns the icon
	 */
	ImageIcon getPasteIcon();


	/**
	 * Gets the icon for deleting from text.
	 * @return returns the icon
	 */
	ImageIcon getDeleteIcon();


	/**
	 * Gets the icon for selecting all text.
	 * @return returns the icon
	 */
	ImageIcon getSelectAllIcon();


	/**
	 * Gets the icon for sorting ascendingly.
	 * @return returns the icon
	 */
	ImageIcon getSortAscendingIcon();


	/**
	 * Gets the icon for sorting descendingly.
	 * @return returns the icon
	 */
	ImageIcon getSortDescendingIcon();


	/**
	 * Gets the icon for transforming text to upper case.
	 * @return returns the icon
	 */
	ImageIcon getUpperCaseIcon();


	/**
	 * Gets the icon for transforming text to lower case.
	 * @return returns the icon
	 */
	ImageIcon getLowerCaseIcon();


	/**
	 * Gets the icon for inverting case of text.
	 * @return returns the icon
	 */
	ImageIcon getInvertCaseIcon();


	/**
	 * Gets the icon for eliminating non unique lines.
	 * @return returns the icon
	 */
	ImageIcon getUniqueIcon();


	/**
	 * Gets the icon for message displays.
	 * @return returns the icon
	 */
	ImageIcon getMessageIcon();

}
