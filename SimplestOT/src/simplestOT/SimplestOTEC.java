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

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class SimplestOTEC implements ObliviousTransfer{
	private BigInteger n;
	private ECCurve curve;
	private ECPoint G;
	
	private long start, end, total;
	
	@SuppressWarnings("deprecation")
	public SimplestOTEC(BigInteger n) {
		super();
		this.n = n;
		
		
		//NIST P-256 CURVE		
		BigInteger q = new BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
		BigInteger a = new BigInteger("ffffffff00000001000000000000000000000000fffffffffffffffffffffffc", 16);
		BigInteger b = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
		BigInteger order = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16);
		BigInteger cofactor = new BigInteger("1", 16);
		
		curve = new ECCurve.Fp(q, a, b, order, cofactor);

		BigInteger Gx = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16);
		BigInteger Gy = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16);

		G = curve.createPoint(Gx, Gy);
		
		
	}

	@Override
	public String[] receive(byte[] choices, Random r, Socket channel) {
		// TODO Auto-generated method stub
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
		
		ECPoint A = null;
		
		try {
			String stringPoint = in.readUTF();
			
			start = System.currentTimeMillis();
			String[] coords = stringPoint.split(",");
			
			A = curve.createPoint(new BigInteger(coords[0]), new BigInteger(coords[1])).normalize();  //receive A
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("R: recibido A: "+A.toString());
		//generate b and B
		BigInteger b = Auxiliar.primeNumberGenerator(n, r);
		
		ECPoint B = null;
		B = G.multiply(b).normalize();
		
		if(c==1)
			B = A.add(B).normalize();
		
		end = System.currentTimeMillis();
		total += (end - start);
		
		try {
			//send B
			out.writeUTF(B.getAffineXCoord().toBigInteger()+","+B.getAffineYCoord().toBigInteger());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("R: enviado B: "+B.toString());
				
		start = System.currentTimeMillis();
		//create key
		ECPoint Kr = A.multiply(b).normalize();
		
		String KrHash = Auxiliar.sha256hash(Kr.getAffineXCoord().toBigInteger().toString()+Kr.getAffineYCoord().toBigInteger().toString());
		
		
		end = System.currentTimeMillis();
		total += end - start;
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
		
		start = System.currentTimeMillis();
		//decrypt Mc with Kr
		String m = AES.decrypt(messages[c], KrHash);
		
		end = System.currentTimeMillis();
		total += end - start;
		
	    System.out.println("Rtime: "+ total);
				
		String[] returnMsg = { m };
		return returnMsg;
	}

	@Override
	public void send(String[] messages, Random r, Socket channel) {
		// TODO Auto-generated method stub
		
				//long start2 = System.currentTimeMillis();
				
				DataOutputStream out = null;
				DataInputStream in = null;
				
				try {
					out = new DataOutputStream(channel.getOutputStream());
					in = new DataInputStream(channel.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				start = System.currentTimeMillis();
				//generate a and A
				BigInteger a = Auxiliar.primeNumberGenerator(n, r);
						
				ECPoint A = G.multiply(a).normalize(), B = null;
				
				end = System.currentTimeMillis();
				total += end - start;
				
				try {
					out.writeUTF(A.getAffineXCoord().toBigInteger()+","+A.getAffineYCoord().toBigInteger());  //send A
					
					System.out.println("S: enviado A: "+A.toString());
					
					String sPoint = in.readUTF();
					
					start = System.currentTimeMillis();
					String[] coords = sPoint.split(",");
					
					B = curve.createPoint(new BigInteger(coords[0]), new BigInteger(coords[1])).normalize();
					
					System.out.println("S: recibido B: "+B.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//generate keys
				ECPoint k0, k1;
				
				
				k0 = B.multiply(a).normalize();
				k1 = B.multiply(a).subtract(A.multiply(a)).normalize();

				String k0Hash = Auxiliar.sha256hash(k0.getAffineXCoord().toBigInteger().toString()+k0.getAffineYCoord().toBigInteger().toString());
				String k1Hash = Auxiliar.sha256hash(k1.getAffineXCoord().toBigInteger().toString()+k1.getAffineYCoord().toBigInteger().toString());
				
				//encrypt messages
				String em[] = new String[2];
				em[0] = AES.encrypt(messages[0], k0Hash);
				em[1] = AES.encrypt(messages[1], k1Hash);
				
				end = System.currentTimeMillis();
				total += end - start;
				
				System.out.println("Stime: " + total);
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
				
		
	}

}
