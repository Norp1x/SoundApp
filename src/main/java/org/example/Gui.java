package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Gui {

    static ArrayList<JCheckBox> fieldCheckList;
    JPanel mainPanel;
    JFrame mainFrame;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell",
            "Vibraharp", "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    static int [] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};


    public void createGui() {
        mainFrame = new JFrame("MuzMachina");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel backgroundPanel = new JPanel(layout);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainFrame.setVisible(true);
        mainFrame.setSize(950,680);
        mainFrame.setLocationRelativeTo(null);

        fieldCheckList = new ArrayList<>();
        Box buttonArea = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MidiSound.MyStartListener());
        buttonArea.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MidiSound.MyStopListener());
        buttonArea.add(stop);

        JButton tempoUp = new JButton("Speed Up");
        tempoUp.addActionListener(new MidiSound.MyTempoUpListener());
        buttonArea.add(tempoUp);

        JButton tempoDown = new JButton("Speed Down");
        tempoDown.addActionListener(new MidiSound.MyTempoDownListener());
        buttonArea.add(tempoDown);

        Box namesArea = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            namesArea.add(new Label(instrumentNames[i]));
        }

        backgroundPanel.add(BorderLayout.EAST, buttonArea);
        backgroundPanel.add(BorderLayout.WEST, namesArea);

        mainFrame.getContentPane().add(backgroundPanel);

        GridLayout fieldCheckGrid = new GridLayout(16, 16);
        fieldCheckGrid.setVgap(1);
        fieldCheckGrid.setHgap(2);
        mainPanel = new JPanel(fieldCheckGrid);
        backgroundPanel.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(false);
            fieldCheckList.add(checkBox);
            mainPanel.add(checkBox);
        }
    }
}
