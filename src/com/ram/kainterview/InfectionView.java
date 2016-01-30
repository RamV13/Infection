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
import java.util.List;

import javax.swing.JFrame;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.Viewer;

/**
 * View for the infection model
 */
public class InfectionView extends JFrame implements GraphView {
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
	
	/**
	 * Graph instance for containing visualizable nodes
	 */
	private Graph graph;
	
	/**
	 * Viewer associated with this graph
	 */
	private Viewer viewer;

	public InfectionView(String title) {
		setTitle(title);

		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (screen.width*WIDTH_FRACTION); 
		height = (int) (screen.height*HEIGHT_FRACTION);
		setSize(width,height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initGraph(title);
		
		this.setVisible(true);
	}

	/**
	 * Initializes the graph with the given title
	 * @param title the title
	 */
	private void initGraph(String title) {
		System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		graph = new SingleGraph(title);
		graph.setStrict(true);
		
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "graph {fill-color: black;}"
				+ "node {size: 20px;fill-color: white;text-alignment: center;}"
				+ "edge { fill-color: gray; }");
		
		viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();
		DefaultView view = (DefaultView) viewer.addDefaultView(false);
		view.getCamera().setAutoFitView(true);
		view.getCamera().resetView();
		this.add(view);
	}
	
	@Override
	public void initController(GraphController controller) {
		controller.init(this, graph, viewer);
	}

	@Override
	public void addNode(String id, int label, List<String> toIds, 
			List<String> fromIds) {
		Node node = graph.addNode(id);
		node.setAttribute("ui.label", label);
		
		for (String s : toIds)
			if (graph.getNode(s) != null)
				graph.addEdge(s+id, s, id, true);
		
		for (String s : fromIds)
			if (graph.getNode(s) != null)
				graph.addEdge(id+s, id, s, true);
	}

	@Override
	public void updateNode(String id, int label) {
		graph.getNode(id).setAttribute("ui.label", label);
	}

}
