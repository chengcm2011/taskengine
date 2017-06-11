package com.application.taskengine.common;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.wtang.isay.DataflowDemoJob;
import com.wtang.isay.SimpleDemoJob;

/**
 * 创建任务
 */
public class JobConfigurationFactory {
    public static LiteJobConfiguration createJobConfiguration(ElasticJobInit.JobInfo jobInfo) {
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(jobInfo.getJobName(), jobInfo.getCron(), jobInfo.getShardingTotalCount()).build();

        if (jobInfo.getJobType().equals(JobType.SIMPLE)) {
            return createSimpleJobConfiguration(coreConfiguration, jobInfo);
        }
        if (jobInfo.getJobType().equals(JobType.DATAFLOW)) {
            return createDataflowJobConfiguration(coreConfiguration, jobInfo);
        }
//        if(jobInfo.getJobType().equals(JobType.SCRIPT)){
//            return createScriptJobConfiguration(coreConfiguration,jobInfo);
//        }
        throw new RuntimeException(" not support " + jobInfo.getJobType().name());
    }


    private static LiteJobConfiguration createSimpleJobConfiguration(JobCoreConfiguration coreConfiguration, ElasticJobInit.JobInfo jobInfo) {
        // 创建作业配置
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/10 * * * * ?", 1).jobParameter("{\"code\":\"20000\",\"message\":\"success\",\"success\":true}").build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, SimpleDemoJob.class.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;
    }

    private static LiteJobConfiguration createDataflowJobConfiguration(JobCoreConfiguration coreConfiguration, ElasticJobInit.JobInfo jobInfo) {

        // 定义作业核心配置
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("demoDataflowJob", "0/30 * * * * ?", 10).build();
        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, DataflowDemoJob.class.getCanonicalName(), false);
        // 定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfig).build();
        return dataflowJobRootConfig;
    }

    private static LiteJobConfiguration createScriptJobConfiguration(JobCoreConfiguration coreConfiguration, ElasticJobInit.JobInfo jobInfo) {
        // 定义SCRIPT类型配置
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(coreConfiguration, "test.sh");
        // 定义Lite作业根配置
        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfig).build();
        return scriptJobRootConfig;
    }


}