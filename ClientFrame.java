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
	JButton btn = new JButton("�Է�");
	
	WriteThread wt;
	ClientFrame cf;
	
	public Id() {}
	public Id(WriteThread wt, ClientFrame cf) {
		super("���̵�");
		this.wt=wt;
		this.cf=cf;
		
		setLayout(new FlowLayout());
		add(new JLabel("���̵�"));
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
		this.dispose(); //â�� �ƿ� �ݴ°��� �ƴ϶� ���� ������� �Ѿ�� exit�� �ƴ� dispose ���
	}
	static public String getId(){
		return w.getText();
	}
}

public class ClientFrame extends JFrame implements ActionListener{
	JTextArea txtArea = new JTextArea();
	JTextField txtField = new JTextField(15);
	JButton btnTransfer = new JButton("����");
	JButton btnFile = new JButton("����");
	
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
		
		add("South",p1); //JPanel p1�� ������Ʈ ���̱�
		
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
			jFileChooser.setDialogTitle("���� ����");
			jFileChooser.setMultiSelectionEnabled(true);
			jFileChooser.setApproveButtonToolTipText("������ ������ �����ϼ���");
			jFileChooser.showDialog(this, "����");
			File path = jFileChooser.getSelectedFile();	
			String name= path.getName();
			MultiChatServer.name = name;
			String filepath = path.getParent();
			MultiChatServer.x = 1;
			FileSender fs = new FileSender(filepath , name);
	    
	        
		}
		else {
			this.dispose();	//â�� �ƿ� �ݴ°��� �ƴ϶� ���� ������� �Ѿ�� exit�� �ƴ� dispose ���
		}
	}
}
//ClientFrame�� id 
