import java.io.*;
import java.util.*;
import java.util.Scanner.*;
import java.math.BigInteger;

public class Project2 {
    
    private BigInteger p, q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e, d;
    
    public Project2(){
		
		//generates keys and writes to a file
        keyGen();
    }
    
    public void keyGen(){
        int SIZE = 512;
        
        //Select 2 large prime numbers
        p = new BigInteger(SIZE, 15, new Random());
        q = new BigInteger(SIZE, 15, new Random());
        
        //calculate n
        n = p.multiply(q);
        
        //calculate phi of n
        phi = p.subtract(BigInteger.valueOf(1));
        phi = phi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        //find exponent
        do {
            e = new BigInteger(2*SIZE, new Random());
        } while( (e.compareTo(phi) != 1)||(e.gcd(phi).compareTo(BigInteger.valueOf(1)) != 0));
        //calculate d
        d = e.modInverse(phi);
		
    }
    
    public BigInteger rsaEncrypt(BigInteger plainText){ 
        return plainText.modPow(e,n);
    }
    
    public BigInteger rsaDecrypt(BigInteger cipherText){
        return cipherText.modPow(d,n);
    }
	
    
    public static void main(String[] args) throws IOException{
        
        Project2 pjt2 = new Project2();
		
        //reads in plaintext message, specified by the user
		Scanner file = new Scanner(new FileReader("message.txt"));
        String input = file.useDelimiter("\\A").next();
		input = input.replaceAll("\\W","");
		
        BigInteger bPlainText = new BigInteger(input, 36);
        BigInteger bCipherText= pjt2.rsaEncrypt(bPlainText);
        
        System.out.println("Plaintext: "+bPlainText.toString(36));
        System.out.println("Ciphertext: "+bCipherText.toString(36));
        
        bPlainText = pjt2.rsaDecrypt(bCipherText);
        System.out.println("After Decryption plaintext: "+bPlainText.toString(36));
	}
}
