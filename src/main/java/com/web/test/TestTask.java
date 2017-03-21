package com.web.test;

import com.application.taskengine.AbstractTaskImpl;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.Predef;
import org.quartz.JobDataMap;

/**
 * 测试任务
 */
public class TestTask extends AbstractTaskImpl {

    @Override
    public String doexecute(JobDataMap jobDataMap) throws BusinessException {
        Predef.p(jobDataMap);
//        Predef.p("get conf sdsdsdsd: " + ConfClient.getInstance().get("sdsdsdsd", "没有获取到配置"));
        return SUCCESS;
    }
}
