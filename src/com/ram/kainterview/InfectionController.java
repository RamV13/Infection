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

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

/**
 * Controller of the infection model
 */
public class InfectionController implements GraphController {

	/**
	 * List of all users in the user base
	 */
	private List<User> users;
	
	/**
	 * Manages GraphStream's ViewerPipe pump requests
	 */
	private boolean loop;
	
	/**
	 * Constructs a controller with the given user base
	 */
	public InfectionController(List<User> users) {
		this.users = users;
		loop = true;
	}
	
	@Override
	public void init(GraphView view, Graph graph, Viewer viewer) {
		for (User user : users) {
			// build ids of adjacent nodes
			List<String> ids = new LinkedList<String>();
			for (User coach : user.coaches())
				ids.add(coach.id());
			for (User student : user.students())
				ids.add(student.id());
			
			view.addNode(user.id(), user.version(), ids);
		}
		
		ViewerPipe fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(new ViewerListener() {
			@Override
			public void buttonPushed(String id) {
				// unused as only a release event indicates a complete click
			}

			@Override
			public void buttonReleased(String id) {	
				System.out.println("Button pushed on node "+id);
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

}
