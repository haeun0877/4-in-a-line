package project3;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class project3 extends JFrame implements KeyListener{
	private GameHandler handler;
	private JTextArea textArea = new JTextArea();
	
	public project3() {
		setTitle("Let's play 4 in A Line");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 30));
		textArea.addKeyListener(this);
		add(textArea);
		textArea.setEditable(false);
		setVisible(true);
		
		handler = new GameHandler(textArea);
		new Thread(new GameThread()).start();
	}

	public static void main(String[] args) {
		new project3();
	}
	
	class GameThread implements Runnable{

		public void run() {
			handler.drawAll();
			handler.win();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			handler.moveRight();
			break;
		case KeyEvent.VK_LEFT:
			handler.moveLeft();
			break;
		case KeyEvent.VK_DOWN:
			handler.putDoll();
			break;
		case KeyEvent.VK_Y:
			handler.putY();
			break;
		case KeyEvent.VK_N:
			handler.putN();
			break;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

}
