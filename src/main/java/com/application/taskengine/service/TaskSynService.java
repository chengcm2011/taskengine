package com.application.taskengine.service;

import com.application.console.JobAPIFactory;
import com.application.taskengine.model.TaskDeployModel;
import com.cheng.jdbcspring.IDataBaseService;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobSettings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 任务数据同步注册中心服务
 *
 * @author chengys4
 *         2017-07-28 17:14
 **/
@Component
public class TaskSynService {


    @Resource
    private TaskParamService taskParamService;
    @Resource
    private IDataBaseService dataBaseService;

    /**
     * 同步任务参数到注册中心
     *
     * @param pkTaskdeploy 任务主键
     * @return
     */
    public boolean synTaskParameter(String pkTaskdeploy) {

        TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pkTaskdeploy);
        if (taskDeployModel.getRunnable().equals("Y")) {
            JobSettings jobSettings = JobAPIFactory.createJobSettingsAPI().getJobSettings(pkTaskdeploy);
            jobSettings.setJobParameter(taskParamService.getTaskParameter(pkTaskdeploy));
            JobAPIFactory.createJobSettingsAPI().updateJobSettings(jobSettings);
        }
        return true;
    }
}
