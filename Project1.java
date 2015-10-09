import java.io.*;
import java.util.*;
import java.util.Scanner.*;

class Project1 {

	static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Scanner cin = new Scanner(System.in);
	
	public static void shift(String message, int k) {
		
		//keeps track of final message
		String finalText = "";
		
		//enciphers the message
		for(int i=0; i<=message.length()-1; i++) {
		
			//gets position of cipher letter and plain text letter
			int x = ALPHABET.indexOf(message.charAt(i));
			int y = (x + k) % 26;
			
			//wraps alphabet
			if (x < 0) {	x = ALPHABET.length() + x;	}
			
			//updates encoded message
			char replaceVal = ALPHABET.charAt(y);
			finalText += replaceVal;
			 
			//prints out messages once complete
			if( i == message.length()-1) {
				System.out.println("Original: " + message);
				System.out.println("Encrypted: " + finalText);
			}
		}
	}
	
	public static int keyInverse(int k) {
		
		//finds key inverse
		int key_inv=0;
		
		for(int i=1; i<26; i++) {
			if ((i*k)%26==1)
				key_inv = i; 
		}
		return key_inv;
	}

	public static void affine(String message, int k, int b, int ed) {
				
		//keeps track of final
		String finalText = "";	
		
		int key_inv = keyInverse(k);
		
		for (int i=0; i<=message.length()-1; i++) {
			
			//gets position of cipher letter and plain text letter
			int y = ALPHABET.indexOf(message.charAt(i));
			int x;
			
			//determines if it should be encrypted or decrypted
			if(ed == 1) { 
				 x = ((k*y) + b) % 26; 	}
			else {
				 x = (key_inv * (y - b)) % 26;	}				
		
			//wraps alphabet
			if (x < 0) {	x = ALPHABET.length() + x;	}
		
			//updates decoded message
			char replaceVal = ALPHABET.charAt(x);
			finalText += replaceVal;
			 
			//prints out messages once complete
			if( i == message.length()-1) {
				System.out.println("Original: " + message);
				System.out.println("Encrypted: " + finalText);
			}
		}
	}
	
	public static char contains(String cipherList, char c) {

		//checks to see if letter is already in string
		for(int i=0; i<cipherList.length(); i++) {
		
			if (c == cipherList.charAt(i)) {
				c++;
				contains(cipherList, c);
			}
		}
		
		return c;	
	}	
	
	/*
	public static void ProcessKey(String keyword) {
		int min;
		char originalKey[] = keyword.toCharArray();
		char temp;
		
		
	
	
	} */
	
	public static void substitution(String message, String keyword, int ed) {

		String finalText = "";
		String cipherList = "";
		
		System.out.println(keyword);
	
		//creates array of key letters
		char key[] = new char[keyword.length()];
		for(int i=0; i<keyword.length(); i++) {
			key[i] = keyword.charAt(i);
		}

		//sorts keyword letters
		char k[] = new char [keyword.length()];
		k = keyword.toCharArray();
		Arrays.sort(k);
		
		//determines number of rows and columns
		int numCols = keyword.length();
		int numRows = ALPHABET.length()/keyword.length();
		
		//if there's a remainder adds extra row
		if((ALPHABET.length()%keyword.length()) != 0) {
			numRows = numRows + 1;
		}
		
		//creates grid for letters
		char[][] grid = new char[numRows][numCols];
	
		//fills in grid
		for(int i=0; i<numRows; i++){
			for(int j=0; j<numCols; j++){

				if(i==0) {
					grid[i][j] = keyword.charAt(j);
				} else {
					char temp;
					temp = ALPHABET.charAt(j);
					
					grid[i][j] = contains(cipherList, temp);
				}
				
				cipherList += grid[i][j];
		
			}
		}
		
		
		/*
		//gets columns in order
		for(int i=0; i<numCols; i++){
			for(int j=0; j<numRows; j++) {
			}
		
		}	*/
		
		
		System.out.println(cipherList);
	}
	
	public static void vigenere(String message, String keyword, int ed) {
		
		String finalText = "";
		
		for(int i=0, j=0; i<message.length(); i++) {
			
			//gets plain text value
			char c = message.charAt(i);
			
			if(c < 'A' || c > 'Z') continue;
			
			//determines if encrypted or decrypted and updates final text accordingly
			if( ed == 1 ) {
				finalText += (char) ((c + keyword.charAt(j) - 2 * 'A') % 26 + 'A');
			} else {
				finalText += (char) ((c - keyword.charAt(j) + 26) % 26 + 'A');
			}
			j = ++j % keyword.length();
		}
		
		System.out.println("Original: " + message);
		System.out.println("Encrypted: " + finalText);	
	}

	public static void ciphers(String message, int cipher, int encrypt_decrypt) {
		
		//determines which function to use depending on user input
		if( cipher == 1 ) {
			//asks user for a key
			System.out.println("Enter a key value: ");
			int k = cin.nextInt();
			if(encrypt_decrypt == 1) {
				shift(message, k);
			} else {
				shift(message, -k);
			}
		} else if (cipher == 2){
			//asks user for a key
			System.out.println("Enter a key value: ");
			int k = cin.nextInt();
			
			//asks user for 'b' value
			System.out.println("Enter a 'b' value: ");
			int b = cin.nextInt();
			
			if(encrypt_decrypt == 1) {	
				affine(message, k, b, 1);
			} else {
				affine(message, k, b, 2);
			}
		} else if (cipher == 3) {
			//asks user for a keyword & gets rid of repeated characters
			System.out.println("Enter a keyword: ");
			String keyword = cin.next();
			keyword = keyword.toUpperCase();
			keyword = keyword.replaceAll((String.format("(.)(?<=(?:(?=\\1).).{0,%d}(?:(?=\\1).))", keyword.length())), "");
			
			if(encrypt_decrypt == 1) {
				substitution(message, keyword, 1);
			} else {
				substitution(message, keyword, 2);
			}
		} else if (cipher == 4) {
			//asks user for a keyword
			System.out.println("Enter a keyword: ");
			String keyword = cin.next();
			keyword = keyword.toUpperCase();
			
			if(encrypt_decrypt == 1) {
				vigenere(message, keyword, 1);
			} else {
				vigenere(message, keyword, 2);
			}
		}
	}
	
	public static void main(String [] args) throws IOException {
		
		//reads in text file
		Scanner file = new Scanner (new FileReader("input.txt"));
		String message = file.useDelimiter("\\A").next();
		
		//capitalizes all letters and gets rid of blank spaces
		message = message.toUpperCase();
		message = message.replaceAll("\\s+", "");
		
		int cipher, encrypt_decrypt;
		
		//asks user of they want to encrypt or decrypt a message
		System.out.println("Do you want to encrypt or decrypt?");
		System.out.println("1 - Encrypt a message");
		System.out.println("2 - Decrypt a message");
		encrypt_decrypt = cin.nextInt();
		
		//asks user which algorithm to use
		System.out.println("Which cipher do you want to use?");
		System.out.println("1 - Shift Cipher");
		System.out.println("2 - Affine Cipher");
		System.out.println("3 - Substitution Cipher");
		System.out.println("4 - Vigenere Cipher");
		cipher = cin.nextInt();
		
		ciphers(message, cipher, encrypt_decrypt);
	}
}
