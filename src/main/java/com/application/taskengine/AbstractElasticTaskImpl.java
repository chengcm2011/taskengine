package com.application.taskengine;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 弹性任务父类
 * cheng
 * 2017-06-18
 */
public class AbstractElasticTaskImpl implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

    }
}
