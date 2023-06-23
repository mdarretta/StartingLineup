package org.startinglineup.gui;

import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.GroupLayout.Alignment;

import org.startinglineup.Modeler;
import org.startinglineup.simulator.BatterStatsMap;
import org.startinglineup.simulator.GameStats;
import org.startinglineup.simulator.Schedule;
import org.startinglineup.simulator.Standings;

public class MainWindow {

    private JFrame mainFrame;
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
    private Modeler modeler;

    public MainWindow() {

        this.modeler = new Modeler();
        mainFrame = createFrame();
    }

    public void show() {
        mainFrame.show();
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Starting Lineup");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel = new JPanel();
        panel.setLayout(createLayout());
        frame.add(panel);

        return frame;
    }

    private GroupLayout createLayout() {
        GroupLayout layout = new GroupLayout(panel);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel resultsLbl = new JLabel("Results:");
        numSeasonsTF = new TextField("Number of seasons to model: ", ""+modeler.getNumSeasons());
        startDateTF = new TextField("Starting date: ", modeler.getStartDate());
        endDateTF = new TextField("Ending date: ", modeler.getEndDate());
        startGameTF = new TextField("Starting game: ", modeler.getStartGame());
        endGameTF = new TextField("Ending game: ", modeler.getEndGame());
        clearBtn = createClearBtn();
        startBtn = createStartBtn();
        saveBtn = createSaveBtn();
        resultsTA = new JTextArea();
        resultsTA.setEditable(false);
        resultsSP = new JScrollPane(resultsTA);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        hGroup.addGroup(layout.createParallelGroup().
            addComponent(numSeasonsTF.getLabel()).addComponent(startDateTF.getLabel()).
                addComponent(endDateTF.getLabel()).addComponent(startGameTF.getLabel()).
                    addComponent(endGameTF.getLabel()).
                        addComponent(saveBtn).addComponent(startBtn));
        hGroup.addGroup(layout.createParallelGroup().
            addComponent(numSeasonsTF.getField()).addComponent(startDateTF.getField()).
                addComponent(endDateTF.getField()).addComponent(startGameTF.getField()).
                    addComponent(endGameTF.getField()).addComponent(clearBtn).addComponent(resultsSP));
        //hGroup.addGroup(layout.createParallelGroup().addComponent(saveBtn).addComponent(new JLabel("Results")));
        //hGroup.addGroup(layout.createParallelGroup().addComponent(startBtn));
        //hGroup.addGroup(layout.createParallelGroup().addComponent(resultsSP));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(numSeasonsTF.getLabel()).addComponent(numSeasonsTF.getField()));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(startDateTF.getLabel()).addComponent(startDateTF.getField()));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(endDateTF.getLabel()).addComponent(endDateTF.getField()));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(startGameTF.getLabel()).addComponent(startGameTF.getField()));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(endGameTF.getLabel()).addComponent(endGameTF.getField()));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(saveBtn).addComponent(clearBtn));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(startBtn).addComponent(resultsSP));
        /*vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
            addComponent(resultsSP));*/
        layout.setVerticalGroup(vGroup);

        return layout;
    }

    private void addResultsLine(String line) {
        resultsTA.append(line + "\n");
    }

    private void populateResults() {

        int numSeasonsToModel = Integer.valueOf(numSeasonsTF.getValue());

        if (numSeasonsToModel > 1) {
            Standings.getInstance().normalizeStats(numSeasonsToModel);
            BatterStatsMap.getInstance().updateStatsForSingleSeason(numSeasonsToModel);
        }

        addResultsLine("Leaders by BA");
        addResultsLine("-------------");
        addResultsLine(BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByBattingAverage(), 10));
        addResultsLine("Leaders by Home Runs");
        addResultsLine("-------------");
        addResultsLine(BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByHomeRuns(), 10));
        addResultsLine("Leaders by RBIs");
        addResultsLine("-------------");
        addResultsLine(BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByRbis(), 10));
        addResultsLine("Leaders by OPS");
        addResultsLine("-------------");
        addResultsLine(BatterStatsMap.getInstance().forString(
                        BatterStatsMap.getInstance().getStatsByOPS(), 10));
        addResultsLine(Standings.getInstance().toString());
    }

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

    private JButton createStartBtn() {
        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeler = new Modeler();
                modeler.update(
                    numSeasonsTF.getValue(),
                    startDateTF.getValue(),
                    endDateTF.getValue(),
                    startGameTF.getValue(),
                    endGameTF.getValue()
                );

                for (int x=0; x < Integer.valueOf(numSeasonsTF.getValue()); x++) {
                    modeler.run();
                }
                
                populateResults();
            }
        });

        return button;
    } 

    private JButton createSaveBtn() {
        JButton button = new JButton("Save");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeler.export(
                    numSeasonsTF.getValue(),
                    startDateTF.getValue(),
                    endDateTF.getValue(),
                    startGameTF.getValue(),
                    endGameTF.getValue()
                );
            }
        });

        return button;
    }

    private class TextField {
  
        protected JTextField textField;
        protected JLabel label;

        protected TextField(String label) {
            this(label,"");
        }

        protected TextField(String label, String value) {
            this.label = new JLabel(label, SwingConstants.RIGHT);
            this.textField = new JTextField(value, 40);
        }

        protected JLabel getLabel() {
            return label;
        }
 
        protected JTextField getField() {
            return textField;
        }

        protected String getValue() {
            return textField.getText();
        }
    }
}
