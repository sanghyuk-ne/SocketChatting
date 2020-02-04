import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

class Id extends JFrame implements ActionListener{
	static JTextField w = new JTextField(8);
	JButton btn = new JButton("입력");
	
	WriteThread wt;
	ClientFrame cf;
	
	public Id() {}
	public Id(WriteThread wt, ClientFrame cf) {
		super("아이디");
		this.wt=wt;
		this.cf=cf;
		
		setLayout(new FlowLayout());
		add(new JLabel("아이디"));
		add(w);
		add(btn);
		
		btn.addActionListener(this);
		
		setSize(300,200);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		wt.sendMsg();
		cf.isFirst = false;
		cf.setVisible(true);
		this.dispose(); //창을 아예 닫는것이 아니라 다음 페이즈로 넘어가게 exit이 아닌 dispose 사용
	}
	static public String getId(){
		return w.getText();
	}
}

public class ClientFrame extends JFrame implements ActionListener{
	JTextArea txtArea = new JTextArea();
	JTextField txtField = new JTextField(15);
	JButton btnTransfer = new JButton("전송");
	JButton btnFile = new JButton("파일");
	
	boolean isFirst = true;
	JPanel p1 = new JPanel();
	Socket socket;
	WriteThread wt;
	
	public ClientFrame(Socket socket) {
		super("Chatting");
		this.socket=socket;
		wt = new WriteThread(this);
		new Id(wt, this);
		
		add("Center", txtArea);
		p1.setLayout(new BorderLayout());
		
		p1.add(txtField,BorderLayout.CENTER);
		p1.add(btnTransfer,BorderLayout.EAST);
		p1.add(btnFile, BorderLayout.WEST);
		
		add("South",p1); //JPanel p1에 컴포넌트 붙이기
		
		btnTransfer.addActionListener(this);
		btnFile.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 300);
		setVisible(false);
	}
	public void actionPerformed(ActionEvent e) {
		String id = Id.getId();
		if(e.getSource()==btnTransfer) {
			if(txtField.getText().contentEquals("")) {
				return;
		}
			txtArea.append("("+id+")" + txtField.getText()+ "\n");
			wt.sendMsg();
			txtField.setText("");
		}
		else if(e.getSource()==btnFile) {
			
			JFileChooser jFileChooser = new JFileChooser("C://temp");
			jFileChooser.setDialogTitle("파일 선택");
			jFileChooser.setMultiSelectionEnabled(true);
			jFileChooser.setApproveButtonToolTipText("전송할 파일을 선택하세요");
			jFileChooser.showDialog(this, "열기");
			File path = jFileChooser.getSelectedFile();	
			String name= path.getName();
			MultiChatServer.name = name;
			String filepath = path.getParent();
			MultiChatServer.x = 1;
			FileSender fs = new FileSender(filepath , name);
	    
	        
		}
		else {
			this.dispose();	//창을 아예 닫는것이 아니라 다음 페이즈로 넘어가게 exit이 아닌 dispose 사용
		}
	}
}
//ClientFrame은 id 
