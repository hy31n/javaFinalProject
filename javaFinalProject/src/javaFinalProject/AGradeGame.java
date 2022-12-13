package javaFinalProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AGradeGame extends JFrame {
	Image bufferImage;
	Graphics screenGraphic;

	Image background = new ImageIcon("src/image/background.png").getImage();
	Image player = new ImageIcon("src/image/player.png").getImage();
	Image professor = new ImageIcon("src/image/professor.png").getImage();
	Image aScore = new ImageIcon("src/image/aScore.png").getImage();

	int WIDTH = 1270, HEIGHT = 700;
	int playerX, playerY;
	int playerWidth = player.getWidth(null);
	int playerHeight = player.getHeight(null);
	int professorX, professorY;
	int professorWidth = professor.getWidth(null);
	int professorHeight = professor.getHeight(null);
	int aScoreX, aScoreY;
	int aScoreWidth = aScore.getWidth(null);
	int aScoreHeight = aScore.getHeight(null);

	int score;

	boolean up, down, left, right;

	public AGradeGame() {
		setTitle("교수님 피하기 게임");
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = true;
					break;
				case KeyEvent.VK_DOWN:
					down = true;
					break;
				case KeyEvent.VK_LEFT:
					left = true;
					break;
				case KeyEvent.VK_RIGHT:
					right = true;
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = false;
					break;
				case KeyEvent.VK_DOWN:
					down = false;
					break;
				case KeyEvent.VK_LEFT:
					left = false;
					break;
				case KeyEvent.VK_RIGHT:
					right = false;
					break;
				}
			}
		});
		init();

		while (true) {
			try {
				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playerMove();
			moveProfessor();
			crashCheck();
			gameOverCheck();
		}

	}

	public void init() {
		score = 0;

		playerX = (WIDTH - playerWidth) / 2;
		playerY = (HEIGHT - playerHeight) / 2;

		aScoreX = (int) (Math.random() * (WIDTH - playerWidth));
		aScoreY = (int) (Math.random() * (HEIGHT - playerHeight - 80)) + 80;
	}

	public void gameOverCheck() {
		if (professorX + professorWidth > playerX && professorY + professorHeight > playerY
				&& playerX + playerWidth > professorX && playerY + playerHeight > professorY) {
			JOptionPane.showMessageDialog(null, "Game Over");
			System.exit(0);
		}
	}

	public void crashCheck() {
		if (playerX + playerWidth > aScoreX && aScoreX + aScoreWidth > playerX && playerY + playerHeight > aScoreY
				&& aScoreY + aScoreHeight > playerY) {
			score += 100;
			aScoreX = (int) (Math.random() * (1271 - playerWidth));
			aScoreY = (int) (Math.random() * (701 - playerHeight - 80)) + 80;
		}
	}

	public void playerMove() {
		if (up && playerY - 3 > 30)
			playerY -= 5;
		if (down && playerY + playerHeight + 3 < HEIGHT)
			playerY += 5;
		if (left && playerX - 3 > 0)
			playerX -= 5;
		if (right && playerX + playerWidth + 3 < WIDTH)
			playerX += 5;
	}

	public void moveProfessor() {
		int x = professorX, y = professorY;
		if (x > playerX)
			x -= 3;
		else
			x += 3;
		if (y > playerY)
			y -= 3;
		else
			y += 3;
		professorX = x;
		professorY = y;
	}

	public void paint(Graphics g) {
		bufferImage = createImage(WIDTH, HEIGHT);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(player, playerX, playerY, null);
		g.drawImage(aScore, aScoreX, aScoreY, null);
		g.drawImage(professor, professorX, professorY, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 30));
		g.drawString("SCORE " + score, 25, 65);
		this.repaint();
	}
}
