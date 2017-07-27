package com.wtang.isay;

import com.application.taskengine.AbstractElasticTaskImpl;
import com.application.taskengine.common.JobConfig;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.ApplicationLogger;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 简单重复的任务
 */
@Component
@JobConfig(value = "0/10 * * * * ?", shardingTotalCount = 2)
public class SimpleDemoJob extends AbstractElasticTaskImpl {
    @Override
    public void execute(ShardingContext shardingContext) {
        ApplicationLogger.info(TimeToolkit.getCurrentTs() + ":" + shardingContext.getJobParameter());
    }

    @Override
    public void execute(ShardingContext shardingContext, String taskKey, Map<String, Object> taskParams) {

    }
}
