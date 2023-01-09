import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
/**
* Reference to most of the pictures used in the program, anything else was found in google images if not listed.
 * https://freepngimg.com/png/32295-space-invaders-transparent-background
 * https://freepngimg.com/png/32282-space-invaders-free-download
 * https://freepngimg.com/png/32291-space-invaders-transparent-picture
 * https://freepngimg.com/png/25214-spaceship-transparent
 * https://freepngimg.com/png/25066-spaceship-picture
 * https://freepngimg.com/png/81313-sound-sonic-flower-explosion-symmetry-boom
 * https://www.pngwing.com/en/free-png-bxenk
 * https://freepngimg.com/png/4054-bullets-png-image
 * https://www.pngwing.com/en/free-png-pgtsl
 * https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Space_invaders_logo.svg/1000px-Space_invaders_logo.svg.png
 * https://freepngimg.com/png/15681-sharpener-png
 * https://freepngimg.com/png/6930-shield-png-image-picture-download
 *
 * Some of the sprites were made by me in Adobe Photoshop, or in Paint
* */
public class Pictures {

	// Space Background
	private static BufferedImage spaceBackground1;
	private static BufferedImage spaceBackground2;
	private static BufferedImage spaceBackground3;
	private static BufferedImage spaceBackground4;
	private static BufferedImage spaceShield;
	private static BufferedImage blueEnemyAlien;
	private static BufferedImage redEnemyAlien;
	private static BufferedImage purpleEnemyAlien;
	private static BufferedImage flyingEnemyAlien;
	private static BufferedImage projectileBomb;
	private static BufferedImage spaceshipBullet;
	private static BufferedImage spaceShip;
	private static BufferedImage headTitle;
	private static BufferedImage playGameBtn;
	private static BufferedImage mainBackground;
	private static BufferedImage restoreShields;
	private static BufferedImage heart;
	private static BufferedImage miniSpaceship;
	private static BufferedImage stopTime;
	private static BufferedImage invincibility;
	private static BufferedImage sharpShot;
	private static BufferedImage defeatWindow;
	private static BufferedImage infoBtn;
	private static BufferedImage continueBtn;
	private static BufferedImage pauseBtn;
	private static BufferedImage instructions;
	private static BufferedImage muteBtn;
	private static BufferedImage unMuteBtn;
	private static BufferedImage enemyExplosion;
	private static BufferedImage spaceShipExplosion;

	public void uploadPics() throws IOException {
		InputStream istream;

		/*
		* Backgrounds
		* */
		istream = this.getClass().getResourceAsStream("Images/SpaceBackground1.png");
		spaceBackground1 = ImageIO.read(istream);
		
		istream = this.getClass().getResourceAsStream("Images/SpaceBackground2.jpg");
		spaceBackground2 = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/SpaceBackground3.jpg");
		spaceBackground3 = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/SpaceBackground4.jpg");
		spaceBackground4 = ImageIO.read(istream);
		/*
		 * Barrier/Shield
		 * */
		istream = this.getClass().getResourceAsStream("Images/spaceShield.png");
		spaceShield = ImageIO.read(istream);

		/*
		* Enemies
		* */

		istream = this.getClass().getResourceAsStream("Images/purpleEnemyAlien.png");
		purpleEnemyAlien = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/blueEnemyAlien.png");
		blueEnemyAlien = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/redEnemyAlien.png");
		redEnemyAlien = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/flyingEnemyAlien.png");
		flyingEnemyAlien = ImageIO.read(istream);

		/*
		 * Home Background
		 * */

		istream = this.getClass().getResourceAsStream("Images/mainBackground.png");
		mainBackground = ImageIO.read(istream);

		/*
		 * Projectiles
		 * */

		istream = this.getClass().getResourceAsStream("Images/projectileBomb.png");
		projectileBomb = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/spaceshipBullet.png");
		spaceshipBullet = ImageIO.read(istream);

		/*
		 * Spaceship
		 * */
		istream = this.getClass().getResourceAsStream("Images/spaceShip.png");
		spaceShip = ImageIO.read(istream);

		/*
		 * HomePage
		 * */

		istream = this.getClass().getResourceAsStream("Images/SpaceInvaders.png");
		headTitle = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/playGameBtn.png");
		playGameBtn = ImageIO.read(istream);

		/*
		 * Powers
		 * */

		istream = this.getClass().getResourceAsStream("Images/restoreShields.png");
		restoreShields = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/heart.png");
		heart = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/miniSpaceship.png");
		miniSpaceship = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/stopTime.png");
		stopTime = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/invincibility.png");
		invincibility = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/sharpShot.png");
		sharpShot = ImageIO.read(istream);

		/*
		 * Defeat
		 * */

		istream = this.getClass().getResourceAsStream("Images/defeatWindow.png");
		defeatWindow = ImageIO.read(istream);

		/*
		 * Buttons
		 * */

		istream = this.getClass().getResourceAsStream("Images/infoBtn.png");
		infoBtn = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/instructions.png");
		instructions = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/continueBtn.png");
		continueBtn = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/pauseBtn.png");
		pauseBtn = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/muteBtn.png");
		muteBtn = ImageIO.read(istream);

		istream = this.getClass().getResourceAsStream("Images/unMuteBtn.png");
		unMuteBtn = ImageIO.read(istream);

		/*
		 * Explosions
		 * */

		istream = this.getClass().getResourceAsStream("Images/enemyExplosion.png");
		enemyExplosion = ImageIO.read(istream);
		
		istream = this.getClass().getResourceAsStream("Images/spaceShipExplosion.png");
		spaceShipExplosion = ImageIO.read(istream);
	}

