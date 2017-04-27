package com.wtang.isay;

import com.cheng.lang.TimeToolkit;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 简单重复的任务
 */
public class SimpleDemoJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(TimeToolkit.getCurrentTs());
    }
}
