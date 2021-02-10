package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatServerThread extends Thread{
    
    private boolean run = true;
    boolean error = false;
    private int id;
    
    private String nombre = "";
    private ChatServer server;
    private DataInputStream flujoE;
    private DataOutputStream flujoS;
    
    private final Socket servidor;

    public ChatServerThread(Socket servidor, ChatServer server) {
        this.servidor = servidor;
        this.server = server;
        
        try {
            
            flujoE = new DataInputStream(servidor.getInputStream());
            flujoS = new DataOutputStream(servidor.getOutputStream());
            
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run() {
        String text;
        while(run) {
            
            try {
                
                text = flujoE.readUTF();
                
                if(this.nombre.length() == 0){
                    
                    this.setNombre(text);
                    server.broadcast("¡" + this.getNombre() + " ha entrado al chat!");
     
                }else{
                    
                    if(text.compareTo("sdfjbsidfhjbfuidhbsdfbuigusrfiuwu9wqsdhbasklbndeuwyvfosdfaujqw3ebibwefiyasnjkasbpfue") == 0) {
                        
                        server.broadcast("" + this.nombre + " ha abandonado el chat...");
                        server.eliminarUsuario(ChatServerThread.this);
                        
                    } else {
                        
                        if(text.charAt(0) == '/' && text.charAt(1) == 'm' && text.charAt(2) == 's' && text.charAt(3) == 'g'){
                            
                            String mensajeA = "";
                            System.out.println(text);
                            System.out.println("" + text.length());
                            for (int i = 5; i < text.length(); i++) {
                                System.out.println(text.charAt(i));
                                if(text.charAt(i) != ' '){
                                    
                                    mensajeA += text.charAt(i);
                                    
                                }else{
                                    
                                    break;
                                    
                                }
                            }

                            
                            
                            String mensajePrivado = "";
                            
                            for (int i = mensajeA.trim().length() + 5; i < text.length(); i++) {
                                
                             mensajePrivado += text.charAt(i);
                             
                            }
                            
                            server.broadcastMsgPrivadoMio(this.nombre, mensajePrivado.trim(), mensajeA.trim());
                            server.broadcastMsgPrivado(mensajeA.trim(), mensajePrivado.trim(), this.nombre);

                        } else {
                            
                            server.broadcast(this.nombre + " > " + text);
                            
                        }
                        
                    }
   
                }
                
            } catch (IOException ex) {
                run = false;
                
            }
            
        }
        
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void send(String text){
         try {
               
               flujoS.writeUTF(text);
               flujoS.flush();
               
           } catch (IOException ex) {
               System.out.println("catch: "+ex.getLocalizedMessage());
               run = false;
               
           }
    }
    
    public void sendTo(String text, String name){
         try {
               
               flujoS.writeUTF("Susurro de " + name + " > " + text);
               flujoS.flush();
               
           } catch (IOException ex) {
               System.out.println("catch: "+ex.getLocalizedMessage());
               run = false;
           }
    }
    
    public void sendToMio(String text, String name){
         try {
               
               flujoS.writeUTF("Susurro a " + name + " > " + text);
               flujoS.flush();
               
           } catch (IOException ex) {
               System.out.println("catch: "+ex.getLocalizedMessage());
               run = false;
           }
    }

    public boolean isRun() {
        return run;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}