package game.ui.gui;

import game.logic.Game;
import game.objects.Dragon;
import game.ui.GameInterface;
import game.ui.GameOptions;
import game.ui.GameOutput;
import game.ui.MazeInput;
import game.ui.MazePictures;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class GUInterface constructs the main GUI to interact with the user. It implements two main frames,
 * one for asking the initial game options and the other do display the game.
 */
public class GUInterface extends GameInterface implements KeyListener {

	/** The side size of the sprites currently being used. */
	public static final int SPRITESIZE = 32;

	/** The maximum window size. */
	public static Dimension MAXIMUM_WINDOW_SIZE = new Dimension(500,500);

	/** The maze images to be used. */
	private final MazePictures mazePictures = new MazePictures();
	
	/** The frame that will be displayed. */
	private JFrame frame;
	
	/** The maze panel integrated in the main game gui. */
	private MazePanel mazePanel;
	
	/** The informations panel integrated in the main game gui. */
	private InfoPanel infoPanel;
	
	/** The rows text field to be used in the options dialog. */
	private JTextField rowsTextField;
	
	/** The columns text field to be used in the options dialog. */
	private JTextField columnsTextField;

	/** The currently loaded save game file, if any. */
	private File loadedFile;
	
	/** Indicates if a loaded save file is being used. */
	private boolean useLoadedFile = false;

	/** Indicates whether the user wants to use the predefined maze or make a new one */
	private boolean usePredefinedMaze = true;
	
	private boolean useMultipleDragons = true;
	
	/** The dragon type specified by the user. */
	private int dragonType = Dragon.SLEEPING;
	
	private int maze_rows;
	
	private int maze_columns;
	
	/** This variable indicates whether to continue the game or not */
	private boolean goOn = true;

	/** The options specified by the user */
	private GameOptions options = new GameOptions(false);

	private JMenuBar menuBar;

