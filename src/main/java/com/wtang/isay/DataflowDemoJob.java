package com.wtang.isay;

import com.application.taskengine.AbstractElasticTaskImpl;
import com.application.taskengine.common.JobConfig;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.ApplicationLogger;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 */
@Component
@JobConfig(value = "0/10 * * * * ?", jobType = JobType.DATAFLOW, shardingTotalCount = 1)
public class DataflowDemoJob extends AbstractElasticTaskImpl {
    @Override
    public void execute(ShardingContext shardingContext, String taskKey, Map<String, Object> taskParams) {
        ApplicationLogger.info(TimeToolkit.getCurrentTs() + ":" + shardingContext.getJobParameter());
    }
}
