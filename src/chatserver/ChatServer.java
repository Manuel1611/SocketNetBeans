package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ChatServer {
    
    private boolean run = true;
    private List<ChatServerThread> serverThreads = new ArrayList();
    private ServerSocket servicio;
    
    public ChatServer(int port) {
        
        try {
            
            servicio = new ServerSocket(port);
            
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        
    }
    
    public void broadcastMsgPrivado(String a, String texto, String de){
        
        for(ChatServerThread cliente: serverThreads){
            
            if(cliente.getNombre().compareTo(a) == 0){
               cliente.sendTo(texto, de); 
            }
        }
        
    }
    
    public void broadcastMsgPrivadoMio(String a, String texto, String de){
        
        for(ChatServerThread cliente: serverThreads){
            
            if(cliente.getNombre().compareTo(a) == 0){
               cliente.sendToMio(texto, de); 
            }
        }
        
    }
    
    public void broadcast(String text){
        try{

            for(ChatServerThread cliente: serverThreads){
                if(cliente.isRun()){
                   cliente.send(text); 
                }else{
                    serverThreads.remove(cliente);

                }
            }

        }catch(ConcurrentModificationException ex){

        }

    }
    
    public void eliminarUsuario(ChatServerThread cliente) {
        
            
            serverThreads.remove(cliente);
           
        
    }
    
    public void startService() {
        
        Thread mainThread = new Thread() {
            
            @Override
            public void run() {
                
                ChatServerThread serverThread;
                Socket servidor;
                
                while(run) {
            
                    try {
                
                        servidor = servicio.accept();
                        serverThread = new ChatServerThread(servidor, ChatServer.this);
                        
                        serverThreads.add(serverThread);
                        serverThread.setId(serverThreads.indexOf(serverThread));
                        
                        serverThread.start();
                
                    } catch (IOException ex) {
                
                    }
            
                }
                
            }
            
        };
        mainThread.start();
        
    }
    
    public static void main(String[] args) {
        
        ChatServer chatServer = new ChatServer(5000);
        chatServer.startService();       
        
    }
    
}