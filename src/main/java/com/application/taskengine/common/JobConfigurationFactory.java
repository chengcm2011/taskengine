package com.application.taskengine.common;

import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;

/**
 * 创建任务
 */
public class JobConfigurationFactory {
    public static LiteJobConfiguration createJobConfiguration(ElasticJobInit.JobInfo jobInfo) {
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(jobInfo.getJobCode(), jobInfo.getCron(), jobInfo.getShardingTotalCount()).jobParameter(jobInfo.getJobParameter()).build();

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
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfiguration, jobInfo.getJobClass().getCanonicalName());

        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(jobInfo.isOverwrite()).build();

        return simpleJobRootConfig;
    }

    private static LiteJobConfiguration createDataflowJobConfiguration(JobCoreConfiguration coreConfiguration, ElasticJobInit.JobInfo jobInfo) {

        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(coreConfiguration, jobInfo.getJobClass().getCanonicalName(), false);

        // 定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfig).overwrite(jobInfo.isOverwrite()).build();

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
