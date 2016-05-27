package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hr.fer.zemris.java.gui.calc.CalculatorMemory;
import hr.fer.zemris.java.gui.calc.operations.CalculatorButton;

/**
 * <p>
 * Calculator layout. Forms the elements in the parent panel in the form of a
 * simple calculator.
 * </p>
 * <p>
 * Calculator layout places the elements in 5 rows and 7 columns with first 5
 * columns of the first row taken up by a single element, prefferably JLabel
 * serving as a screen. All elements take up same space except for element 1,1
 * which takes up first 5 places in the 1st row.
 * </p>
 * <p>
 * Element constraints are given as objects of {@link RCPosition} class or as
 * string with coordinates in form "y,x" with y being the row and x being the
 * column. IF given position already holds a component, layout will throw an
 * exception.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see CalculatorButton
 * @see CalculatorMemory
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Minimum size of the layout.
	 */
	private Dimension minLayoutSize;

	/**
	 * Preffered size of the layout.
	 */
	private Dimension prefferedLayoutSize;

	/**
	 * Greatest minimum size of the component.
	 */
	private Dimension minComponentSize;

	/**
	 * Greatest preffered size of the component.
	 */
	private Dimension prefferedComponentSize;

	/**
	 * Spacing between buttons.
	 */
	private int spacing;

	/**
	 * Knows if the layout placing is updated.
	 */
	private boolean updated;

	/**
	 * If layout has minimum size.
	 */
	private boolean hasMininumSize;

	/**
	 * If layout has preffered size.
	 */
	private boolean hasPrefferedSize;

	/**
	 * Number of rows.
	 */
	private static final int ROWS = 5;

	/**
	 * Number of columns.
	 */
	private static final int COLUMNS = 7;

	/**
	 * Map tying positions and components.
	 */
	Map<RCPosition, Component> components;


	/**
	 * Creates the layout with no spaces between.
	 */
	public CalcLayout() {
		this(0);
	}


	/**
	 * Creates the layout with the given spacin inbetween the buttons.
	 * 
	 * @param spacing space inbetween buttons
	 */
	public CalcLayout(int spacing) {
		if (spacing < 0) {
			throw new IllegalArgumentException(
					"Warning - Cannot have spacing less thann zero, "
							+ "but was given:" + spacing + "!");
		}
		components = new HashMap<>();

		this.spacing = spacing;
	}


	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition position = null;

		for (RCPosition key : components.keySet()) {
			if (components.get(key).equals(comp)) {
				position = key;
				break;
			}
		}

		components.remove(position);
		updated = false;
	}


	@Override
	public void layoutContainer(Container parent) {
		updateLayout();
		Insets insets = parent.getInsets();

		int topPadding = insets.top;
		int leftPadding = insets.left;
		int bottomPadding = insets.bottom;
		int rightPadding = insets.right;

		Dimension parentSize = parent.getSize();

		int allWidth = (parentSize.width - leftPadding - rightPadding
				- (COLUMNS - 1) * spacing) / COLUMNS;

		int allHeight = (parentSize.height - topPadding - bottomPadding
				- (ROWS - 1) * spacing) / ROWS;

		for (RCPosition pos : components.keySet()) {
			Component comp = components.get(pos);

			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				comp.setLocation(leftPadding + 1, topPadding + 1);
				comp.setSize(5 * allWidth + 4 * spacing, allHeight);
				continue;
			}

			int x = leftPadding + (pos.getColumn() - 1) * (allWidth + spacing);
			int y = topPadding + (pos.getRow() - 1) * (allHeight + spacing);

			comp.setLocation(x, y);
			comp.setSize(allWidth, allHeight);
		}
	}


	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);
		RCPosition position;

		if (constraints.getClass().equals(String.class)) {
			position = new RCPosition((String) constraints);
		} else if (constraints.getClass().equals(RCPosition.class)) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException(
					"Warning - Unsupported constraint type!");
		}

		if (position.getColumn() <= 0 || position.getColumn() > COLUMNS) {
			throw new IllegalArgumentException(
					"Warning - Given x exceeds desired bounds, "
							+ "should be between " + 1 + " and " + COLUMNS
							+ " but was: " + position.getColumn() + "!");
		}
		if (position.getRow() <= 0 || position.getRow() > ROWS) {
			throw new IllegalArgumentException(
					"Warning - Given y exceeds desired bounds, "
							+ "should be between " + 1 + " and " + ROWS
							+ " but was: " + position.getRow() + "!");
		}

		if (position.getRow() == 1) {
			int col = position.getColumn();
			if (col > 1 && col <= 5) {
				throw new IllegalArgumentException(
						"Warning - Cannot place element ar given position!");
			}
		}

		if (components.containsKey(position)) {
			throw new IllegalArgumentException(
					"Warning - Position " + position + " is already taken!");
		}

		components.put(position, comp);
		updated = false;
	}


	/**
	 * Updates the layout if not updated.
	 */
	private void updateLayout() {
		if (updated) {
			return;
		}

		for (Component component : components.values()) {
			updateSizes(component);
		}
		updated = true;
	}


	/**
	 * Updates the sizes of the layout manager with the size of the component.
	 * @param component component with new sizes 
	 */
	private void updateSizes(Component component) {
		int height;
		int width;

		Dimension comp = component.getMinimumSize();
		if (comp != null) {
			if (!hasMininumSize) {
				hasMininumSize = true;
				minComponentSize = new Dimension(0, 0);
				minLayoutSize = new Dimension(0, 0);
			}

			height = Math.max(comp.height, minComponentSize.height);
			width = Math.max(comp.width, minComponentSize.width);
			minComponentSize = new Dimension(width, height);

			height = ROWS * height + (ROWS - 1) * spacing;
			width = COLUMNS + width + (COLUMNS - 1) * spacing;
			minLayoutSize = new Dimension(width, height);
		}

		comp = component.getPreferredSize();
		if (comp != null) {
			if (!hasPrefferedSize) {
				hasPrefferedSize = true;
				prefferedLayoutSize = new Dimension(0, 0);
				prefferedComponentSize = new Dimension(0, 0);
			}

			height = Math.max(comp.height, prefferedComponentSize.height);
			width = Math.max(comp.width, prefferedComponentSize.width);
			prefferedComponentSize = new Dimension(width, height);

			height = ROWS * height + (ROWS - 1) * spacing;
			width = COLUMNS + width + (COLUMNS - 1) * spacing;
			prefferedLayoutSize = new Dimension(width, height);
		}

	}


	@Override
	public Dimension minimumLayoutSize(Container parent) {
		updateLayout();

		if (minLayoutSize == null) {
			return null;
		}

		Insets insets = parent.getInsets();
		Dimension min = new Dimension(
				minLayoutSize.width + insets.left + insets.right,
				minLayoutSize.height + insets.top + insets.bottom);

		return min;
	}


	@Override
	public Dimension preferredLayoutSize(Container parent) {
		updateLayout();

		if (prefferedLayoutSize == null) {
			return null;
		}

		Insets insets = parent.getInsets();
		Dimension preffered = new Dimension(
				prefferedLayoutSize.width + insets.left + insets.right,
				prefferedLayoutSize.height + insets.top + insets.bottom);

		return preffered;
	}


	@Override
	public Dimension maximumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}


	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}


	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}


	/**
	 * Required by LayoutManager, not used.
	 * 
	 * @param name 
	 * @param comp
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}


	/**
	 * Required by LayoutManager, not used.
	 * @param target
	 */
	@Override
	public void invalidateLayout(Container target) {
		updated = false;
	}

}
