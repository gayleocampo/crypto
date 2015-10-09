import java.io.*;
import java.util.*;
import java.math.*;
import java.util.Scanner.*;

class fermatFactoring{

	public static void fermat(long n){
		long a = (long)Math.ceil(Math.sqrt(n));
		long b = (a*a)-n;
		
		while(!isSquare(b)){
			a++;
			b = (a*a)-n;
		}
	
		long root1 = a - (long)Math.sqrt(b);
		long root2 = n/root1;
		System.out.println("The factors are "+root1+" and "+root2);	
	}
	
	public static boolean isSquare(long n){
	
		long sqrt = (long) Math.sqrt(n);
		if(sqrt*sqrt == n || (sqrt+1)*(sqrt+1) == n)
			return true;
		return false;	
	}

	public static void main(String[] args){
	
		Scanner cin = new Scanner(System.in);
	
		System.out.println("Enter a number");
		long n = cin.nextLong();
		fermat(n);
	}
}

/*
thomas% java fermatFactoring
Enter a number
1111111128777777847
The factors are 1000000007 and 1111111121
thomas%
*/
















