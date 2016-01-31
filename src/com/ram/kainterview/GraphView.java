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

import java.util.List;

/**
 * Interface for modifying the visual graph
 */
public interface GraphView {

	/**
	 * Adds a node to the graph
	 * @param id the id of the node
	 * @param label the label of the node
	 * @param toIds the id's of the nodes that are connected to this node
	 * @param fromIds the id's of the nodes that are connected from this node
	 */
	public void addNode(String id, int label, List<String> toIds, 
			List<String> fromIds);
	
	/**
	 * Updates the label of the specified node
	 * @param id the id of the node
	 * @param label the new label
	 */
	public void updateNode(String id, int label);
	
}
