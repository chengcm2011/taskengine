package com.web.common;

import com.application.config.contextloader.IUserDefinedApplicationContextLoaderListener;
import com.web.task.AbstractTaskFactory;
import com.web.task.DefaultTaskFactory;
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
		try {
			AbstractTaskFactory factory = AbstractTaskFactory.getFactory();
			factory.stopTaskCentery();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 初始化任务中心
	 * @author cheng
	 * 2014年7月27日
	 * 下午9:28:15
	 * @param event
	 */
	private void initTaskCentry(ServletContextEvent event) {
		try {
			AbstractTaskFactory factory = AbstractTaskFactory.getFactory();
			factory.initTasks();
			factory.startTaskCentery();
		} catch (Exception e) {
			e.printStackTrace();
			event.getServletContext().log("ConfigurationException: ", e);
		}
	}
}
