package com.application.taskengine.action;

import com.application.console.service.JobAPIService;
import com.application.console.service.impl.JobAPIServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import com.google.common.base.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by cheng on 17/6/12.
 */
@RestController
public class ConsoleAction extends BusinessCommonAction {

    private JobAPIService jobAPIService = new JobAPIServiceImpl();

    /**
     * 获取作业总数.
     *
     * @return 作业总数
     */
    @RequestMapping("/management/console/getJobsTotalCount")
    public int getJobsTotalCount() {
        return jobAPIService.getJobStatisticsAPI().getJobsTotalCount();
    }

    /**
     * 获取作业详情.
     *
     * @return 作业详情集合
     */
    @RequestMapping("/management/console/getAllJobsBriefInfo")
    public Collection<JobBriefInfo> getAllJobsBriefInfo() {
        return jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
    }

    /**
     * 触发作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("/management/console/{jobName}/trigger")
    public void triggerJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().trigger(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 禁用作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("/management/console/{jobName}/disable")
    public void disableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 启用作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("/management/console/{jobName}/enableJob")
    public void enableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 终止作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("/management/console/{jobName}/shutdown")
    public void shutdownJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 获取分片信息.
     *
     * @param jobName 作业名称
     * @return 分片信息集合
     */
    @RequestMapping("/management/console/{jobName}/sharding")
    public Collection<ShardingInfo> getShardingInfo(@PathVariable("jobName") final String jobName) {
        return jobAPIService.getShardingStatisticsAPI().getShardingInfo(jobName);
    }

    @RequestMapping("/management/console/{jobName}/sharding/{item}/disable")
    public void disableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().disable(jobName, item);
    }

    @RequestMapping("/management/console/{jobName}/sharding/{item}/enable")
    public void enableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().enable(jobName, item);
    }
}
