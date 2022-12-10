
package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public static String serverMsg = "";
    public static String clientMsg = "";
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static BufferedReader br;
    public static Socket s;
    
public static boolean isConnect(String Serverip,int Serverport){
        try {
            s = new Socket(Serverip,Serverport);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            return s.isConnected();
        }
        catch (Exception ex) {
            return false;
        }
  
    }
    
public static void sendMsg(String str){
        try{
            clientMsg = str;
            if(!clientMsg.equals("end")){  
                dos.writeUTF("Client: "+clientMsg);
                dos.flush();
            }else{
                System.exit(0);
            }
        }catch(Exception ex){}
    }
public static void RMsg(javax.swing.JTextArea Textarea, javax.swing.JLabel state){
    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                    while(!clientMsg.equals("end")){
                        serverMsg = dis.readUTF();
                        if(serverMsg.equals("Server: turnoff")){
                            Textarea.setEnabled(false);
                            state.setText("Not connected");
                            
                        }else if(serverMsg.equals("Server: turnon")){
                            Textarea.setEnabled(true);
                            state.setText("connected");
                        }
                        else{
                            if(Textarea.getText().equals("")){
                                    Textarea.setText(serverMsg);
                                }else if(Textarea.getText().substring(Textarea.getText().lastIndexOf("\n")+1).equals("")){
                                 Textarea.setText(Textarea.getText()+serverMsg);
                             }
                            else{
                                Textarea.setText(Textarea.getText()+"\n"+serverMsg);
                            }
                        }
                    }

            }catch(Exception ex){}
        }
    });
    t2.start();
}

}
