package com.wtang.isay;

import com.application.taskengine.AbstractElasticTaskImpl;
import com.application.taskengine.common.JobConfig;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.ApplicationLogger;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
@JobConfig(value = "0/10 * * * * ?", jobType = JobType.DATAFLOW, shardingTotalCount = 1)
public class DataflowDemoJob extends AbstractElasticTaskImpl {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        return Lists.newArrayList(TimeToolkit.getCurrentTs());
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {

        ApplicationLogger.info(list.get(0).toString());
    }
}
