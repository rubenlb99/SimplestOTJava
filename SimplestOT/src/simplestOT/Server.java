package simplestOT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server() throws IOException, ClassNotFoundException {
		//long start2 = System.currentTimeMillis();
		
		ServerSocket server = new ServerSocket(8080);
		Socket sender = server.accept();
		Socket receiver = server.accept();
		
		DataOutputStream inSender = new DataOutputStream(sender.getOutputStream());
		DataOutputStream inReceiver = new DataOutputStream(receiver.getOutputStream());
		
        //Se obtiene el flujo entrante 
        DataInputStream outSender = new DataInputStream(sender.getInputStream());
        DataInputStream outReceiver = new DataInputStream(receiver.getInputStream());


        String A = outSender.readUTF();
        
        System.out.println("SERVER: Recibido A");
        
        inReceiver.writeUTF(A);
        
        System.out.println("SERVER: Enviado A");
        
        String B = outReceiver.readUTF();
        
        System.out.println("SERVER: Recibido B");
        
        inSender.writeUTF(B);
        
        System.out.println("SERVER: Enviado B");
        
        ObjectOutputStream oosReceiver = new ObjectOutputStream(receiver.getOutputStream());
        
        ObjectInputStream oisSender = new ObjectInputStream(sender.getInputStream());
        
        String[] messages = (String[]) oisSender.readObject();
        
        System.out.println("SERVER: Recibidos mensajes");
        
        oosReceiver.writeObject(messages);
        
        System.out.println("SERVER: Enviados mensajes");
        
        sender.close();
        receiver.close();
        server.close();
        
        //long end2 = System.currentTimeMillis();      
	    //System.out.println("Execution time: "+ (end2-start2));
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
	}

}
