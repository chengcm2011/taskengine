package com.application.taskengine.common;

import com.alibaba.fastjson.JSON;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.application.taskengine.model.TaskPluginModel;
import com.cheng.jdbc.SQLParameter;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.jdbc.opt.Condition;
import com.cheng.jdbc.opt.Query;
import com.cheng.lang.ClassUtil;
import com.cheng.lang.exception.BusinessException;
import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * job 初始化 替换spring 方式
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ElasticJobInit {

    @Resource
    IBaseDAO baseDAO;

    public void JobInit() throws BusinessException {
        // 连接注册中心
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        regCenter.init();
        // 启动简单作业
        initJob(null, regCenter);
    }

    /**
     * @param pkJob
     * @throws Exception
     */
    public void JobInit(String pkJob) throws BusinessException {
        // 连接注册中心
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        // 启动简单作业
        initJob(pkJob, regCenter);
    }

    private void initJob(String pkJob, CoordinatorRegistryCenter regCenter) throws BusinessException {
        List<JobInfo> data = getJobConfigFromDB(pkJob);
        // 启动作业
        for (JobInfo jobInfo : data) {
            LiteJobConfiguration liteJobConfiguration = JobConfigurationFactory.createJobConfiguration(jobInfo);
            new JobScheduler(regCenter, liteJobConfiguration).init();
        }

    }

    private List<JobInfo> getJobConfigFromDB(String pkJob) throws BusinessException {

        List<JobInfo> jobInfos = new LinkedList<>();
        Query query = Query.query(Condition.eq("runnable", "Y")).eq("dr", 0);
        if (StringUtils.isNotBlank(pkJob)) {
            query.eq("pkTaskdeploy", pkJob);
        }
        List<TaskDeployModel> tasks = baseDAO.queryByClause(TaskDeployModel.class, query);
        for (TaskDeployModel task : tasks) {
            JobInfo jobInfo = new JobInfo();
            jobInfo.setCron(task.getCronExpression());
            jobInfo.setDescription(task.getTaskDescription());
            TaskPluginModel taskPluginModel = baseDAO.queryByPK(TaskPluginModel.class, task.getPkTaskplugin(), new String[]{"pluginclass"});
            jobInfo.setJobClass(ClassUtil.initClass1(taskPluginModel.getPluginclass()));
            jobInfo.setJobName(task.getTaskName());
            jobInfo.setJobCode(task.getPrimaryKey());
            jobInfo.setJobParameter(getJobParameter(task));
            jobInfo.setJobType(JobType.valueOf(task.getTaskType()));
            jobInfo.setOverwrite(true);
            jobInfo.setShardingTotalCount(2);
            jobInfos.add(jobInfo);

        }
        return jobInfos;
    }

    private String getJobParameter(TaskDeployModel taskDeployModel) throws BusinessException {
        Map<String, Object> taskunitmap = new HashMap<>();
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(taskDeployModel.getPrimaryKey());
        Query query = Query.query(Condition.eq("pkTaskdeploy", taskDeployModel.getPrimaryKey()));
        query.eq("dr", 0);
        List<TaskParamValueModel> taskParamValueModels = baseDAO.queryByClause(TaskParamValueModel.class, query);
        for (int j = 0; j < taskParamValueModels.size(); j++) {
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskunitmap.put(t.getParamkey(), t.getParamvalue());
        }
        taskunitmap.put(taskDeployModel.getPKFieldName(), taskDeployModel.getPrimaryKey());
        return JSON.toJSONString(taskunitmap);
    }

    /**
     * 获取作业配置
     *
     * @return
     * @throws BeansException
     */
    private List<JobInfo> getJobConfig() throws BeansException {
        List<JobInfo> jobInfos = new LinkedList();
        Map<String, Object> beanDefinitionNames = ApplicationServiceLocator.getWebApplicationContext().getBeansWithAnnotation(JobConfig.class);
        Iterator<Map.Entry<String, Object>> set = beanDefinitionNames.entrySet().iterator();
        while (set.hasNext()) {
            JobInfo jobInfo = new JobInfo();
            Map.Entry<String, Object> objectEntry = set.next();
            JobConfig jobConfig = objectEntry.getValue().getClass().getAnnotation(JobConfig.class);
            if (StringUtils.isBlank(jobConfig.jobCode())) {
                jobInfo.setJobCode(objectEntry.getValue().getClass().getSimpleName());
            } else {
                jobInfo.setJobCode(jobConfig.jobCode());
            }
            if (StringUtils.isBlank(jobConfig.jobName())) {
                jobInfo.setJobName(jobConfig.jobCode());
            } else {
                jobInfo.setJobName(jobInfo.getJobCode());
            }
            jobInfo.setCron(jobConfig.value());
            jobInfo.setJobClass(objectEntry.getValue().getClass());
            jobInfo.setJobType(jobConfig.jobType());
            jobInfo.setShardingTotalCount(jobConfig.shardingTotalCount());
            jobInfo.setOverwrite(jobConfig.overwrite());
            jobInfo.setDescription(jobConfig.description());
            jobInfos.add(jobInfo);
        }
        return jobInfos;
    }

    /**
     * job 配置
     */
    public class JobInfo {

        /**
         * 任务名称
         */
        private String jobName;
        /**
         * 任务编码
         */
        private String jobCode;
        /**
         * 任务类型
         */
        private JobType jobType;

        /**
         * 执行类
         */
        private Class jobClass;

        /**
         * 表达式
         */
        private String cron;

        /**
         * 分片数
         */
        private int shardingTotalCount;

        /**
         * 任务参数
         */
        private String jobParameter;

        public String getJobParameter() {
            return jobParameter;
        }

        public void setJobParameter(String jobParameter) {
            this.jobParameter = jobParameter;
        }

        private boolean overwrite;

        private String description;

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public JobType getJobType() {
            return jobType;
        }

        public void setJobType(JobType jobType) {
            this.jobType = jobType;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public int getShardingTotalCount() {
            return shardingTotalCount;
        }

        public Class getJobClass() {
            return jobClass;
        }

        public void setJobClass(Class jobClass) {
            this.jobClass = jobClass;
        }

        public boolean isOverwrite() {
            return overwrite;
        }

        public void setOverwrite(boolean overwrite) {
            this.overwrite = overwrite;
        }

        public String getDescription() {
            return description;
        }

        public String getJobCode() {
            return jobCode;
        }

        public void setJobCode(String jobCode) {
            this.jobCode = jobCode;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setShardingTotalCount(int shardingTotalCount) {
            this.shardingTotalCount = shardingTotalCount;
        }
    }
}
