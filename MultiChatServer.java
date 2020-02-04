import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
 
//��ſ� ������ ������� id�� �����ϰ�, �ϳ��� Ŭ���̾�Ʈ���� �ۼ��� ���ڿ��� �ٸ� �̿����� Ŭ���̾�Ʈ�� �����ִ� �����带 �ۼ��� Ŭ����
class EchoThread extends Thread{
		
       Socket socket;
       Vector<Socket> vec;
       public EchoThread(Socket socket, Vector<Socket> vec){
             this.socket = socket;
             this.vec = vec;
       }
       public void run(){
             BufferedReader br = null;
             try{
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str =null;
                    while(true){
                           //Ŭ���̾�Ʈ�� ���� ���ڿ� �ޱ�
                           str=br.readLine();
                           //��밡 ������ ������ break;
                           if(str==null){
                                 //���Ϳ��� ���ֱ�
                                 vec.remove(socket);
                                 break;
                           }
                           //����� ���ϵ��� ���ؼ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ� �����ֱ�
                           sendMsg(str);                   
                    }
             }
             catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
          
             
       }
      
       //���۹��� ���ڿ� �ٸ� Ŭ���̾�Ʈ�鿡�� �����ִ� �޼���
       public void sendMsg(String str){
             try{
                    for(Socket socket:vec){
                           //for�� ���� ������ socket�� �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ�
                           //������ socket�鿡�Ը� �����͸� ������.
                           if(socket != this.socket){
                                 PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                                 pw.println(str);
                                 pw.flush();
                                 //��,���⼭ ���� ���ϵ��� ���ǰ͵��̱� ������ ���⼭ ������ �ȵȴ�.
                           }
                    }
             }
             catch(IOException ie){
                    System.out.println(ie.getMessage());
             }
       }
       
}

public class MultiChatServer {
	static String name;
	static int x;
	public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        
        //MultiChatClient�� ����� ���ϵ��� �迭�� ���� ���Ͱ�ü�� �ֱ� ���� ���Ͱ�ü ����
        Vector<Socket> vec = new Vector<Socket>();
        try{
               server= new ServerSocket(7654);
               while(true){
            	   	
                      System.out.println("���Ӵ����..");
                      socket = server.accept();
                      //Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ��� by add �޼ҵ�
                      vec.add(socket);
                      //������ ����
                      new EchoThread(socket, vec).start();
                      
                      Receiver receiver = new Receiver();
                      receiver.start();
                      
               }
        }
        catch(IOException ie){
               System.out.println(ie.getMessage());
        }
  }
}
