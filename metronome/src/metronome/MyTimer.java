package metronome;

import java.awt.Color;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JLabel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MyTimer extends Thread {
	Metronome myMetronome;
	boolean suspended = false;
	int index = 0;

	public MyTimer(Metronome metronome) {
		// TODO Auto-generated constructor stub
		this.myMetronome = metronome;
	}

	public synchronized void resumeTimer(){
        suspended = false;
        notify();
    }
	
	public void stopTimer() {
        suspended = true;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Component[] labels = myMetronome.showPanel.getComponents();

		while (true) {
			try {
				synchronized(this) {
                    while(suspended) {
                       wait();
                    }
                }
				if (index - 1 == -1)
					labels[labels.length - 1].setForeground(Color.GRAY);
				else
					labels[index - 1].setForeground(Color.GRAY);
				labels[index].setForeground(Color.RED);
				index = (index + 1) % labels.length;

				FileInputStream fileau = new FileInputStream("/Users/zhuzirui/Documents/workspace/new/metronome/src/metronome/dididi.wav");
				AudioStream as = new AudioStream(fileau);
				AudioPlayer.player.start(as);
				long time = 60000 / Integer.valueOf(myMetronome.speedArea.getText());
				if (Integer.valueOf(myMetronome.note.getText()) == 8) {
					time /= 2;
				} else if (Integer.valueOf(myMetronome.note.getText()) == 16) {
					time /= 4;
				}
				sleep(time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
