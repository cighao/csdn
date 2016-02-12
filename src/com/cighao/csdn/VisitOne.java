package com.cighao.csdn;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * 刷指定博文
 */
public class VisitOne extends JPanel{
	private static int WIDTH = 280;
	private static int HEIGHT = 300;
	public VisitOne(){
		init();
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(300, 0);
		this.setBorder(BorderFactory.createTitledBorder("刷指定文章"));
	}
	
	public void init(){
		this.setLayout(null);
		JLabel username = new JLabel("CSDN用户名");
		username.setSize(80, 50);
		username.setLocation(30, 68);
		this.add(username);
		final JTextField usernameInput = new JTextField();
		usernameInput.setSize(90, 25);
		usernameInput.setLocation(110, 78);
		this.add(usernameInput);
		JLabel articleName = new JLabel("文章标题");
		articleName.setSize(80, 50);
		articleName.setLocation(50, 110);
		this.add(articleName);
		final JTextField articleInput = new JTextField();
		articleInput.setSize(160, 25);
		articleInput.setLocation(110, 122);
		this.add(articleInput);
		JLabel count = new JLabel("访问的次数");
		count.setSize(120, 50);
		count.setLocation(40, 150);
		this.add(count);
		final JTextField countInput = new JTextField();
		countInput.setSize(50, 25);
		countInput.setLocation(110, 160);
		this.add(countInput);
		JButton start = new JButton("开始");
		start.setSize(60,30);
		start.setLocation(120, 210);
		this.add(start);
		//添加start按钮事件
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int num = 0;
				String name = usernameInput.getText().trim();
				String title = articleInput.getText().trim();
				String numStr = countInput.getText().trim();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null, "请输入用户名", "", JOptionPane.ERROR_MESSAGE);
				}else if(title.equals("")){
					JOptionPane.showMessageDialog(null, "请输入文章标题", "", JOptionPane.ERROR_MESSAGE);
				}else if(numStr.equals("")){
					JOptionPane.showMessageDialog(null, "请输入需要刷的次数", "", JOptionPane.ERROR_MESSAGE);
				}else if(!strIsDig(numStr)){
					JOptionPane.showMessageDialog(null, "次数不合法，必须为整数", "", JOptionPane.ERROR_MESSAGE);
				}else if(Integer.parseInt(numStr)==0){
					JOptionPane.showMessageDialog(null, "次数不能为0", "", JOptionPane.ERROR_MESSAGE);
				}else{
					num = Integer.parseInt(numStr);					
					GetBlog csdn = new GetBlog(name);
					csdn.visitOne(title, num);;
				}				
			}
		});
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
}

