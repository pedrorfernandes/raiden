package game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeysPanel extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1820059510973932243L;

	// Class defining a general purpose message box
	public KeysPanel(Frame parent, String title, String message)  {
		super(parent, title, true);
		setTitle("");
		// If there was a parent, set dialog position inside
		if(parent != null) {
			Dimension parentSize = parent.getSize();     // Parent size
			Point p = parent.getLocation();              // Parent position
			setLocation(p.x+parentSize.width/4,p.y+parentSize.height/4); 
		}

		// Create the message pane
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));        
		getContentPane().add(messagePane);

		// Create the button pane
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("OK");        // Create OK button
		buttonPane.add(button);                    // add to content pane
		button.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();                                    // Size window for components
		setVisible(true);
	}

	// OK button action
	public void actionPerformed(ActionEvent e)  {
		setVisible(false);                         // Set dialog invisible
		dispose();                                 // Release the dialog resources
	}

}
