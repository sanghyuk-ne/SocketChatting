import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
 

class Receiver extends Thread {
    ServerSocket s_socket_file;
    Socket socket_file;
    String file_name;
    static InputStream is;
   static BufferedReader br;
   static File f;
   static FileOutputStream out;
    String path = "c:\\develop\\testdata\\receiver";
 
    public Receiver() {
        
    }
 
    //@Override
    public void run() {
        while(true){
            try {
                
                s_socket_file = new ServerSocket(9999);
                socket_file = s_socket_file.accept();
                System.out.println("소켓" + socket_file + "에 연결됨");
                is = socket_file.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                
                file_name = br.readLine();

                //폴더가 없을경우 생성코드
                String folder="\\develop\\testdata\\receiver";
                File dir = new File("C:" + "\\" + folder);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //생성코드 종료

                f = new File(path+"\\"+file_name);

                out = new FileOutputStream(f);
                int n = 0;
                while((n = is.read()) != -1) {
                	out.write((char) n);
                }

                System.out.println("저장완료 !!!");
                s_socket_file.close();
                socket_file.close();
                br.close();
                is.close();
                out.close();
                s_socket_file = null;
                socket_file = null;
                br = null;
                is = null;
                out = null;
            }catch (IOException e) {
                System.out.println("run() Fail!");
                e.printStackTrace();
            }
        }
    }
} 