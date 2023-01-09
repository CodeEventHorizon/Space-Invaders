import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Enemy extends JPanel {

	private int rowE;
	private int colE;
	private BufferedImage pictureE;
	private int widthE = 100;
	private int heightE = 100;
	private boolean unavailable = false;
	private int numberOfExplosions;
	private BufferedImage projectile;
	private String powerActivate;
	private int score;
	private static ArrayList<Object> redEnemyAlien = new ArrayList<Object>();
	private static ArrayList<Object> blueEnemyAlien = new ArrayList<Object>();
	private static ArrayList<Object> purpleEnemyAlien = new ArrayList<Object>();
	private static ArrayList<Object> flyingEnemyAlien = new ArrayList<Object>();

	//Constructor
	public Enemy(int rowE, int colE, ArrayList<Object> enemyArray) {
		this.rowE = rowE;
		this.colE = colE;
		this.score = (Integer) enemyArray.get(0);
		this.pictureE = (BufferedImage) enemyArray.get(1);
		numberOfExplosions = 0;
		if (enemyArray.get(2) != null) {
			this.projectile = (BufferedImage) enemyArray.get(2);
		}
	}

	//Constructor 2
	public Enemy(int rowE, int colE, String powerActivate) {
		this.rowE = rowE;
		this.colE = colE;
		this.powerActivate = powerActivate;
	}
	/*
	* Creates the list for enemies
	* */
	public static void createEList() {
		redEnemyAlien.add(50);
		redEnemyAlien.add(Pictures.getRedEnemyAlien());
		redEnemyAlien.add(Pictures.getProjectileBomb());

		blueEnemyAlien.add(100);
		blueEnemyAlien.add(Pictures.getBlueEnemyAlien());
		blueEnemyAlien.add(Pictures.getProjectileBomb());

		purpleEnemyAlien.add(150);
		purpleEnemyAlien.add(Pictures.getPurpleEnemyAlien());
		purpleEnemyAlien.add(Pictures.getProjectileBomb());

		flyingEnemyAlien.add(300);
		flyingEnemyAlien.add(Pictures.getFlyingEnemyAlien());
		flyingEnemyAlien.add(null);
	}
	/*
	* Displays Enemy
	* */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(pictureE, colE, rowE, widthE, heightE, this);
	}

	// getters and setters

	public int getRowE() {
		return rowE;
	}
	public void setRowE(int rowE) {
		this.rowE = rowE;
	}
	public int getColE() {
		return colE;
	}
	public void setColE(int colE) {
		this.colE = colE;
	}
	public int getWidth() {
		return widthE;
	}
	public void setWidthE(int widthE) {
		this.widthE = widthE;
	}
	public int getHeight() {
		return heightE;
	}
	public void setHeightE(int heightE) {
		this.heightE = heightE;
	}
	public boolean isUnavailable() {
		return unavailable;
	}
	public void setUnavailable(boolean unavailable) {
		this.unavailable = unavailable;
	}
	public static ArrayList<Object> getRedEnemyAlien() {
		return redEnemyAlien;
	}
	public static ArrayList<Object> getBlueEnemyAlien() {
		return blueEnemyAlien;
	}
	public static ArrayList<Object> getPurpleEnemyAlien() {
		return purpleEnemyAlien;
	}
	public static ArrayList<Object> getFlyingEnemyAlien() {
		return flyingEnemyAlien;
	}
	public int getScore() {
		return score;
	}
	public void setPictureE(BufferedImage pictureE) {
		this.pictureE = pictureE;
	}
	public BufferedImage getProjectile() {
		return projectile;
	}
	public BufferedImage getPictureE() {
		return this.pictureE;
	}
	public String getPowerActivate() {
		return powerActivate;
	}
	public int getNumberOfExplosions() {
		return numberOfExplosions;
	}
	public void incrementNumBlasts() {
		this.numberOfExplosions++;
	}
}
