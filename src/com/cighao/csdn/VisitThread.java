package com.cighao.csdn;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
/*
 * 该线程类主要是用来刷一组文章的访问量的
 * Map<String,String> 里面保存的是文章的 <文章url，文章标题的键值对>
 * num 是需要刷的次数
 */
public class VisitThread extends Thread {
	Map<String,String> articles;
	int num = 0;

	public VisitThread(Map<String,String> articles,int num) {
		this.articles = articles;
		this.num = num;
	}
	@Override
	public void run() {
		for(int i=0;i<num;i++){
			for (String url : articles.keySet()) {
				try {
					Thread.sleep(1000);// 间隔一秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("正在访问文章：" + articles.get(url));
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpget = new HttpGet(url);
				httpget.setHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
				HttpResponse response = null;
				try {
					response = httpclient.execute(httpget);
				} catch (IOException e) {
					e.printStackTrace();
				}
				HttpEntity entity = response.getEntity();
			}			
		}
	}
}
