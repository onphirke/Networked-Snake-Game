import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menus extends JFrame{
	
	public Client client; 
	public static final int WIDTH = 1024, HEIGHT = 768;
	
	public Menus(Client client) {
		this.client = client;
	}
	
	public void mainMenu(JPanel panel, JFrame frame) {
		frame.setTitle("Network Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		if(panel != null)
			panel.setVisible(false);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(null);
		panelMenu.setBackground(new Color(120, 200, 100));
		frame.add(panelMenu);
		
		JButton play = new JButton("PLAY!");
		play.setFont(new Font("Arial", Font.BOLD, 70));
		play.setForeground(new Color(100,100,100));
		play.setBorderPainted(false);
		play.setBackground(new Color(190,190,190));
		int bw = 320, bh = 70;
		play.setBounds(WIDTH/2 - bw/2, HEIGHT/2 - bh/2 - 50, bw, bh);
		panelMenu.add(play);
		
		/*JButton nameLabel = new JButton("Enter Username");
		nameLabel.setFocusable(false);
		nameLabel.setFocusPainted(false);
		nameLabel.setSelected(false);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		nameLabel.setBounds(WIDTH/2 - bw/2 - 21, HEIGHT/2 - bh/2 + 17, 160, 40);
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBorderPainted(false);
		nameLabel.setBackground(panel.getBackground());
		panel.add(nameLabel);*/
		
		JTextField nameInput = new JTextField();
		nameInput.setBounds(WIDTH/2 - bw/2, HEIGHT/2 - bh/2 + 40, bw, 30);
		nameInput.setHorizontalAlignment(JTextField.CENTER);
		nameInput.setText("Enter Username Here.");
		panelMenu.add(nameInput);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.Connect(frame);
					client.username = nameInput.getText();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}	
		});		
		
		JButton optionsB = new JButton("Options");
		optionsB.setBounds(WIDTH/2 - bw/2, HEIGHT/2 - bh/2 + 80, bw/2 - 10, 40);
		optionsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options(panelMenu, frame);
			}	
		});
		panelMenu.add(optionsB);
		
		JButton exitB = new JButton("Exit");
		exitB.setBounds(WIDTH/2 + 10 , HEIGHT/2 - bh/2 + 80, bw/2 - 10, 40);
		exitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit(frame);
			}	
		});
		panelMenu.add(exitB);
		
		frame.setVisible(true);
		
	}
	
	public void options(JPanel panel, JFrame frame) {
		//System.out.println("Working");
		panel.setVisible(false);
		
		JPanel panelOption = new JPanel();
		panelOption.setLayout(null);
		panelOption.setBackground(new Color(120, 200, 100));
		frame.add(panelOption);
		
		JButton mainMenuB = new JButton("Main Menu");
		mainMenuB.setFont(new Font("Arial", Font.BOLD, 30));
		mainMenuB.setForeground(new Color(50, 100, 50));
		mainMenuB.setBackground(new Color(200, 200, 70));
		mainMenuB.setBorderPainted(false);
		mainMenuB.setBounds(WIDTH/2 - 160, HEIGHT/2 - 150, 320, 60);
		mainMenuB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainMenu(panelOption, frame);
			}	
		});
		panelOption.add(mainMenuB);
		
	}
	
	public void play(JFrame frame) throws IOException {
		frame.dispose();
	}

	
	public void exit(JFrame frame) {
		frame.dispose();
		System.exit(0);
	}
}