	/**
	 * Initializes the graphical interface, setting up the needed components.
	 */
	private void startInterface() {
		frame = new JFrame("Maze"); //$NON-NLS-1$

		Dimension mazePanelDimension = new Dimension(game.getMaze().getColumns() * GUInterface.SPRITESIZE,
				game.getMaze().getRows() * GUInterface.SPRITESIZE);

		Dimension infoPanelDimension = new Dimension(game.getMaze().getColumns() * GUInterface.SPRITESIZE,
				100);


		Container c = frame.getContentPane();
		c.setLayout(new GridBagLayout());
		c.addKeyListener(this);
		c.setFocusable(true);

		mazePanel = new MazePanel(game, mazePictures, mazePanelDimension, MAXIMUM_WINDOW_SIZE);

		infoPanel = new InfoPanel(infoPanelDimension, MAXIMUM_WINDOW_SIZE);

		createMenuBar();

		GridBagConstraints infoPanel_constraints = new GridBagConstraints();

		infoPanel_constraints.weightx = 1;
		infoPanel_constraints.weighty = 0;
		infoPanel_constraints.gridx = 0;
		infoPanel_constraints.gridy = 0;
		infoPanel_constraints.fill = GridBagConstraints.HORIZONTAL;

		c.add(infoPanel, infoPanel_constraints);

		GridBagConstraints mazePanel_constraints = new GridBagConstraints();

		mazePanel_constraints.weightx = 1;
		mazePanel_constraints.weighty = 1;
		mazePanel_constraints.gridx = 0;
		mazePanel_constraints.gridy = 1;
		mazePanel_constraints.fill = GridBagConstraints.BOTH;

		c.add(mazePanel, mazePanel_constraints);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	/**
	 * Creates the menu bar.
	 */
	private void createMenuBar() {
		menuBar =  new JMenuBar();

		JMenu fileMenu = new JMenu(Messages.getString("GUInterface.1")); //$NON-NLS-1$
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.2")); //$NON-NLS-1$
		menuBar.add(fileMenu);

		JMenuItem saveGameMenuItem = new JMenuItem(Messages.getString("GUInterface.3"), KeyEvent.VK_S); //$NON-NLS-1$
		saveGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.4")); //$NON-NLS-1$
		fileMenu.add(saveGameMenuItem);

		JMenuItem loadGameMenuItem = new JMenuItem(Messages.getString("GUInterface.5"), KeyEvent.VK_L); //$NON-NLS-1$
		loadGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.6")); //$NON-NLS-1$
		fileMenu.add(loadGameMenuItem);

		loadGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter(GameOutput.SAVE_EXTENSION_DESCRIPTION, GameOutput.SAVE_EXTENSION_TYPE);
				fileChooser.setFileFilter(filter);

				boolean fileNotChosenOrCancelled = true;

				while(fileNotChosenOrCancelled) {
					if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

						File file = fileChooser.getSelectedFile();

						String filePath = file.getAbsolutePath();

						if(filePath.endsWith(GameOutput.SAVE_EXTENSION) && file.exists()) {
							loadedFile = fileChooser.getSelectedFile();
							// load from file
							game = GameOutput.load(loadedFile);
							useLoadedFile = true;
							frame.dispose();
							startInterface();
							fileNotChosenOrCancelled = false;
						}
						else
							JOptionPane.showMessageDialog(frame,
									Messages.getString("GUInterface.7"), //$NON-NLS-1$
									Messages.getString("GUInterface.8"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);

					}
					else
						fileNotChosenOrCancelled = false;
				}
			}
		});

		saveGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameOutput.showSaveGameDialog(game);
			}
		});

		JMenu gameMenu = new JMenu(Messages.getString("GUInterface.9")); //$NON-NLS-1$
		gameMenu.setMnemonic(KeyEvent.VK_G);
		gameMenu.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.10")); //$NON-NLS-1$
		menuBar.add(gameMenu);

		JMenuItem optionsGameMenuItem = new JMenuItem(Messages.getString("GUInterface.11"), //$NON-NLS-1$
				KeyEvent.VK_O);
		optionsGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.12")); //$NON-NLS-1$
		gameMenu.add(optionsGameMenuItem);

		JMenuItem changeKeysItem = new JMenuItem(Messages.getString("GUInterface.13"), KeyEvent.VK_C); //$NON-NLS-1$
		changeKeysItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.14")); //$NON-NLS-1$
		gameMenu.add(changeKeysItem);

		changeKeysItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KeysPanel(frame, Messages.getString("GUInterface.15")); //$NON-NLS-1$
			}
		});

		optionsGameMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(
						frame,
						Messages.getString("GUInterface.16"), //$NON-NLS-1$
						Messages.getString("GUInterface.17"), //$NON-NLS-1$
						JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {

					int predefMazeOption = JOptionPane.showConfirmDialog(
							frame,
							Messages.getString("GUInterface.18"), //$NON-NLS-1$
							Messages.getString("GUInterface.19"), //$NON-NLS-1$
							JOptionPane.YES_NO_OPTION);

					if(predefMazeOption == JOptionPane.YES_OPTION) {
						usePredefinedMaze = false;
						String rows, columns;

						do {
							rows = JOptionPane.showInputDialog(frame, Messages.getString("GUInterface.20")); //$NON-NLS-1$
						}
						while(!MazeInput.isInteger(rows) && rows != null);

						if(rows == null)
							return;

						do {
							columns = JOptionPane.showInputDialog(frame, Messages.getString("GUInterface.21")); //$NON-NLS-1$
						}
						while(!MazeInput.isInteger(columns) && columns != null);

						if(columns == null)
							return;

						if(Integer.parseInt(rows)  < 6 || Integer.parseInt(columns) < 6
								|| Integer.parseInt(columns) > 500 || Integer.parseInt(columns) > 500) {
							JOptionPane.showMessageDialog(frame,
									Messages.getString("GUInterface.22"), //$NON-NLS-1$
									Messages.getString("GUInterface.23"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);
						}
						else {
							maze_rows = Integer.parseInt(rows);
							maze_columns = Integer.parseInt(columns);
						}
					}
					else
						usePredefinedMaze = true;

					String[] possibilities = {Messages.getString("GUInterface.24"), Messages.getString("GUInterface.25"), Messages.getString("GUInterface.26")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					String dragonOption = (String)JOptionPane.showInputDialog(
							frame,
							Messages.getString("GUInterface.27"), //$NON-NLS-1$
							Messages.getString("GUInterface.28"), //$NON-NLS-1$
							JOptionPane.QUESTION_MESSAGE,
							null,
							possibilities,
							possibilities[0]);

					if(dragonOption == null)
						return;
					if(dragonOption.equals( Messages.getString("GUInterface.29") )) //$NON-NLS-1$
						dragonType = Dragon.SLEEPING;
					else if(dragonOption.equals( Messages.getString("GUInterface.30") )) //$NON-NLS-1$
						dragonType = Dragon.NORMAL;
					else
						dragonType = Dragon.STATIC;

					int multipleDragonsOption = JOptionPane.showConfirmDialog(
							frame,
							Messages.getString("GUInterface.31"), //$NON-NLS-1$
							Messages.getString("GUInterface.32"), //$NON-NLS-1$
							JOptionPane.YES_NO_OPTION);

					if(multipleDragonsOption == JOptionPane.YES_OPTION)
						useMultipleDragons = true;
					else
						useMultipleDragons = false;

					useLoadedFile = false;
					updateOptions();

					JOptionPane.showMessageDialog(frame,
							Messages.getString("GUInterface.33"), //$NON-NLS-1$
							Messages.getString("GUInterface.34"), //$NON-NLS-1$
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		JMenuItem mazeEditorGameMenuItem = new JMenuItem(Messages.getString("GUInterface.35"), //$NON-NLS-1$
				KeyEvent.VK_M);
		mazeEditorGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.36")); //$NON-NLS-1$
		gameMenu.add(mazeEditorGameMenuItem);

		mazeEditorGameMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new MazeEditorPanel(frame, game, mazePictures);
			}
		});

		JMenuItem restartGameMenuItem = new JMenuItem(Messages.getString("GUInterface.37"), //$NON-NLS-1$
				KeyEvent.VK_R);
		restartGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.38")); //$NON-NLS-1$
		gameMenu.add(restartGameMenuItem);

		restartGameMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(
						frame,
						Messages.getString("GUInterface.39"), //$NON-NLS-1$
						Messages.getString("GUInterface.40"), //$NON-NLS-1$
						JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					restartGame();
					return;
				}
			}
		});

		JMenuItem exitGameMenuItem = new JMenuItem(Messages.getString("GUInterface.41"), //$NON-NLS-1$
				KeyEvent.VK_E);
		exitGameMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.42")); //$NON-NLS-1$
		gameMenu.add(exitGameMenuItem);

		exitGameMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(
						frame,
						Messages.getString("GUInterface.43"), //$NON-NLS-1$
						Messages.getString("GUInterface.44"), //$NON-NLS-1$
						JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});


		JMenu helpMenu = new JMenu(Messages.getString("GUInterface.45")); //$NON-NLS-1$
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.46")); //$NON-NLS-1$
		menuBar.add(helpMenu);

		JMenuItem keysHelpMenuItem = new JMenuItem(Messages.getString("GUInterface.47"), KeyEvent.VK_K); //$NON-NLS-1$
		keysHelpMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.48")); //$NON-NLS-1$
		helpMenu.add(keysHelpMenuItem);

		keysHelpMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						Messages.getString("GUInterface.49") + //$NON-NLS-1$
								Messages.getString("GUInterface.50") + //$NON-NLS-1$
								Messages.getString("GUInterface.51"), //$NON-NLS-1$
								Messages.getString("GUInterface.52"), //$NON-NLS-1$
								JOptionPane.PLAIN_MESSAGE);
			}
		});

		JMenuItem infoHelpMenuItem = new JMenuItem(Messages.getString("GUInterface.53"), KeyEvent.VK_I); //$NON-NLS-1$
		infoHelpMenuItem.getAccessibleContext().setAccessibleDescription(
				Messages.getString("GUInterface.54")); //$NON-NLS-1$
		helpMenu.add(infoHelpMenuItem);

		infoHelpMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						Messages.getString("GUInterface.55") + //$NON-NLS-1$
								Messages.getString("GUInterface.56") + //$NON-NLS-1$
								Messages.getString("GUInterface.57") + //$NON-NLS-1$
								Messages.getString("GUInterface.58") + //$NON-NLS-1$
								Messages.getString("GUInterface.59") + //$NON-NLS-1$
								Messages.getString("GUInterface.60") + //$NON-NLS-1$
								Messages.getString("GUInterface.61") + //$NON-NLS-1$
								Messages.getString("GUInterface.62") + //$NON-NLS-1$
								Messages.getString("GUInterface.63") + //$NON-NLS-1$
								Messages.getString("GUInterface.64") + //$NON-NLS-1$
								Messages.getString("GUInterface.65") + //$NON-NLS-1$
								Messages.getString("GUInterface.66"), //$NON-NLS-1$
								Messages.getString("GUInterface.67"), //$NON-NLS-1$
								JOptionPane.PLAIN_MESSAGE);
			}
		});

		frame.setJMenuBar(menuBar);
	}


	/* (non-Javadoc)
	 * @see game.ui.GameInterface#startGame()
	 */
	public void startGame() {
		startOptions();
	}

	/**
	 * Starts and displays the first options dialog.
	 */
	public void startOptions() {

		final JFrame optionsFrame = new JFrame(Messages.getString("GUInterface.68")); //$NON-NLS-1$
		optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(500, 250));
		optionsFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel mazeSizePanel = new JPanel();
		tabbedPane.addTab(Messages.getString("GUInterface.69"), null, mazeSizePanel, null); //$NON-NLS-1$
		mazeSizePanel.setLayout(null);

		JLabel predefMazeLabel = new JLabel(Messages.getString("GUInterface.70")); //$NON-NLS-1$
		predefMazeLabel.setBounds(8, 0, 256, 30);
		mazeSizePanel.add(predefMazeLabel);

		ButtonGroup predefMaze = new ButtonGroup();

		JRadioButton yesPredefButton = new JRadioButton(Messages.getString("GUInterface.71")); //$NON-NLS-1$
		predefMaze.add(yesPredefButton);
		yesPredefButton.setSelected(true);
		yesPredefButton.setBounds(0, 40, 54, 23);
		mazeSizePanel.add(yesPredefButton);

		yesPredefButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				columnsTextField.setEnabled(false);
				rowsTextField.setEnabled(false);
				usePredefinedMaze = true;
			}
		});

		JRadioButton noPredefButton = new JRadioButton(Messages.getString("GUInterface.72")); //$NON-NLS-1$
		predefMaze.add(noPredefButton);
		noPredefButton.setBounds(56, 36, 299, 30);
		mazeSizePanel.add(noPredefButton);

		noPredefButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				columnsTextField.setEnabled(true);
				rowsTextField.setEnabled(true);
				usePredefinedMaze = false;
			}
		});

		JLabel lblRows = new JLabel(Messages.getString("GUInterface.73")); //$NON-NLS-1$
		lblRows.setBounds(8, 110, 46, 14);
		mazeSizePanel.add(lblRows);

		JLabel lblColumns = new JLabel(Messages.getString("GUInterface.74")); //$NON-NLS-1$
		lblColumns.setBounds(8, 135, 65, 14);
		mazeSizePanel.add(lblColumns);

		rowsTextField = new JTextField();
		rowsTextField.setBounds(71, 107, 86, 20);
		rowsTextField.setEnabled(false);
		mazeSizePanel.add(rowsTextField);
		rowsTextField.setColumns(10);

		columnsTextField = new JTextField();
		columnsTextField.setBounds(71, 132, 86, 20);
		columnsTextField.setEnabled(false);
		mazeSizePanel.add(columnsTextField);
		columnsTextField.setColumns(10);

		JPanel dragonOptionsPanel = new JPanel();
		tabbedPane.addTab(Messages.getString("GUInterface.75"), null, dragonOptionsPanel, null); //$NON-NLS-1$

		JLabel dragonTypeLabel = new JLabel(Messages.getString("GUInterface.76")); //$NON-NLS-1$
		dragonTypeLabel.setBounds(10, 11, 377, 14);
		dragonTypeLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		dragonTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		ButtonGroup dragonTypeButtons = new ButtonGroup();

		JRadioButton randomlySleepingButton = new JRadioButton(Messages.getString("GUInterface.77")); //$NON-NLS-1$
		randomlySleepingButton.setSelected(true);
		randomlySleepingButton.setBounds(10, 32, 234, 23);
		dragonTypeButtons.add(randomlySleepingButton);
		dragonOptionsPanel.setLayout(null);
		dragonOptionsPanel.add(dragonTypeLabel);
		dragonOptionsPanel.add(randomlySleepingButton);

		randomlySleepingButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dragonType = Dragon.SLEEPING;
			}
		});

		JRadioButton staticButton = new JRadioButton(Messages.getString("GUInterface.78")); //$NON-NLS-1$
		staticButton.setBounds(10, 84, 234, 23);
		dragonTypeButtons.add(staticButton);
		dragonOptionsPanel.add(staticButton);

		staticButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dragonType = Dragon.STATIC;
			}
		});

		JRadioButton alwaysAwakeButton = new JRadioButton(Messages.getString("GUInterface.79")); //$NON-NLS-1$
		alwaysAwakeButton.setBounds(10, 58, 234, 23);
		dragonTypeButtons.add(alwaysAwakeButton);
		dragonOptionsPanel.add(alwaysAwakeButton);

		alwaysAwakeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dragonType = Dragon.NORMAL;
			}
		});

		JLabel multipleDragonLabel = new JLabel(Messages.getString("GUInterface.80")); //$NON-NLS-1$
		multipleDragonLabel.setBounds(10, 131, 262, 14);
		dragonOptionsPanel.add(multipleDragonLabel);

		ButtonGroup multipleDragonButtons = new ButtonGroup();

		JRadioButton yesMultipleDragonsButton = new JRadioButton(Messages.getString("GUInterface.81")); //$NON-NLS-1$
		yesMultipleDragonsButton.setSelected(true);
		yesMultipleDragonsButton.setToolTipText(Messages.getString("GUInterface.82")); //$NON-NLS-1$
		yesMultipleDragonsButton.setBounds(10, 152, 109, 23);
		multipleDragonButtons.add(yesMultipleDragonsButton);
		dragonOptionsPanel.add(yesMultipleDragonsButton);

		yesMultipleDragonsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				useMultipleDragons = true;
			}
		});

		JRadioButton noMultipleDragonsButton = new JRadioButton(Messages.getString("GUInterface.83")); //$NON-NLS-1$
		noMultipleDragonsButton.setToolTipText(Messages.getString("GUInterface.84")); //$NON-NLS-1$
		noMultipleDragonsButton.setBounds(10, 178, 109, 23);
		multipleDragonButtons.add(noMultipleDragonsButton);
		dragonOptionsPanel.add(noMultipleDragonsButton);

		noMultipleDragonsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				useMultipleDragons = false;
			}
		});

		JButton btnEnterTheMaze = new JButton(Messages.getString("GUInterface.85")); //$NON-NLS-1$
		btnEnterTheMaze.setBounds(251, 84, 136, 61);
		dragonOptionsPanel.add(btnEnterTheMaze);

		btnEnterTheMaze.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				//Get Maze options from user
				options.randomMaze = !usePredefinedMaze;

				//Get Dragon options from user
				options.dragonType = dragonType;

				//Get Multiple dragon options
				options.multipleDragons = useMultipleDragons;

				options.randomSpawns = true;

				if(!usePredefinedMaze) {
					if(MazeInput.isInteger(rowsTextField.getText()) && MazeInput.isInteger(columnsTextField.getText())) {
						maze_rows = options.rows = Integer.parseInt(rowsTextField.getText()); 
						maze_columns = options.columns = Integer.parseInt(columnsTextField.getText());

						if(options.rows < 6 || options.columns < 6) {
							JOptionPane.showMessageDialog(optionsFrame,
									Messages.getString("GUInterface.86"), //$NON-NLS-1$
									Messages.getString("GUInterface.87"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);

							tabbedPane.setSelectedIndex(0);
						}
						else if(options.rows > 500 || options.columns > 500) {
							JOptionPane.showMessageDialog(optionsFrame,
									Messages.getString("GUInterface.88"), //$NON-NLS-1$
									Messages.getString("GUInterface.89"), //$NON-NLS-1$
									JOptionPane.ERROR_MESSAGE);

							tabbedPane.setSelectedIndex(0);
						}
						else {
							game = new Game(options);

							optionsFrame.setVisible(false);
							startInterface();
						}
					}
					else {
						JOptionPane.showMessageDialog(optionsFrame,
								Messages.getString("GUInterface.90"), //$NON-NLS-1$
								Messages.getString("GUInterface.91"), //$NON-NLS-1$
								JOptionPane.ERROR_MESSAGE);

						tabbedPane.setSelectedIndex(0);
					}
				}
				else {
					game = new Game(options);

					optionsFrame.setVisible(false);
					startInterface();
				}
			}
		});

		optionsFrame.setResizable(false);
		optionsFrame.pack();
		optionsFrame.setLocationRelativeTo(null);
		optionsFrame.setVisible(true);

	}

	/**
	 * Restarts game.
	 */
	private void restartGame() {
		if(useLoadedFile) {
			goOn = true;
			game = GameOutput.load(loadedFile);
		}
		else {
			goOn = true;
			game = new Game(options);
		}
		startInterface();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		for (GameKey gameKey : GameKeys.keyList) {
			if (e.getKeyCode() == gameKey.getKey())
				updateGame(gameKey.getChar());
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	/**
	 * Updates the game depending on user input.
	 *
	 * @param input the user input
	 */
	private void updateGame(char input) {

		if(goOn){ 
			goOn = game.heroTurn(input);

			mazePanel.repaint();

			goOn = game.dragonTurn(goOn);
			mazePanel.repaint();

			goOn = game.checkState(goOn);

			mazePanel.repaint();
			GameOutput.printEventQueue(game.getEvents(), infoPanel);
		}


		if(!goOn) {
			int option = JOptionPane.showConfirmDialog(
					frame,
					Messages.getString("GUInterface.92"), //$NON-NLS-1$
					Messages.getString("GUInterface.93"), //$NON-NLS-1$
					JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				frame.setVisible(false);
				restartGame();
				return;
			}
			else
				System.exit(0);
		}
	}


	/**
	 * Updates the options variable with the user specified parameters.
	 */
	private void updateOptions() {
		if(options.randomMaze = !usePredefinedMaze) {
			options.rows = maze_rows;
			options.columns = maze_columns;
		}

		options.dragonType = dragonType;

		options.multipleDragons = useMultipleDragons;

		options.randomSpawns = true;
	}

	/**
	 * Formats the preferred dimension variable given depending on the maximum dimension specified.
	 *
	 * @param oldPreferred the original preferred dimension
	 * @param maximumDimension the maximum dimension to be compared
	 * @return maximumDimension in cases where the preferred dimension is wider or taller than the maximum dimension,
	 * preferredDimension otherwise.
	 */
	public static Dimension getFormattedPreferredDimension(Dimension oldPreferred, Dimension maximumDimension) {
		Dimension formattedDimension = new Dimension(oldPreferred);

		if(oldPreferred.height > maximumDimension.height)
			formattedDimension.height = maximumDimension.height;

		if(oldPreferred.width > maximumDimension.width)
			formattedDimension.width = maximumDimension.width;

		return formattedDimension;
	}

}
