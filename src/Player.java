import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Player extends JPanel{
	
	private int rowPlayer;
	private int colPlayer;
	private int widthPlayer;
	private int heightPlayer;
	private int hearts;
	private BufferedImage picture;

	// Constructor
	public Player(int rowPlayer, int colPlayer, int hearts) {
		this.rowPlayer = rowPlayer;
		this.colPlayer = colPlayer;
		this.hearts = hearts;
	}

	/*
	* Displays player spaceship
	* */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(picture, colPlayer, rowPlayer, widthPlayer, heightPlayer, this);
	}
	
	//getters and setters

	public boolean isPlayerDead() {
		if(hearts <= 0) {
			return true;
		}
		return false;
	}
	public int getHearts() {
		return hearts;
	}
	public void setHearts(int hearts) {
		this.hearts = hearts;
	}
	public int getRowPlayer() {
		return rowPlayer;
	}
	public int getColPlayer() {
		return colPlayer;
	}
	public void setColPlayer(int colPlayer) {
		this.colPlayer = colPlayer;
	}
	public int getWidth() {
		return widthPlayer;
	}
	public void setWidthPlayer(int widthPlayer) {
		this.widthPlayer = widthPlayer;
	}
	public int getHeight() {
		return heightPlayer;
	}
	public void setHeightPlayer(int heightPlayer) {
		this.heightPlayer = heightPlayer;
	}
	public BufferedImage getPicture() {
		return picture;
	}
	public void setPicture(BufferedImage picture) {
		this.picture = picture;
	}
	public void removeHeart() {
		hearts -= 1;
	}

}
