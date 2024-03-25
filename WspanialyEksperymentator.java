
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
public class WspanialyEksperymentator implements Eksperymentator {
private KostkaDoGry kostka;
private long czasEksperymentu;
	/**
	 * Eksperymentatorowi przekazujemy kostkę do gry. Wszystkie eksperymenty należy
	 * przeprowadzić z zastosowaniem powierzonej tu kostki. Kostki nie wolno używać
	 * do innych celów niż wykonanie eksperymentów (wszystkie rzuty kostką muszą
	 * zostać uwzględnione w wyliczonych prawdopodobieństwach).
	 * 
	 * @param kostka kostka do gry
	 */
	public void użyjKostki(KostkaDoGry kostka) {
	this.kostka = kostka;
	}

	/**
	 * Ustalenie całkowitego czasu trwania eksperymentu w milisekundach.
	 * Prawdopodobieństwa mają być szacowane przez eksperymentatora jako iloraz
	 * liczby prób zakończonych sukcesem do liczby wszystkich prób. Na wykonanie
	 * wszystkich prób eksperymentator ma czasEksperymentu. W okresie
	 * czasEksperymentu należy wykonać możliwie dużo prób.
	 * 
	 * @param czasEksperymentu całkowity czas na wykonanie eksperymentu
	 */
	public void czasJednegoEksperymentu(long czasEksperymentu) {
		this.czasEksperymentu = czasEksperymentu;
	}

	/**
	 * Metoda zwraca prawdopodobieństwo wyrzucenia określonej, sumarycznej liczby
	 * oczek przy rzucie pewną liczbaKostek. W tym eksperymencie przez
	 * czasEksperymentu rzucamy liczbaKostek. Metoda stara się oszacować szansę na
	 * wyrzucenie określonej sumy oczek, zliczamy więc wylosowane liczby oczek.
	 * Znając liczbę wszystkich rzutów (każdy to rzut liczbaKostek kostek) i ile
	 * razy wylosowała się określona suma można wyznaczyć poszukiwane
	 * prawdopodobieństwa.
	 * 
	 * @param liczbaKostek liczba kostek używana w jedym rzucie
	 * @return mapa, w której kluczem jest sumaryczna liczba oczek a wartością
	 *         szansa na wystąpienie tej sumy oczek.
	 */
	public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek){
		long liczbarzutow = 0;
		long czaspracy = System.currentTimeMillis() + czasEksperymentu;
		Map <Integer, Integer> liczbapowtorzen = new HashMap<>();
		int wartosc = 0;
		int highestscore = liczbaKostek*6;
		Map<Integer, Double> mapa = new HashMap<>();
		for(int i = 1;i<=highestscore;i++) {
			liczbapowtorzen.put(i, 0);
		}
		do {	
		for(int i=0;i<liczbaKostek;i++) {
			
			wartosc += kostka.rzut();
		}
			
			
			liczbapowtorzen.put(wartosc,liczbapowtorzen.getOrDefault(wartosc,  0)+1);
			liczbarzutow++;
			wartosc=0;
		}
		while(System.currentTimeMillis()<czaspracy);
		for(int i=1;i <=highestscore;i++) {
		mapa.put(i, (double) liczbapowtorzen.get(i)/liczbarzutow);
		}
		return mapa;
		
	}

	/**
	 * Metoda sprawdza szansę na wyrzucenie określonej sekwencji oczek. Zadaną
	 * sekwencją może być np. 1, 2 i 4. Jeśli w kolejnych rzutach kostką otrzymamy
	 * przykładowo:
	 * 
	 * <pre>
	 * 1 2 5
	 * 3 4 1  &lt;- w tej i kolejnej linijce mamy łącznie 1 2 i 4, ale tu nie zliczamy trafienia
	 * 2 4 1
	 * <b>1 2 4</b>
	 * </pre>
	 * to szansa na wyrzucenie tej sekwencji to: 1/5 czyli 0.2.
	 * 
	 * @param sekwencja lista kolejnych liczb oczek jakie mają zostać wyrzucone
	 * @return szansa na wyrzucenie wskazanej sekwencji.
	 */
	public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja) {
		ArrayList<Integer> tab= new ArrayList<>();	//pomocnicza tablica
		int value = 0;	//zmienna pomocnicza zapisujaca wartosc rzutu kostka
		int i = 0;		//iterator zapisujacy ile razy rzucilismy kostką
		int succesful_sequence = 0;	//liczba pojawienia się szukanej sekwencji
		int iter;
		double sequence_number = 0.0;
		double probability = 0.0;
		long czaspracy = System.currentTimeMillis() + czasEksperymentu;
			do{
				value= kostka.rzut();
				tab.add(value);
				i++;
			}	//rzucam koscmi i wpisuje wynik do tablicy
		while(System.currentTimeMillis()<czaspracy);
			for(int j = 0;j<=i-sekwencja.size();j+=sekwencja.size()) {
				iter = 0;
				for(int k=0;k<sekwencja.size();k++) {
					if(tab.get(j+k)== sekwencja.get(k)){
						iter++;
					}
					}
				if(iter == sekwencja.size()) {
					succesful_sequence++;
				}
				sequence_number++;
				
			}
			probability = (double)succesful_sequence/sequence_number;
			return probability;
	}

	/**
	 * Metoda sprawdza szansę na wyrzucenie określonych liczb oczek w dowolnej
	 * kolejności. Zadanym zbiorem może być np. 1, 2 i 4. Jeśli w kolejnych rzutach
	 * kostką otrzymamy przykładowo:
	 * 
	 * <pre>
	 * <b>2 1 4</b>
	 * 3 4 1  &lt;- w tej i kolejnej linijce mamy łącznie 1 2 i 4, ale tu nie zliczamy trafienia
	 * 2 4 5
	 * <b>1 2 4</b>
	 * </pre>
	 * to szansa na wyrzucenie tej sekwencji to: 2/5 czyli 0.4.
	 * 
	 * @param oczka liczba oczek jakie mają zostać wyrzucone
	 * @return szansa na wyrzucenie wskazanych liczb oczek
	 */
	public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka) {
		long czaspracy = System.currentTimeMillis() + czasEksperymentu;
		ArrayList<Integer> wartosciwyrzucone = new ArrayList<>();
		Set<Integer> checkset = new HashSet<>(); 
		int value = 0;
		int i=0;
		double probability = 0;
		double sequence_number = 0;
		double succesful_sequence = 0;
		while(System.currentTimeMillis()< czaspracy); {
			value = kostka.rzut();
			wartosciwyrzucone.add(value);
			i++;
		}
		for(int j = 0;j<i-oczka.size();j+=oczka.size() ) {
			for(int k = 0;k<oczka.size();k++) {
				checkset.add(wartosciwyrzucone.get(j+k));

			}
			if(oczka.containsAll(checkset)) {
				succesful_sequence++;
			}
			sequence_number++;
			checkset.clear();
		}
		probability= succesful_sequence/sequence_number;
		return probability;
		
	}
	
	}