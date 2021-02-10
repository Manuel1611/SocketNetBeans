package prechatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreChatServer {

    public static void main(String[] args) {
        
        
        try {
            
            System.out.println("1");
            ServerSocket servicio = new ServerSocket(5000);
            System.out.println("2");
            Socket servidor = servicio.accept();
            DataInputStream flujoE = new
            DataInputStream(servidor.getInputStream());
            DataOutputStream flujoS = new
            DataOutputStream(servidor.getOutputStream());
            flujoS.writeUTF("hola cliente ");
            String valor = flujoE.readUTF();
            System.out.println("servidor recibe: " + valor);
            
            while(valor.compareTo("fin") != 0) {
                
                valor = flujoE.readUTF();
                System.out.println("servidor recibe: " + valor);
                
            }
            
            flujoE.close();
            flujoS.close();
            servidor.close();
            servicio.close();
            
        } catch (IOException ex) {
            
        }
        
    }
    
}
