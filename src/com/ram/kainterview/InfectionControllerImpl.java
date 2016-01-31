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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 * Controller of the infection model
 */
public class InfectionControllerImpl implements InfectionController {

	/**
	 * Map of all id's to users in the user base
	 */
	private Map<String,User> users;
	
	/**
	 * Manages GraphStream's ViewerPipe pump requests
	 */
	private boolean loop;
	
	/**
	 * Constructs a controller with the given user base
	 */
	public InfectionControllerImpl(List<User> users) {
		this.users = new HashMap<>();
		for (User user : users)
			this.users.put(user.id(), user);
		
		loop = true;
	}
	
	@Override
	public void init(GraphView view, Graph graph, Viewer viewer) {
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
				// unused as only a release event indicates a complete click
				// TODO highlight outline of selected node 
			}

			@Override
			public void buttonReleased(String id) {	
				users.get(id).setVersion(users.get(id).version()+1);
				for (Entry<String,User> entry : users.entrySet())
					view.updateNode(entry.getKey(), entry.getValue().version());
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
				fromViewer.pump();
			}
		}).start();
	}

	@Override
	public void registerInfectField(JTextField textField) {
		// TODO
	}
	
	@Override
	public void registerInfectButton(JButton button) {
		button.addActionListener((event) -> {
			// TODO
		});
	}

	@Override
	public void registerHelpButton(JButton button) {
		button.addActionListener((event) -> {
			// TODO
		});
	}
	
}
