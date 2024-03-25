
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class WatkowyEksperymentator  implements BadaczKostekDoGry{	
	ThreadFactory fabryka;
	private int limitWatkow  ;
	Map<Integer,Map<Integer,Integer>> experiment = new HashMap<>();
	int identyfikator = 0;
	Map<Integer, Boolean> czyzakonczony = new HashMap<>();
	long id ;
	int rzut;
	int dzialajacewatki=0;
	private final Object lock = new Object();
	 private synchronized int getNextId() {
	        return ++identyfikator;
	    }
	/**
	 * Metoda ustala liczbÄ wÄtkĂłw, ktĂłre jednoczeĹnie mogÄ badaÄ kostki do gry.
	 * PoniewaĹź jednÄ kostkÄ badaÄ moĹźe tylko jeden wÄtek metoda jednoczeĹnie ustala
	 * liczbÄ jednoczeĹnie testowanych kostek.
	 * 
	 * @param limitWatkow dozwolona liczba wÄtkĂłw
	 */
	public void dozwolonaLiczbaDzialajacychWatkow(int limitWatkow) {
		this.limitWatkow = limitWatkow;
	}
	/**
	 * Metoda dostarcza obiektu, ktĂłry bÄdzie odpowiedzialny za produkcjÄ wÄtkĂłw
	 * niezbÄdnych do pracy programu. Tylko wyprodukowane przez fabrykÄ wÄtki mogÄ
	 * uĹźywaÄ kostek.
	 * 
	 * @param fabryka referencja do obiektu produkujÄcego wÄtki
	 */
	public void fabrykaWatkow(ThreadFactory fabryka) {
		this.fabryka = fabryka;
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
	    Thread newthread = fabryka.getThread(new Runnable() {
	        @Override
	        public void run() {
	            int thisthreadid = currentId;
	            synchronized (lock) {
	                try {
	                    while (dzialajacewatki >= limitWatkow) {
	                        lock.wait();
	                    }
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                dzialajacewatki++;

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
	                dzialajacewatki--;
	                lock.notify();
	            }
	        }
	    });

	    id = newthread.getId();
	    newthread.start();
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
	 *                      kostkaDoZbadaniaS
	 * @return histogram - mapa, ktĂłrej kluczem jest liczba oczek, wartoĹciÄ liczba
	 *         rzutĂłw, w ktĂłrych otrzymano tÄ liczbÄ oczek.
	 */
	public Map<Integer, Integer> histogram(int identyfikator){
		while(badanieKostkiZakonczono(identyfikator)==false) {
			
		}
		return experiment.get(identyfikator);
}
}