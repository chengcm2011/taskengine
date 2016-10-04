package com.web.test;

import cheng.lib.exception.BusinessException;
import cheng.lib.util.Predef;
import com.application.taskengine.AbstractTaskImpl;
import org.quartz.JobDataMap;

/**
 * Created by cheng on 2015/10/23.
 */
public class TestTask extends AbstractTaskImpl {

	@Override
	public String doexecute(JobDataMap jobDataMap) throws BusinessException {
		Predef.p(jobDataMap);
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
