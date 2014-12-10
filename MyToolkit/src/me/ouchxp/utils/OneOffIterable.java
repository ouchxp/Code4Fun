package me.ouchxp.utils;

import java.util.Iterator;
import java.util.Queue;

/**
 * Make "read then remove" (or we could say "poll" a queue) operations support foreach semantic.<br>
 * This can allow iterate through a Iterable with early memory release 
 * @author Nan Wu
 *
 * @param <T>
 */
public class OneOffIterable<T> implements Iterable<T> {
	
	/** Wrapped Iterable object */
	private Iterable<T> it;
	
	/**
	 * Constructor
	 * 
	 * @param it
	 */
	public OneOffIterable(Iterable<T> it) {
		this.it = it;
	}
	
	/**
	 * Generate Iterator
	 */
	@Override
	public Iterator<T> iterator() {
		
		if (it instanceof Queue) {
			//If instance is a Queue then use poll operation
			return new Iterator<T>() {
				private Queue<T> queue = (Queue<T>) it;
				
				@Override
				public boolean hasNext() {
					return !queue.isEmpty();
				}
				
				@Override
				public T next() {
					return queue.poll();
				}
				
				@Override
				public void remove() {
					throw new RuntimeException(new IllegalAccessException("One off Iterable do not allow remove element explicitly."));
				}
			};
		} else {
			//If instance is NOT a Queue, use next() then remove();
			return new Iterator<T>() {
				private Iterator<T> iter = it.iterator();
				
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public T next() {
					T obj = iter.next();
					iter.remove();
					return obj;
				}
				
				@Override
				public void remove() {
					throw new RuntimeException(new IllegalAccessException("One off Iterable do not allow remove element explicitly."));
				}
			};
		}
	}
	
	/**
	 * Static wrap method, ease to use.
	 * @param it
	 * @return OneOffIterable<T>
	 */
	public static <T> OneOffIterable<T> wrap(Iterable<T> it) {
		return new OneOffIterable<T>(it);
	}
}
