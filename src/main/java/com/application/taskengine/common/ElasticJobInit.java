package com.application.taskengine.common;

import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * job 初始化 替换spring 方式
 */
@Component
public class ElasticJobInit implements IElasticJobInit {

    public void JobInit() {
        // 连接注册中心
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        regCenter.init();
        // 启动简单作业
        initJob(regCenter);
    }

    private void initJob(CoordinatorRegistryCenter regCenter) {
        List<JobInfo> data = getJobConfig();
        // 启动作业
        for (JobInfo jobInfo : data) {
            LiteJobConfiguration liteJobConfiguration = JobConfigurationFactory.createJobConfiguration(jobInfo);
            new JobScheduler(regCenter, liteJobConfiguration).init();
        }

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
            if (StringUtils.isBlank(jobConfig.jobName())) {
                jobInfo.setJobName(objectEntry.getValue().getClass().getSimpleName());
            } else {
                jobInfo.setJobName(jobConfig.jobName());
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

        public void setDescription(String description) {
            this.description = description;
        }

        public void setShardingTotalCount(int shardingTotalCount) {
            this.shardingTotalCount = shardingTotalCount;
        }
    }
}
