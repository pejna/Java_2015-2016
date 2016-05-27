package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;


/**
 * Factory method used for creating daemon threads.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class DaemonicThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	}

}
