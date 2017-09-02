public class Task2 {
	public static void main(String[] args) {
		PhoneBook book = new PhoneBook();
		book.add("1234", "Морозов");
		book.add("2345", "Петров");
		book.add("3456", "Иванов");
		book.add("4567", "Морозов");
		book.add("5678", "Иванов");
		System.out.println(book.get("Иванов").toString());
	}
}
