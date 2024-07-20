package org.example;

public class SoundApp {


    public static void main(String[] args) {

        new MidiSound().configureMidi();
        new Gui().createGui();
    }




}