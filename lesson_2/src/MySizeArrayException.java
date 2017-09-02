public class MySizeArrayException extends Exception {
	MySizeArrayException(int a, int b){
		System.out.println("Массив неверного размера " + a + "x" + b + ", должен быть 4x4");
	}
}
