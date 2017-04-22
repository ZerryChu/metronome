package metronome;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MyAudioRecorder extends Thread {
	public static File outputFile = new File(
			"/Users/zhuzirui/Documents/workspace/new/metronome/src/metronome/record.wav");
	public static TargetDataLine targetDataLine = null;
	public static AudioFileFormat.Type targetType = AudioFileFormat.Type.AU;
	// public static ByteArrayOutputStream bos = new ByteArrayOutputStream();  
    // public static byte[] buf; 
    
	public MyAudioRecorder() {
		// TODO Auto-generated constructor stub
		AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 8, 1, 1, 44100F, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			System.out.println("录音系统初始化失败");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void startRecording() {
		targetDataLine.start();
		try {    
			if (outputFile.exists()) {
				outputFile.delete();
			}
			outputFile.createNewFile();
			AudioSystem.write(new AudioInputStream(targetDataLine), targetType, outputFile);
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}

	public void stopRecording() {
		targetDataLine.stop();
	}
	
	public void endRecording() {
        targetDataLine.close();        

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		startRecording();
	}
	
}
