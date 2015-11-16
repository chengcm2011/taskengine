package com.web.test;

import arch.util.lang.Predef;
import arch.util.toolkit.DBRunner;
import com.application.common.exception.BusinessException;
import com.web.task.AbstractTaskImpl;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cheng on 2015/10/23.
 */
public class TestTask extends AbstractTaskImpl {
	protected Logger logger = LoggerFactory.getLogger(TestTask.class);

	@Override
	public String doexecute(DBRunner dbRunner, JobDataMap jobDataMap) throws BusinessException {
		logger.info(" task run begin ");
		Predef.p(jobDataMap);
		logger.info(" task run end ");
		return "success";
	}
}
