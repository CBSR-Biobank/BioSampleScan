package edu.ualberta.med.biosamplescan;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class SimpleSynth {

    Synthesizer synth;
    Receiver rcvr;

    public SimpleSynth() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            rcvr = synth.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (int i = 0; i < 127; i++) {
            rcvr.send(getNoteOnMessage(1 + i), 0);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rcvr.send(getNoteOffMessage(1 + i), 0);
        }
        for (int i = 0; i < 50; i++) {
            rcvr.send(getNoteOnMessage(100 + (i % 5) * 5), 0);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rcvr.send(getNoteOffMessage(1 + (i % 5) * 5), 0);
        }

    }

    private MidiMessage getNoteOnMessage(int note) {
        return getMessage(ShortMessage.NOTE_ON, note);
    }

    private MidiMessage getNoteOffMessage(int note) {
        return getMessage(ShortMessage.NOTE_OFF, note);
    }

    private MidiMessage getMessage(int cmd, int note) {
        try {
            ShortMessage msg = new ShortMessage();
            msg.setMessage(cmd, 0, note, 60);
            return msg;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
