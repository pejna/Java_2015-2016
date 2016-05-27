package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.Timer;

import hr.fer.zemris.java.hw11.jnotepadpp.Notepad;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Status toolbar intended for use with {@link Notepad} implementing classes.
 * Provides displaying potential for information about the current editor of the
 * notepad.
 * <p>
 * Contains {@link LocalizableLabel} as it's contained elements so
 * {@link ILocalizationProvider} should be provided in the constructor. Requires
 * the folowing localiation keys:
 * </p>
 * <p>
 * length.text - for name of the text length displaying label line.text - for
 * name of the line displaying label column.text - for name of the column
 * displaying label selected.text - for name of the selected character number
 * dispaying label
 * </p>
 * <p>
 * Also shows current time in the right corner of the toolbar. Labels have key
 * according to their intended use but can also be used for other purpses.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see LocalizableLabel
 */
public class NotepadStatusToolbar extends JToolBar {

	/**
	 * Serial version ID of the class.
	 */
	private static final long serialVersionUID = -4433683654114969037L;

	/**
	 * Length name displaying label.
	 */
	private JLabel lengthNameLabel;

	/**
	 * Length value displaying label.
	 */
	private JLabel lengthValueLabel;

	/**
	 * Line name displaying label.
	 */
	private JLabel lineNameLabel;

	/**
	 * Line value displaying label.
	 */
	private JLabel lineValueLabel;

	/**
	 * Column name displaying label.
	 */
	private JLabel columnNameLabel;

	/**
	 * Column value displaying label.
	 */
	private JLabel columnValueLabel;

	/**
	 * Selected name displaying label.
	 */
	private JLabel selectedNameLabel;

	/**
	 * Selected value displaying label.
	 */
	private JLabel selectedValueLabel;

	/**
	 * Label displaying time and date.
	 */
	private JLabel dateTimeLabel;

	/**
	 * Formatter of date and time display.
	 */
	private SimpleDateFormat formatter;

	/**
	 * Timer for date ticking.
	 */
	private Timer timer;

	/**
	 * Default value displayed on the labels.
	 */
	private static final String DEFAULT_NUMBER = "0";

	/**
	 * Default date time format of the formatter.
	 */
	private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd   hh:mm:ss";

	/**
	 * Length name label localization key.
	 */
	private static final String LENGTH_LABEL_KEY = "length.text";

	/**
	 * Line name label localization key.
	 */
	private static final String LINE_LABEL_KEY = "line.text";

	/**
	 * Column name label localization key.
	 */
	private static final String COLUMN_LABEL_KEY = "column.text";

	/**
	 * Selected name label localization key.
	 */
	private static final String SELECTED_LABEL_KEY = "selected.text";


	/**
	 * Creates the status toolbar of the given notepad.
	 * 
	 * @param notepad
	 *            notepad requireing this toolbar
	 * @param provider
	 *            localization provider
	 */
	public NotepadStatusToolbar(Notepad notepad,
			ILocalizationProvider provider) {
		Objects.requireNonNull(notepad);
		Objects.requireNonNull(provider);

		lengthNameLabel = new LocalizableLabel(LENGTH_LABEL_KEY, provider);
		lengthNameLabel.setOpaque(false);
		lengthNameLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 2));
		lengthValueLabel = new JLabel(DEFAULT_NUMBER);
		lengthValueLabel.setOpaque(false);
		lengthValueLabel
				.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 30));

		add(lengthNameLabel);
		add(lengthValueLabel);
		addSeparator();

		lineNameLabel = new LocalizableLabel(LINE_LABEL_KEY, provider);
		lineNameLabel.setOpaque(false);
		lineNameLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 1));
		lineValueLabel = new JLabel(DEFAULT_NUMBER);
		lineValueLabel.setOpaque(false);
		lineValueLabel.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));

		add(lineNameLabel);
		add(lineValueLabel);

		columnNameLabel = new LocalizableLabel(COLUMN_LABEL_KEY, provider);
		columnNameLabel.setOpaque(false);
		columnNameLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 1));
		columnValueLabel = new JLabel(DEFAULT_NUMBER);
		columnValueLabel.setOpaque(false);
		columnValueLabel.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));

		add(columnNameLabel);
		add(columnValueLabel);

		selectedNameLabel = new LocalizableLabel(SELECTED_LABEL_KEY, provider);
		selectedNameLabel.setOpaque(false);
		selectedNameLabel
				.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 1));
		selectedValueLabel = new JLabel(DEFAULT_NUMBER);
		selectedValueLabel.setOpaque(false);
		selectedValueLabel
				.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));

		add(selectedNameLabel);
		add(selectedValueLabel);

		add(Box.createHorizontalGlue());

		addSeparator();

		dateTimeLabel = new JLabel(DEFAULT_NUMBER);
		dateTimeLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 16));
		dateTimeLabel.setOpaque(false);
		formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
		timer = new Timer(500, new ActionListener() {

			private long currentTime = 0;


			@Override
			public void actionPerformed(ActionEvent e) {
				long newTime = System.currentTimeMillis();
				if (newTime - currentTime > 1000) {
					currentTime = newTime;
				}
				dateTimeLabel.setText(formatter.format(currentTime));
				dateTimeLabel.revalidate();
			}
		});
		timer.start();

		add(dateTimeLabel);
	}


	/**
	 * Gets the length value label for editing purposes.
	 * 
	 * @return returns the length value label
	 */
	public JLabel getLengthLabel() {
		return lengthValueLabel;
	}


	/**
	 * Gets the line value label for editing purposes.
	 * 
	 * @return returns the line value label
	 */
	public JLabel getLineLabel() {
		return lineValueLabel;
	}


	/**
	 * Gets the line value label for editing purposes.
	 * 
	 * @return returns the line value label
	 */
	public JLabel getColumnLabel() {
		return columnValueLabel;
	}


	/**
	 * Gets the selected value label for editing purposes.
	 * 
	 * @return returns the value label
	 */
	public JLabel getSelectedLabel() {
		return selectedValueLabel;
	}
}
