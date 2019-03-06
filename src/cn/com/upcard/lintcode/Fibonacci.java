package cn.com.upcard.lintcode;

public class Fibonacci {

	public static void main(String[] args) {
		Long time = System.currentTimeMillis();
		System.out.println(fibonacci(45));
		System.out.println(System.currentTimeMillis() - time);
	}
	//0, 1, 1, 2, 3, 5, 8, 13, 21, 34 ... 701408733
	public static int fibonacci(int n) {
		if (n <= 1) {
			return 0;
		}
		if (n == 2) {
			return 1;
		}
		int[] arr = new int[n+1];
		arr[1] = 0;
		arr[2] = 1;
		int i = 3;
		for (; i <= n;) {
			arr[i] = arr[i-1] + arr[i-2];
			i = i + 1;
		}
		return arr[n];
		
//		int a = 1;
//		int b = 1;
//		int c = 1;
//		for (; i < n;) {
//			c = a + b;
//			b = a;
//			a = c;
//			i = i + 1;
//		}
//		return c;
		
		
		// return fibonacci(n-1) + fibonacci(n-2);
	}

}
