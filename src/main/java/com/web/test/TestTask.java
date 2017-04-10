package com.web.test;

import com.application.taskengine.AbstractTaskImpl;
import com.cheng.lang.exception.BusinessException;
import org.quartz.JobDataMap;

/**
 * 测试任务
 */
public class TestTask extends AbstractTaskImpl {

    @Override
    public String doexecute(JobDataMap jobDataMap) throws BusinessException {
//        IDataBaseService dataBaseService = ApplicationServiceLocator.getBean(IDataBaseService.class);
//        List<String> d = FileUtil.readFileByLines("D:\\用户账户\\Desktop\\init.txt");
//        for (String dd:d){
//            AccountNOModel accountNOModel = new AccountNOModel();
//            accountNOModel.setSuccess(UFBoolean.FALSE);
//            accountNOModel.setTs(TimeToolkit.getCurrentTs());
//            accountNOModel.setDr(0);
//            accountNOModel.setAid(Predef.toInt(dd,0));
//            dataBaseService.insert(accountNOModel);
//        }
        return SUCCESS;
    }
}
