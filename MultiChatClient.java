import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class WriteThread{
	Socket socket;
	ClientFrame cf;
	String str;
	String id;
	public WriteThread(ClientFrame cf) {
		this.cf=cf;
		this.socket=cf.socket;
	}
	public void sendMsg() { //채팅_정보량 많을 것이므로 buffer메모리 사용, 키보드 입력값을 읽어오는 스트림 객체 생성
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw=null;
		try {
			//서버로 문자열 전송하기 위한 스트림 객체 생성
			pw = new PrintWriter(socket.getOutputStream(),true);
			if(cf.isFirst==true) {
				InetAddress iaddr=socket.getLocalAddress();                      
                String ip = iaddr.getHostAddress();                        
                getId();
                System.out.println("ip:"+ip+" id:"+id);
                str = "["+id+"] 님 로그인 ("+ip+")";
			}
			else{
                str= "["+id+"] "+cf.txtField.getText();
			}
			pw.println(str);
			br.close();
		}
		catch(IOException ie){
			System.out.println(ie.getMessage());
		}
		finally {
            	try{
                    while(true) {
                    	if(br!=null) {
                    		br.close(); 
                    		break;
                    	}
                    if(pw!=null) {
                    	pw.close(); 
                    	break;
                        }
                    if(socket!=null) {
                    	socket.close(); 
                    	break;
                    	}
                    }
             }	
            catch(IOException ie){
                System.out.println(ie.getMessage());
            }
		}
	}     
    public void getId(){            
          id = Id.getId();
    }
}
class ReadThread extends Thread{
    Socket socket;
    ClientFrame cf;
    public ReadThread(Socket socket, ClientFrame cf) {
          this.cf = cf;
          this.socket=socket;
    }
    public void run() {
          BufferedReader br=null;
          try{
                 br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 while(true){
                        String str=br.readLine();
                        if(str==null){
                              System.out.println("접속이 끊겼음");
                              break;
                        }
                        
                        System.out.println("[server] " + str);
                        cf.txtArea.append(str+"\n");
                 }
          }
          catch(IOException ie){
                 System.out.println(ie.getMessage());
          }
          finally{
                 try{
                        if(br!=null) br.close();
                       
                 }
                 catch(IOException ie){}
          }
    }
}
public class MultiChatClient {
	static String ipsv="172.16.101.121";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Socket socket=null;
        ClientFrame cf;
        try{
               socket=new Socket(ipsv,7654);
               System.out.println("연결성공!");
               cf = new ClientFrame(socket);
               new ReadThread(socket, cf).start();
        }
        catch(IOException ie){
               System.out.println(ie.getMessage());
        }
	}
}
