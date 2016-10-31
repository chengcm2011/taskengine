package com.web.test;

import cheng.lib.exception.BusinessException;
import cheng.lib.util.Predef;
import com.application.taskengine.AbstractTaskImpl;
import org.quartz.JobDataMap;

/**
 * 测试任务
 */
public class TestTask extends AbstractTaskImpl {

	@Override
	public String doexecute(JobDataMap jobDataMap) throws BusinessException {
		Predef.p(jobDataMap);
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
