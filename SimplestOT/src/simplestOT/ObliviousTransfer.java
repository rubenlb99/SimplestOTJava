package simplestOT;

import java.net.Socket;
import java.util.Random;

public interface ObliviousTransfer {
		
	String[] receive(byte[] choices, Random r, Socket channel);
	
	void send(String[] messages, Random r, Socket channel);

}
