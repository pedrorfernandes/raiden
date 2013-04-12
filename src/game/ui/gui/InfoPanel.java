package game.ui.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

/**
 * The Class InfoPanel implements a TextPane to be changed according to game events.
 */
public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JTextPane textPane;

	/**
	 * Instantiates a new info panel with the given preferredSize and maximumSize
	 *
	 * @param preferred the preferred size
	 * @param maximum the maximum size
	 */
	public InfoPanel(Dimension preferred, Dimension maximum) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setMaximumSize(maximum);

		Dimension preferredDimension = GUInterface.getFormattedPreferredDimension(preferred, maximum);

		setPreferredSize(preferredDimension);
		setMinimumSize(preferredDimension);


		setBorder(UIManager.getBorder("TextField.border"));

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("Welcome, challenger");
		textPane.setAlignmentX(CENTER_ALIGNMENT);
		textPane.setAlignmentY(CENTER_ALIGNMENT);
		textPane.setBackground(null);
		textPane.setFocusable(false);

		add(textPane);
	}
}
