/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatSocket;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ManagerGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField textField;
    private JLabel lblNewLabel_1;
    private JTabbedPane tabbedPane;
    private JButton btnNewButton;
    // khởi tạo biến toàn cục phía ngoài
    ManagerGUI thisManager;
    ServerSocket socket = null;
    BufferedReader br = null;
    Thread t;
    private JLabel lblNewLabel_2;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    ManagerGUI frame = new ManagerGUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public ManagerGUI() {
	initComponents();
	thisManager = this;
    }

    private void initComponents() {
	setTitle("Manager");
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 835, 674);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	contentPane.add(getLblNewLabel());
	contentPane.add(getTextField());
	contentPane.add(getLblNewLabel_1());
	contentPane.add(getTabbedPane());
	contentPane.add(getBtnNewButton());
    }

    public JLabel getLblNewLabel() {
	if (lblNewLabel == null) {
	    lblNewLabel = new JLabel("Manager Port");
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lblNewLabel.setBounds(202, 22, 148, 53);
	}
	return lblNewLabel;
    }

    public JTextField getTextField() {
	if (textField == null) {
	    textField = new JTextField();
	    textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
	    textField.setBounds(331, 27, 144, 46);
	    textField.setColumns(10);
	}
	return textField;
    }

    public JLabel getLblNewLabel_1() {
	if (lblNewLabel_1 == null) {
	    lblNewLabel_1 = new JLabel("@PhucAndTin");
	    lblNewLabel_1.setForeground(Color.DARK_GRAY);
	    lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 15));
	    lblNewLabel_1.setBounds(670, 597, 147, 29);
	}
	return lblNewLabel_1;
    }

    public JTabbedPane getTabbedPane() {
	if (tabbedPane == null) {
	    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setFont(new Font("Sylfaen", Font.PLAIN, 20));
	    tabbedPane.setBorder(null);
	    tabbedPane.setBounds(12, 73, 805, 521);
	    tabbedPane.addTab(null, null, getLblNewLabel_2(), null);
	}
	return tabbedPane;
    }

    @Override
    public void run() {
	while (true)

	    try {
		// Chấp nhận kết nối từ Client
		Socket sk = socket.accept();
		if (sk != null) {
		    // Lấy tên của từng thành viên nhắn cho giảng viên
		    br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		    String staffName = br.readLine();
		    staffName = staffName.substring(0, staffName.indexOf(":"));

		   
		    ChatPanel chatPanel = new ChatPanel(sk, "Giang Vien", staffName);
		    tabbedPane.add(staffName, chatPanel);
		    chatPanel.updateUI();

		    
		    Thread t = new Thread(chatPanel);
		    t.start();
		}
		Thread.sleep(1000);
	    } catch (Exception e) {
		e.printStackTrace();
            }
    }

    public JButton getBtnNewButton() {
	if (btnNewButton == null) {
	    btnNewButton = new JButton("START SERVER");
	    btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
	    btnNewButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    // khởi tạo cổng mặc định 5000
		    int port = 5000;
		    try {
			// Kiểm tra dữ liệu nhập vào
			port = Integer.parseInt(textField.getText());
		    } catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane,
			                "Can't start at this port, program will use the default port=5000: " + e,
			                "Error while read Port", JOptionPane.ERROR_MESSAGE);
		    }
		    try {
			// chạy server
			socket = new ServerSocket(port);
			JOptionPane.showMessageDialog(contentPane, "Server is running at port: " + port, "Started server",
			                JOptionPane.INFORMATION_MESSAGE);

		    } catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Details: " + e, "Start server error",
			                JOptionPane.ERROR_MESSAGE);
		    }

		    
		    Thread t = new Thread(thisManager);
		    t.start();
		}
	    });
	    btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
	    btnNewButton.setBounds(487, 25, 167, 47);
	}
	return btnNewButton;
    }

    public JLabel getLblNewLabel_2() {
	if (lblNewLabel_2 == null) {
	    lblNewLabel_2 = new JLabel("Waitting for client");
	    lblNewLabel_2.setBackground(Color.WHITE);
	    lblNewLabel_2.setForeground(Color.RED);
	    lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 28));
	    lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
	}
	return lblNewLabel_2;
    }
}