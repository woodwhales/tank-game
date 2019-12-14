package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

	private static final long serialVersionUID = 1L;

	Tank myTank = new Tank(200, 200, Dir.RIGHT, this);
	Bullet bullet = new Bullet(150, 150, Dir.DOWN);
	
	static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	
	public TankFrame() {
		setVisible(true);
		setResizable(false);
		setTitle("tank war game");
		setSize(GAME_WIDTH, GAME_HEIGHT);
		
		addKeyListener(new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	// 每次窗口在显示器上重新显示出来就会调用一次paint()方法
	// 因此想要方框能够移动，那么就需要不停地调用paint()方法，重新画方框的位置
	@Override
	public void paint(Graphics g) {
		
		// 注意这里，不要再把myTank的属性取出来，再画位置，这样破坏了面向对象的封装思想。
		// 只有tank自己知道自己要画在哪里，因此把画笔传给这个tank，让它自己画自己应该出现的位置。
		myTank.paint(g);
		bullet.paint(g);
	}

	class MyKeyListener extends KeyAdapter {
		
		// 使用标识变量记录键盘按键的情况，之后再计算方块应该移动的方向
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_UP:
					bU = true;
					break;
				case KeyEvent.VK_RIGHT:
					bR = true;
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					break;
			}
			
			setMainTankDir();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_CONTROL:
					myTank.fire();
					break;
			}
			
			setMainTankDir();
		}
		
		// 根据键盘的按键情况，改变方向
		private void setMainTankDir() {
			if(bL || bU || bR || bD) {
				myTank.setMoving(true);
				
				if(bL) myTank.setDir(Dir.LEFT);
				if(bU) myTank.setDir(Dir.UP);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bD) myTank.setDir(Dir.DOWN);
				return;
			}
			
			myTank.setMoving(false);
		}
	}
	
	
}
