package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;

/**
 * Special kind of {@link IconPack} used with {@link JNotepadPP} implementing
 * {@link Notepad}. Provides all icons used by that program.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see JNotepadPP
 * @see IconPack
 */
public class NotepadPPIconPack implements IconPack {

	static NotepadPPIconPack thisOne;

	/**
	 * Frame icon.
	 */
	private ImageIcon frame;

	/**
	 * Save icon.
	 */
	private ImageIcon save;

	/**
	 * Mini save icon.
	 */
	private ImageIcon saveMini;

	/**
	 * Exit icon.
	 */
	private ImageIcon exit;

	/**
	 * Exit hovering icon.
	 */
	private ImageIcon exitHover;

	/**
	 * Close file icon.
	 */
	private ImageIcon closeFile;

	/**
	 * Close all files icon.
	 */
	private ImageIcon closeAllFile;

	/**
	 * New file icon.
	 */
	private ImageIcon newFile;

	/**
	 * Open file icon.
	 */
	private ImageIcon openFile;

	/**
	 * Save as icon.
	 */
	private ImageIcon saveAs;

	/**
	 * Cut icon.
	 */
	private ImageIcon cut;

	/**
	 * Copy icon.
	 */
	private ImageIcon copy;

	/**
	 * Paste icon.
	 */
	private ImageIcon paste;

	/**
	 * Delete icon.
	 */
	private ImageIcon delete;

	/**
	 * Select all icon.
	 */
	private ImageIcon selectAll;

	/**
	 * Unique line icon.
	 */
	private ImageIcon unique;

	/**
	 * Upper case icon.
	 */
	private ImageIcon upperCase;

	/**
	 * Lower case icon.
	 */
	private ImageIcon lowerCase;

	/**
	 * Invert case icon.
	 */
	private ImageIcon invertCase;

	/**
	 * Sort ascending icon.
	 */
	private ImageIcon ascending;

	/**
	 * Sort descending icon.
	 */
	private ImageIcon descending;

	/**
	 * Message icon.
	 */
	private ImageIcon message;


	/**
	 * Loads all images.
	 */
	private NotepadPPIconPack() {
		try {
			frame = loadIcon("./frame.png");

			save = loadIcon("./save.png");

			saveMini = loadIcon("./save_mini.png");

			exit = loadIcon("./exit.png");

			exitHover = loadIcon("./exit_hover.png");

			closeFile = loadIcon("./close.png");

			closeAllFile = loadIcon("./close_all.png");

			newFile = loadIcon("./new.png");

			openFile = loadIcon("./open.png");

			saveAs = loadIcon("./save_as.png");

			cut = loadIcon("./cut.png");

			copy = loadIcon("./copy.png");

			paste = loadIcon("./paste.png");

			delete = loadIcon("./delete.png");

			selectAll = loadIcon("./select_all.png");

			unique = loadIcon("./unique.png");

			ascending = loadIcon("./sort_ascend.png");

			descending = loadIcon("./sort_descend.png");

			upperCase = loadIcon("./uppercase.png");

			lowerCase = loadIcon("./lowercase.png");

			invertCase = loadIcon("./invertcase.png");

			message = loadIcon("./message.png");

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}


	/**
	 * Gets the icon pack.
	 * 
	 * @return returns the icon pack
	 */
	public static IconPack getIcons() {
		if (thisOne == null) {
			thisOne = new NotepadPPIconPack();
		}

		return thisOne;
	}


	/**
	 * Loads a single image from the given path.
	 * 
	 * @param path
	 *            path of the icon
	 * @return returns the loaded icon
	 * @throws IOException
	 *             thrown if a loading error happes
	 */
	private ImageIcon loadIcon(String path) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(path);
		if (is == null) {
			throw new IOException(
					"Warning, image at path " + path + " couldn't be opened!");
		}
		byte[] bytes = readAllBytes(is);
		is.close();
		return new ImageIcon(bytes);
	}


	/**
	 * Reads all bytes from the given stream.
	 * @param is input stream with bytes
	 * @return returns all bytes from the stream
	 */
	private static byte[] readAllBytes(InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[4096];

		try {
			while ((nRead = is.read(data, 0, data.length)) > 0) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		} catch (IOException e) {
			System.out.println("went wrong");
		}

		return buffer.toByteArray();
	}


	@Override
	public ImageIcon getFrameIcon() {
		return frame;
	}


	@Override
	public ImageIcon getSaveIcon() {
		return save;
	}


	@Override
	public ImageIcon getExitIcon() {
		return exit;
	}


	@Override
	public ImageIcon getCloseIcon() {
		return closeFile;
	}


	@Override
	public ImageIcon getCloseAllIcon() {
		return closeAllFile;
	}


	@Override
	public ImageIcon getNewFileIcon() {
		return newFile;
	}


	@Override
	public ImageIcon getOpenFileIcon() {
		return openFile;
	}


	@Override
	public ImageIcon getSaveAsIcon() {
		return saveAs;
	}


	@Override
	public ImageIcon getCutIcon() {
		return cut;
	}


	@Override
	public ImageIcon getCopyIcon() {
		return copy;
	}


	@Override
	public ImageIcon getPasteIcon() {
		return paste;
	}


	@Override
	public ImageIcon getDeleteIcon() {
		return delete;
	}


	@Override
	public ImageIcon getSelectAllIcon() {
		return selectAll;
	}


	@Override
	public ImageIcon getSortAscendingIcon() {
		return ascending;
	}


	@Override
	public ImageIcon getSortDescendingIcon() {
		return descending;
	}


	@Override
	public ImageIcon getUpperCaseIcon() {
		return upperCase;
	}


	@Override
	public ImageIcon getLowerCaseIcon() {
		return lowerCase;
	}


	@Override
	public ImageIcon getInvertCaseIcon() {
		return invertCase;
	}


	@Override
	public ImageIcon getUniqueIcon() {
		return unique;
	}


	@Override
	public ImageIcon getSaveMiniIcon() {
		return saveMini;
	}


	@Override
	public ImageIcon getMessageIcon() {
		return message;
	}


	@Override
	public ImageIcon getExitHoverIcon() {
		return exitHover;
	}
}
