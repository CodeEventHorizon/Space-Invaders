import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Main extends JPanel implements MouseListener {

	private final static JFrame jFrame = new JFrame("Space Invaders");
	private final static Main jPanel = new Main();

	/* Settings */
	private static String statsTextColor = "#FFFFFF";
	private final static int MAIN_WIDTH = 1124;
	private final static int MAIN_HEIGHT = 750;
	private final static int MARGIN = 150;
	private static BufferedImage backgroundPicture;
	private static boolean defeat = false;
	private final static String startBackgroundPicture = "space1";
	private static String currentBackgroundPicture = startBackgroundPicture; //this is here for changing backgrounds for levels
	private static int score = 0;
	private static int hearts;
	private static int timePassed = 0;
	private static int anInt = 1;
	private static int timeMs = 20 * anInt; //milliseconds
	private static Timer timer = new Timer(timeMs, null);
	private static boolean pauseGame = false;
	private static boolean showMainPanel = true;
	private static ArrayList<Integer> playGameRectangle = new ArrayList<Integer>();
	private static ArrayList<Integer> infoRectangle = new ArrayList<Integer>();
	private static ArrayList<Integer> continueOrPauseRectangle = new ArrayList<Integer>();
	private static ArrayList<Integer> muteOrUnmuteRectangle = new ArrayList<Integer>();
	private static boolean showInstructions;
	private static boolean muted = false;
	private static SoundManger soundManger = new SoundManger();

	/* Barriers/Shields */
	private static ArrayList<Shield> shields = new ArrayList<Shield>();
	private final static int numberOfShields = 4;

	/* Enemies */
	private static ArrayList<ArrayList<Enemy>> enemyAliens = new ArrayList<ArrayList<Enemy>>();
	private final static int enemyRowY = 5;
	private final static int enemyColumnX = 12;
	private static int moveDownwards = 0;
	private static ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
	private static double enemySpeed = 1 * anInt;
	private static int enemyProjectileSpeed = 8 * anInt;
	private static double enemyProjectileAppearanceProbability = 0.98;

	/* Powers */
	private static ArrayList<String> playerPowers = new ArrayList<String>();
	private static boolean hasPowers = false;

	private static int stopTimeTime = 0;
	private static int numberOfBulletProjectiles = 0;
	private static int playerSpaceshipSizeRatio = 9;
	private static int miniSpaceshipTime = 1000;
	private static int invincibilityTime = 0;

	private static boolean stopTime = false;
	private static boolean invincibility = false;
	private static boolean sharpShot = false;
	private static boolean miniSpaceship = false;

	private static int powerCurrentRow;
	private boolean powerMovingUp = true;

	private static String powerDrawString = "";

	/* Flying enemy Alien */
	private static int flyingEnemyRow = (int) (MARGIN / 1.5);
	private static int flyingEnemyCol = MARGIN;
	private static Enemy flyingEnemy;
	private static int flyingEnemyTime = 30 * 1000 / timeMs / anInt;
	private static int flyingEnemySpeed = 3 * anInt;

	/* Spaceship a.k.a Player */
	private static int playerRow = MAIN_HEIGHT - 120;
	private static int playerColumn = MARGIN;
	private static int heartsStart = 3;
	private static Player player = new Player(playerRow, playerColumn, heartsStart); //create player spaceship
	private static int movementLimit = 10;
	private static int movedBy = movementLimit;
	private static int direction = 0; // direction = 1 is right and direction = -1 is left
	private static int playerSpeed = 5 * anInt;
	private static int playerProjectileSpeed = -20 * anInt;
	private static int playerExplosionTimes = 0;
	private static ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();// list of projectiles or bullets projected by the player spaceship

	/* Movement and shooting */
	private static boolean movingRight = false;
	private static boolean movingLeft = false;
	private static boolean shooting = false;

	/* Main method */
	public static void main(String[] args) throws IOException {
		/**
		 * UIManager.getSystemLookAndFeelClassName() returns a String that specifies the platform
		 * a current user is using, for example Windows (Classic), Windows XP, GTK+ etc...
		 * then UIManager.setLookAndFeel() sets the look and feel according on what getter returned.
		 * More on: https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 * */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pictures pictures = new Pictures();
		pictures.uploadPics();
		initPowers();

		backgroundPicture = Pictures.getSpaceBackground1();
		Enemy.createEList();
		newGame();
		startTimer();
		//Creating Frame
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(jPanel);
		jPanel.setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jPanel.bingKeys();
		Main main = new Main();
		jPanel.addMouseListener(main);
	}
	/*
	* this methods starts the game & resets all the variables
	* */
	public static void newGame() {
		hasPowers = false;
		stopTime = false;
		invincibility = false;
		sharpShot = false;
		miniSpaceship = false;
		playerSpaceshipSizeRatio = 9;
		powerDrawString = "";
		stopTimeTime = 0;
		defeat = false;
		score = 0;
		hearts = heartsStart;
		timePassed = 0;
		enemyProjectileAppearanceProbability = 0.98;
		enemySpeed = 1;
		playerRow = MAIN_HEIGHT - 120;
		playerColumn = MARGIN;
		heartsStart = 3;

		player = new Player(playerRow, playerColumn, heartsStart); //create player spaceship

		movementLimit = 10;
		movedBy = movementLimit;
		direction = 0; // direction = 1 is right and direction = -1 is left
		playerSpeed = 5 * anInt;
		playerProjectileSpeed = -20 * anInt;
		playerProjectiles = new ArrayList<Projectile>();
		playerExplosionTimes = 0;

		createEnemyAliens();
		createShields();
		flyingEnemy.setUnavailable(true);
		flyingEnemy.setColE(MARGIN);
		player.setPicture(Pictures.getSpaceShip());
		player.setHearts(heartsStart);

		timer.start();
		if (playerProjectiles.size() > 0) { //
			for (int i = 0; i < playerProjectiles.size(); i++) {
				playerProjectiles.remove(i);
			}
		}
		if (enemyProjectiles.size() > 0) { //
			for (int i = 0; i < enemyProjectiles.size(); i++) {
				enemyProjectiles.remove(i);
			}
		}
		setBackgroundPicture(startBackgroundPicture, false);
		jPanel.removeAll(); //removes all the components from this container

	}

	/*
	 * following method
	 */
	private void bingKeys() {
		//Key bindings/Mapping
		//REF: https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "stop");
		this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "stop");
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shoot");
		this.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "stop shoot");
		this.getInputMap().put(KeyStroke.getKeyStroke("P"), "pause");
		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "newGame");
		this.getInputMap().put(KeyStroke.getKeyStroke("I"), "info");
		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
		this.getInputMap().put(KeyStroke.getKeyStroke("M"), "mute");
		/* To put it simply, getInputMap() part basically binds keys to a key (which is a String)
		* Value:Key, and then using these keys we tell getActionMap() to perform actions
		* Example: getInputMap() binding "SPACE" value to a "shoot" key
		* So that then using getActionMap() passing "shoot" key performs an action which makes shooting = true
		* */
		this.getActionMap().put("stop", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movingLeft = false;
				movingRight = false;
			}
		});

		this.getActionMap().put("stop shoot", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shooting = false;
			}
		});

		this.getActionMap().put("right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movingRight = true;
			}
		});

		this.getActionMap().put("left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				movingLeft = true;
			}
		});

		this.getActionMap().put("shoot", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				shooting = true;
			}
		});

		this.getActionMap().put("pause", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				pauseGame = !pauseGame;
				showInstructions = false;
				jPanel.repaint();
			}
		});
		this.getActionMap().put("newGame", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (showMainPanel) {
					showMainPanel = false;
					timer.start();
				}
				newGame();
			}
		});
		this.getActionMap().put("info", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				showInstructions = !showInstructions;
				pauseGame = showInstructions;
				jPanel.repaint();
			}
		});
		this.getActionMap().put("escape", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (showInstructions) {
					showInstructions = false;
					pauseGame = false;
					jPanel.repaint();
				}
			}
		});
		this.getActionMap().put("mute", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				muted = !muted;
			}
		});
		this.requestFocusInWindow();
		// requestFocusInWindow() gets the focus for the component on which it is called ONLY when its top-level ancestor is the focused window
	}
	/*
	* sets background picture
	* */
	public static void setBackgroundPicture(String changeToThisPicture, boolean modifiedShield) {
		currentBackgroundPicture = changeToThisPicture;
		if (currentBackgroundPicture.equals("space1")) {
			backgroundPicture = Pictures.getSpaceBackground1();
			for (int i = 0; i < shields.size(); i++) {
				shields.get(i).setImage(Pictures.getSpaceShield(), modifiedShield);
			}
			for (int row = 0; row < enemyAliens.size(); row++) {
				for (int column = 0; column < enemyAliens.get(row).size(); column++) {
					Enemy enemy = enemyAliens.get(row).get(column);
					if (enemy.getScore() == 150) {
						enemy.setPictureE(Pictures.getPurpleEnemyAlien());
					}
					if (enemy.getScore() == 100) {
						enemy.setPictureE(Pictures.getBlueEnemyAlien());
					}
					if (enemy.getScore() == 50) {
						enemy.setPictureE(Pictures.getRedEnemyAlien());
					}
				}
			}
		}
		if (currentBackgroundPicture.equals("space2")) {
			backgroundPicture = Pictures.getSpaceBackground2();
			for (int i = 0; i < shields.size(); i++) {
				shields.get(i).setImage(Pictures.getSpaceShield(), modifiedShield);
			}
			for (int row = 0; row < enemyAliens.size(); row++) {
				for (int column = 0; column < enemyAliens.get(row).size(); column++) {
					Enemy enemy = enemyAliens.get(row).get(column);
					if (enemy.getScore() == 150) {
						enemy.setPictureE(Pictures.getPurpleEnemyAlien());
					}
					if (enemy.getScore() == 100) {
						enemy.setPictureE(Pictures.getBlueEnemyAlien());
					}
					if (enemy.getScore() == 50) {
						enemy.setPictureE(Pictures.getRedEnemyAlien());
					}
				}
			}
		}
		if (currentBackgroundPicture.equals("space3")) {
			backgroundPicture = Pictures.getSpaceBackground3();
			for (int i = 0; i < shields.size(); i++) {
				shields.get(i).setImage(Pictures.getSpaceShield(), modifiedShield);
			}
			for (int row = 0; row < enemyAliens.size(); row++) {
				for (int column = 0; column < enemyAliens.get(row).size(); column++) {
					Enemy enemy = enemyAliens.get(row).get(column);
					if (enemy.getScore() == 150) {
						enemy.setPictureE(Pictures.getPurpleEnemyAlien());
					}
					if (enemy.getScore() == 100) {
						enemy.setPictureE(Pictures.getBlueEnemyAlien());
					}
					if (enemy.getScore() == 50) {
						enemy.setPictureE(Pictures.getRedEnemyAlien());
					}
				}
			}
		}
		if (currentBackgroundPicture.equals("space4")) {
			backgroundPicture = Pictures.getSpaceBackground4();
			for (int i = 0; i < shields.size(); i++) {
				shields.get(i).setImage(Pictures.getSpaceShield(), modifiedShield);
			}
			for (int row = 0; row < enemyAliens.size(); row++) {
				for (int column = 0; column < enemyAliens.get(row).size(); column++) {
					Enemy enemy = enemyAliens.get(row).get(column);
					if (enemy.getScore() == 150) {
						enemy.setPictureE(Pictures.getPurpleEnemyAlien());
					}
					if (enemy.getScore() == 100) {
						enemy.setPictureE(Pictures.getBlueEnemyAlien());
					}
					if (enemy.getScore() == 50) {
						enemy.setPictureE(Pictures.getRedEnemyAlien());
					}
				}
			}
		}
	}

	/*
	 * Following method creates rows of enemies and adds to the list for enemies
	 * To put it simply we set the vertical and horizontal spacing between enemies then
	 * we loop through rows and then columns which means we create enemies horizontally one by one and
	 * then move to the next row vertically.
	 */
	public static void createEnemyAliens() {
		hasPowers = false;
		enemyAliens = new ArrayList<ArrayList<Enemy>>();
		int columnSpacing = (MAIN_WIDTH - MARGIN * 2) / (enemyColumnX + 1); //space between columns, between enemies
		int rowSpacing = (int) ((MAIN_HEIGHT * 0.4) / (enemyRowY)); //space between rows, between enemies
		for (int row = 0; row < enemyRowY; row++) {
			ArrayList<Enemy> enemyRow = new ArrayList<Enemy>();
			for (int column = 0; column < enemyColumnX; column++) {
				if (row < 1) { // add Purple enemies first top row
					Enemy enemy = new Enemy(row * rowSpacing + MARGIN, column * columnSpacing + MARGIN, Enemy.getPurpleEnemyAlien());
					if (moveDownwards == 0)
						moveDownwards = enemy.getHeight() / 4;
					enemyRow.add(enemy);
				} else if (row < 3) { // add following 2 rows of blue enemies in the middle
					Enemy enemy = new Enemy(row * rowSpacing + MARGIN, column * columnSpacing + MARGIN, Enemy.getBlueEnemyAlien());
					enemyRow.add(enemy);
				} else { // add other bottom 2 rows of red enemies
					Enemy enemy = new Enemy(row * rowSpacing + MARGIN, column * columnSpacing + MARGIN, Enemy.getRedEnemyAlien());
					enemyRow.add(enemy);
				}

			}
			enemyAliens.add(enemyRow); // adds the whole enemy row to the list
		}
		addPower();
		flyingEnemy = new Enemy(flyingEnemyRow, flyingEnemyCol, Enemy.getFlyingEnemyAlien()); //creates flying enemy
	}
	/*
	*  Creates Barriers/Shields to protect the spaceship
	* */
	public static void createShields() {
		shields = new ArrayList<Shield>();
		int columns = numberOfShields * 2 + 1;
		int row = player.getRowPlayer() - 150;
		int widthOfBarrier = MAIN_WIDTH / columns;
		for (int i = 1; i < numberOfShields * 2; i += 2) {
			int col = widthOfBarrier * i;
			Shield shield = new Shield(row, col);
			shield.setImage(Pictures.getSpaceShield(), false);
			shield.setWidthShield(widthOfBarrier);
			shield.setHeightShield(widthOfBarrier / shield.getPicture().getWidth() * shield.getPicture().getHeight());
			shields.add(shield);
		}
	}
	/*
	 * Following method starts the timer and listens to actions
	 */
	private static void startTimer() {
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!pauseGame && !defeat) {
					tick();
					jPanel.repaint();
					timePassed++;
				}
			}
		});
		timer.start();
	}
	/*
	 * Following method executes every millisecond
	 */
	protected static void tick() {
		spaceShipMoveRight(movingRight);
		spaceShipMoveLeft(movingLeft);
		shootSpaceshipBullet(shooting);
		if (!stopTime) {
			enemiesMovement();
		}
		if (!hasPowers) {
			powerDrawString = "";
		}
		if (numberOfAvailableEnemies() > 5) {
			addPower();
		}
		spaceShipMovement();
		shootSpaceshipBullet();
		flyingEnemyMovement();
		chooseRandomEnemyEligibleForShooting();
		enemyProjectileMove();
		powerTimer();
		nextStage();
		if (playerExplosionTimes == 0) {
			defeat();
		}
	}
	/*
	* returns the amount of enemies remaining
	* */
	private static int numberOfAvailableEnemies() {
		int numberOfAvailableEnemies = 0;
		for (int row = 0; row < enemyRowY; row++) {
			for (int column = 0; column < enemyColumnX; column++) {
				if (!enemyAliens.get(row).get(column).isUnavailable()) {
					numberOfAvailableEnemies++;
				}
			}
		}
		return numberOfAvailableEnemies;
	}
	/*
	* Following method changes an enemy into a power, only existing one
	* */
	private static void addPower() {
		if (!hasPowers) {
			Enemy enemy = new Enemy(0, 0, "");
			enemy.setUnavailable(true);
			int i = 0;
			while (i == 0) {
				int randRow = (int) (enemyRowY * Math.random());
				int randCol = (int) (enemyColumnX * Math.random());
				String powerString = playerPowers.get((int) (Math.random() * playerPowers.size()));
				enemy = enemyAliens.get(randRow).get(randCol);
				int row = enemy.getRowE();
				int column = enemy.getColE();
				if (!enemy.isUnavailable()) {
					Enemy superpower = new Enemy(row, column, powerString);
					initPowerPictures(superpower);
					powerCurrentRow = row;
					hasPowers = true;
					enemyAliens.get(randRow).set(randCol, superpower);
					break;
				}

			}

		}

	}

	/*
	 * Following methods makes flying enemy appear every interval, makes it move from left to right and then removes it
	 * once reached the end
	 */
	private static void flyingEnemyMovement() {
		if (flyingEnemy.isUnavailable()) {
			if (timePassed % flyingEnemyTime == 0 && timePassed != 0) {
				flyingEnemy.setUnavailable(false);
				flyingEnemy.setColE(MARGIN);

			}
		} else {
			int col = flyingEnemy.getColE() + flyingEnemySpeed;
			flyingEnemy.setColE(col);
			if (flyingEnemy.getColE() > MAIN_WIDTH) {
				flyingEnemy.setUnavailable(true);
			}
		}

	}

	/*
	 * Following method sets the direction for the enemies
	 * when it start the direction initially is from left to right and once it reaches the end of the window
	 * it changes the direction
	 */
	public static void enemiesMovement() {
		int lastColumn = -1;
		int firstColumn = -1;
		for (int column = 0; column < enemyColumnX; column++) {
			for (int row = 0; row < enemyAliens.size(); row++) {
				if (!enemyAliens.get(row).get(column).isUnavailable()) {
					lastColumn = enemyAliens.get(row).get(column).getColE();
				}
			}
		}

		for (int column = enemyColumnX - 1; column >= 0; column--) {
			for (int row = 0; row < enemyAliens.size(); row++) {
				if (!enemyAliens.get(row).get(column).isUnavailable()) {
					firstColumn = enemyAliens.get(row).get(column).getColE();
				}
			}
		}
		int enemyWidth = enemyAliens.get(0).get(enemyColumnX - 1).getPictureE().getWidth() / 9;
		int tot = (int) (lastColumn + enemySpeed + enemyWidth);
		if (Math.signum(enemySpeed) > 0) {
			if (tot > MAIN_WIDTH) {
				enemySpeed = -1 * enemySpeed; // change direction to left
				enemiesDownwardMovement();
			}
		} else if (Math.signum(enemySpeed) < 0) {
			if (firstColumn - enemySpeed < 0) {
				enemySpeed = -1 * enemySpeed; //change direction to right
				enemiesDownwardMovement();
			}
		}
		for (int row = 0; row < enemyAliens.size(); row++) {
			for (int column = 0; column < enemyAliens.get(row).size(); column++) {
				Enemy enemy = enemyAliens.get(row).get(column);
				enemy.setColE((int) (enemy.getColE() + enemySpeed));
			}

		}
	}

	/*
	 * Following method moves the enemies down by the interval specified
	 * it is called when the direction for enemies is changed
	 */
	private static void enemiesDownwardMovement() {
		for (int row = 0; row < enemyAliens.size(); row++) {
			for (int column = 0; column < enemyAliens.get(row).size(); column++) {
				Enemy enemy = enemyAliens.get(row).get(column);
				int startingY = enemy.getRowE();
				int nextY = startingY + moveDownwards;
				enemy.setRowE(nextY);
				if (enemy.getPowerActivate() != null && !enemy.isUnavailable()) {
					powerCurrentRow = powerCurrentRow + moveDownwards;
				}
			}
		}
	}
	/*
	* executes if the spaceship is moving right
	* */
	private static void spaceShipMoveRight(boolean right) {
		if (right) {
			direction = 1;
			movedBy = 0;
		}
	}
	/*
	 * executes if the spaceship is moving left
	 * */
	private static void spaceShipMoveLeft(boolean left) {
		if (left) {
			direction = -1;
			movedBy = 0;
		}
	}
	/*
	* Following method shoots the bullet/projectile from the player spaceship
	* (that will go VERTICALLY UPWARDS (this part isn't implemented here))
	* */
	private static void shootSpaceshipBullet(boolean shoot) {
		if (shoot) {
			if (!defeat) {
				if (playerProjectiles.size() == 0) {
					Projectile bullet = new Projectile(Pictures.getSpaceshipBullet(), playerProjectileSpeed, true); //create
					int row = player.getRowPlayer() - bullet.getHeight();
					int column = player.getColPlayer() + player.getWidth() / 2 - bullet.getWidth() / 2;
					bullet.setLocation(row, column); // start coordinate to display
					if (sharpShot && numberOfBulletProjectiles <= 3) {
						numberOfBulletProjectiles++;
					}
					if (sharpShot && numberOfBulletProjectiles == 4) {
						hasPowers = false;
						sharpShot = false;
						numberOfBulletProjectiles = 0;
					}
					playerProjectiles.add(bullet);
				}
			}
		}
	}
	/*
	 * Following method allows to move player spaceship either left or right
	 */
	private static void spaceShipMovement() {
		if (movedBy < movementLimit) {
			if (direction == 1 & player.getColPlayer() + playerSpeed + player.getWidth() <= MAIN_WIDTH) { //right
				player.setColPlayer(player.getColPlayer() + playerSpeed);
				movedBy += playerSpeed;
			} else if (direction == -1 && player.getColPlayer() - playerSpeed >= 0) { //left
				player.setColPlayer(player.getColPlayer() - playerSpeed);
				movedBy += playerSpeed;
			}
		}

	}
	/*
	 * Following method allows the projectile/bullet ro move VERTICALLY UPWARDS and checks if it
	 * has collided with an enemy or a barrier/shield
	 */
	private static void shootSpaceshipBullet() {
		if (playerProjectiles.size() > 0) {
			for (int i = 0; i < playerProjectiles.size(); i++) {
				Projectile bullet = playerProjectiles.get(i);
				if (bullet.getRowP() < 0) { // above the panel
					playerProjectiles.remove(bullet);
				} else {
					bullet.move();
					// Following loop checks the collision between a bullet/projectile and a barrier/shield
					for (int b = 0; b < shields.size(); b++) {
						if (doesCollide(shields.get(b), bullet)) {
							playerProjectiles.remove(bullet);
							if (!muted) {
								soundManger.playFastInvader();
							}
						}
					}
					// Following loop checks the collision between a bullet/projectile and an enemy(purple,blue,red)
					for (int row = 0; row < enemyAliens.size(); row++) {
						for (int column = 0; column < enemyAliens.get(row).size(); column++) {
							Enemy enemy = enemyAliens.get(row).get(column);
							if (doesCollide(enemy, bullet)) {
								if (!sharpShot) {
									playerProjectiles.remove(bullet);
								}
								if (enemy.getPowerActivate() == null)
									enemy.incrementNumBlasts();
								activatePower(enemy);
								enemy.setUnavailable(true);
								if (!muted) {
									if (enemy.getPowerActivate() == null) {
										soundManger.playInvaderKilled();
									} else {
										soundManger.playHighpitch();
									}
								}
								score += (enemy.getScore() * 100) / timePassed;
								break;
							}
						}
					}
					// Following checks the collision between a bullet/projectile and a flying enemy
					if (doesCollide(flyingEnemy, bullet)) {
						flyingEnemy.setUnavailable(true);
						playerProjectiles.remove(bullet);
						score += flyingEnemy.getScore();
						if (!muted) {
							soundManger.playLowpitch();
						}
					}
				}
			}
		}
	}
	/*
	 * setting powers: restore shields, heart, mini spaceship,
	 * sharp shot, stop time, invincibility
	 */
	private static void initPowers() {
		playerPowers.add("restoreShields");
		playerPowers.add("heart");
		playerPowers.add("miniSpaceship");
		playerPowers.add("sharpShot");
		playerPowers.add("stopTime");
		playerPowers.add("invincibility");
	}
	/*
	* setting images for powers
	* */
	private static void initPowerPictures(Enemy power) {
		if (power.getPowerActivate().equals("restoreShields")) {
			power.setPictureE(Pictures.getRestoreShields());
		} else if (power.getPowerActivate().equals("heart")) {
			power.setPictureE(Pictures.getHeart());
		} else if (power.getPowerActivate().equals("miniSpaceship")) {
			power.setPictureE(Pictures.getMiniSpaceship());
		} else if (power.getPowerActivate().equals("stopTime")) {
			power.setPictureE(Pictures.getStopTime());
		} else if (power.getPowerActivate().equals("invincibility")) {
			power.setPictureE(Pictures.getInvincibility());
		} else if (power.getPowerActivate().equals("sharpShot")) {
			power.setPictureE(Pictures.getSharpShot());
		}
	}
	/*
	* Following method activates power that was passed in after execution
	* */
	private static void activatePower(Enemy power) {
		if (power.getPowerActivate() != null) {
			if (power.getPowerActivate().equals("restoreShields")) {
				setBackgroundPicture(currentBackgroundPicture, false);
				hasPowers = false;
			}
			if (power.getPowerActivate().equals("heart")) {
				int lives = player.getHearts() + 1;
				player.setHearts(lives);
				hearts = player.getHearts();
				hasPowers = false;
			}
			if (power.getPowerActivate().equals("miniSpaceship")) {
				miniSpaceship = true;
				miniSpaceshipTime = 0;
				playerSpaceshipSizeRatio = 15;
			}
			if (power.getPowerActivate().equals("stopTime")) {
				stopTime = true;
			}
			if (power.getPowerActivate().equals("invincibility")) {
				invincibility = true;
			}
			if (power.getPowerActivate().equals("sharpShot")) {
				sharpShot = true;
			}
		}
	}
	/*
	* Following method manipulates powers that last a specific amount of time
	* basically sets timer for these powers
	* */
	public static void powerTimer() {
		// Mini Spaceship
		if (miniSpaceship) {
			miniSpaceshipTime++;
			if (miniSpaceshipTime >= 10 * 1000 / 20) {
				playerSpaceshipSizeRatio = 9;
				miniSpaceshipTime = 0;
				hasPowers = false;
				miniSpaceship = false;
			} else {
				int timing = (int) (10 - miniSpaceshipTime * 0.02);
				powerDrawString = "Mini spaceship for " + timing + " seconds";
			}
		}
		// Stop Time
		if (stopTime) {
			stopTimeTime++;
			if (stopTimeTime >= 10 * 1000 / 20) {
				stopTime = false;
				stopTimeTime = 0;
				hasPowers = false;
			} else {
				int timing = (int) (10 - stopTimeTime * 0.02);
				powerDrawString = "Time stopped for " + timing + " seconds";
			}
		}
		// Invincibility for barriers
		if (invincibility) {
			invincibilityTime++;
			if (invincibilityTime >= 10 * 1000 / 20) {
				invincibility = false;
				invincibilityTime = 0;
				hasPowers = false;
			} else {
				int timing = (int) (10 - invincibilityTime * 0.02);
				powerDrawString = "Barriers will be invincible for " + timing + " seconds";
			}

		}
		// Sharp Shot
		if (sharpShot) {
			int shooting = 4 - numberOfBulletProjectiles;
			powerDrawString = (shooting - 1) + " Sharpeners left";
			if (shooting == 1) {
				powerDrawString = "";
			}
			if (shooting == 0) {
				sharpShot = false;
			}
		}
		if (!hasPowers) {
			powerDrawString = "";
		}
	}
	/*
	* Collision Detection between Object and projectiles/bullets/bombs...
	* */
	private static boolean doesCollide(Object object, Projectile projectile) {
		if (object instanceof Shield) {
			Shield shield = (Shield) object;
			if (!projectile.isSpaceShipProjectile()) { //if the projectile is shot by an enemy
				for (int row = projectile.getRowP(); row < projectile.getRowP() + projectile.getHeight() + enemySpeed + 1; row++) {
					for (int column = projectile.getColP(); column < projectile.getColP() + projectile.getWidth(); column++) {
						if (row >= shield.getRowShield()
								&& row < shield.getRowShield() + shield.getHeight()
								&& column >= shield.getColShield()
								&& column < shield.getColShield() + shield.getWidth()) {
							int rY = (row - shield.getRowShield());
							int cX = (column - shield.getColShield());
							boolean invisible = shield.getPicture().getRGB(cX, rY) == 0; // projectile disappears
							if (!invisible && !invincibility) {  // here projectile is shot at a barrier/shield and if
								//we don't have invincibility power then it "deforms", removes
								shield.setDamagedShieldX(cX); // coordinates where it got damaged and that part is set to invisible
								shield.setDamagedShieldY(rY);
								shield.setDamagedShieldWidth(shield.getWidth() / 10);
								shield.changePicture(projectile.isSpaceShipProjectile());
								return true;
							}
							if (invincibility) { //here the barrier doesn't deform, unless shot by a player (well it isn't shot by a player in a first play)
								return true;
							}
						}
					}
				}
			} else { //shot by a player
				for (int row = projectile.getRowP() + projectile.getHeight() + playerSpeed + 1; row >= projectile
						.getRowP(); row--) {
					for (int column = projectile.getColP(); column < projectile.getColP() + projectile.getWidth(); column++) {
						if (row >= shield.getRowShield()
								&& row < shield.getRowShield() + shield.getHeight()
								&& column >= shield.getColShield()
								&& column < shield.getColShield() + shield.getWidth()) {
							int rY = (row - shield.getRowShield());
							int cX = (column - shield.getColShield());
							boolean invisible = shield.getPicture().getRGB(cX, rY) == 0;
							if (!invisible) {
								shield.setDamagedShieldX(cX);
								shield.setDamagedShieldY(rY);
								shield.setDamagedShieldWidth(shield.getWidth() / 7);
								shield.changePicture(projectile.isSpaceShipProjectile());
								return true;
							}
							//here we could say if invincibility return true, but it looks weird, i think
						}
					}
				}
			}
		} else if (object instanceof Enemy) { //if player spaceship shot at an enemy
			Enemy enemy = (Enemy) object;
			if (projectile.getRowP() > enemy.getRowE()
					&& projectile.getRowP() < enemy.getRowE() + enemy.getHeight() / 2
					&& projectile.getColP() > enemy.getColE() + 10
					&& projectile.getColP() < enemy.getColE() + enemy.getWidth() - 10
					&& !enemy.isUnavailable()) {
				return true;
			}
		}
		else if (object instanceof Player) { //if enemy shot at a player spaceship
			if (projectile.getRowP() >= player.getRowPlayer()
					&& projectile.getRowP() <= player.getRowPlayer() + player.getHeight()
					&& projectile.getColP() + projectile.getWidth() >= player.getColPlayer()
					&& projectile.getColP() <= player.getColPlayer() + player.getWidth()) {
				return true;
			}
		}
		return false; //if none of the above then it didn't collide with anything
	}
	/*
	* Following method basically checks bottom row of each column for the enemy which is going to shoot the projectile.
	* */
	private static ArrayList<Enemy> checkEnemiesEligibleForShooting() {
		ArrayList<Enemy> enemiesEligibleForShooting = new ArrayList<Enemy>();
		for (int column = enemyColumnX - 1; column >= 0; column--) {
			for (int row = enemyRowY - 1; row >= 0; row--) {
				if (!enemyAliens.get(row).get(column).isUnavailable()) {
					enemiesEligibleForShooting.add(enemyAliens.get(row).get(column));
					break;
				}
			}
		}
		return enemiesEligibleForShooting;
	}
	/*
	* Following method basically chooses random enemy from the Arraylist that was implemented in
	* the previous method and that random enemy is the selected to shoot a projectile
	* toward player spaceship
	* */
	public static void chooseRandomEnemyEligibleForShooting() {
		ArrayList<Enemy> enemiesEligibleForShooting = checkEnemiesEligibleForShooting();
		if (enemiesEligibleForShooting.size() != 0) {
			int random = (int) (enemiesEligibleForShooting.size() * Math.random());
			Enemy enemy = enemiesEligibleForShooting.get(random);
			if (enemy.getPowerActivate() == null) {
				Projectile bomb = new Projectile(enemy.getProjectile(), enemyProjectileSpeed, false);
				bomb.setColP(enemy.getColE() + enemy.getWidth() / 2 - bomb.getWidth() / 2);
				bomb.setRowP(enemy.getRowE() + enemy.getHeight() / 2);
				double randomAdd = Math.random() * 500;
				if (enemyProjectiles.size() == 0 && randomAdd > 500 * enemyProjectileAppearanceProbability) {
					enemyProjectiles.add(bomb);
				}
			}
		}
	}
	/*
	* Following methods moves projectiles down or removes it from the arraylist if it exceeds the height
	* */
	private static void enemyProjectileMove() {
		if (enemyProjectiles.size() > 0) {
			for (int i = 0; i < enemyProjectiles.size(); i++) {
				Projectile bomb = enemyProjectiles.get(i);
				// checks collision between enemy projectiles and barriers/shields
				for (int b = 0; b < shields.size(); b++) {
					if (doesCollide(shields.get(b), bomb)) {
						enemyProjectiles.remove(bomb);
					}
				}
				// moves down
				if (bomb.getRowP() > MAIN_HEIGHT) {
					enemyProjectiles.remove(bomb);
				} else {
					bomb.move();
				}
			}
		}
		// Following loop checks if the enemy projectile collided with the player spaceship
		for (int b = 0; b < enemyProjectiles.size(); b++) {
			Projectile bomb = enemyProjectiles.get(b);
			if (doesCollide(player, bomb)) {
				player.removeHeart();
				playerExplosionTimes++;
				if (!muted)
					soundManger.playExplosion();
				hearts = player.getHearts();
				enemyProjectiles.remove(bomb);
			}
		}
	}
	/*
	* Following method executes when all the enemies are killed which means we progress to the
	* next level and the settings are updated which means enemies move faster
	* */
	public static void nextStage() {
		boolean allEnemiesKilled = true;
		for (int row = 0; row < enemyAliens.size(); row++) {
			for (int column = 0; column < enemyAliens.get(row).size(); column++) {
				if (!enemyAliens.get(row).get(column).isUnavailable()) {
					allEnemiesKilled = false;
				}
			}
		}
		if (allEnemiesKilled) { //next level
			score += 100; // +100 for killing all the enemies
			enemyAliens = new ArrayList<ArrayList<Enemy>>(); //new enemies array
			hasPowers = false; //stop time dependant powers
			stopTime = false;
			invincibility = false;
			sharpShot = false;
			miniSpaceship = false;
			playerSpaceshipSizeRatio = 9;
			powerDrawString = "";
			createEnemyAliens(); //new enemies created
			nextBackgroundPicture(); //next background picture
			if (enemySpeed < 0 && enemySpeed > -4) {
				enemySpeed--;
				moveDownwards /= 1.2;
			} else if (enemySpeed < 4) {
				enemySpeed++;
				moveDownwards /= 1.1;
			}
			playerSpeed += 3;
		}
	}
	/*
	* Following method sets the next background picture (there are 4 in total)
	* */
	private static void nextBackgroundPicture() {
		if (currentBackgroundPicture.equals("space1")) {
			setBackgroundPicture("space2", true);
		} else if (currentBackgroundPicture.equals("space2")) {
			setBackgroundPicture("space3", true);
		} else if (currentBackgroundPicture.equals("space3")) {
			setBackgroundPicture("space4", true);
		} else if (currentBackgroundPicture.equals("space4")) {
			setBackgroundPicture("space1", true);
		}

	}
	/*
	* Following method checks if the game is lost
	* 2 scenarios:
	* 	1) player spaceship has no more hearts
	* 	2) enemies are below player spaceship
	* */
	public static void defeat() {
		boolean belowPlayer = false;
		for (int row = 0; row < enemyAliens.size(); row++) { //this loop checks if the enemies are below player spaceship
			for (int column = 0; column < enemyAliens.get(row).size(); column++) {
				if (!enemyAliens.get(row).get(column).isUnavailable() && enemyAliens.get(row).get(column).getRowE() > player.getRowPlayer()) {
					belowPlayer = true;
				}
			}
		} //if player has no more hearts or enemies below according to above loop, the game is lost
		if (player.isPlayerDead() || belowPlayer) {
			defeat = true;
		}
	}
	/*
	* Following is a executed when we call setVisible(true);
	* and renders components on a JPanel
	* */
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundPicture, 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);
		displayMainBackgroundPanel(g);
		if (!defeat && timePassed != 0 && !showMainPanel) {
			displayBtns(g);
			displayShields(g);
			displayEnemies(g);
			displaySpaceShip(g);
			displayProjectiles(g);
			displayFlyingEnemy(g);
			displayStats(g);
			if (showInstructions) {
				displayInstructions(g);
			}
		}
		else if (defeat) { //if game is lost the background picture is set to game over
			displayDefeatPanel(g); // (to exit from this view click anywhere on a screen or press enter)
		}
	}
	/*
	* Following method displays 3 buttons at the bottom-right corner
	* */
	private void displayBtns(Graphics g) {
		//Info button
		int infoWidth = Pictures.getInfoBtn().getWidth() / 25;
		int infoHeight = Pictures.getInfoBtn().getHeight() / 25;
		int infoX = MAIN_WIDTH - MARGIN / 2 - infoWidth;
		int infoY = MAIN_HEIGHT - MARGIN / 2;
		g.drawImage(Pictures.getInfoBtn(), infoX, infoY, infoWidth, infoHeight, null);
		infoRectangle.removeAll(infoRectangle);
		infoRectangle.add(infoX);
		infoRectangle.add(infoX + infoWidth);
		infoRectangle.add(infoY);
		infoRectangle.add(infoX + infoHeight);
		// Continue/pause game button
		int cpX = (int) (MAIN_WIDTH - MARGIN / 2) - infoWidth * 2 - 15 * 1;
		BufferedImage pauseOrContinue;
		if (!pauseGame) {
			pauseOrContinue = Pictures.getPauseBtn();
		} else {
			pauseOrContinue = Pictures.getContinueBtn();
		}
		g.drawImage(pauseOrContinue, (int) cpX, infoY, infoWidth, infoHeight, null);
		continueOrPauseRectangle.add(cpX);
		continueOrPauseRectangle.add(cpX + infoWidth);
		continueOrPauseRectangle.add(infoY);
		continueOrPauseRectangle.add(cpX + infoHeight);
		// mute/un-mute button
		int muteX = (int) (MAIN_WIDTH - MARGIN / 2) - infoWidth * 3 - 15 * 2;
		BufferedImage muteOrUnmute;
		if (!muted) {
			muteOrUnmute = Pictures.getUnMuteBtn();
		} else {
			muteOrUnmute = Pictures.getMuteBtn();
		}
		g.drawImage(muteOrUnmute, (int) muteX, infoY, infoWidth, infoHeight, null);
		muteOrUnmuteRectangle.add(muteX);
		muteOrUnmuteRectangle.add(cpX + infoWidth);
		muteOrUnmuteRectangle.add(infoY);
		muteOrUnmuteRectangle.add(cpX + infoHeight);
	}
	/*
	* displays game over picture/panel with the scores
	* */
	private void displayDefeatPanel(Graphics g) {
		g.drawImage(Pictures.getDefeatWindow(), 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);
		Font font = new Font("Helvetica", Font.PLAIN, 50);
		g.setColor(Color.decode("#FFFFFF"));
		g.setFont(font);
		g.drawString("Score: " + score + "", (int) (MAIN_WIDTH * 0.1), (int) (MAIN_HEIGHT * 0.1));
		g.drawString("Time Passed: " + (timePassed) / timeMs / 2 + "s", (int) (MAIN_WIDTH * 0.1), (int) (MAIN_HEIGHT * 0.2));
		jPanel.repaint();
	}
	/*
	* Displays top 3 important trackers: time, hearts and score
	* */
	private void displayStats(Graphics g) {
		// Display Hearts, in the top-left corner of the screen
		Font font = new Font("Helvetica", Font.PLAIN, 22);
		g.setColor(Color.decode(statsTextColor));
		g.setFont(font);
		g.drawString("Hearts: " + hearts, MARGIN / 3, MARGIN / 3);

		// Display Score, in the top-right corner of the screen
		String string1 = "Score: " + score;
		g.drawString(string1, MAIN_WIDTH - MARGIN / 3 - 15 * string1.length(), MARGIN / 3);

		// Display Time passed in the top-center of the screen
		String string2 = "Time Passed: " + (timePassed) / timeMs / 2;
		g.drawString(string2, MAIN_WIDTH / 2 - string2.length() * 15 / 2, MARGIN / 3);
		// Display active power under Time passed
		g.drawString(powerDrawString, MAIN_WIDTH / 2 - powerDrawString.length() * 15 / 2, (int) (MARGIN / 1.5));
	}
	/*
	* Following method generates a flying enemy that goes from left to right
	* */
	private void displayFlyingEnemy(Graphics g) {
		if (timePassed != 0 && !flyingEnemy.isUnavailable()) {
			flyingEnemy.setWidthE(flyingEnemy.getPictureE().getWidth() / 7);
			flyingEnemy.setHeightE(flyingEnemy.getPictureE().getHeight() / 7);
			flyingEnemy.paintComponent(g);
		}
	}
	/*
	* displays projectiles/bullets/bombs
	* */
	private void displayProjectiles(Graphics g) {
		// Player Spaceship projectiles/bullets
		if (playerProjectiles.size() > 0) {
			for (int i = 0; i < playerProjectiles.size(); i++) {
				Projectile bullet = playerProjectiles.get(i);
				bullet.paintComponent(g);
			}
		}
		// Enemy projectiles/bombs
		if (enemyProjectiles.size() > 0) {
			for (int i = 0; i < enemyProjectiles.size(); i++) {
				Projectile bomb = enemyProjectiles.get(i);
				bomb.paintComponent(g);
			}
		}
	}
	/*
	* Following method displays player spaceship
	* */
	private void displaySpaceShip(Graphics g) {
		if (playerExplosionTimes > 0 && playerExplosionTimes < 15) {
			player.setPicture(Pictures.getSpaceShipExplosion());
			BufferedImage picture = Pictures.getSpaceShipExplosion();
			int width = player.getPicture().getWidth() / playerSpaceshipSizeRatio * (playerExplosionTimes / 3 + 1);
			int height = player.getPicture().getHeight() / playerSpaceshipSizeRatio * (playerExplosionTimes / 3 + 1);
			g.drawImage(picture, player.getColPlayer() - (width - player.getWidth()) / 2,
					player.getRowPlayer() - (height - player.getHeight()) / 2, width, height, null);
			playerExplosionTimes++;
		} else {
			player.setPicture(Pictures.getSpaceShip());
			playerExplosionTimes = 0;
			player.setWidthPlayer(player.getPicture().getWidth() / playerSpaceshipSizeRatio);
			player.setHeightPlayer(player.getPicture().getHeight() / playerSpaceshipSizeRatio);
			player.paintComponent(g);
		}
	}
	/*
	* Following method displays enemies and powers
	* */
	private void displayEnemies(Graphics g) {
		for (int row = 0; row < enemyAliens.size(); row++) {
			for (int column = 0; column < enemyAliens.get(row).size(); column++) {
				Enemy enemy = enemyAliens.get(row).get(column);
				if (!enemy.isUnavailable()) {
					if (enemy.getPowerActivate() != null) {
						if (!pauseGame) {
							if (enemy.getRowE() > powerCurrentRow + 12) {
								powerMovingUp = true;
							}
							if (enemy.getRowE() > powerCurrentRow - 12 && powerMovingUp) {
								enemy.setRowE(enemy.getRowE() - 1);
							} else {
								powerMovingUp = false;
								enemy.setRowE(enemy.getRowE() + 1);
							}
						}
						enemy.setWidthE(enemy.getPictureE().getWidth() / 8);
						enemy.setHeightE(enemy.getPictureE().getHeight() / 8);
						enemy.paintComponent(g);
					}  else {
						enemy.setWidthE(enemy.getPictureE().getWidth() / 8);
						enemy.setHeightE(enemy.getPictureE().getHeight() / 8);
						enemy.paintComponent(g);
					}
				}
				else if (enemy.getNumberOfExplosions() > 0 && enemy.getNumberOfExplosions() < 5) {
					enemy.setPictureE(Pictures.getEnemyExplosion());
					enemy.setWidthE(enemy.getPictureE().getWidth() / 8);
					enemy.setHeightE(enemy.getPictureE().getHeight() / 8);
					enemy.paintComponent(g);
					enemy.incrementNumBlasts();
				}
			}
		}
	}
	/*
	* Following methods displays Barriers/Shields
	* */
	private void displayShields(Graphics g) {
		for (Shield shield : shields) {
			shield.setWidthShield((int) (shield.getPicture().getWidth()));
			shield.setHeightShield((int) (shield.getPicture().getHeight()));
			shield.paintComponent(g);
		}
	}
	/*
	* Following method displays Main/Home panel
	* */
	private void displayMainBackgroundPanel(Graphics g) {
		if (showMainPanel) {
			timer.stop();
			g.drawImage(Pictures.getMainBackground(), 0, 0, MAIN_WIDTH, MAIN_HEIGHT, null);

			BufferedImage headTitle = Pictures.getHeadTitle();
			g.drawImage(headTitle, MAIN_WIDTH / 2 - headTitle.getWidth() / 2, (int) (MARGIN / 2), headTitle.getWidth(),
					headTitle.getHeight(), null);
			BufferedImage playGameBtn = Pictures.getPlayGameBtn();
			g.drawImage(playGameBtn, MAIN_WIDTH / 2 - playGameBtn.getWidth() / 2, (int) (MAIN_HEIGHT - 2.25 * MARGIN),
					playGameBtn.getWidth(), playGameBtn.getHeight(), null);
			playGameRectangle.add(MAIN_WIDTH / 2 - playGameBtn.getWidth() / 2); // minimum X position
			playGameRectangle.add(playGameRectangle.get(0) + playGameBtn.getWidth()); // maximum X position
			playGameRectangle.add((int) (MAIN_HEIGHT - 2.25 * MARGIN)); // minimum Y position
			playGameRectangle.add(playGameRectangle.get(2) + playGameBtn.getHeight()); // maximum Y position
			// display information button
			int infoX = MAIN_WIDTH - Pictures.getInfoBtn().getWidth() / 20 - 10;
			int infoY = 10;
			int infoWidth = Pictures.getInfoBtn().getWidth() / 20;
			int infoHeight = Pictures.getInfoBtn().getHeight() / 20;
			g.drawImage(Pictures.getInfoBtn(), infoX, infoY, infoWidth, infoHeight, null);
			infoRectangle.add(infoX);
			infoRectangle.add(infoX + infoWidth);
			infoRectangle.add(infoY);
			infoRectangle.add(infoX + infoHeight);
			if (showInstructions) {
				displayInstructions(g);
			}
		}
	}
	/*
	* Following method displays the info button
	* */
	private void displayInstructions(Graphics g) {
		if (showInstructions) {
			pauseGame = true;
			jPanel.repaint();
			int width = (int) (Pictures.getInstructions().getWidth() / 3.5);
			int height = (int) (Pictures.getInstructions().getHeight() / 3.5);
			g.drawImage(Pictures.getInstructions(), MAIN_WIDTH / 2 - width / 2, MAIN_HEIGHT / 2 - height / 2, width, height, null);
		}
	}
	/*
	* Following method checks where the mouse have been clicked
	* and performs actions according to that
	* */
	public void mouseClicked(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		if (showMainPanel) {
			if (checkInRectangle(mX, mY, playGameRectangle)) {
				showMainPanel = false;
				timer.start();
				pauseGame = false;
			} else if (checkInRectangle(mX, mY, infoRectangle)) {
				showInstructions = !showInstructions;
				jPanel.repaint();
			}
		} else if (defeat) {
			newGame();
		}
		else if (!defeat && !showMainPanel) {
			if (checkInRectangle(mX, mY, infoRectangle)) {
				showInstructions = !showInstructions;
				pauseGame = showInstructions;
				jPanel.repaint();
			} else if (checkInRectangle(mX, mY, continueOrPauseRectangle)) {
				pauseGame = !pauseGame;
				showInstructions = false;
				jPanel.repaint();
			} else if (checkInRectangle(mX, mY, muteOrUnmuteRectangle)) {
				muted = !muted;
				jPanel.repaint();
			}
		}
	}
	/*
	* returns true or false, depending on if the coordinate is in a rectangle
	* */
	private boolean checkInRectangle(int x, int y, ArrayList<Integer> rectangle) {
		if (x >= rectangle.get(0) && x <= rectangle.get(1) && y >= rectangle.get(2) && y <= rectangle.get(3)) {
			return true;
		}
		return false;
	}

	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}