package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.elements.ExitableTab;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.LocalizableMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadButton;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadEditor;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadStatusToolbar;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.CloseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.CloseAllAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.DeleteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.LanguageSelectAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.OpenAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.SaveAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.SaveAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.SelectAllAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.SortAscendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.SortDescendingAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.ToLowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.ToUpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elements.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.IconPack;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.NotepadPPIconPack;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;

/**
 * {@link Notepad} implementation that enables use of many {@link NotepadEditor}
 * s at the same time.
 * <p>
 * Also implements {@link ILocalizationListener} to enable localization.
 * Requires language_"language".properties files for the localization to work.
 * </p>
 * <p>
 * Uses {@link NotepadEditor} as tabs.
 * </p>
 * <p>
 * Creates a working notepad window ready for customer use.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see Notepad
 * @see IconPack
 * @see ILocalizationProvider
 */
public class JNotepadPP extends JFrame
		implements Notepad, ILocalizationListener {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = 2782217276073071014L;

	/**
	 * Pane containing editors.
	 */
	private JTabbedPane pane;

	/**
	 * Current editor selected.
	 */
	private NotepadEditor currentEditor;

	/**
	 * Info on the current editor.
	 */
	private Info currentInfo;

	/**
	 * Icons used by the notepad.
	 */
	private IconPack icons;

	/**
	 * Informations connected with tabs.
	 */
	private Map<Component, Info> tabInfo;

	/**
	 * Status bar of the notepad.
	 */
	private NotepadStatusToolbar statusBar;

	/**
	 * Localization provider.
	 */
	private ILocalizationProvider provider = LocalizationProvider
			.getProvider(L18N_PATH, DEFAULT_LANGUAGE);

	/**
	 * Save action.
	 */
	private SaveAction saveAction = new SaveAction(this, provider);

	/**
	 * Open action.
	 */
	private Action openAction = new OpenAction(this, provider);

	/**
	 * Exit action.
	 */
	private Action exitAction = new ExitAction(this, provider);

	/**
	 * Close action.
	 */
	private CloseAction closeAction = new CloseAction(this, provider);

	/**
	 * Close all action.
	 */
	private Action closeAllAction = new CloseAllAction(this, provider);

	/**
	 * Save as action.
	 */
	private Action saveAsAction = new SaveAsAction(this, provider);

	/**
	 * New file action.
	 */
	private Action newFileAction = new NewFileAction(this, provider);

	/**
	 * Copy action.
	 */
	private Action copyAction = new CopyAction(this, provider);

	/**
	 * Cut action.
	 */
	private Action cutAction = new CutAction(this, provider);

	/**
	 * Paste action.
	 */
	private Action pasteAction = new PasteAction(this, provider);

	/**
	 * Delete action.
	 */
	private Action deleteAction = new DeleteAction(this, provider);

	/**
	 * Select all action.
	 */
	private Action selectAllAction = new SelectAllAction(this, provider);

	/**
	 * Statistics action.
	 */
	private Action statisticsAction = new StatisticsAction(this, provider);

	/**
	 * Language action.
	 */
	private Action languageAction = new LanguageSelectAction(this, provider);

	/**
	 * To upper case action.
	 */
	private Action toUppercaseAction = new ToUpperCaseAction(this, provider);

	/**
	 * To lower case action.
	 */
	private Action toLowerCaseAction = new ToLowerCaseAction(this, provider);

	/**
	 * Invert case action.
	 */
	private Action invertCaseAction = new InvertCaseAction(this, provider);

	/**
	 * Sort ascending action.
	 */
	private Action sortAscendingAction = new SortAscendingAction(this,
			provider);

	/**
	 * Sort descending action.
	 */
	private Action sortDescendingAction = new SortDescendingAction(this,
			provider);

	/**
	 * Unique action.
	 */
	private Action uniqueAction = new UniqueAction(this, provider);

	/**
	 * Key for localizing abort exit message.
	 */
	protected String ABORT_EXIT_MESSAGE = "frame.exit.text";

	/**
	 * Key for lacalizing abort exit message name.
	 */
	protected String ABORT_EXIT_NAME = "frame.exit.name";

	/**
	 * Default name of the tab.
	 */
	public static final String DEFAULT_TAB_NAME = "Tab";

	/**
	 * Tooltip text for the default tabs.
	 */
	private static final String DEFAULT_TOOLTIP_KEY = "defaultTooltip.text";

	/**
	 * Background color of the toolbar.
	 */
	private static final Color TOOLBAR_BACKGROUND = new Color(187, 234, 198);

	/**
	 * Color of toolbar border.
	 */
	private static final Color TOOLBAR_BORDER = Color.BLACK;

	/**
	 * Path to the localization files.
	 */
	private static final String L18N_PATH = "hr.fer.zemris.java.hw11."
			+ "jnotepadpp.localization.language";

	/**
	 * Default language.
	 */
	private static final String DEFAULT_LANGUAGE = "en";

	/**
	 * Default size of the font.
	 */
	private static final int DEFAULT_FONT_SIZE = 14;

	/**
	 * Localization key of the frame name.
	 */
	private static final String NAME = "frame.name";


	/**
	 * Creates and initializes the notepad.
	 */
	public JNotepadPP() {
		icons = NotepadPPIconPack.getIcons();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(900, 600);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		tabInfo = new HashMap<>();
		setIconImage(icons.getFrameIcon().getImage());

		provider.AddLocalizationListener(this);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}

		});
		initGUI();
	}


	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (Exception ignorable) {
		}

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		UIManager.put("TabbedPane.selected", TOOLBAR_BACKGROUND);
		UIManager.put("TabbedPane.shadow", ColorUIResource.GRAY);

		pane = new JTabbedPane();
		pane.setFocusable(false);
		pane.setBackground(Color.WHITE);
		pane.setBorder(BorderFactory.createLineBorder(TOOLBAR_BORDER));
		pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		pane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				onTabChange();
			}
		});

		centerPanel.add(pane, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);
		createMenus();
		createUpperToolbar(centerPanel);
		createBottomToolbar();
		newEditor();
	}


	/**
	 * Creates the menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);

		JMenu fileMenu = new LocalizableMenu("fileMenu.name", provider);
		fileMenu.add(new JMenuItem(newFileAction));
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(closeAction));
		fileMenu.add(new JMenuItem(closeAllAction));
		fileMenu.add(new JMenuItem(exitAction));
		menuBar.add(fileMenu);

		JMenu editMenu = new LocalizableMenu("editMenu.name", provider);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(deleteAction));
		editMenu.add(new JMenuItem(selectAllAction));
		menuBar.add(editMenu);

		JMenu toolsMenu = new LocalizableMenu("toolsMenu.name", provider);
		JMenu changeCaseMenu = new LocalizableMenu("changeCaseMenu.name",
				provider);
		changeCaseMenu.add(new JMenuItem(toUppercaseAction));
		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(invertCaseAction));
		toolsMenu.add(changeCaseMenu);
		JMenu sortMenu = new LocalizableMenu("sortMenu.name", provider);
		sortMenu.add(new JMenuItem(sortAscendingAction));
		sortMenu.add(new JMenuItem(sortDescendingAction));
		toolsMenu.add(sortMenu);
		toolsMenu.add(new JMenuItem(uniqueAction));
		menuBar.add(toolsMenu);

		JMenu settingsMenu = new LocalizableMenu("settingsMenu.name", provider);
		settingsMenu.add(new JMenuItem(languageAction));
		menuBar.add(settingsMenu);

		JMenu helpMenu = new LocalizableMenu("helpMenu.name", provider);
		helpMenu.add(new JMenuItem(statisticsAction));
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}


	/**
	 * Creates the upper toolbar in the panel.
	 * 
	 * @param center
	 *            center panel of the outer border layout
	 */
	private void createUpperToolbar(JPanel center) {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		toolBar.setBackground(TOOLBAR_BACKGROUND);
		toolBar.setBorder(BorderFactory.createLineBorder(TOOLBAR_BORDER));

		toolBar.add(createToolbarButton(newFileAction, icons.getNewFileIcon()));
		toolBar.add(createToolbarButton(openAction, icons.getOpenFileIcon()));
		toolBar.add(createToolbarButton(saveAction, icons.getSaveIcon()));
		toolBar.add(createToolbarButton(saveAsAction, icons.getSaveAsIcon()));
		toolBar.add(createToolbarButton(closeAction, icons.getCloseIcon()));
		toolBar.add(
				createToolbarButton(closeAllAction, icons.getCloseAllIcon()));

		toolBar.addSeparator();

		toolBar.add(createToolbarButton(toUppercaseAction,
				icons.getUpperCaseIcon()));
		toolBar.add(createToolbarButton(toLowerCaseAction,
				icons.getLowerCaseIcon()));
		toolBar.add(createToolbarButton(invertCaseAction,
				icons.getInvertCaseIcon()));

		toolBar.addSeparator();

		toolBar.add(createToolbarButton(sortAscendingAction,
				icons.getSortAscendingIcon()));
		toolBar.add(createToolbarButton(sortDescendingAction,
				icons.getSortDescendingIcon()));

		toolBar.addSeparator();

		toolBar.add(createToolbarButton(uniqueAction, icons.getUniqueIcon()));

		toolBar.addSeparator();

		toolBar.add(createToolbarButton(cutAction, icons.getCutIcon()));
		toolBar.add(createToolbarButton(copyAction, icons.getCopyIcon()));
		toolBar.add(createToolbarButton(pasteAction, icons.getPasteIcon()));
		toolBar.add(createToolbarButton(deleteAction, icons.getDeleteIcon()));
		toolBar.add(
				createToolbarButton(selectAllAction, icons.getSelectAllIcon()));

		toolBar.addSeparator();

		JButton tempButton;
		tempButton = new JButton(languageAction);
		tempButton.setFocusable(false);
		tempButton.setOpaque(false);
		tempButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		toolBar.add(tempButton);

		toolBar.addSeparator();

		tempButton = new JButton(statisticsAction);
		tempButton.setFocusable(false);
		tempButton.setOpaque(false);
		tempButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		toolBar.add(tempButton);

		center.add(toolBar, BorderLayout.NORTH);
	}


	/**
	 * Creates the bottom toolbar.
	 */
	private void createBottomToolbar() {
		statusBar = new NotepadStatusToolbar(this, provider);
		statusBar.setBackground(TOOLBAR_BACKGROUND);
		statusBar.setBorder(BorderFactory.createLineBorder(TOOLBAR_BORDER));
		add(statusBar, BorderLayout.SOUTH);
	}


	/**
	 * Creates a toolbar button.
	 * 
	 * @param action
	 *            action associated with the button
	 * @param icon
	 *            icon of the button
	 * @return returns the created button
	 */
	private JButton createToolbarButton(Action action, ImageIcon icon) {
		JButton tempButton = new NotepadButton(action);
		tempButton.setIcon(icon);
		return tempButton;
	}


	@Override
	public void saved() {
		if (currentInfo.saved) {
			return;
		}
		saveAction.setEnabled(false);
		currentInfo.saved = true;
		currentInfo.edited = false;
		updateTabName(false);
	}


	@Override
	public void edited() {
		if (currentInfo.edited) {
			return;
		}
		saveAction.setEnabled(true);
		currentInfo.saved = false;
		currentInfo.edited = true;
		updateTabName(true);
	}


	/**
	 * Updates the name of the tab if it has been changed.
	 * 
	 * @param notEdited
	 *            true if file has not been edited, false if it has
	 */
	private void updateTabName(boolean notEdited) {
		String name;
		String tooltip;
		if (currentInfo.filePath == null) {
			name = DEFAULT_TAB_NAME;
			tooltip = provider.getTranslation(DEFAULT_TOOLTIP_KEY);
		} else {
			name = currentInfo.filePath.getFileName().toString();
			tooltip = currentInfo.filePath.toAbsolutePath().toString();
		}
		pane.setTabComponentAt(pane.getSelectedIndex(),
				new ExitableTab(pane, name, icons.getExitIcon(),
						icons.getExitHoverIcon(), icons.getSaveMiniIcon(),
						closeAction, notEdited));
		pane.setToolTipTextAt(pane.getSelectedIndex(), tooltip);
	}


	@Override
	public boolean isSaved(NotepadEditor editor) {
		return tabInfo.get(editor).saved;
	}


	@Override
	public boolean isEdited(NotepadEditor editor) {
		return tabInfo.get(editor).edited;
	}


	/**
	 * Performs button activation/deactivation of caret change event in the
	 * current editor.
	 */
	private void onCaretChange() {
		Caret currentCaret = currentEditor.getCaret();
		updateStatus();
		if (currentCaret.getDot() != currentCaret.getMark()) {
			if (currentInfo.selected) {
				return;
			}
			currentInfo.selected = true;
			cutAction.setEnabled(true);
			copyAction.setEnabled(true);
			toUppercaseAction.setEnabled(true);
			toLowerCaseAction.setEnabled(true);
			invertCaseAction.setEnabled(true);
			sortAscendingAction.setEnabled(true);
			sortDescendingAction.setEnabled(true);
			uniqueAction.setEnabled(true);
		} else {
			if (!currentInfo.selected) {
				return;
			}
			currentInfo.selected = false;
			cutAction.setEnabled(false);
			copyAction.setEnabled(false);
			toUppercaseAction.setEnabled(false);
			toLowerCaseAction.setEnabled(false);
			invertCaseAction.setEnabled(false);
			sortAscendingAction.setEnabled(false);
			sortDescendingAction.setEnabled(false);
			uniqueAction.setEnabled(false);
		}
	}


	/**
	 * Performs change of frame and status bar on tab change.
	 */
	private void onTabChange() {
		if (pane.getTabCount() == 0) {
			newEditor();
		}
		NotepadEditor selectedEditor = (NotepadEditor) pane
				.getComponentAt(pane.getSelectedIndex());
		if (!selectedEditor.equals(currentEditor)) {
			currentEditor = selectedEditor;
			currentInfo = tabInfo.get(currentEditor);
			if (pane.getTabCount() != 0) {
				onCaretChange();

			}
		}

		localizationChanged();
	}


	/**
	 * Updates the status bar with new values. Called on caret and tab change.
	 */
	private void updateStatus() {
		String text = currentEditor.getText();
		int offset = currentEditor.getCaretPosition();
		statusBar.getLengthLabel().setText("" + text.length());
		int counter = 1;
		int lastNewline = 0;
		for (int i = 0; i < offset; i++) {
			if (text.charAt(i) == '\n') {
				counter++;
				lastNewline = i;
			}
		}
		statusBar.getLineLabel().setText("" + counter);
		statusBar.getColumnLabel().setText("" + (offset - lastNewline));
		Caret caret = currentEditor.getCaret();
		statusBar.getSelectedLabel()
				.setText("" + Math.abs(caret.getMark() - caret.getDot()));
	}


	@Override
	public void newEditor() {
		NotepadEditor newEditor = new NotepadEditor();

		addTab(newEditor);
		pane.setSelectedComponent(newEditor);
	}


	/**
	 * Adds the given tab to the pane.
	 * 
	 * @param editor
	 *            editor to be added
	 */
	private void addTab(NotepadEditor editor) {
		editor.setFont(new Font("Consolas", Font.PLAIN, DEFAULT_FONT_SIZE));
		currentEditor = editor;
		editor.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				onCaretChange();
			}
		});

		editor.addKeyListener(new KeyListener() {

			int length = editor.getText().length();


			@Override
			public void keyTyped(KeyEvent e) {
			}


			@Override
			public void keyReleased(KeyEvent e) {
				if (editor.getText().length() != length) {
					length = editor.getText().length();
					edited();
				}
			}


			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		currentInfo = new Info();
		tabInfo.put(currentEditor, currentInfo);

		pane.insertTab(null, null, editor, null, pane.getTabCount());
		pane.setSelectedIndex(pane.getTabCount() - 1);
		pane.setTabComponentAt(pane.getSelectedIndex(),
				new ExitableTab(pane, DEFAULT_TAB_NAME, icons.getExitIcon(),
						icons.getExitHoverIcon(), icons.getSaveMiniIcon(),
						closeAction, false));

		currentInfo.edited = true;
		edited();
		currentInfo.saved = false;
		saved();
		currentInfo.selected = true;
		onCaretChange();
	}


	@Override
	public Path getFilePath() {
		return currentInfo.filePath;
	}


	@Override
	public Frame getFrame() {
		return this;
	}


	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		currentInfo.filePath = path;
		pane.setTabComponentAt(pane.getSelectedIndex(),
				new ExitableTab(pane, path.getFileName().toString(),
						icons.getExitIcon(), icons.getExitHoverIcon(),
						icons.getSaveMiniIcon(), closeAction, false));
		pane.setToolTipTextAt(pane.getSelectedIndex(),
				currentInfo.filePath.toAbsolutePath().toString());
	}


	@Override
	public NotepadEditor getEditor() {
		return currentEditor;
	}


	@Override
	public void setEditor(NotepadEditor editor) {
		addTab(editor);
	}


	@Override
	public IconPack getIcons() {
		return icons;
	}

	/**
	 * Class used locally to represent the infromation about editors used.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private static class Info {

		/**
		 * Is the editor saved.
		 */
		public boolean saved;

		/**
		 * Is the editor edited.
		 */
		public boolean edited;

		/**
		 * Is the editor selected.
		 */
		public boolean selected;

		/**
		 * Path to the current file, null if newly created file.
		 */
		public Path filePath;
	}


	@Override
	public void save(NotepadEditor editor) {
		pane.setSelectedIndex(pane.indexOfComponent(editor));
		saveAction.actionPerformed(null);
	}


	@Override
	public void close(NotepadEditor editor) {
		pane.remove(editor);
		tabInfo.remove(editor);
	}


	@Override
	public Component getPane() {
		return pane;
	}


	@Override
	public void exit() {
		boolean notSaved = false;
		for (int i = 0; i < pane.getTabCount(); i++) {
			NotepadEditor editor = (NotepadEditor) pane.getComponentAt(i);
			if (!isSaved(editor)) {
				save(editor);
			}
			if (!isSaved(editor)) {
				notSaved = true;
			}
		}
		if (notSaved) {
			int option = JOptionPane.showConfirmDialog(JNotepadPP.this,
					provider.getTranslation(ABORT_EXIT_MESSAGE),
					provider.getTranslation(ABORT_EXIT_NAME),
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				dispose();
				return;
			} else {
				return;
			}
		}
		dispose();
	}


	@Override
	public void localizationChanged() {
		String path;
		if (currentInfo != null && currentInfo.filePath != null) {
			path = currentInfo.filePath.toAbsolutePath().toString();
		} else {
			path = DEFAULT_TAB_NAME;
		}
		this.setTitle(path + " - " + provider.getTranslation(NAME));
	}


	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
