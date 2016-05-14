package com.web.common;

import com.application.config.contextloader.IUserDefinedApplicationContextLoaderListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;

/**
 * Created by cheng on 2015/8/25.
 */
@Component
public class UserDefinedApplicationContextLoader implements IUserDefinedApplicationContextLoaderListener {
	@Override
	public void definedContextInitialized(ServletContextEvent servletContextEvent) {
		initTaskCentry(servletContextEvent);
	}

	@Override
	public void definedContextDestroyed(ServletContextEvent servletContextEvent) {

	}
	/**
	 * 初始化任务中心
	 * @author cheng
	 * 2014年7月27日
	 * 下午9:28:15
	 * @param event
	 */
	private void initTaskCentry(ServletContextEvent event) {

	}
}
