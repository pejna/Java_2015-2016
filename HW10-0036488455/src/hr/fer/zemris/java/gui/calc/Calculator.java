package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.operations.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.operations.CalculatorButton;
import hr.fer.zemris.java.gui.calc.operations.InputButton;
import hr.fer.zemris.java.gui.calc.operations.StateChangeButton;
import hr.fer.zemris.java.gui.calc.operations.UnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * <p>
 * Calculator program that uses gui for input and output.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class Calculator extends JFrame {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = -7535153362779674455L;

	/**
	 * Memory of the calculator.
	 */
	CalculatorMemory memory;

	/**
	 * Icon of the program and unselected inverse button.
	 */
	private static final ImageIcon ICON = new ImageIcon("icon.png");

	/**
	 * Icon of selected inverse button.
	 */
	private static final ImageIcon NEGATIVE = new ImageIcon(
			"icon_negative.png");

	/**
	 * Title name of the window of the calculator.
	 */
	private static final String TITLE = "CalcuPeđa";


	/**
	 * Creates the calculator.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(TITLE);
		setIconImage(ICON.getImage());
		setSize(600, 300);
		initGUI();
	}


	/**
	 * Initializes the GUI components.
	 */
	private void initGUI() {
		memory = new CalculatorMemory();
		JPanel panel = new JPanel();
		panel.setLayout(new CalcLayout(10));

		JLabel screen = new JLabel();
		screen.setBackground(Color.YELLOW);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		screen.setOpaque(true);
		screen.enableInputMethods(false);
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		memory.addScreen(screen);
		panel.add(screen, new RCPosition(1, 1));

		CalculatorButton equals = new StateChangeButton("=",
				(m) -> m.calculate());
		panel.add(equals.installOnClick(memory), "1,6");

		CalculatorButton clr = new StateChangeButton("CLR", (m) -> m.clear());
		clr.setBackground(Color.RED);
		clr.setForeground(Color.WHITE);
		clr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(clr.installOnClick(memory), "1,7");

		CalculatorButton reciprocate = new UnaryOperationButton("1/X",
				(x) -> 1 / x, (x) -> x);
		panel.add(reciprocate.installOnClick(memory), "2,1");

		CalculatorButton sin = new UnaryOperationButton("sin",
				(x) -> Math.sin(x), (x) -> Math.asin(x));
		panel.add(sin.installOnClick(memory), "2,2");

		CalculatorButton seven = new InputButton("7", "7");
		panel.add(seven.installOnClick(memory), "2,3");

		CalculatorButton eight = new InputButton("8", "8");
		panel.add(eight.installOnClick(memory), "2,4");

		CalculatorButton nine = new InputButton("9", "9");
		panel.add(nine.installOnClick(memory), "2,5");

		CalculatorButton divide = new BinaryOperationButton("/",
				(x, y) -> x / y, (x, y) -> y / x);
		panel.add(divide.installOnClick(memory), "2,6");

		CalculatorButton reset = new StateChangeButton("RES", (m) -> m.reset());
		panel.add(reset.installOnClick(memory), "2,7");

		CalculatorButton log = new UnaryOperationButton("log",
				(x) -> Math.log10(x), (x) -> Math.pow(10, x));
		panel.add(log.installOnClick(memory), "3,1");

		CalculatorButton cos = new UnaryOperationButton("cos",
				(x) -> Math.cos(x), (x) -> Math.acos(x));
		panel.add(cos.installOnClick(memory), "3,2");

		CalculatorButton four = new InputButton("4", "4");
		panel.add(four.installOnClick(memory), "3,3");

		CalculatorButton five = new InputButton("5", "5");
		panel.add(five.installOnClick(memory), "3,4");

		CalculatorButton six = new InputButton("6", "6");
		panel.add(six.installOnClick(memory), "3,5");

		CalculatorButton multiply = new BinaryOperationButton("*",
				(x, y) -> x * y, (x, y) -> y * x);
		panel.add(multiply.installOnClick(memory), "3,6");

		CalculatorButton push = new StateChangeButton("PUSH", (m) -> m.push());
		panel.add(push.installOnClick(memory), "3,7");

		CalculatorButton ln = new UnaryOperationButton("ln", (x) -> Math.log(x),
				(x) -> Math.pow(Math.E, x));
		panel.add(ln.installOnClick(memory), "4,1");

		CalculatorButton tan = new UnaryOperationButton("tan",
				(x) -> Math.tan(x), (x) -> Math.atan(x));
		panel.add(tan.installOnClick(memory), "4,2");

		CalculatorButton one = new InputButton("1", "1");
		panel.add(one.installOnClick(memory), "4,3");

		CalculatorButton two = new InputButton("2", "2");
		panel.add(two.installOnClick(memory), "4,4");

		CalculatorButton three = new InputButton("3", "3");
		panel.add(three.installOnClick(memory), "4,5");

		CalculatorButton subtract = new BinaryOperationButton("-",
				(x, y) -> x - y, (x, y) -> y - x);
		panel.add(subtract.installOnClick(memory), "4,6");

		CalculatorButton pop = new StateChangeButton("POP", (m) -> m.pop());
		panel.add(pop.installOnClick(memory), "4,7");

		CalculatorButton power = new BinaryOperationButton("x^n",
				(x, y) -> Math.pow(x, y), (x, y) -> Math.pow(x, 1.0 / y));
		panel.add(power.installOnClick(memory), "5,1");

		CalculatorButton ctg = new UnaryOperationButton("ctg",
				(x) -> 1 / Math.tan(x), (x) -> 1 / Math.atan(x));
		panel.add(ctg.installOnClick(memory), "5,2");

		CalculatorButton zero = new InputButton("0", "0");
		panel.add(zero.installOnClick(memory), "5,3");

		CalculatorButton plusMin = new UnaryOperationButton("+/-", (x) -> -x,
				(x) -> -x);
		panel.add(plusMin.installOnClick(memory), "5,4");

		CalculatorButton dot = new InputButton(".", ".");
		panel.add(dot.installOnClick(memory), "5,5");

		CalculatorButton add = new BinaryOperationButton("+", (x, y) -> x + y,
				(x, y) -> y + x);
		panel.add(add.installOnClick(memory), "5,6");

		JCheckBox invert = new JCheckBox("INV");
		invert.setIcon(ICON);
		invert.setSelectedIcon(NEGATIVE);
		invert.setBackground(Color.LIGHT_GRAY);
		invert.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		invert.setText("<html><h2>" + "INV" + "</h2></html>");
		invert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				memory.switchInverse();
			}
		});
		panel.add(invert, "5,7");

		this.add(panel);
	}


	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

}
