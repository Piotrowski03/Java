import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
public class LeniwyEksperymentator implements LeniwyBadaczKostekDoGry {
	ExecutorService executor;
	Map<Integer,Map<Integer,Integer>> experiment = new HashMap<>();
	int identyfikator = 0;
	Map<Integer, Boolean> czyzakonczony = new HashMap<>();
	int rzut;
	private final Object lock = new Object();
	 private synchronized int getNextId() {
	        return ++identyfikator;
	    }
	/**
	 * Metoda dostarcza obiektu, ktĂłry bÄdzie odpowiedzialny za realizacjÄ zadaĹ.
	 * Zadania analizy pracy kostek muszÄ byÄ realizowane przez dostarczony przez tÄ
	 * metodÄ ExecutorService.
	 * 
	 * @param executorService serwis, do ktĂłrego naleĹźy dostarczaÄ zadania badania
	 *                        kostek do gry.
	 */
	public void fabrykaWatkow(ExecutorService executorService) {
		executor = executorService;
	}

	/**
	 * Metoda przekazujÄ kostkÄ do zbadania. Metoda nie blokuje wywoĹujÄcego jÄ
	 * wÄtku na czas badania kostki. Metoda zwraca unikalny identyfikator zadania.
	 * Za pomocÄ tego identyfikatora uĹźytkownik bÄdzie mĂłgĹ odebraÄ wynik zlecenia.
	 * 
	 * @param kostka       kostka do zbadania
	 * @param liczbaRzutow liczba rzutĂłw, ktĂłre naleĹźy wykonaÄ w celu zbadania
	 *                     kostki
	 * @return unikalny identyfikator zadania.
	 */
	public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {
		 int currentId = getNextId();
		    Future<Map<Integer,Integer>> rezultat =  executor.submit(() ->{
		            int thisthreadid = currentId;
		            synchronized (lock) {

		                czyzakonczony.put(thisthreadid, false);
		                lock.notify();
		            }

		            Map<Integer, Integer> temporarymap = new HashMap<>();
		            int lokalnaliczbarzutow = liczbaRzutow;
		            KostkaDoGry lokalnakostka = kostka;
		            int rzut;

		            for (int j = 1; j <= lokalnaliczbarzutow; j++) {
		                rzut = lokalnakostka.rzut();
		                temporarymap.put(rzut, temporarymap.getOrDefault(rzut, 0) + 1);
		            }

		            synchronized (lock) {
		                experiment.put(thisthreadid, new HashMap<>(temporarymap)); // Utwórz nową instancję HashMap
		                czyzakonczony.put(thisthreadid, true);
		                lock.notify();
		            }
		            return temporarymap;
		        
		        
		    });
		    
		    return currentId;
		}
	

	/**
	 * Metoda pozwala uĹźytkownikowi spawdziÄ, czy badanie kostki zostaĹo zakoĹczone.
	 * Zaraz po zakoĹczeniu badania kostki uĹźytkownik powinien uzyskaÄ prawdÄ.
	 * 
	 * @param identyfikator identyfikator zadania zwrĂłcony przez metodÄ
	 *                      kostkaDoZbadania
	 * @return true - analiza kostki zakoĹczona, w kaĹźdym innym przypadku false.
	 */
	public boolean badanieKostkiZakonczono(int identyfikator) {
		if(czyzakonczony.containsKey(identyfikator)) {
			return czyzakonczony.get(identyfikator);
			}
			return true;
		
	}

	/**
	 * Wynik badania kostki. Zaraz po potwierdzeniu, Ĺźe wynik jest gotowy uĹźytkownik
	 * powinien uzyskaÄ histogram zawierajÄcy wynik wszystkich rzutĂłw kostkÄ.
	 * 
	 * @param identyfikator identyfikator zadania zwrĂłcony przez metodÄ
	 *                      kostkaDoZbadania
	 * @return histogram - mapa, ktĂłrej kluczem jest liczba oczek, wartoĹciÄ liczba
	 *         rzutĂłw, w ktĂłrych otrzymano tÄ liczbÄ oczek.
	 */
	public Map<Integer, Integer> histogram(int identyfikator){
while(badanieKostkiZakonczono(identyfikator)==false) {
			
		}
		return experiment.get(identyfikator);
	}

}