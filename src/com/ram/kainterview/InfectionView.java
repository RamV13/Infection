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

import java.awt.Dimension;

import javax.swing.JFrame;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;

/**
 * View for the infection model
 */
public class InfectionView extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Fraction of the screen width to use
	 */
	private static final double WIDTH_FRACTION = 0.8;

	/**
	 * Fraction of the screen height to use
	 */
	private static final double HEIGHT_FRACTION = 0.8;

	/**
	 * Window width
	 */
	private int width;

	/**
	 * Window height
	 */
	private int height;

	private static boolean loop = true;

	public InfectionView() {
		setTitle("Total Infection");

		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (screen.width*WIDTH_FRACTION); 
		height = (int) (screen.height*HEIGHT_FRACTION);
		setSize(width,height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initGraph();
	}

	public void initGraph() {
		Graph graph = new SingleGraph("Test Graph");
		
		// TODO connect graph to model data
		graph.addNode("A" ).addAttribute("version", "1");
		graph.addNode("B" ).addAttribute("version", "1");
		graph.addNode("C" ).addAttribute("version", "1");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "graph { fill-color: black; }"
				+ "node { size: 20px; fill-color: white; text-color: white; }"
				+ "edge { fill-color: gray; }");
		
		for (Node node : graph)
			node.setAttribute("ui.label",(String) node.getAttribute("version"));
		
		Viewer viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		View view = viewer.addDefaultView(false);
		this.add(view);

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
