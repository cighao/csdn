package com.cighao.csdn;

import javax.swing.JFrame;
import javax.swing.JTextField;
/*
 * 主窗口类
 */
public class CsdnMain extends JFrame {
	private static int WIDTH = 600;
	private static int HEIGHT = 500;
	public static void main(String[] args) {
		CsdnMain csdn = new CsdnMain();
		
	}
	public CsdnMain(){
		this.setTitle("CSDN刷访问量");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null); //不使用布局管理器，自定义位置
		this.setResizable(false);
		VisitAll va = new VisitAll();
		this.add(va);
		VisitOne vo = new VisitOne();
		this.add(vo);
		View view = new View();
		this.add(view);
		this.setVisible(true);
	}
	
}
