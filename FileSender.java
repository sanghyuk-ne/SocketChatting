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
            /* �������κ��� OutputStream �� ���ϸ��� ���� ���� */
           Socket socket = new Socket(MultiChatClient.ipsv,9999);
            System.out.println("���Ͽ���");
           
            /* �������κ��� OutputStream �� ���ϸ��� ���� ���� */
         /* ���������� ���� ��ü ������ �̿��� ���� */
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         System.out.println("���ϸ� : " + filePath + "\\" + fileNm);
         bw.write(fileNm + "\n");
         bw.flush();

            /* ������ ���Ϸ� ���� ��Ʈ���� �о�鿩�� ������ OutputStream�� ���� */
         DataInputStream dis = new DataInputStream(new FileInputStream(new File(filePath+"\\"+fileNm)));
         DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

         /* ����Ʈ������ �о ��Ʈ������ ���� */
         int b = 0;
         while ((b = dis.read()) != -1) {
            dos.writeByte(b);
            dos.flush();
         }
            
            /* �ڿ����� */
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