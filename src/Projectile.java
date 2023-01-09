import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Projectile extends JPanel {

	private int speedP;
	private int heightP;
	private int widthP;
	private int colP;
	private int rowP;
	private BufferedImage projectilePicture;
	private boolean spaceShipProjectile;

	//Constructor
	public Projectile(BufferedImage projectilePicture, int speedP, boolean spaceShipProjectile) {
		this.projectilePicture = projectilePicture;
		this.spaceShipProjectile = spaceShipProjectile;
		setSpeedP(speedP);
		setSizeP();
	}

	//sets the speed for the projectile
	public void setSpeedP(int speedP) {
		this.speedP = speedP;
	}

	//sets the size for the projectile
	public void setSizeP() {
		heightP = projectilePicture.getHeight() / 15;
		widthP = projectilePicture.getWidth() / 15;
	}

	//sets location of the projectile
	public void setLocation(int rowP, int colP) {
		this.rowP = rowP;
		this.colP = colP;
	}

	//allows projectile to move vertically
	public void move() {
		rowP += speedP;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(projectilePicture, colP, rowP, widthP, heightP, null);
	}

	// getters and setters

	public int getHeight() {
		return heightP;
	}
	public int getWidth() {
		return widthP;
	}
	public int getColP() {
		return colP;
	}
	public void setColP(int colP) {
		this.colP = colP;
	}
	public int getRowP() {
		return rowP;
	}
	public void setRowP(int rowP) {
		this.rowP = rowP;
	}
	public boolean isSpaceShipProjectile() {
		return spaceShipProjectile;
	}
}
