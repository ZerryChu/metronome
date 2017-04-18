package metronome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	
	public static JButton runButton = new JButton("run");
	public static JButton stopButton = new JButton("stop");
	public static MyTimer myTimer = new MyTimer(metronome);
	public static boolean isStarted = false;
	
	public static void setSpeedPanel() {
		deceleration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				speedArea.setText(String.valueOf(Integer.valueOf(speedArea.getText()) - 1));

			}
		});

		acceleration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				speedArea.setText(String.valueOf(Integer.valueOf(speedArea.getText()) + 1));

			}
		});

		speedPanel.setSize(300, 50);
		// speedArea.setBackground(Color.GRAY);
		speedPanel.add(deceleration);
		speedPanel.add(speedArea);
		speedPanel.add(acceleration);
		metronome.add(speedPanel);

		// set button
	}

	public static void setShowPanel(int num) {
		showPanel.setPreferredSize(new Dimension(380, 50));
		for (int i = 0; i < num; i++) {
			JLabel pace = new JLabel(" * ");
			pace.setPreferredSize(new Dimension(40, 40));
			pace.setBackground(Color.GRAY);
			showPanel.add(pace);
		}
		metronome.add(showPanel);

	}

	public static void resetShowPanel(int num) {
		showPanel.removeAll();
		showPanel.setPreferredSize(new Dimension(380, 50));
		for (int i = 0; i < num; i++) {
			JLabel pace = new JLabel(" * ");
			pace.setPreferredSize(new Dimension(40, 40));
			pace.setBackground(Color.GRAY);
			showPanel.add(pace);
		}
	}
	
	public static void setSettingPanel() {
		decreaseBeats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int beat_num_now = Integer.valueOf(beats.getText()) - 1;
				if (beat_num_now < 1)
					return;
				beats.setText(String.valueOf(beat_num_now));
				resetShowPanel(beat_num_now);
			}
		});
		
		addBeats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int beat_num_now = Integer.valueOf(beats.getText()) + 1;
				if (beat_num_now > 8)
					return;
				beats.setText(String.valueOf(beat_num_now));
				resetShowPanel(beat_num_now);

			}
		});
		
		decreaseNote.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int note_num = Integer.valueOf(note.getText());
				if (note_num == 1)
					return;
				note.setText(String.valueOf(note_num / 2));

			}
		});
		
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
		
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isStarted == false) {
					myTimer.start();
					isStarted = true;
				} else {
					myTimer.resumeTimer();
				}
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myTimer.stopTimer();
			}
		});
		
		operationPanel.setSize(200, 50);
		operationPanel.add(runButton);
		operationPanel.add(stopButton);
		metronome.add(operationPanel);
	}
	
	public static void main(String[] args) {
		metronome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭按钮的动作为退出窗口
		metronome.setSize(400, 220);
		metronome.setLayout(new FlowLayout());

		setSpeedPanel();
		setShowPanel(4);
		setSettingPanel();
		setOperationPanel();
		
		metronome.setVisible(true);
		
	}

}