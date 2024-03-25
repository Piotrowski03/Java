
import java.util.ArrayList;
import java.util.List;

public class MyMadSet implements MadSet {
	DistanceMeasure measure;
	double minAllowed;
	List<Point> removedpoints = new ArrayList<>();
	List<Point> zbior = new ArrayList<>();
	/**
	 * Ustawienie narzędzia pozwalającego na wyliczenie odległości pomiędzy punktami
	 * 
	 * @param measure obiekt odpowiedzialny za obliczanie odległości
	 * @throws TooCloseException zmiana sposobu liczenia odległości doprowadziła do
	 *                           usunięcia punktów ze zbioru
	 */
	public void setDistanceMeasure(DistanceMeasure measure) throws TooCloseException{
		this.measure = measure;
		
		
			for(int i = 0;i<zbior.size();i++) {
				for(int n=i+1;n<zbior.size();n++) {
						if(measure.distance(zbior.get(i), zbior.get(n))<minAllowed) {
							removedpoints.add(zbior.get(i));
							removedpoints.add(zbior.get(n));
							zbior.remove(i);
							zbior.remove(n-1);
							n--;
							throw new TooCloseException(removedpoints);
						}
				}
			}
		
	}

	/**
	 * Ustalenie minimalnego, dozwolonego dystansu pomiędzy punktami
	 * 
	 * @throws TooCloseException zmiana dystansu doprowadziła do usunięcia punktów
	 *                           ze zbioru
	 * @param minAllowed minimalna dozwolona odległość pomiędzy punktami
	 */
	public void setMinDistanceAllowed(double minAllowed) throws TooCloseException{
		this.minAllowed=minAllowed;
		for(int i = 0;i<zbior.size();i++) {
			for(int n=i+1;n<zbior.size();n++) {
					if(measure.distance(zbior.get(i), zbior.get(n))<minAllowed) {
						removedpoints.add(zbior.get(i));
						removedpoints.add(zbior.get(n));
						zbior.remove(i);
						zbior.remove(n-1);
						n--;
						throw new TooCloseException(removedpoints);
					}
				
				
				
			}
		}
	}

	/**
	 * Próba dodania punktu do zbioru. Punkt jest dodawany jeśli oddalony jest od
	 * każdego innego punktu w zbiorze co najmniej o minAllowed. Jeśli odległość
	 * punktu od istniejących w zbiorze nie jest wystarczająca, nowy punkt nie jest
	 * dodawany i <b>dodatkowo</b> ze zbioru usuwane są także wszystkie punkty,
	 * które z nowym sąsiadowałyby o odległość mniejszą niż limit.
	 * 
	 * @param point punkt, który ma zostać dodany do zbioru
	 * @throws TooCloseException nowy punkt znajduje się zbyt blisko istniejących.
	 *                           Nowy punkt nie jest dodawany do zbioru. Oodatkowo
	 *                           usuwane są ze zbioru te punkty, dla których dodanie
	 *                           nowego spowodowałoby przekroczenie minimalnego
	 *                           dystansu. Nowy punkt również znajduje się na liście
	 *                           usuwanych punktów.
	 */
	public void addPoint(Point point) throws TooCloseException{
		double distance;
		boolean czydodac=true;
		boolean czydodacremoded = true;
		boolean thrown = false;
		List<Integer> toremove = new ArrayList<>();
		int n =0;
		if(zbior.size() ==0) {
			zbior.add(point);
		}
		else {
			while(n<zbior.size()) {
				
				distance = measure.distance(point, zbior.get(n));
				if(distance <minAllowed) {
					czydodac=false;
					removedpoints.add(zbior.get(n));
					if(czydodacremoded == true) {
					removedpoints.add(point);
					czydodacremoded = false;
					}
					toremove.add(n);
					thrown = true;
				}
				
				n++;
				
				
			}
			if(toremove.size()!=0) {
				for(int k=0;k<toremove.size();k++) {
					zbior.remove(toremove.get(k));
				}
			}
			if(czydodac==true) {
				zbior.add(point);
			}
			if(thrown == true) {
				throw new TooCloseException(removedpoints);
			}
			
				
		}
	}
	

	/**
	 * Lista punktów w zbiorze. Lista zawiera punkty w kolejności ich dodawnia do
	 * zbioru.
	 * 
	 * @return lista punktów w zbiorze
	 */
	public List<Point> getPoints(){
		List<Point> korona = new ArrayList<>(zbior);
		return korona;
	}

	/**
	 * Lista punktów w zbiorze posortowana wg. rosnącej odległości od punktu
	 * odniesienia.
	 * 
	 * @param referencePoint punkt odniesienia
	 * @return posortowana lista punktów
	 */
	public List<Point> getSortedPoints(Point referencePoint){
		List<Point> sortedlist = new ArrayList<>(zbior);
		
		Point helpful;
		
		for(int k=0;k<sortedlist.size();k++) {
			for(int i = 0;i<sortedlist.size()-k-1
					;i++) {
			if(measure.distance(sortedlist.get(i), referencePoint)>measure.distance(sortedlist.get(i+1), referencePoint)) {
				helpful = sortedlist.get(i);
				sortedlist.set(i, sortedlist.get(i+1));
				sortedlist.set(i+1, helpful);
			}
			}
		}
		return sortedlist;
	}
}
