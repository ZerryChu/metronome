package metronome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Metronome extends JFrame {
	public static Metronome metronome = new Metronome();
	public static JPanel speedPanel = new JPanel();
	public static JPanel showPanel = new JPanel(new FlowLayout());
	public static JPanel settingPanel = new JPanel(new FlowLayout());
	public static JPanel operationPanel = new JPanel();

	public static JButton deceleration = new JButton(" - ");
	public static JButton acceleration = new JButton(" + ");
	public static JTextArea speedArea = new JTextArea("70");

	public static JButton decreaseBeats = new JButton(" - ");
	public static JButton addBeats = new JButton(" + ");
	public static JButton decreaseNote = new JButton(" - ");
	public static JButton increaseNote = new JButton(" + ");
	public static JTextArea beats = new JTextArea("4");
	public static JTextArea note = new JTextArea("4");

	public static JButton startOrStopButton = new JButton("start");
	public static MyTimer myTimer = new MyTimer(metronome);
	public static boolean isStarted = false;

	public static JButton startOrStopRecording = new JButton("record");
	public static JButton playSong = new JButton("play");
	public static AudioStream as = null;
	public static MyAudioRecorder audioRecorder = null;

	public static void setSpeedPanel() {
		deceleration.setPreferredSize(new Dimension(60, 30));
		deceleration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				speedArea.setText(String.valueOf(Integer.valueOf(speedArea.getText()) - 1));

			}
		});

		acceleration.setPreferredSize(new Dimension(60, 30));
		acceleration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				speedArea.setText(String.valueOf(Integer.valueOf(speedArea.getText()) + 1));

			}
		});

		speedPanel.setSize(300, 50);
		speedArea.setBackground(metronome.getBackground());
		speedArea.setFont(new Font("Dialog", Font.BOLD, 26));
		speedArea.setForeground(Color.GRAY);
		speedPanel.add(deceleration);
		speedPanel.add(speedArea);
		speedPanel.add(acceleration);
		metronome.add(speedPanel);

		// set button
	}

	public static void setShowPanel(int num) {
		showPanel.setPreferredSize(new Dimension(380, 50));
		for (int i = 0; i < num; i++) {
			JLabel pace = new JLabel();
			pace.setPreferredSize(new Dimension(40, 40));
			pace.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/circle-gray.png"));
			showPanel.add(pace);
		}
		metronome.add(showPanel);

	}

	public static void resetShowPanel(int flag) {
		if (flag == -1)
			showPanel.remove(showPanel.getComponentCount() - 1);
		else if (flag == 1) {
			JLabel pace = new JLabel();
			pace.setPreferredSize(new Dimension(40, 40));
			pace.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/circle-gray.png"));
			showPanel.add(pace);
		}
		showPanel.repaint();
	}

	public static void setSettingPanel() {
		decreaseBeats.setPreferredSize(new Dimension(50, 30));
		decreaseBeats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int beat_num_now = Integer.valueOf(beats.getText()) - 1;
				if (beat_num_now < 1)
					return;
				beats.setText(String.valueOf(beat_num_now));
				resetShowPanel(-1);
			}
		});

		addBeats.setPreferredSize(new Dimension(50, 30));
		addBeats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int beat_num_now = Integer.valueOf(beats.getText()) + 1;
				if (beat_num_now > 8)
					return;
				beats.setText(String.valueOf(beat_num_now));
				resetShowPanel(1);

			}
		});

		decreaseNote.setPreferredSize(new Dimension(50, 30));
		decreaseNote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int note_num = Integer.valueOf(note.getText());
				if (note_num == 4)
					return;
				note.setText(String.valueOf(note_num / 2));

			}
		});

		increaseNote.setPreferredSize(new Dimension(50, 30));
		increaseNote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int note_num = Integer.valueOf(note.getText());
				if (note_num == 16)
					return;
				note.setText(String.valueOf(Integer.valueOf(note.getText()) * 2));

			}
		});

		beats.setBackground(metronome.getBackground());
		beats.setFont(new Font("Dialog", Font.PLAIN, 20));
		beats.setForeground(Color.BLACK);
		;
		note.setBackground(metronome.getBackground());
		note.setFont(new Font("Dialog", Font.PLAIN, 20));
		note.setForeground(Color.BLACK);

		settingPanel.setSize(300, 50);
		settingPanel.add(decreaseBeats);
		settingPanel.add(addBeats);
		settingPanel.add(beats);
		settingPanel.add(new JLabel(" / "));
		settingPanel.add(note);
		settingPanel.add(increaseNote);
		settingPanel.add(decreaseNote);

		metronome.add(settingPanel);
	}

	public static void setOperationPanel() {

		startOrStopButton.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/begin.png"));
		startOrStopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (startOrStopButton.getText().equals("start")) {
					if (isStarted == false) {
						myTimer.start();
						startOrStopButton.setIcon(
								new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/stop.png"));
						isStarted = true;
					} else {
						myTimer.resumeTimer();
					}
					startOrStopButton
							.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/stop.png"));
					startOrStopButton.setText("stop");
				} else {
					myTimer.stopTimer();
					startOrStopButton
							.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/begin.png"));
					startOrStopButton.setText("start");
				}
			}
		});

		startOrStopRecording.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/start.png"));
		startOrStopRecording.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (startOrStopRecording.getText().equals("record")) {
					audioRecorder = new MyAudioRecorder();
					audioRecorder.start();
					startOrStopRecording
							.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/stop.png"));
					startOrStopRecording.setText("end");
				} else {
					audioRecorder.stopRecording();
					startOrStopRecording
							.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/start.png"));
					startOrStopRecording.setText("record");
				}
			}
		});

		playSong.setIcon(new ImageIcon("/Users/zhuzirui/Documents/workspace/new/metronome/src/sound.png"));
		playSong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (playSong.getText().equals("stop")) {
						AudioPlayer.player.stop(as);
						playSong.setText("play");
					} else {
						as = new AudioStream(new FileInputStream("/Users/zhuzirui/Documents/workspace/new/metronome/src/metronome/record.wav"));
						AudioPlayer.player.start(as);
						playSong.setText("stop");
					}
				} catch (Exception _e) {
					// TODO Auto-generated catch block
					_e.printStackTrace();
				}
			}
		});

		// operationPanel.setSize(200, 50);
		operationPanel.add(startOrStopButton);
		operationPanel.add(startOrStopRecording);
		operationPanel.add(playSong);
		metronome.add(operationPanel);
	}

	public static void main(String[] args) {
		metronome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭按钮的动作为退出窗口
		metronome.setSize(400, 250);
		metronome.setLayout(new FlowLayout());

		setSpeedPanel();
		setShowPanel(4);
		setSettingPanel();
		setOperationPanel();

		metronome.setVisible(true);

	}

}
