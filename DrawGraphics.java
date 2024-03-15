import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class MyPanel extends JPanel implements ActionListener{
	
	//Timer for Movement Animation
	Timer tm = new Timer(5, this);
	
	//declaring x ,y and their speed
	int x = 1, Velx = 1;
	int y = 1, Vely = 1;
	
	//char array that we get from the Maze class
	char[] ways;
	int number = 0;
	int Numberpath = 0;
	
	//mouse image
	Image mouse;
	
	MyPanel(char[] ways,int Numberpath){
		this.setPreferredSize(new Dimension(500,500));
		this.ways = ways.clone();
		this.Numberpath = Numberpath;
		mouse = new ImageIcon("mouseMaze.png").getImage();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(Color.black);
		g2D.setStroke(new BasicStroke(4));
		
		//int n = 9;
		//Space between rectangles
		int distancex = 0;
		int distancey = 0;
		
		//Drawing Matrix
		for (int i = 0; i < Numberpath; i++) {
			for (int j = 0; j < Numberpath; j++) {
				g2D.fillRect(distancex, distancey, 50, 50);
				distancex += 75;
			}
			distancex = 0;
			distancey += 75;
		}
		//g2D.drawRect(0, 0, 200, 200);
		//setBackDefaultOval(g2D, 3, 3);
		
		//Draw the mouse
		g2D.setPaint(Color.red);
		g2D.drawImage(mouse, x, y, 50, 50,null, null);
		//g2D.fillOval(x, y, 45, 45);
		tm.start();
		//setBackDefaultRec(g2D,0,0);
		
	}

	//check the end of the char array
	public boolean checker() {
		if(number > ways.length-1)
			return false;
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Sleep();
		//Start Animation Movements
		try {
			if(checker()) {
				//going East
				if(ways[number] == 'E') {
					//	
					x = x + Velx;
					repaint();
					
					if(x%75 == 0) {
						number++;
					}
					
				} 
				//going West
				if(ways[number] == 'W') {
					//
					x = x - Velx;
					repaint();
					if(x%75 == 0) {
						number++;
					}
					
				}
				//going South
				if(ways[number] == 'S') {
					y = y + Vely;
					repaint();
					
					if(y%75 == 0)
						number++;
					
				}
				//going North
				if(ways[number] == 'N') {
					y = y - Vely;
					repaint();
					
					if(y%75 == 0)
						number++;
				}
				
				//going North East
				if(ways[number] == '5') {
					y = y - Vely;
					x = x + Velx;
					repaint();
					
					if(y%75 == 0)
						number++;
				}
				
				//going North West
				if(ways[number] == '6') {
					y = y - Vely;
					x = x - Velx;
					repaint();
					
					if(y%75 == 0)
						number++;
				}
				
				//going South East
				if(ways[number] == '7') {
					y = y + Vely;
					x = x + Velx;
					repaint();
					
					if(y%75 == 0)
						number++;
				}
				
				//going South West
				if(ways[number] == '8') {
					y = y + Vely;
					x = x - Velx;
					repaint();
					
					if(y%75 == 0)
						number++;
				}
			}else {
				return;
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
	
}

public class DrawGraphics extends JFrame{
	MyPanel panel;
	
	DrawGraphics(int numberPath,char[] path){
		
		//wait 3 second
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//char[] ways = new char[] {'E','S','S','7','8','6'};
		panel = new MyPanel(path,numberPath);
		
		//Setting Frame for Graphic Design
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

}
