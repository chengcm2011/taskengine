package com.web.test;

import com.application.taskengine.AbstractTaskImpl;
import com.cheng.lang.exception.BusinessException;
import com.wtang.isay.SimpleDemoJob;
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
//            RentContractModel accountNOModel = new RentContractModel();
//            accountNOModel.setSuccess(UFBoolean.FALSE);
//            accountNOModel.setTs(TimeToolkit.getCurrentTs());
//            accountNOModel.setDr(0);
//            accountNOModel.setAid(Predef.toInt(dd,0));
//            dataBaseService.insert(accountNOModel);
//        }
        return SUCCESS;
    }

    public static void main(String[] args) {
        System.out.println(SimpleDemoJob.class.getCanonicalName());
        System.out.println(SimpleDemoJob.class.getName());
    }
}
