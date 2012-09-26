import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Random {
	
	public static void main(String args[]){
		Set<String> set = new HashSet<String>();

		// Add elements to the set
		set.add("a");
		set.add("b");
		set.add("c");
		set.add("d");
		set.add("e");
		set.add("f");
		set.add("g");
		set.add("h");
		set.add("i");

		// Remove elements from the set
		set.remove("c");

		// Get number of elements in set
		int size = set.size();          // 2

		// Adding an element that already exists in the set has no effect
		set.add("a");
		size = set.size();              // 2

		// Determining if an element is in the set
		boolean b = set.contains("a");  // true
		b = set.contains("c");          // false

		// Iterating over the elements in the set
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
		    // Get element
		    System.out.println(it.next());
		}
		set.add("j");
		set.add("k");
		set.add("l");
		set.add("m");
		set.add("n");
		set.add("o");
		set.add("p");
		set.add("q");
		set.add("r");
		System.out.println();
		it = set.iterator();
		while (it.hasNext()) {
		    // Get element
		    System.out.println(it.next());
		}
	    
	}
}
