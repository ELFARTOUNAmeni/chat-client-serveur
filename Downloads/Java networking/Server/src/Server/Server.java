package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    public static String serverMsg = "";
    public static String clientMsg = "";
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static BufferedReader br;
    public static Socket s ; 
    public static ServerSocket ss ; 
    public static boolean b = false;
    
public static void Connected(){
        
        try {
            ss = new ServerSocket(3333,2,InetAddress.getLocalHost()); 
        }catch(java.net.BindException e){
            JOptionPane.showMessageDialog(null, "Server is aleard connected");
            System.exit(0);
        }
        catch (Exception ex) {
        }
            
    }
public static void acceptConnected(){
        try {
            s = ss.accept();
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
        } catch (Exception ex) {
        }
    }
public static void stopConnected(){
        try {
            ss.close();
            s.close();
        } catch (Exception ex) {
            System.out.println("Problem is on stopConnected method : "+ex);
        }
    }
 public static String isConnected(){
         if(ss.isClosed()){
             return "Not Connected";
         }else{
            return "Connected";
            
         }
    }
public static String getIp(){
        return ss.getInetAddress().getHostAddress();
    }
public static String getPort(){
        return String.valueOf(ss.getLocalPort());
    }

public static void sendMsg(String str){
        try{
            serverMsg = str;
            if(!serverMsg.equals("end")){
                dos.writeUTF("Server: "+serverMsg);
                dos.flush();
                System.out.print(serverMsg);
            }else{
                dos.writeUTF("Server: turnoff");
                dos.flush();
                System.exit(0);
            }
        }catch(Exception ex){ 
        }
    }
public static void reciveMsg(javax.swing.JTextArea Textarea){
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while(!clientMsg.equals("end")){
                             clientMsg = dis.readUTF();
                             if(Textarea.getText().equals("")){
                                Textarea.setText(clientMsg);
                             }else if(Textarea.getText().substring(Textarea.getText().lastIndexOf("\n")+1).equals("")){
                                 Textarea.setText(Textarea.getText()+clientMsg);
                             }
                             else{
                                 Textarea.setText(Textarea.getText()+"\n"+clientMsg);
                             }
                             
                        }
                        System.exit(0); 
                                    
                    }catch(Exception ex){}
                }
            });
            t2.start();
    }
}
