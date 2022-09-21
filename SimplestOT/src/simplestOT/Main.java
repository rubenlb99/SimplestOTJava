package simplestOT;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.djb.Curve25519;
import org.bouncycastle.util.encoders.Hex;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		ObliviousTransfer ot = new OT(OT.SIMPLEST_OT_EC);
				
		Thread server = new PartyHandler((byte) 0);
		server.start();
				
		Thread sender = new PartyHandler((byte) 1, ot);
		sender.start();
		
		Thread receiver = new PartyHandler((byte) 2, ot);
		receiver.start();	
		
		/*ECFieldElement ap = curve.fromBigInteger(A), bp = curve.fromBigInteger(B);
        
        ECFieldElement xr = ap.multiply(bp);


        BigInteger Px = new BigInteger("2");
        
        
        BigInteger Py = new BigInteger("4018974056539037503335449422937059775635739389905545080690979365213431566280");
        BigInteger Qx = new BigInteger("57520216126176808443631405023338071176630104906313632182896741342206604859403");
        BigInteger Qy = new BigInteger("17614944419213781543809391949654080031942662045363639260709847859438286763994");


        // Point addition using built-in formulae
        ECPoint P = curve.createPoint(Px, Py);
        ECPoint Q = curve.createPoint(Qx, Qy);
        ECPoint R = P.add(Q).normalize(); 

        	
        System.out.println("BUILT-IN");
        System.out.println(R.getAffineXCoord().toBigInteger().toString(10));
        System.out.println(R.getAffineYCoord().toBigInteger().toString(10));
		*/
		
		
		/*//CURVA ANOMALOUS		
		BigInteger prime = new BigInteger("17676318486848893030961583018778670610489016512983351739677143");
        BigInteger A = new BigInteger("15347898055371580590890576721314318823207531963035637503096292");
        BigInteger B = new BigInteger("7444386449934505970367865204569124728350661870959593404279615");

        ECCurve curve = new ECCurve.Fp(prime, A, B);
        
        BigInteger baseX = new BigInteger("1619092589586542907492569170434842128165755668543894279235270");
		BigInteger baseY = new BigInteger("3436949547626524920645513316569700140535482973634182925459687");
        
        ECPoint basePoint = curve.createPoint(baseX, baseY);
        
        Random r = new Random();
        
        BigInteger n = new BigInteger(OT.b3072.replace(" ", "").replace("\r\n", ""), 16);
        
        BigInteger a = Auxiliar.primeNumberGenerator(0, n, r);
        
        BigInteger b = Auxiliar.primeNumberGenerator(0, n, r);
        
        ECPoint AOT = basePoint.multiply(a);
        
        ECPoint BOT = basePoint.multiply(b).add(AOT);
        
        ECPoint K0A = BOT.multiply(a).subtract((AOT.multiply(a)));
        
        ECPoint K0B = AOT.multiply(b);
        
        System.out.println(K0B.normalize().equals(K0A.normalize()));
        
        */
        
        
       /* Curve25519 c = new Curve25519();
        
        
        System.out.println("A: " + c.getA().toBigInteger());

        System.out.println("B: " + c.getB().toBigInteger());
                */
	}

}




