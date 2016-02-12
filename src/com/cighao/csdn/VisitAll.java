package com.cighao.csdn;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * 访问所有文章的子窗口
 */
public class VisitAll extends JPanel {
	private static int WIDTH = 280;
	private static int HEIGHT = 300;
	private JFrame mainFrame;

	public VisitAll() {
		init();
		this.setLocation(0, 0);
		this.setSize(WIDTH, HEIGHT);
		this.setBorder(BorderFactory.createTitledBorder("刷所有文章"));
	}
	/*
	 * 判断字符串是不是纯数字
	 */
	public boolean strIsDig(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public void init() {
		this.setLayout(null);
		JLabel username = new JLabel("CSDN用户名");
		username.setSize(80, 50);
		username.setLocation(75, 98);
		this.add(username);
		final JTextField usernameInput = new JTextField();
		usernameInput.setSize(90, 25);
		usernameInput.setLocation(155, 110);
		this.add(usernameInput);
		JLabel count = new JLabel("每篇文章访问的次数");
		count.setSize(130, 50);
		count.setLocation(30, 148);
		this.add(count);
		final JTextField countInput = new JTextField();
		countInput.setSize(90, 25);
		countInput.setLocation(155, 160);
		this.add(countInput);
		JButton start = new JButton("开始");
		start.setSize(60, 30);
		start.setLocation(120, 210);
		this.add(start);
		// 添加start按钮事件
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int num = 0;
				String name = usernameInput.getText().trim();
				String numStr = countInput.getText().trim();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null, "请输入用户名", "", JOptionPane.ERROR_MESSAGE);
				}else if(numStr.equals("")){
					JOptionPane.showMessageDialog(null, "请输入需要刷的次数", "", JOptionPane.ERROR_MESSAGE);
				}else if(!strIsDig(numStr)){
					JOptionPane.showMessageDialog(null, "次数不合法，必须为整数", "", JOptionPane.ERROR_MESSAGE);
				}else if(Integer.parseInt(numStr)==0){
					JOptionPane.showMessageDialog(null, "次数不能为0", "", JOptionPane.ERROR_MESSAGE);
				}else{
					num = Integer.parseInt(numStr);					
					GetBlog csdn = new GetBlog(name);
					csdn.visitAll(num);
				}
				
			}
		});
	}

}
