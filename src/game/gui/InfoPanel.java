package game.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InfoPanel extends JPanel {

	public JTextPane infoPanel;
	
	public InfoPanel() {
		setLayout(new BorderLayout());
		
		infoPanel = new JTextPane();
		infoPanel.setBounds(10, 230, 430, 70);
		infoPanel.setText("Welcome, challenger");
		add(infoPanel);
	}

}
