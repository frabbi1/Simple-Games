package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.*;

public class PongGame extends JPanel implements ActionListener, KeyListener{
	
	private int W = 680, H = 500;
	private Rectangle bar1  = new Rectangle(5+10, H/2-40, 10, 80); 
	private Rectangle bar2  = new Rectangle(5+W-20, H/2-40, 10, 80);
	private Rectangle ball  = new Rectangle(5+10+12, H/2-10, 15, 15);
	Timer t;
	Random rand = new Random();
	
	private int ballXdir = 1, ballYdir = 1, ballSpeed, barSpeed = 15, spd ;
	private boolean started = false, upDir = true, downDir = true,
					won = false, over = false;
	
	public PongGame() {
		t = new Timer(10, this);
		setBackground(Color.DARK_GRAY);
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.drawRect(5, 5, W, H);
		
		g2.setColor(Color.WHITE.darker());
		g2.fill(bar1);
		g2.fill(bar2);
		g2.setColor(Color.WHITE.brighter());
		g2.fillOval(ball.x, ball.y, ball.width, ball.height);
		
		if(over) {
			g2.setColor(Color.RED);
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
			if(won) g2.drawString("YOU  WON", 90, 300);
			else g2.drawString("YOU LOST", 90, 300);
			setFocusable(false);
			t.stop();
			started = false;
		}
		g.dispose();
		repaint();
	}

	public void keyPressed(KeyEvent k) {
		int code  = k.getKeyCode();
		if(code == KeyEvent.VK_UP && upDir) {
			bar1.y -= barSpeed;
			if(!started) {
				ball.y -= barSpeed;
			}
		}
		
		if(code == KeyEvent.VK_DOWN && downDir) {
			bar1.y += barSpeed;
			if(!started) {
				ball.y += barSpeed;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			started = true;
			t.start();
		}
	}
	public void actionPerformed(ActionEvent arg0) {
		
		ballSpeed = 2;
		spd = 1 + rand.nextInt(3);
		System.out.println(spd);
		
		if(started) {
			ball.x += ballSpeed*ballXdir;
			ball.y += ballSpeed*ballYdir;
			bar2.y = (bar2.y+(spd*ballYdir));
		}
		if(ball.y<5 || ball.y+ball.height > H) {
			ballYdir = - ballYdir;
		}
		
		if(ball.intersects(bar2) || ball.intersects(bar1)) {
			ballXdir = -ballXdir;
		}
		if(bar1.y < 15) upDir = false;
		else upDir = true;
		if(bar1.y + bar1.height > H-10) downDir = false;
		else downDir = true;
		
		if(bar2.y < 15) bar2.y = 15;
		if(bar2.y + bar2.height > H-10) bar2.y = H-10-bar2.height;
		
		if(ball.x < bar1.x) {
			won = false; over = true;
		}
		if(ball.x >= bar2.x) {
			won = true; over = true;
		}
		
		
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}


}
