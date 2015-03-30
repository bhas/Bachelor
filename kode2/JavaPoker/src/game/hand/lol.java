package game.hand;

public class lol {
	
	public static void main(String[] args) {
		final long startTime = System.nanoTime();

		loop();
		final long duration = System.nanoTime() - startTime;
		System.out.println(duration/1000000000);

	}
	
	private static void loop (){
		int num = 0;
		for (int i = 1; i < 10000000; i++){
			if(isPrime (i) == true)
			{
				num++;
			}
		}
		System.out.println(num);
	}
	
	private static boolean isPrime(int n) {
		 int k = 2;
		 while (k * k <= n && n % k != 0)
		 k++;
		 return n >= 2 && k * k > n;
		}

}
