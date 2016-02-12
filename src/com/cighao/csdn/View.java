package com.cighao.csdn;

import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * 显示程序的运行状态
 */
public class View extends JPanel{
	private static int WIDTH = 580;
	private static int HEIGHT = 140;
	private JLabel [] contents = null;
	
	public JLabel[] getContents() {
		return contents;
	}
	public void setContents(JLabel[] contents) {
		this.contents = contents;
	}
	public View(){
		init();
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(0, 320);
		this.setBorder(BorderFactory.createTitledBorder("显示"));
	}
	public void init(){
		
	}
}
