import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Task1 {
	public static void main(String[] args) {
		String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
											"one", "two", "three", "eight", "two", "three", "four", "one", "nine", "ten"};
		Iterator iterator = workItOut(words).entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry pair = (Map.Entry) iterator.next();
			System.out.println("Слово - " + pair.getKey() + ",\tповторов - " + pair.getValue() + ".");
		}
	}

	public static HashMap workItOut(String[] array) {
		HashMap<String, Integer> solution = new HashMap<>();
		for (String word : array){
			int count;
			if (solution.containsKey(word)) count = solution.get(word).intValue() + 1;
			else count = 1;
			solution.put(word, count);
		}
		return solution;
	}
}
