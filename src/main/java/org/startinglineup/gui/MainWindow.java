package org.startinglineup.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.Modeler;
import org.startinglineup.Properties;

/**
 * Main application window that is an extension of the <code>JFrame</code>
 * class. All application interactions will be received by this window.
 * 
 * @author Mike Darretta
 */
public class MainWindow extends JFrame implements WindowFocusListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane resultsSP;
	private JTextArea resultsTA;
	private TextField numSeasonsTF;
	private TextField startDateTF;
	private TextField endDateTF;
	private TextField startGameTF;
	private TextField endGameTF;
	private JButton clearBtn;
	private JButton startBtn;
	private JButton saveBtn;
	private JCheckBox WARcb;
	private JCheckBox WAAcb;
	
	/**
	 * The model for this viewer.
	 */
	private Modeler modeler;

	/**
	 * Instantiates the window and the modeler.
	 */
	public MainWindow() {

		super("Starting Lineup");
		this.modeler = new Modeler();
		createFrame();
	}

	/**
	 * Creates this <code>JFrame</code> instance.
	 */
	private void createFrame() {
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		panel = new JPanel();
		panel.setLayout(createLayout());
		this.add(panel);
		
		this.addWindowFocusListener(this);
	}

	/**
	 * Placeholder for future focus improvements.
	 * @param e The window event.
	 */
	public void windowGainedFocus(WindowEvent e) {
		// placeholder
	}
	
	/**
	 * Placeholder for future focus improvements.
	 * @param e The window event.
	 */
	public void windowLostFocus(WindowEvent e) {
		// placeholder
	}

	/**
	 * Creates the layout as a <code>GroupLayout</code>
	 * @return The layout.
	 */
	private GroupLayout createLayout() {
		GroupLayout layout = new GroupLayout(panel);

		// Turn on automatically adding gaps between components
		layout.setAutoCreateGaps(true);

		// Turn on automatically creating gaps between components that touch
		// the edge of the container and the container.
		layout.setAutoCreateContainerGaps(true);

		numSeasonsTF = new TextField("Number of seasons to model: ", "" + modeler.getNumSeasons());
		startDateTF = new TextField("Starting date: ", modeler.getStartDate());
		endDateTF = new TextField("Ending date: ", modeler.getEndDate());
		startGameTF = new TextField("Starting game: ", modeler.getStartGame());
		endGameTF = new TextField("Ending game: ", modeler.getEndGame());
		clearBtn = createClearBtn();
		startBtn = createStartBtn();
		saveBtn = createSaveBtn();
		resultsTA = new JTextArea();
		resultsTA.setEditable(false);
		resultsTA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		resultsSP = new JScrollPane(resultsTA);
		WARcb = new JCheckBox("Include WAR");
		WAAcb = new JCheckBox("Include WAA");

		// Create a sequential group for the horizontal axis.
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

		hGroup.addGroup(layout.createParallelGroup().addComponent(numSeasonsTF.getLabel())
				.addComponent(startDateTF.getLabel()).addComponent(endDateTF.getLabel())
				.addComponent(startGameTF.getLabel()).addComponent(endGameTF.getLabel()).addComponent(WARcb)
				.addComponent(WAAcb).addComponent(saveBtn).addComponent(startBtn));
		hGroup.addGroup(
				layout.createParallelGroup().addComponent(numSeasonsTF.getField()).addComponent(startDateTF.getField())
						.addComponent(endDateTF.getField()).addComponent(startGameTF.getField())
						.addComponent(endGameTF.getField()).addComponent(clearBtn).addComponent(resultsSP));
		layout.setHorizontalGroup(hGroup);

		// Create a sequential group for the vertical axis.
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(numSeasonsTF.getLabel())
				.addComponent(numSeasonsTF.getField()));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(startDateTF.getLabel())
				.addComponent(startDateTF.getField()));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(endDateTF.getLabel())
				.addComponent(endDateTF.getField()));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(startGameTF.getLabel())
				.addComponent(startGameTF.getField()));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(endGameTF.getLabel())
				.addComponent(endGameTF.getField()));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(WARcb));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(WAAcb));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(saveBtn).addComponent(clearBtn));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(startBtn).addComponent(resultsSP));
		layout.setVerticalGroup(vGroup);
		
		return layout;
	}

	/**
	 * Creates the clear button.
	 * @return The clear button.
	 */
	private JButton createClearBtn() {
		JButton button = new JButton("Clear Results");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultsTA.selectAll();
				resultsTA.replaceSelection("");
			}
		});

		return button;
	}

	/**
	 * Creates the create button.
	 * @return The create button.
	 */
	private JButton createStartBtn() {
		modeler = new Modeler();

		JButton button = new JButton("Start");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeler.update(numSeasonsTF.getValue(), startDateTF.getValue(), endDateTF.getValue(),
						startGameTF.getValue(), endGameTF.getValue());

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				modeler.run();

				resultsTA.append(modeler.getResults() + "\n");

				if (WARcb.isSelected()) {
					resultsTA.append(modeler.getAdvancedMetricResults(AdvancedMetric.MetricType.WAR));
				}
				if (WAAcb.isSelected()) {
					resultsTA.append(modeler.getAdvancedMetricResults(AdvancedMetric.MetricType.WAA));
				}

				setCursor(Cursor.getDefaultCursor());
			}
		});

		return button;
	}
	
	/**
	 * Creates the save button.
	 * @return The save button.
	 */
	private JButton createSaveBtn() {
		JButton button = new JButton("Save Properties");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					modeler.export(numSeasonsTF.getValue(), startDateTF.getValue(), endDateTF.getValue(),
							startGameTF.getValue(), endGameTF.getValue());

					JOptionPane.showMessageDialog(null, "Properties saved to " + Properties.PATHNAME + ".",
							"Confirmation", JOptionPane.INFORMATION_MESSAGE, null);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		return button;
	}

	/**
	 * Inner class to encapsulate a combination of a <code>JTextField</code>
	 * along with its associated <code>JLabel</code>.
	 */
	private class TextField {

		/**
		 * The field.
		 */
		protected JTextField textField;
		
		/**
		 * The label.
		 */
		protected JLabel label;

		/**
		 * Instantiates this class for a label and a default field value.
		 * @param label The label.
		 * @param value The default field value.
		 */
		protected TextField(String label, String value) {
			this.label = new JLabel(label, SwingConstants.RIGHT);
			this.textField = new JTextField(value, 40);
		}

		/**
		 * Returns the label.
		 * @return The label.
		 */
		protected JLabel getLabel() {
			return label;
		}

		/**
		 * Returns the text field.
		 * @return THe text field.
		 */
		protected JTextField getField() {
			return textField;
		}

		/**
		 * Returns the text field value.
		 * @return The text field value.
		 */
		protected String getValue() {
			return textField.getText();
		}
	}
}
