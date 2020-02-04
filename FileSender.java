import java.awt.TextField;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

class FileSender{
    
    String filePath;
    String fileNm;
    Socket socket;
   TextField tf;
   DataInputStream dis;
   DataOutputStream dos;
    BufferedWriter bw;

    public FileSender(String filePath, String fileNm) {
        this.fileNm = fileNm;
        this.filePath = filePath;
        try {
            /* 소켓으로부터 OutputStream 얻어서 파일명을 먼저 보냄 */
           Socket socket = new Socket(MultiChatClient.ipsv,9999);
            System.out.println("소켓연결");
           
            /* 소켓으로부터 OutputStream 얻어서 파일명을 먼저 보냄 */
         /* 서버측에서 파일 객체 생성시 이용할 것임 */
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         System.out.println("파일명 : " + filePath + "\\" + fileNm);
         bw.write(fileNm + "\n");
         bw.flush();

            /* 선택한 파일로 부터 스트림을 읽어들여서 얻어놓은 OutputStream에 연결 */
         DataInputStream dis = new DataInputStream(new FileInputStream(new File(filePath+"\\"+fileNm)));
         DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

         /* 바이트단위로 읽어서 스트림으로 쓰기 */
         int b = 0;
         while ((b = dis.read()) != -1) {
            dos.writeByte(b);
            dos.flush();
         }
            
            /* 자원정리 */
         dis.close();
         dos.close();
         socket.close();
         dis = null;
         dos = null;
         socket = null;
         //System.exit(1);
            
         }catch (IOException e){
             e.printStackTrace();
         }
    }
         
    }