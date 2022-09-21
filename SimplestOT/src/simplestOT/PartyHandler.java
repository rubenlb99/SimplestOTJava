package simplestOT;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

class PartyHandler extends Thread{
	private byte sender;
	private ObliviousTransfer ot;
	private byte[] c = { 0 };
	private String[] messages = {"mensaje 1", "mensaje 2"};
	private Socket s;
	//private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

	
	public PartyHandler(byte k) {
		this.sender = k;
	}
	
	public PartyHandler(byte k , ObliviousTransfer ot) {
		this.sender = k;
		
		this.ot = ot;

		try {
			s = new Socket("localhost", 8080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void run() {
		if(sender==1) {
			ot.send(messages, new Random(), s);
			//System.out.println("sender: "+threadMXBean.getThreadCpuTime(this.getId()));
		}else if(sender==2){
			ot.receive(c, new Random(), s);
			//System.out.println("receiver: "+threadMXBean.getThreadCpuTime(this.getId()));
		}else {
			try {
				new Server();
				//System.out.println("server: "+threadMXBean.getThreadCpuTime(this.getId()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
				
	}
}