	// getters and setters

	public static boolean inRange(BufferedImage image, int row, int column) {
		return row >= 0 && row < image.getHeight() && column >= 0 && column < image.getWidth();
	}
	public static BufferedImage getBlueEnemyAlien() {
		return blueEnemyAlien;
	}
	public static BufferedImage getSpaceBackground4() {
		return spaceBackground4;
	}
	public static BufferedImage getEnemyExplosion() {
		return enemyExplosion;
	}
	public static BufferedImage getFlyingEnemyAlien() {
		return flyingEnemyAlien;
	}
	public static BufferedImage getStopTime() {
		return stopTime;
	}
	public static BufferedImage getDefeatWindow() {
		return defeatWindow;
	}
	public static BufferedImage getHeart() {
		return heart;
	}
	public static BufferedImage getMainBackground() {
		return mainBackground;
	}
	public static BufferedImage getInfoBtn() {
		return infoBtn;
	}
	public static BufferedImage getInstructions() {
		return instructions;
	}
	public static BufferedImage getInvincibility() {
		return invincibility;
	}
	public static BufferedImage getPauseBtn() {
		return pauseBtn;
	}
	public static BufferedImage getContinueBtn() {
		return continueBtn;
	}
	public static BufferedImage getPlayGameBtn() {
		return playGameBtn;
	}
	public static BufferedImage getPurpleEnemyAlien() {
		return purpleEnemyAlien;
	}
	public static BufferedImage getProjectileBomb() {
		return projectileBomb;
	}
	public static BufferedImage getRedEnemyAlien() {
		return redEnemyAlien;
	}
	public static BufferedImage getRestoreShields() {
		return restoreShields;
	}
	public static BufferedImage getSharpShot() {
		return sharpShot;
	}
	public static BufferedImage getSpaceBackground3() {
		return spaceBackground3;
	}
	public static BufferedImage getSpaceBackground2() {
		return spaceBackground2;
	}
	public static BufferedImage getMiniSpaceship() {
		return miniSpaceship;
	}
	public static BufferedImage getSpaceBackground1() {
		return spaceBackground1;
	}
	public static BufferedImage getSpaceShield() {
		return spaceShield;
	}
	public static BufferedImage getSpaceShip() {
		return spaceShip;
	}
	public static BufferedImage getSpaceshipBullet() {
		return spaceshipBullet;
	}
	public static BufferedImage getHeadTitle() {
		return headTitle;
	}
	public static BufferedImage getMuteBtn() {
		return muteBtn;
	}
	public static BufferedImage getUnMuteBtn() {
		return unMuteBtn;
	}
	public static BufferedImage getSpaceShipExplosion() {
		return spaceShipExplosion;
	}
}
