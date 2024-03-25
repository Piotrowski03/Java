//package zestaw5;

import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Stack;

class ProgrammableCalculator implements ProgrammableCalculatorInterface {
	Map<Integer,String> console = new HashMap<>();
	private LineReader stdin;
	private LinePrinter stdout;
	BufferedReader reader;
	int biggestkey;
	Map<String, Integer> wartosci = new HashMap<>();
	/**
	 * Metoda ustawia BufferedReader, ktĂłry pozwala na odczyt kodu ĹşrĂłdĹowego
	 * programu.
	 * 
	 * @param reader obiekt BufferedReader.
	 */
	public void programCodeReader(BufferedReader reader) {
	    this.reader = reader;
	    String actual_line;
	    String[] words;

	    try {
	        while ((actual_line = reader.readLine())!= null) {
	        	if(!actual_line.trim().isEmpty()) {
	        	//actual_line=actual_line.toLowerCase();
	            words = actual_line.split("\\s+");
	            console.put(Integer.parseInt(words[0]), actual_line);
	            // Check for the biggest key
	            if (Integer.parseInt(words[0]) > biggestkey) {
	                biggestkey = Integer.parseInt(words[0]);
	            }
	        	}
	        }
	    } catch (IOException e ) {
	        e.printStackTrace(); // You might want to log the exception or handle it differently
	    }
	}
/**class ConsoleLineReader implements LineReader {
        private BufferedReader reader;

        public ConsoleLineReader() {
            this.reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        }

        @Override
        public String readLine() {
            StringBuilder input = new StringBuilder();
            try {
                int charCode;
                while ((charCode = reader.read()) != -1) {
                    char character = (char) charCode;
                    if (character == '\n') {
                        break;
                    }
                    input.append(character);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return input.toString();
        }
    }

    class ConsoleLinePrinter implements LinePrinter {
        @Override
        public void printLine(String line) {
            System.out.println(line);
        }
    }*/

	/**
	 * Metoda zwraca pojedynczÄ liniÄ odczytanÄ ze standardowego wejĹcia. Na koĹcu
	 * linii <b>nie ma</b> znaku przejĹcia do nowej linii.
	 * 
	 * @return ciÄg znakĂłw odczytany ze standardowego wejĹcia
	 * @throws IOException 
	 */
	/**
	 * Przekierowanie standardowego wejĹcia
	 * 
	 * @param input nowe standardowe wejĹcie/
	 */
	public void setStdin(LineReader input) {
		stdin= input;
	}

	/**
	 * Przekierowanie standardowego wyjĹcia
	 * 
	 * @param output nowe standardowe wyjĹcie
	 */
	public void setStdout(LinePrinter output) {
		stdout = output;
	}
	

	/**
	 * RozpoczÄcie realizacji kodu programu od wskazanego numeru linii.
	 * 
	 * @param line numer linii programu
	 */
	public void run(int line) {
		Stack<Integer> stack = new Stack<>();
		int zmiennai = 0;
		int i = line;
		String actual_line;
		String[] Words;
		int variable1;
		int variable2;
		boolean czypracowac=true;
		while(i<=biggestkey && czypracowac ==true) {
		while(!console.containsKey(i)) {
			i++;
		}
		actual_line = console.get(i);
		Words = actual_line.split("\\s+");
		
		if(Words[1].toLowerCase().equals("let")) {
			String variable = Words[2];
			if(wartosci.containsKey(Words[4])) {
				variable1=wartosci.get(Words[4]);
			}
			else {
				variable1=  Integer.parseInt(Words[4]);
			}
			if(Words.length >= 7) {
				if(wartosci.containsKey(Words[6])) {
					variable2=wartosci.get(Words[6]);
				}
				else 	{
					variable2= Integer.parseInt(Words[6]);
				}
				if(Words[5].equals("+")) {
					wartosci.put(variable, variable1+variable2);
				}
				else if (Words[5].equals("-")){
					wartosci.put(variable, variable1 -variable2);
				}
				else if(Words[5].equals("*")) {
					wartosci.put(variable, variable1*variable2);
				}
				else {
					wartosci.put(variable, variable1/variable2);    
				}
				}
			else{
			 wartosci.put(variable,variable1);
				
			}
	}
		if(Words[1].toLowerCase().equals("goto")) {
			i=Integer.parseInt(Words[2])-1;
		}
		if(Words[1].toLowerCase().equals("if")) {
			if(wartosci.containsKey(Words[2])) {
				variable1=wartosci.get(Words[2]);
			}
			else {
				variable1=Integer.parseInt(Words[2]);
			}
			if(wartosci.containsKey(Words[4])) {
				variable2=wartosci.get(Words[4]);
			}
			else {
				variable2=Integer.parseInt(Words[4]);
			}
			if(Words[3].equals(">")) {
				if(variable1>variable2) {
					i=Integer.parseInt(Words[6])-1;
				}
			}
			if(Words[3].equals("<")) {
				if(variable1<variable2) {
					i=Integer.parseInt(Words[6])-1;
				}
			}
			if(Words[3].equals("=")) {
				if(variable1==variable2) {
					i=Integer.parseInt(Words[6])-1;
				}
			}
			
		}
		if(Words[1].toLowerCase().equals("end")) {
			czypracowac=false;

		}
		if(Words[1].toLowerCase().equals("print")) {
			if(wartosci.containsKey(Words[2])) {
				String value = String.valueOf(wartosci.get(Words[2]));
				stdout.printLine(value);
			}
			else {
				String wyrazenie = Words[2];
				for(int k= 3;k<Words.length;k++) {
					wyrazenie += " " + Words[k];
				}
			wyrazenie = wyrazenie.substring(1,wyrazenie.length()-1);
			stdout.printLine(wyrazenie);
			}
		}
		if(Words[1].toLowerCase().equals("input")) {
			String zmienna = Words[2];
			String wartosc = stdin.readLine();
			wartosci.put(zmienna,Integer.parseInt(wartosc.trim()));
		}
		if(Words[1].toLowerCase().equals("gosub")) {
			stack.push(i);
			i=Integer.parseInt(Words[2])-1;
			
		}
		if(Words[1].toLowerCase().equals("return")) {
			i=stack.pop();
			
			
		}
	 i++;
	}
	}


}

