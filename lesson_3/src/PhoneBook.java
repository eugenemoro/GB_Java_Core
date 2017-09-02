import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PhoneBook {
	HashMap<String, String> book = new HashMap<>();

	public ArrayList get(String name) {
		return book.entrySet().stream()
						.filter(e -> e.getValue().equals(name))
						.map(Map.Entry :: getKey)
						.collect(Collectors.toCollection(ArrayList :: new));
	}

	public void add(String phoneNumber, String name) {
		book.put(phoneNumber, name);
	}
}
