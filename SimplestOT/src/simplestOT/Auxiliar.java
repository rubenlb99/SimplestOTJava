package simplestOT;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Auxiliar {
	public static long primeNumberGenerator(int min, int max, Random r) {
        long num = 0;
        Random rand = r; // generate a random number
        num = rand.nextLong(min, max); 

        while (!isPrime(num)) {          
            num = rand.nextLong(min, max); 
        }

        return num; 
	}
	
	public static BigInteger primeNumberGenerator(BigInteger max, Random r) {
		BigInteger randomNumber;
		do {
		    randomNumber = new BigInteger(max.bitLength(), r);
		} while (randomNumber.compareTo(max) >= 0);

        return randomNumber; 
	}
	
	private static boolean isPrime(long num){
	    if (num <= 3 || num % 2 == 0) 
	        return num == 2 || num == 3; //this returns false if number is <=1 & true if number = 2 or 3
	    int divisor = 3;
	    while ((divisor <= Math.sqrt(num)) && (num % divisor != 0)) 
	        divisor += 2; //iterates through all possible divisors
	    return num % divisor != 0; //returns true/false
	}
    
    public static int getG(int p, Random r) {
    	int g = 0;
    	int rand, exp, next;
    	List<Integer> G = new ArrayList<Integer>();
    	for (int i = 1; i < p; i++) {
    		rand = i;
    		exp=1;
    		next = rand % p;
    		
    		while( next != 1) {
    			next = (next*rand)%p;
    			exp+=1;
    		}
    		
    		if(exp==p-1)
    			G.add(rand);
		}
    	
    	g = G.get(r.nextInt(G.size()));
    	
    	return g;
    }
    
    public static String sha256hash(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
    
}


