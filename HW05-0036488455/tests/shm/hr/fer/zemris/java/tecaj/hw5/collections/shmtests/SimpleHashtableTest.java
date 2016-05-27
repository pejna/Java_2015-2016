package hr.fer.zemris.java.tecaj.hw5.collections.shmtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	@Test
	public void testDefaultConstructorNotNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		assertNotNull("Default hashtable constructor should'n return null.",
				table);
	}
	
	@Test
	public void testConstructorNotNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		
		assertNotNull("Hashtable constructor should'n return null",
				table);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorZeroCapacity() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeCapacity() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(-1);
	}
	
	@Test
	public void testEmptyTable() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable();
		
		assertEquals("Table should be the size of 0", 0, table.size());
	}
	
	@Test
	public void testPutSingleElement() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable();
		
		table.put("one", 1);
		
		assertEquals("Table should be the size of 1", 1, table.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPutIllegalKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put(null, null);
	}
	
	@Test
	public void testPutSameKey() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Hodor", "Hodor");
		
		table.put("Hodor", "Hodor Hodor");
		
		assertEquals("Hodor hodored twice.", "Hodor Hodor", table.get("Hodor"));
	}
	
	@Test
	public void testGetReturnsSameValue() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Jon Snow", "Knows nothing");
		
		String value = table.get("Jon Snow");
		
		assertEquals("You can't change his knowledge.", "Knows nothing", value);
	}
	
	@Test
	public void testGetNonExistent() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		String characterPeopleLike = table.get("Joffrey Baratheon");
		
		assertEquals("TNobody likes Joffrey.", null, characterPeopleLike);
	}
	
	@Test
	public void testGetNull() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		String value = table.get(null);
		
		assertEquals("Table can't have null key.", null, value);
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashtable<String, String> favorites = new SimpleHashtable<>();
		
		favorites.put("Tyrion Lannister", "The Imp");
		
		assertTrue("Come on, he's everyone's favorite.",
				favorites.containsKey("Tyrion Lannister"));
	}
	
	@Test
	public void testContainsKeyNonExistent() {
		SimpleHashtable<String, String> baratheons = new SimpleHashtable<>();
		
		assertFalse("Joffrey isn't a Baratheon.",
				baratheons.containsKey("Joffrey"));
		
	}
	
	@Test
	public void testContainsKeyNull() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		assertFalse("Table can't have null values.",
				table.containsKey(null));
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Ned Stark", "alive");
		
		TableEntry<String, String>entrio = null;
		for(TableEntry<String, String> entry : table){
			entrio = entry;
		}
		
		assertTrue(entrio.toString(), table.containsValue("alive"));		
	}
	
	@Test
	public void testContainsValueNonExistent() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("The Winds of Winter", "in progress");
		
		assertFalse("George Martin isn't done yet.",
				table.containsValue("done"));		
	}
	
	@Test
	public void testContainsValueNull() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Date of the next book", null);
		assertTrue("Table can have null values.",
				table.containsValue(null));		
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, String> couple = new SimpleHashtable<>();
		
		couple.put("Jaime", "Lannister");
		couple.put("Cersei", "Lannister");
		
		couple.remove("Cersei");
		
		assertEquals("They're not a couple anymore.", 1, couple.size());
		
		assertTrue("But he's a good guy now!",
				couple.containsKey("Jaime"));
		
		assertFalse("Not interested anymore.",
				couple.containsKey("Cersei"));
	}
	
	@Test
	public void testRemoveFromEmpty() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.remove("Troll"); //shouldn't explode
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, String> starks = new SimpleHashtable<>();
		
		starks.put("Eddard", "Stark");
		starks.put("Catelyn", "Stark");
		starks.put("Rob", "Stark");
		
		starks.clear();
		
		assertTrue("Sorry, but I have bad news for you", starks.isEmpty());
	}
	
	@Test
	public void testClearEmpty() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.clear(); //shouldn't explode
	}
	
	
	@Test
	public void testIterator() {
		String[] people = {"Renly", "Sansa", "Mace", "Daenerys"};
		String[] houses = {"Baratheon", "Stark", "Tyrell", "Targaryen"};
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		for (int i = 0, n = people.length; i < n; i++) {
			
			table.put(people[i], houses[i]);
			
		}
		
		StringBuilder itHouses = new StringBuilder();
		
		Iterator<SimpleHashtable.TableEntry<String, String>> it =
				table.iterator();
		
		while (it.hasNext()) {
			SimpleHashtable.TableEntry<String, String> pair = it.next();
			
			itHouses.append(pair.getValue());
		}
		
		String strHouses = itHouses.toString();
		
		for (String house : houses) {
			
			assertTrue("Something is missing.", strHouses.contains(house));
			
		}
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testIteratorInvalidUse1() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.iterator().next();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testIteratorInvalidUse2() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Daenerys", "Targaryen");
		table.put("Viserys", "Targaryen");
		
		Iterator<SimpleHashtable.TableEntry<String, String>> it =
				table.iterator();
		
		while (it.hasNext()) {
			SimpleHashtable.TableEntry<String, String> pair = it.next();
			
			if (pair.getKey().equals("Viserys")) {
				
				it.remove();
				it.remove(); //he's dead already, no need to be a savage
				
			}
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorInvalidUse3() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Melisandre", "red hair");
		table.put("Ygritte", "red hair");
		
		Iterator<SimpleHashtable.TableEntry<String, String>> it =
				table.iterator();
		
		table.remove("Ygritte");
		
		while (it.hasNext()) {
			it.next();
		}
	}
	
	@Test
	public void testIteratorNotInvalidUse() {
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put("Melisandre", "red hair");
		table.put("Ygritte", "red hair");
		
		Iterator<SimpleHashtable.TableEntry<String, String>> it =
				table.iterator();
		
		table.put("Ygritte", "black hair"); //NOT a structural change!
		
		while (it.hasNext()) {
			it.next();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCapacity() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>(0);
	}

	@Test
	public void putTest1() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);

		assertEquals(Integer.valueOf(1), shm.get("Ivo"));
		assertEquals(Integer.valueOf(2), shm.get("Marko"));
		assertEquals(Integer.valueOf(3), shm.get("Zvonko"));
		assertEquals(Integer.valueOf(4), shm.get("Marija"));
		assertEquals(4, shm.size());
	}

	@Test
	public void putTest2() {
		// put update test
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		shm.put("Ivo", 5);
		shm.put("Zvonko", 10);

		assertEquals(Integer.valueOf(5), shm.get("Ivo"));
		assertEquals(Integer.valueOf(2), shm.get("Marko"));
		assertEquals(Integer.valueOf(10), shm.get("Zvonko"));
		assertEquals(Integer.valueOf(4), shm.get("Marija"));
		assertEquals(4, shm.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void putTestIllegal() {
		// put update test
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put(null, 1);
	}

	@Test
	public void containsTest() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);

		assertEquals(true, shm.containsKey("Ivo"));
		assertEquals(false, shm.containsKey("Iv"));

		assertEquals(true, shm.containsValue(3));
		assertEquals(false, shm.containsKey(10));
	}

	@Test
	public void containsIllegal() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);

		assertEquals(false, shm.containsKey(null));
	}

	@Test
	public void removeTest() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);

		shm.remove("Ivo");
		assertEquals(3, shm.size());

		shm.remove("Ivica");
		assertEquals(3, shm.size());

		shm.remove("Zvonko");
		assertEquals(2, shm.size());

		shm.remove("Marija");
		assertEquals(1, shm.size());

		shm.remove("Marko");
		assertEquals(0, shm.size());
	}

	@Test
	public void clearTest() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);

		shm.clear();
		assertEquals(true, shm.isEmpty());
	}

	@Test(expected = NoSuchElementException.class)
	public void firstRemoveCall() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		Iterator<TableEntry<String, Integer>> it = shm.iterator();
		it.remove();
	}

	@Test
	public void validIteration() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		Iterator<TableEntry<String, Integer>> it = shm.iterator();
		assertEquals("Zvonko=3", it.next().toString());
		assertEquals("Ivo=1", it.next().toString());
		assertEquals("Marko=2", it.next().toString());
		assertEquals("Marija=4", it.next().toString());
	}

	@Test(expected = NoSuchElementException.class)
	public void noMoreElements() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		Iterator<TableEntry<String, Integer>> it = shm.iterator();
		it.next();
		it.next();
		it.next();
		it.next();
		it.next();
	}

	@Test(expected = IllegalStateException.class)
	public void consecutiveRemoveCalls() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		Iterator<TableEntry<String, Integer>> it = shm.iterator();
		while (it.hasNext()) {
			it.remove();
			it.remove();
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void tableModification() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		Iterator<TableEntry<String, Integer>> iter = shm.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivo")) {
				shm.remove("Ivo");
			}
		}
	}

	@Test
	// have a look at System.out
	public void cartesian() {
		SimpleHashtable<String, Integer> shm = new SimpleHashtable<>();
		shm.put("Ivo", 1);
		shm.put("Marko", 2);
		shm.put("Zvonko", 3);
		shm.put("Marija", 4);
		
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : shm) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : shm) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}
	}
}
