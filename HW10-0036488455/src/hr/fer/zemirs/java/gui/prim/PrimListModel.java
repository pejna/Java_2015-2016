package hr.fer.zemirs.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * <p>
 * {@link ListModel} implementation holding {@link Integer}s containing prime
 * number.
 * </p>
 * <p>
 * Enables to get the next prime number and notify all {@link ListDataListener}s
 * subscribed to the model with using method next().
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see ListModel
 * @see ListDataEvent
 * @see ListDataListener
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List of primary numbers already found.
	 */
	List<Integer> elements;

	/**
	 * List of listeners subscribed.
	 */
	List<ListDataListener> listeners;


	/**
	 * Creates the model.
	 */
	public PrimListModel() {
		elements = new ArrayList<>();
		listeners = new ArrayList<>();
	}


	/**
	 * Calculates the next prime number and notifies all listeners of it's
	 * findings.
	 */
	public void next() {
		ListDataEvent event = new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, getSize(), getSize());

		if (elements.size() == 0) {
			elements.add(2);
		} else {
			int current = elements.get(elements.size() - 1) + 1;

			boolean divisible = true;
			while (divisible)
				divisible = false;
			for (int e : elements) {
				if (current % e == 0) {
					divisible = true;
					current++;
					break;
				}
			}
			elements.add(current);
		}

		for (ListDataListener listener : listeners) {
			listener.intervalAdded(event);
		}
	}


	@Override
	public int getSize() {
		return elements.size();
	}


	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}


	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}


	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
