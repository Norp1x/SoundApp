package org.example;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.Gui.instruments;

public class MidiSound implements MetaEventListener {

    static Sequencer sequencer;
    static Sequence sequence;
    static Track track;

    public void configureMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }


    public static void createTrackAndRun() {
        int[] trackList;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];

            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jCheckBox = Gui.fieldCheckList.get(j + (16 * i));
                if (jCheckBox.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            createTrack(trackList);
            track.add(createEvent(176,1,127,0,16));
        }
        track.add(createEvent(192,9,1,0,15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    private static MidiEvent createEvent(int plc, int channel, int one, int two, int tact) {
        MidiEvent event;
        try {
            ShortMessage message = new ShortMessage();
            message.setMessage(plc, channel, one, two);
            event = new MidiEvent(message, tact);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    private static void createTrack(int[] trackList) {
        for (int i = 0; i < 16; i++) {
            int key = trackList[i];
            if (key != 0) {
                track.add(createEvent(144,9,key,100, i));
                track.add(createEvent(128,9,key,100,i+1));
            }
        }
    }

    @Override
    public void meta(MetaMessage metaMessage) {

    }

    static class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            createTrackAndRun();
        }
    }

    static class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            sequencer.stop();
        }
    }

    static class MyTempoUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    static class MyTempoDownListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }
}
