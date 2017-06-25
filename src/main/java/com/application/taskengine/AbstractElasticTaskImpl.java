package com.application.taskengine;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.util.List;

/**
 * 弹性任务父类
 * cheng
 * 2017-06-18
 */
public class AbstractElasticTaskImpl implements DataflowJob, SimpleJob {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List data) {

    }

    @Override
    public void execute(ShardingContext shardingContext) {

    }
}
