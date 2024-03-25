import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class Klient implements NetConnection {
		
		public final String ODPOWIEDZ_DLA_OSZUSTA = "Figa";
		String password;
		/**
		 * Metoda przekazuje poprawne hasĹo. Jest nim duĹźa liczba caĹkowita zapisana
		 * jako ciÄg znakĂłw.
		 * 
		 * @param password poprawne hasĹo do serwisu
		 */
		public void password( String password ) {
			this.password = password;
		}
		
		/**
		 * Metoda otwiera poĹÄczenie do serwera dostÄpnego protokoĹem TCP/IP pod adresem
		 * host i numerem portu TCP port.
		 * 
		 * @param host adres IP lub nazwa komputera
		 * @param port numer portu, na ktĂłrym serwer oczekuje na poĹÄczenie
		 */
		public void connect(String host, int port) {
			BigInteger wynik= BigInteger.ZERO;
			BigInteger zmienna;
			String[] Words;
			String ichodp;
			String score;
			Boolean czyzmieniac = false;
			try {
			Map<Integer, String> wiadomosci = new HashMap<>();
			List<BigInteger> wartosci = new ArrayList<>();
			 Socket socket = new Socket(host, port);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
			 String linia;
			 //socket.setSoTimeout(1000);
			 linia=reader.readLine();
			 System.out.println(linia);
			 while(!linia.contains("ale")) {
				 linia=reader.readLine();
				 System.out.println(linia);
			 }
			 outputStream.write("Program\n");
			 System.out.println("Program");
			 outputStream.flush();
			 linia = reader.readLine();
			 do {
				 System.out.println(linia);
				 
				 if(czyzmieniac==true) {
				 zmienna = new BigInteger(linia);
				 
				 wynik = wynik.add(zmienna);
				 }
				 if(linia.contains("UWAGA:")){
					 czyzmieniac = true;
				 }
				 linia = reader.readLine();
			 }while(!linia.equals("EOD"));
			 System.out.println(linia);
			 zmienna = new BigInteger(password);
			 wynik = wynik.add(zmienna);
			 System.out.println(wynik);
			 score = wynik.toString();
			 linia=reader.readLine();
			 System.out.println(linia);
			 linia=reader.readLine();
			 System.out.println(linia);
			 Words = linia.split("\\s+");
			 ichodp = Words[2];
			 if(score.equals(ichodp)) {
				 outputStream.println(score);
				 System.out.println("ok");
				 
			 }
			 else {
				 outputStream.println(ODPOWIEDZ_DLA_OSZUSTA);
				 System.out.println("nie ok");
			 }
			 linia=reader.readLine();
			 System.out.println(linia);
			 
			} 
			catch (Exception e) {
				System.out.println("błąd");
			}
		}
	
}
