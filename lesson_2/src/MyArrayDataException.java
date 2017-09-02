public class MyArrayDataException extends Exception {
	int x, y;
	MyArrayDataException(int x, int y){
		System.out.println("Неверные данные в ячейке [" + x + "][" + y + "]");
	}
}
