package simplestOT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class SimplestOT implements ObliviousTransfer{
	private BigInteger g, n;
	
	public SimplestOT(BigInteger g, BigInteger n) {
		super();
		this.g = g;
		this.n = n;
	}

	@Override
	public String[] receive(byte[] choices, Random r, Socket channel) {
		// TODO Auto-generated method stub
		long start, end, total;
		
		start = System.currentTimeMillis();
		
		byte c = choices[0];
		
		DataOutputStream out = null;
		DataInputStream in = null;
		
		try {
			out = new DataOutputStream(channel.getOutputStream());
			in = new DataInputStream(channel.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BigInteger A = null;
		
		try {
			A = new BigInteger(in.readUTF());  //receive A
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("R: recibido A: "+A.toString());
		//generate b and B
				
		BigInteger b = Auxiliar.primeNumberGenerator(n, r);
		
		BigInteger B = null;
		B = g.modPow(b, n);
		
		if(c==1)
			B = A.multiply(B);
		
		
		
		try {
			//send B
			out.writeUTF(B.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("R: enviado B: "+B.toString());
		
		//create key
		BigInteger Kr = A.modPow(b, n);
		
		String KrHash = Auxiliar.sha256hash(Kr.toString());

		//receive encripted messages
		String[] messages = null;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(channel.getInputStream());
			messages = (String[]) ois.readObject();
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("R: recibidos mensajes: "+Arrays.toString(messages));
		
		//decrypt Mc with Kr
		String m = AES.decrypt(messages[c], KrHash);
		
		end = System.currentTimeMillis();
		total = end - start;
		
		System.out.println("Total receptor: " + total);
		
		String[] returnMsg = { m };
		return returnMsg;
	}

	@Override
	public void send(String[] messages, Random r, Socket channel) {
		// TODO Auto-generated method stub
		
		long start, end, total;
		
		start = System.currentTimeMillis();
		
		DataOutputStream out = null;
		DataInputStream in = null;
		
		try {
			out = new DataOutputStream(channel.getOutputStream());
			in = new DataInputStream(channel.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//generate a and A
		BigInteger a = Auxiliar.primeNumberGenerator(n, r);
				
		BigInteger A = g.modPow(a, n), B = null;
		
		try {
			out.writeUTF(A.toString());  //send A
			System.out.println("S: enviado A: "+A.toString());
			B = new BigInteger(in.readUTF()); // receive B
			System.out.println("S: recibido B: "+B.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//generate keys
		BigInteger k0, k1;
		
		
		k0 = B.modPow(a, n);
		k1 = B.divide(A).modPow(a, n);

		String k0Hash = Auxiliar.sha256hash(k0.toString());
		String k1Hash = Auxiliar.sha256hash(k1.toString());
		
		//encrypt messages
		String em[] = new String[2];
		em[0] = AES.encrypt(messages[0], k0Hash);
		em[1] = AES.encrypt(messages[1], k1Hash);
		
		end = System.currentTimeMillis();
		total = end - start;
		
		
		//send encripted messages
		try {
			ObjectOutputStream oos = new ObjectOutputStream(channel.getOutputStream());
			oos.writeObject(em);
			System.out.println("S: enviados mensajes: "+Arrays.toString(em));
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("Total sender: " + total);

		
	}


}
