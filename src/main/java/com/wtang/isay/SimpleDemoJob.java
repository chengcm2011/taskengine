package com.wtang.isay;

import com.application.taskengine.common.JobConfig;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.ApplicationLogger;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * 简单重复的任务
 */
@Component
@JobConfig(value = "0/5 * * * * ?", jobType = JobType.SIMPLE, shardingTotalCount = 1)
public class SimpleDemoJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        ApplicationLogger.info(TimeToolkit.getCurrentTs() + ":" + shardingContext.getJobParameter());
    }
}
