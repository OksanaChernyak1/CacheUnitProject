package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CacheUnitView extends java.util.Observable implements View {

	private JFrame frame;
	private JPanel mainPanel;
	private JPanel btnPanel;
	private JPanel msgPanel;
	private JButton loadRequestButton;
	private JButton showStatisticsButton;
	private JLabel msgLabel;



	public CacheUnitView() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				start();
			}
		});
	}

	@Override
	public void start() {
		
		frame = new JFrame("Cache Unit project By-Oksana Rybanov");
		frame.setLayout(new FlowLayout());
		
		mainPanel=new JPanel(new BorderLayout());
		btnPanel=new JPanel(new FlowLayout());
		msgPanel=new JPanel(new BorderLayout());
		

		loadRequestButton = new JButton("Load a Request");
		loadRequestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fs = new JFileChooser(new File("C:\\Users\\user\\eclipse-workspace\\CacheUnitClient\\src\\main\\resources"));
				fs.setDialogTitle("Load a Request");
				fs.showOpenDialog(null);
				fs.getTypeDescription(fs.getSelectedFile());
				setChanged();
				try {
					notifyObservers(readFromFile(fs.getSelectedFile().getPath()));
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		});
		
		showStatisticsButton = new JButton("Show Statistics");
		showStatisticsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers("statistics");
			}
		});
		
		msgLabel=new JLabel();
		msgLabel.setPreferredSize(new Dimension(400,200));
		
		btnPanel.add(loadRequestButton);
		btnPanel.add(showStatisticsButton);
		msgPanel.add(msgLabel);
		mainPanel.add(btnPanel,BorderLayout.NORTH);
		mainPanel.add(msgPanel,BorderLayout.CENTER);
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	@Override
	public <T> void updateUIData(T t) {
		if(t.toString().equals("true")) {
			JOptionPane.showMessageDialog(null, "Succsesful Loaded");
		}
		else if(t.toString().equals("false")){
			JOptionPane.showMessageDialog(null, "ERROR:Load Faild");
		}
		else {
			msgLabel.setText(t.toString());
		}
	}

	@SuppressWarnings("resource")
	public String readFromFile(String path) throws IOException {

		StringBuilder str = new StringBuilder();
		BufferedReader in;
		String line = null;
		File file = new File(path);
		in = new BufferedReader(new FileReader(file));

		line = in.readLine();
		while (line != null) {

			str.append(line);
			line = in.readLine();

		}
		return str.toString();
	}


}
