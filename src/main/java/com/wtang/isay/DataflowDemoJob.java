package com.wtang.isay;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

/**
 *
 */
public class DataflowDemoJob implements DataflowJob {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {

    }
}
