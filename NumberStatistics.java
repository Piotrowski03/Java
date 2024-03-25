

import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NumberStatistics implements Statistics{
	int[][] pole;
	private int length; 
	/**
	 * Ustalenie długości boku płaszczyzny. 
	 * Płaszczyzna jest kwadratem o boku length.
	 * @param length długość boku płaszczyzny.
	 */
	public void sideLength( int length ) {
	this.length = length;
	pole = new int[length][length];
	}

	/**
	 * Do płaszczyzny dodajemy liczby.
	 * 
	 * @param numberPositions mapa zawierająca jako klucz wartość liczby, zbiór
	 *                        zawiera położenia, w których liczbę należy umieścić.
	 */
	public void addNumbers(Map<Integer, Set<Position>> numberPositions) {
		int currentrow = 0;
		int currentcol = 0;
		Position pozycja;
		for(int i = 1;i<=9;i++) {
			 if (numberPositions.containsKey(i)) {
			        List<Position> myList = new ArrayList<>(numberPositions.get(i));
				for(int j=0;j<myList.size();j++) {
					pozycja = myList.get(j);
					currentcol = pozycja.col()-1;
					currentrow = pozycja.row ()-1;
					pole[currentrow][currentcol]= i;
		        }
			 }
		}
	}

	/**
	 * Pobranie informacji o zajętych przez liczby sąsiednich polach. Wynik zwracany
	 * jest w postaci mapy map. Obliczenia prowadzone są niezależnie dla różnych
	 * wartości sąsiednich liczb. Zewnętrzna mapa zawiera klucz, który określa
	 * wartość liczby, wewnętrzna mapa to informacja dla jakiego kwadratu odległości
	 * znaleziono ile liczb.
	 * 
	 * @param position           położenie, którego sąsiedztwo jest badane.
	 * @param maxDistanceSquared maksymalny kwadrat odległości dla jakiej należy
	 *                           jeszcze odszukiwać sąsiadów.
	 * @return informacja o sąsiadach pola position.
	 */
	public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared){
	Map<Integer, Map<Integer, Integer>> Polaposition  = new HashMap<>();
	int maxdistance = (int)Math.sqrt(maxDistanceSquared);
	int row = 0;
	int column = 0;
	int distancex;
	int distancey;
	int searchx;
	int searchy;
	int value;
	int distance;
	int iteration;
	int i;
	int j;
	row = position.row()  -1;
	column = position.col() -1;
	for( i = row-maxdistance;i<=row+maxdistance;i++) {
			searchy= (i % length + length) % length;
			
			for( j= column - maxdistance;j<= column+maxdistance;j++) {
				searchx= (j % length + length ) % length;
				if(pole[searchy][searchx]!= 0) {
					value = pole[searchy][searchx];
					distance = (row-i)*(row-i)+(column-j)*(column-j);
					if(distance<=maxDistanceSquared) {
					if (!Polaposition.containsKey(value)) {
	                    Polaposition.put(value, new HashMap<>());
	                }
					if (!Polaposition.get(value).containsKey(distance)) {
	                    Polaposition.get(value).put(distance, 0);
	                }
					iteration = Polaposition.get(value).get(distance) + 1;
					Polaposition.get(value).put(distance, iteration);
					}
					}
				}
			
		}
	
	return Polaposition;
	
			
	}
}
