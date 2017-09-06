public class Main {
	static final int size = 10000000;
	static final int h = size / 2;

	public static void main(String[] args) {
		float[] arr = new float[size];
		for (int i = 0; i < arr.length ; i++) {
			arr[i] = 1;
		}
		System.out.println("1-ый способ:");
		method1(arr);
		System.out.println("2-ой способ:");
		method2(arr);
}

	public static void method1(float[] arr){
		long a = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
		}
		System.out.println(System.currentTimeMillis() - a);
	}

	public static void method2(float[] arr){
		float[] a1 = new float[h];
		float[] a2 = new float[h];
		long a = System.currentTimeMillis();
		System.arraycopy(arr, 0, a1, 0, h);
		System.out.println(System.currentTimeMillis() - a);
		System.arraycopy(arr, h, a2, 0, h);
		System.out.println(System.currentTimeMillis() - a);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				method1(a1);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				method1(a2);
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.arraycopy(a1, 0, arr, 0, h);
		System.arraycopy(a2, 0, arr, h, h);
		System.out.println(System.currentTimeMillis() - a);
	}
}
