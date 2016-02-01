/**
 * Package for the infection implementations for the Khan Academy interview
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Ram Vellanki
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 */
package com.ram.kainterview;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import com.ram.kainterview.user.User;

/**
 * Controller of the infection model
 */
public class InfectionControllerImpl implements InfectionController {

	/**
	 * View instance to communicate changes to the view
	 */
	private GraphView view;
	
	/**
	 * Map of all id's to users in the user base
	 */
	private Map<String,User> users;

	/**
	 * Manages GraphStream's ViewerPipe pump requests
	 */
	private boolean loop;

	/**
	 * Infection number text field
	 */
	private JTextField infectTextField;

	/**
	 * Execute button
	 */
	private JButton execute;

	/**
	 * Keeps track of the current infection type
	 */
	private InfectionType type;

	/**
	 * Constructs a controller with the given user base
	 */
	public InfectionControllerImpl(List<User> users) {
		this.users = new HashMap<>();
		for (User user : users)
			this.users.put(user.id(), user);

		type = InfectionType.TOTAL; // default infection type
		loop = true; // enable graph rendering
	}

	@Override
	public void init(GraphView view, Graph graph, Viewer viewer) {
		this.view = view;
		
		for (Entry<String,User> entry : users.entrySet()) {
			User user = entry.getValue();

			// build ids of adjacent nodes
			List<String> toIds = new LinkedList<String>();
			List<String> fromIds = new LinkedList<String>();
			for (User coach : user.coaches())
				toIds.add(coach.id());
			for (User student : user.students())
				fromIds.add(student.id());

			view.addNode(user.id(),user.version(), toIds, fromIds);
		}

		ViewerPipe fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(new ViewerListener() {
			@Override
			public void buttonPushed(String id) {
				// add outline highlight on select
				graph.getNode(id).addAttribute("ui.class", "selected");
			}

			@Override
			public void buttonReleased(String id) {
				// remove highlighting on release
				graph.getNode(id).removeAttribute("ui.class");
				
				switch(type) {
				case TOTAL:
					users.get(id).totalInfect(users.get(id).version()+1);
					for (Entry<String,User> entry : users.entrySet())
						view.updateNode(entry.getKey(), 
								entry.getValue().version());
					break;
				case LIMITED:
					int num = 0;
					try {
						// read number of users from text field
						num = Integer.parseInt(
								infectTextField.getText().toString());
						if (num < 0)
							JOptionPane.showMessageDialog(new JFrame(), 
									Error.NUM_USERS,"Error",
									JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(new JFrame(), 
								Error.NUM_USERS,"Error",
								JOptionPane.ERROR_MESSAGE);
						break;
					}
					users.get(id).limitedInfect(users.get(id).version()+1, num);
					for (Entry<String,User> entry : users.entrySet())
						view.updateNode(entry.getKey(), 
								entry.getValue().version());
					break;
				case STRICT:
					JOptionPane.showMessageDialog(new JFrame(), "In strict "
							+ "limited infection mode,\ninfection can only be "
							+ "performed\nby pressing the 'Execute' button.\n\n"
							+ "See the 'Help' section for more information.", 
							"Warning", JOptionPane.WARNING_MESSAGE);
					break;
				default: assert false; // should never occur
				}
			}

			@Override
			public void viewClosed(String id) {
				loop = false;
			}
		});

		// connect the graph to the viewer
		fromViewer.addSink(graph);
		fromViewer.removeElementSink(graph); // for issue in library

		// run on separate thread to prevent UI thread blocking
		new Thread(() -> {
			while (loop) {
				// request the pipe to check if the viewer thread sent events
				try {
					fromViewer.blockingPump();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public void registerInfectField(JTextField infectTextField) {
		this.infectTextField = infectTextField;
		
		// hint for text field
		infectTextField.setText("# of users to infect");
		infectTextField.setForeground(Color.GRAY);
		infectTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (infectTextField.getText().equals("# of users to infect")) {
					infectTextField.setText("");
					infectTextField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (infectTextField.getText().equals("")) {
					infectTextField.setText("# of users to infect");
					infectTextField.setForeground(Color.GRAY);
				}
			}
		});
	}

	@Override
	public void registerInfectButton(JButton infect) {
		infect.addActionListener((event) -> {
			switch (type) {
			case TOTAL:
				type = InfectionType.LIMITED;
				infect.setText("Limited Infection");
				infectTextField.setVisible(true);
				break;
			case LIMITED:
				type = InfectionType.STRICT;
				infect.setText("Strict Limited Infection");
				execute.setVisible(true);
				break;
			case STRICT:
				type = InfectionType.TOTAL;
				infect.setText("Total Infection");
				execute.setVisible(false);
				infectTextField.setVisible(false);
				break;
			default: assert false; // should never occur
			}
		});
	}

	@Override
	public void registerExecuteButton(JButton execute) {
		this.execute = execute;
		execute.addActionListener((event) -> {
			assert type.equals(InfectionType.STRICT);
			
			boolean completed = false;
			
			for (Entry<String,User> entry : users.entrySet()) {
				User user = entry.getValue();
				
				int num = 0;
				try {
					// read number of users from text field
					num = Integer.parseInt(
							infectTextField.getText().toString());
					if (num < 0)
						JOptionPane.showMessageDialog(new JFrame(), 
								Error.NUM_USERS,"Error",
								JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(new JFrame(), 
							Error.NUM_USERS,"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (user.graphSize() == num) {
					user.totalInfect(user.version()+1);
					completed = true;
					break;
				}
			}
			
			if (!completed)
				JOptionPane.showMessageDialog(new JFrame(), 
						Error.NO_STRICT,"Error",
						JOptionPane.ERROR_MESSAGE);
			else
				for (Entry<String,User> entry : users.entrySet())
					view.updateNode(entry.getKey(), entry.getValue().version());
			
		});
	}

	@Override	
	public void registerHelpButton(JButton help) {
		help.addActionListener((event) -> {
			JOptionPane.showMessageDialog(new JFrame(), "Click the '"+type
			+ "' button to toggle between infection types.\nPress any "
			+ "node (represents a user) to infect from that user.\nFor "
			+ "limited infection, enter the number of users to be "
			+ "infected \nbefore selecting a node. However, when using"
			+ "strict limited \ninfection, the application will "
			+ "automatically attempt the infection\nupon pressing the "
			+ "'Execute' button.\n\nFor information, visit "
			+ "https://github.com/RamV13/Infection", "Help", 
			JOptionPane.PLAIN_MESSAGE);
		});
	}

}
