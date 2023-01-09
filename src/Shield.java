import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Shield extends JPanel {
	private int damagedShieldX;
	private int damagedShieldY;
	private int colShield;
	private int rowShield;
	private int widthShield;
	private int heightShield;
	private int damagedShieldWidth;
	BufferedImage picture;

	//Constructor
	public Shield(int rowShield, int colShield) {
		this.rowShield = rowShield;
		this.colShield = colShield;
	}
	/*
	* Following method sets the picture for the Barrier/shield passed by the other class
	* */
	public void setImage(BufferedImage picture, boolean modifiedPic) {
		if(modifiedPic) {
			if (picture != null && this.picture != null) {
				BufferedImage pictureColor = new BufferedImage(picture.getWidth(), picture.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				for (int row = 0; row < picture.getHeight(); row++) {
					for (int column = 0; column < picture.getWidth(); column++) {
						int RGBA = 0;
						if(Pictures.inRange(picture, row, column) && Pictures.inRange(this.picture, row, column)) {
							if (this.picture.getRGB(column, row) != RGBA && picture.getRGB(column, row) != RGBA) {
								pictureColor.setRGB(column, row, picture.getRGB(column, row));
							}
						}

					}
				}
				this.picture = pictureColor;
			}
		}
		else {
			this.picture = picture;
		}
	}

	// displays shields/barriers
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(picture, colShield, rowShield, widthShield, heightShield, this);
	}

	// getters and setters

	public BufferedImage getPicture() {
		return picture;
	}
	public void setDamagedShieldX(int damagedShieldX) {
		this.damagedShieldX = damagedShieldX;
	}
	public void setDamagedShieldY(int damagedShieldY) {
		this.damagedShieldY = damagedShieldY;
	}
	public int getColShield() {
		return colShield;
	}
	public int getRowShield() {
		return rowShield;
	}
	public void setWidthShield(int widthShield) {
		this.widthShield = widthShield;
	}
	public int getWidth() {
		return widthShield;
	}
	public void setHeightShield(int heightShield) {
		this.heightShield = heightShield;
	}
	public int getHeight() {
		return heightShield;
	}
	public void setDamagedShieldWidth(int width) {
		this.damagedShieldWidth = width;
	}
	public void changePicture(boolean playerProjectile) {
		BufferedImage picture = this.picture;
		if (picture != null) {
			BufferedImage pictureColor = new BufferedImage(picture.getWidth(), picture.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			for (int row = 0; row < picture.getHeight(); row++) {
				for (int column = 0; column < picture.getWidth(); column++) {
					pictureColor.setRGB(column, row, picture.getRGB(column, row));
				}
			}
			if (!playerProjectile) {
				int damagedHeight = damagedShieldWidth;
				for (int row = damagedShieldY; row < damagedShieldY + damagedHeight; row++) {
					for (int column = damagedShieldX; column < damagedShieldX + damagedShieldWidth; column++) {
						if (Pictures.inRange(pictureColor, row, column)) {
							pictureColor.setRGB(column, row, 0);
						}
					}

				}
			} else {
				int damagedHeight = damagedShieldWidth;
				for (int row = damagedShieldY; row > damagedShieldY - damagedHeight; row--) {
					for (int column = damagedShieldX; column < damagedShieldX + damagedShieldWidth; column++) {
						if (Pictures.inRange(pictureColor, row, column)) {
							pictureColor.setRGB(column, row, 0);
						}
					}

				}
			}
			this.picture = pictureColor;
		}
	}
}