import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame{
	
	public Client client;
	public static final int WIDTH = 1024, HEIGHT = 768;
	public final int gridW = 16, gridH = 16;
	public GamePanel gamePanel = new GamePanel(this);
	public Menus menus;
	public Color[] colors;
	public int r = (int)(Math.random() * 256);
	public int g = (int)(Math.random() * 256);
	public int b = (int)(Math.random() * 256);
	
	public Game(Client client) throws IOException {
		this.client = client;
		menus = client.menus;
		colors = new Color[client.userLimit];
		for(int i = 0; i < colors.length; i++) {
			colors[i] = new Color(0, 0, 0);
		}
		colors[client.userID] = new Color(r, g, b);
		setup();
	}
	
	public void setup() throws IOException {
		setTitle("Network Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                try {
					client.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
                menus.mainMenu(null, menus);
            }
        } );
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		//add(gamePanel);
		
		repaint();
	}
	int x = 0;
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		x+=100;
		g.setColor(Color.GREEN);
		g.fillRect(x, 0, WIDTH-x, HEIGHT);
	}

}

class GamePanel extends JPanel{
	public Game game;
	
	public GamePanel(Game game) {
		this.game = game;
		setLayout(null);
		setBackground(new Color(120, 120, 120));
		JButton exitB = new JButton("X");
		exitB.setBorderPainted(false);
		exitB.setBackground(Color.DARK_GRAY);
		exitB.setForeground(new Color(150, 50, 50));
		exitB.setBounds(Game.WIDTH - 75, 0, 75, 50);
		exitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.dispose();
				//disconnect
				//menus
			}	
		});	
		//System.out.println("Higuolv;cvhfbgvodn");
		add(exitB);
		
	}	
	
}
