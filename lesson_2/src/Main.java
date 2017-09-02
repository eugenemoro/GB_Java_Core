public class Main {
	public static void main(String[] args) {
		String[][] array = new String[4][4];
		int fill = 0;
		int randomX, randomY;
		randomX = (int) Math.round(Math.random() * (array.length - 1));
		randomY = (int) Math.round(Math.random() * (array[0].length - 1));
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = "" + fill;
				fill++;
			}
		}
		array[randomX][randomY] = "бяка";

		try{
			System.out.println(sumIt(array));
		} catch (MySizeArrayException e){
			e.printStackTrace();
		} catch (MyArrayDataException e){
			e.printStackTrace();
		}
	}

	public static int sumIt(String[][] array) throws MySizeArrayException, MyArrayDataException{
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array.length != 4 || array[i].length != 4) throw new MySizeArrayException(array.length, array[i].length);
				try {
					sum += Integer.parseInt(array[i][j]);
				} catch (NumberFormatException e) {
					throw new MyArrayDataException(i, j);
				}
			}
		}
		return sum;
	}
}
