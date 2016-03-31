package com.ace.core.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;

import com.ace.core.util.QiNiuUtils;

/**
 * Trigger to consume data from Nucleon Event Bus
 * 
 * @author Josh Wang(Sheng)
 * 
 * @email swang6@ebay.com
 */
@Component("processor")
public class NucleonEventProcessor extends HttpServlet {

	private static final long serialVersionUID = -9045451275234606838L;

	// Servlet的init方法会在Tomcat启动的时候执行
	@Override
	public void init() throws ServletException {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						start(); // 使用另一个线程来执行该方法，会避免占用Tomcat的启动时间
						return "Collection Completed";
					}

				});

		new Thread(task).start();
	}

	// 希望Tomcat启动结束后执行的方法
	private void start() {
		QiNiuUtils.genUptoken();
	}

}