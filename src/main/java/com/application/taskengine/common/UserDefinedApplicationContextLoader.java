package com.application.taskengine.common;

import com.application.taskengine.itf.ITaskService;
import com.cheng.web.ApplicationContextLoaderListener;
import com.cheng.web.ApplicationServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;

/**
 *
 */
@Component
public class UserDefinedApplicationContextLoader extends ApplicationContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedApplicationContextLoader.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        initTaskCentry(servletContextEvent);
    }


    /**
     * 初始化任务中心
     *
     * @param event
     * @author cheng
     * 2014年7月27日
     * 下午9:28:15
     */
    private void initTaskCentry(ServletContextEvent event) {
        logger.info("task init");
        ApplicationServiceLocator.getBean(ITaskService.class).initElasticTask();
//        ApplicationServiceLocator.getBean(IElasticJobInit.class).JobInit();
    }
}
