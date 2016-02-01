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

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * Interface for the controller of the model for infection
 */
public interface InfectionController extends GraphController {
	
	/**
	 * Registers the infect text field for the number of users
	 * @param infectTextField the text field for the number of users
	 */
	public void registerInfectField(JTextField infectTextField);
	
	/**
	 * Registers the infect button
	 * @param infect the infect button
	 */
	public void registerInfectButton(JButton infect);
	
	/**
	 * Registers the execute button
	 * @param execute the execute button
	 */
	public void registerExecuteButton(JButton execute);
	
	/**
	 * Registers the help button
	 * @param help the help button
	 */
	public void registerHelpButton(JButton help);
	
}
