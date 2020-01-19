package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.woodwhales.tank.net.Client;
import org.woodwhales.tank.net.msg.TankDirChangedMsg;
import org.woodwhales.tank.net.msg.TankStartMovingMsg;
import org.woodwhales.tank.net.msg.TankStopMsg;

public class TankFrame extends Frame {
	
	public static final TankFrame INSTANCE = new TankFrame();

	private static final long serialVersionUID = 1L;

	List<Bullet> bullets = new ArrayList<>();
	Map<UUID, Tank> tanks = new HashMap<>();
	List<Explode> explodes = new ArrayList<>();

	Random random = new Random();
	
	private boolean gameOver = false;

	private Tank myTank = new Tank(random.nextInt(GAME_WIDTH-Tank.WIDTH), random.nextInt(GAME_HEIGHT-Tank.HEIGHT), Dir.RIGHT, Group.GOOD, this);

	static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	
	public void addTank(Tank tank) {
		this.tanks.put(tank.getId(), tank);
	}
	
	public void addBullet(Bullet bullet) {
		this.bullets.add(bullet);
	}
	
	public void removeTank(Tank tank) {
		this.tanks.remove(tank.getId());
	}
	
	public Tank findByUIUID(UUID id) {
		return this.tanks.get(id);
	}
	
	public TankFrame() {
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
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("bullets size = " + bullets.size() ,10, 50);
		g.drawString("enemies size = " + tanks.size() ,10, 60);
		g.drawString("explodes size = " + explodes.size() ,10, 70);
		g.drawString("tanks size = " + tanks.size() ,10, 80);
		g.setColor(color);
		
		myTank.paint(g);
		
		this.tanks.values().stream().forEach(tank -> tank.paint(g));
		
		if(this.gameOver) {
			drawGameOver(g);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		
		Collection<Tank> values = tanks.values();
		for (int i = 0; i < bullets.size(); i++) {
			for (Tank tank : values) {
				bullets.get(i).collideWith(tank);
			}
		}
		
	}

	private void drawGameOver(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		Font font = g.getFont();
		Font fonts = new Font("Tahoma", Font.BOLD, 36);
		g.setFont(fonts);
		String gameOverNotice = "YOUR GAME OVER!";
		
		FontMetrics fontMetrics = g.getFontMetrics(fonts);
		int fontHeight = fontMetrics.getHeight();
		int fontWidth = fontMetrics.stringWidth(gameOverNotice);
		g.drawString(gameOverNotice , GAME_WIDTH / 2 - (fontWidth / 2), GAME_HEIGHT / 2 + (fontHeight / 2));
		g.setFont(font);
		g.setColor(color);
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
			if (!gameOver) {
				switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = true;
					setMainTankDir();
					break;
				case KeyEvent.VK_UP:
					bU = true;
					setMainTankDir();
					break;
				case KeyEvent.VK_RIGHT:
					bR = true;
					setMainTankDir();
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					setMainTankDir();
					break;
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (!gameOver) {
				switch (keyCode) {
				case KeyEvent.VK_LEFT:
					bL = false;
					setMainTankDir();
					break;
				case KeyEvent.VK_UP:
					bU = false;
					setMainTankDir();
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					setMainTankDir();
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					setMainTankDir();
					break;
				case KeyEvent.VK_SPACE:

					myTank.fire();

					break;
				}
			}
			
		}
		
		// 根据键盘的按键情况，改变方向
		private void setMainTankDir() {
			
			Dir dir = myTank.getDir(); 
			
			// 当前坦克创建有摁方向键
			if(bL || bU || bR || bD) {
				
				// 改变tank的方向
				if(bL) myTank.setDir(Dir.LEFT);
				if(bU) myTank.setDir(Dir.UP);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bD) myTank.setDir(Dir.DOWN);
				
				// tank原来是静止的状态，此时方向改变，需要发出tank移动消息
				if(!myTank.isMoving()) {
					Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));
				} 

				myTank.setMoving(true);
				
				if(dir != myTank.getDir()) {
					Client.INSTANCE.send(new TankDirChangedMsg(getMainTank()));
				}
				return;
			}
			
			// 没有按任何方向键位，则表示tank停止移动
			myTank.setMoving(false);
			Client.INSTANCE.send(new TankStopMsg(getMainTank()));

		}
	}

	public Tank getMainTank() {
		return myTank;
	}

	public Tank findTankByUUID(UUID id) {
		return tanks.get(id);
	}
	
	public Bullet findBulletByUUID(UUID id) {
		if(Objects.nonNull(bullets) && bullets.size() > 0) {
			for (Bullet bullet : bullets) {
				if(bullet.getId().equals(id)) {
					return bullet;
				}
			}
		}
		
		return null;
	}

	public void gameOver() {
		this.gameOver = true;
	}

}
