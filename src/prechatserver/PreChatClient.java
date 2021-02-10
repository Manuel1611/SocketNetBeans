package prechatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreChatClient {
    
    public static void main(String[] args) {
        
        try {
            
            Socket cliente = new Socket("localhost",5000);
            
            System.out.println("Puerto: " + cliente.getLocalPort());
            DataInputStream flujoE = new DataInputStream(cliente.getInputStream());
            DataOutputStream flujoS = new DataOutputStream(cliente.getOutputStream());
            String valor = flujoE.readUTF();
            System.out.println("cliente recibe: "+valor);
            flujoS.writeUTF("adios servidor");
            flujoE.close();
            flujoS.close();
            cliente.close();
            
        } catch (IOException ex) {
            
        }
        
    }
    
}
