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

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;

/**
 * Interface for the controller that listens for events on the graph
 */
public interface GraphController {

	/**
	 * Initializes the controller given the graph
	 * @param view the GraphView instance tied to this graph
	 * @param graph the graph
	 * @param viewer the viewer tied to this graph
	 */
	public void init(GraphView view, Graph graph, Viewer viewer);
	
}
