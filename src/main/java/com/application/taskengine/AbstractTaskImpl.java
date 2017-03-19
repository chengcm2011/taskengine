package com.application.taskengine;

import com.application.taskengine.model.TaskLogModel;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.SystemInfoUtil;
import com.cheng.web.ApplicationServiceLocator;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTaskImpl implements StatefulJob {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    protected Logger logger = LoggerFactory.getLogger("TASK_LOGGER");

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        final JobExecutionContext tt = arg0;
        String pkTaskdeploy = tt.getJobDetail().getJobDataMap().getString("pkTaskdeploy");
        TaskLogModel taskLogModel = new TaskLogModel();
        taskLogModel.setPkTaskdeploy(pkTaskdeploy);
        taskLogModel.setVdef1(TimeToolkit.getCurrentTs());

        taskLogModel.setRunserver(SystemInfoUtil.getInstance().getOs_name() + ":" + SystemInfoUtil.getInstance().getOs_mac() + ":" + SystemInfoUtil.getInstance().getOs_ip());
        logger.info("task run begin time ：" + taskLogModel.getVdef1());
        long b = System.currentTimeMillis();
        try {
            Object object = doexecute(tt.getJobDetail().getJobDataMap());
            taskLogModel.setIssuccess(true);
            taskLogModel.setReturnstr(object.toString());
            logger.info("task run result ：" + taskLogModel.getReturnstr());
        } catch (Exception e) {
            taskLogModel.setIssuccess(false);
            throw new JobExecutionException(e);
        } finally {
            taskLogModel.setRuntime((System.currentTimeMillis() - b) / 1000 + "s");
            taskLogModel.setVdef2(TimeToolkit.getCurrentTs());
            logger.info("task run end time ：" + taskLogModel.getVdef2());
            try {
                if (LogMap.ispersistence) {
                    ApplicationServiceLocator.getService(IDataBaseService.class).insert(taskLogModel);
                } else {
                    LogMap.addLog(pkTaskdeploy, taskLogModel);
                }
            } catch (Exception e) {
                logger.error("", e);
                throw new JobExecutionException(e);
            }
        }
    }

    public abstract String doexecute(JobDataMap jobDataMap) throws BusinessException;

}
