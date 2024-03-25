package zadania_z_wykładów;

public class pierwiastekN_tego_stopnia {

public static double pierwiastek(double value , double n ) {
	double power = 1.0/n;
	return Math.pow(value,power);
	
}
public static void main(String args[]) {
	int value = Integer.parseInt(args[0]);
	int n = Integer.parseInt(args[1]);
	double result = pierwiastek(value, n);
	System.out.println(result);
}
}
