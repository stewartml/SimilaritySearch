package ndi;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Queue;

import org.junit.Test;

public class FixedSizePriorityQueueTest {

	@Test
	public void test() {
		Queue<Integer> queue = new FixedSizePriorityQueue<Integer>(3);
		queue.add(6);
		assertEquals(1, queue.size());
		queue.add(5);
		assertEquals(2, queue.size());
		queue.add(1);
		assertEquals(3, queue.size());
		queue.add(3);
		assertEquals(3, queue.size());
		
		Iterator<Integer> it = queue.iterator();
		assertEquals(1, it.next().intValue());
		assertEquals(3, it.next().intValue());
		assertEquals(5, it.next().intValue());
		assertFalse(it.hasNext());
		
		assertEquals(1, queue.remove().intValue());
		assertEquals(2, queue.size());
		
		it = queue.iterator();
		assertEquals(3, it.next().intValue());
		assertEquals(5, it.next().intValue());
		assertFalse(it.hasNext());
		
		queue.add(4);
		it = queue.iterator();
		assertEquals(3, it.next().intValue());
		assertEquals(4, it.next().intValue());
		assertEquals(5, it.next().intValue());
		assertFalse(it.hasNext());
		
		queue.remove();
		queue.add(1);
		it = queue.iterator();
		assertEquals(1, it.next().intValue());
		assertEquals(4, it.next().intValue());
		assertEquals(5, it.next().intValue());
		assertFalse(it.hasNext());
	}

}
