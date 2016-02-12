package com.cighao.csdn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GetBlog {
	private String username; // csdn 登录账号
	public GetBlog(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/*
	 * 获取当前用户文章的总页数
	 */
	public int getPageNum() throws Exception {
		int num = 0;
		String url = "http://blog.csdn.net/" + username; // 博客主页
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			java.io.InputStream is = entity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String info = null;
			String pattern = "<span>.*?条数据.*?共(.*?)页</span>"; // 获取总页数
			Pattern r = Pattern.compile(pattern);
			while ((info = br.readLine()) != null) { // 循环读取页面信息
				// System.out.println(info);
				Matcher m = r.matcher(info);
				if (m.find()) {
					num = Integer.parseInt(m.group(1));
					break;
				}
			}
		}
		return num;
	}

	/*
	 * 获取第 num 页所有文章的url和标题
	 */
	public Map<String, String> getArticleByPage(int num) throws Exception {
		Map<String, String> articles = new HashMap<String, String>();
		String url = "http://blog.csdn.net/" + username + "/article/list/"
				+ num; // 第num页文章
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String content = ""; // 保存整个网页的内容
		if (entity != null) {
			java.io.InputStream is = entity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String info = null;
			while ((info = br.readLine()) != null) { // 循环读取客户端的信息
				content = content + info + "\n";
			}
			// System.out.println(content);
		}
		// 获取文章url和标题
		String pattern = "<span class=\"link_title\"><a href=\"/" + username
				+ "/article/details/(.*?)\">" + "\n(.*?)\n";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		while (m.find()) {
			String id = m.group(1);
			String articleURL = "http://blog.csdn.net/" + username + "/article/details/" + id;
			String title = m.group(2);
			title = title.trim();
			articles.put(articleURL, title);
		}
		return articles;
	}

	/*
	 * 刷所有文章num次
	 */
	public void visitAll(int num) {
		int pageNum = 0;
		try {
			pageNum = getPageNum(); // 文章的总页数
			for (int i = 1; i <= pageNum; i++) {  //每页一个线程去刷
				Map<String, String> articles = getArticleByPage(i);
				VisitThread vt = new VisitThread(articles, num);
				vt.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据标题刷某一篇文章num次
	 */
	public void visitOne(String title,int num){
		int pageNum = 0;
		try {
			pageNum = getPageNum(); // 文章的总页数
			Map<String, String> specialArt = new HashMap<String, String>() ; //该篇文章的集合，考虑可能多篇文章同一个标题的情况
			for (int i = 1; i <= pageNum; i++) {  //每页一个线程去刷
				Map<String, String> articles = getArticleByPage(i);
				for (String url : articles.keySet()){
					if(title.equals(articles.get(url))){
						specialArt.put(url, title);
					}
				}
			}
			while(num>0){
				if(num>=50){
					VisitThread vt = new VisitThread(specialArt, num);
					vt.start();
				}else{
					VisitThread vt = new VisitThread(specialArt, num);
					vt.start();
				}
				num = num - 50;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		GetBlog b = new GetBlog("cighao");
//		b.visitAll(1);
//		//b.visitOne("Java 输入/输出流", 1);;
//		while(true);
//	}
}
