import java.io.*;
import java.util.*;
import java.util.Scanner.*;
import java.math.BigInteger;

public class projectRSA {
    
    private static BigInteger p, q, n, phi, e, d;
	static Scanner cin = new Scanner(System.in);
	static int minBit, maxBit;

	public void getBits(int numDigit) throws IOException{
		//determines the max/min bits for an integer of n digits
        minBit = (int)Math.floor(Math.log(numDigit)/Math.log(2))+1;
		maxBit = (int)Math.ceil(numDigit*(Math.log(10)/Math.log(2)));
		        
        //Select 2 large prime numbers
        p = new BigInteger(maxBit, minBit, new Random());
        q = new BigInteger(maxBit, minBit, new Random());

        //calculate n
        n = p.multiply(q);
        
        //calculate phi of n
        phi = p.subtract(BigInteger.valueOf(1));
        phi = phi.multiply(q.subtract(BigInteger.valueOf(1)));
		
		do {
            e = new BigInteger(2*maxBit, new Random());
        } while( (e.compareTo(phi) != 1)||(e.gcd(phi).compareTo(BigInteger.valueOf(1)) != 0));
			//calculate d
			d = e.modInverse(phi);
		
		//writes keys to files
		BufferedWriter op = new BufferedWriter(new FileWriter("mykey.privk"));
		op.write(d+" "+n+" "+p+" "+q);	
		op.close();
		BufferedWriter output = new BufferedWriter(new FileWriter("mykey.pubk"));
		output.write(e+" "+n);
		output.close();
	}

	//returns encrypted message
    public static BigInteger rsaEncrypt(BigInteger plainText, BigInteger e, BigInteger n){ 
        return plainText.modPow(e,n);
    }
    
	//returns decrypted message
    public static BigInteger rsaDecrypt(BigInteger cipherText, BigInteger d, BigInteger n){
        return cipherText.modPow(d,n);
    }
	
	public static String doRSA(BigInteger bE, BigInteger bN, BigInteger bD) throws IOException{
		projectRSA pjt2 = new projectRSA();
		
		String encryptedMessage = "";
		String decryptedMessage = "";
		
		//reads in plaintext message
		Scanner file = new Scanner(new FileReader("message.txt"));
		String line = file.nextLine();
		String[] words = line.split(" ");
		
		for(int i=0; i<words.length; i++){
		
			BigInteger bPlainText = new BigInteger(words[i], 36);
		
			//encrypts the word
			BigInteger bCipherText= pjt2.rsaEncrypt(bPlainText, bE, bN);
			encryptedMessage += bCipherText.toString(36);
			
			//decrypts message
			bPlainText = pjt2.rsaDecrypt(bCipherText, bD, bN);
			decryptedMessage += bPlainText.toString(36);
		}
		
		return decryptedMessage;
	}
	
    public static void main(String[] args) throws IOException{
        projectRSA pjt2 = new projectRSA();
		
	
		System.out.println("----- RSA Encryption/Decryption -----");
		System.out.println("1 - Create a Private/Public Key");
		System.out.println("2 - Load a key");
		System.out.println("3 - Encrypt/Decrypt a text file");
		int c = cin.nextInt();
		
		if(c==1){
			//asks user how many digits the prime numbers should be
			System.out.println("How many digits should the prime numbers be?");
			int pDigit = cin.nextInt();
			pjt2.getBits(pDigit);
		}else if(c==2){
			System.out.println("Enter public key file name: ");
			String pubName = cin.next();
			//gets values for public & private keys
			Scanner readIn = new Scanner(new FileReader(pubName));
			BigInteger bE = readIn.nextBigInteger();
			BigInteger bN = readIn.nextBigInteger();
			System.out.println("Enter private key file name: ");
			String privName = cin.next();
			Scanner inFile = new Scanner(new FileReader(privName));
			BigInteger bD = inFile.nextBigInteger();
			
			System.out.println("Decrypted: " + doRSA(bE, bN, bD));
		}else if (c==3){
			//if user doesn't load, these are automatically set
			Scanner readIn = new Scanner(new FileReader("mykey.pubk"));
			BigInteger bE = readIn.nextBigInteger();
			BigInteger bN = readIn.nextBigInteger();
		
			Scanner inFile = new Scanner(new FileReader("mykey.privk"));
			BigInteger bD = inFile.nextBigInteger();
			
			System.out.println("Decrypted: " +doRSA(bE, bN, bD));
		}
	}
}

/*
----- RSA Encryption/Decryption -----
1 - Create a Private/Public Key
2 - Load a key
3 - Encrypt & Decrypt a text file
3
How many digits should the prime numbers be?
152
hellothisisthesupersecretmessagersaisawesome
*/
