package zadania_z_wykładów;

public class sredniageometryczna {
	public static double obliczanie(String[] args) {
		double sum = 1.0;
		for(int i=0;i<args.length;i++) {
			sum = sum*Double.parseDouble(args[i]);
		}
		double length = args.length;
		double dzielenie= 1.0/length;
		double result = Math.pow(sum,dzielenie);
		return result;
		
	}
public static void main(String[] args) {
 double wynik = obliczanie(args);
 System.out.println(args.length);
 System.out.println(wynik);
}
}
