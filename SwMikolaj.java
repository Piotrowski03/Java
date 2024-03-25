//package zestaw12;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwMikolaj implements Inwentaryzator{
	
	/**
	 * Zlecenie inwentaryzacji klas o nazwach podanych jako kolejne pozycje
	 * przekazywanej listy. Inwentaryzacja polega na przeglÄdniÄciu za pomocÄ
	 * mechanizmĂłw reflekcji klas, i odszukanie w nich <b>publicznych, niestatycznych
	 * pĂłl typu "int" o podanych poniĹźej nazwach</b>. Z pĂłl naleĹźy odczytaÄ ich wartoĹÄ.
	 * WartoĹci pĂłl o identycznych nazwach naleĹźy zsumowaÄ. Wynikiem jest mapa,
	 * ktĂłrej kluczem jest nazwa pola, wartoĹciÄ jest uzyskana suma dla pĂłl o tej
	 * nazwie. JeĹli wĹrĂłd testowanych klas w Ĺźadnej nie bÄdzie pola o odpowiedniej nazwie i 
	 * wĹasnoĹciach, to danej nazwy nie umieszcza siÄ w mapie.
	 * <br>
	 * Lista interesujÄcych pĂłl:
	 * <ul>
	 * <li>bombki</li>
	 * <li>lancuchy</li>
	 * <li>cukierki</li>
	 * <li>prezenty</li>
	 * <li>szpice</li>
	 * <li>lampki</li>
	 * </ul>
	 * 
	 * @param listaKlas klasy do intenteryzacji
	 * @return mapa bÄdÄca wynikiem inwentaryzacji
	 */
	public Map<String, Integer> inwentaryzacja(List<String> listaKlas){
	List<String> nazwypol = new ArrayList<>();
	nazwypol.add("bombki");
	nazwypol.add("lancuchy");
	nazwypol.add("cukierki");
	nazwypol.add("prezenty");
	nazwypol.add("szpice");
	nazwypol.add("lampki");
	Map<String, Integer> wartosci = new HashMap<>();
	try {
		for(String nazwaklasy : listaKlas) {
			Class<?> klasa = Class.forName(nazwaklasy);
			Field[] pola = klasa.getDeclaredFields();
			
			for(Field fieldname : pola) {
					
					
							
						if(nazwypol.contains(fieldname.getName())) {
                        	if (!Modifier.isStatic(fieldname.getModifiers())&& Modifier.isPublic(fieldname.getModifiers())) {
                                
                               
								Object wartoscPola = fieldname.get(klasa.newInstance());
                                if (fieldname.getType()== int.class) {
                        	if (fieldname.getDeclaringClass() == klasa) {
                            int wartoscInt = (Integer) wartoscPola;
                            wartosci.put(fieldname.getName(), wartosci.getOrDefault(fieldname.getName(), 0) + wartoscInt);
                        	}
					}
				}
				
						}
				
									}
			}
	}
	catch(Exception e) {
	}
		
			
		
	
		return wartosci;
	}
}
	
