package com.application.taskengine.action;

import com.application.console.service.JobAPIService;
import com.application.console.service.impl.JobAPIServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import com.google.common.base.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by cheng on 17/6/12.
 */
@RestController
@RequestMapping("/management/console/")
public class TestAction extends BusinessCommonAction {

    private JobAPIService jobAPIService = new JobAPIServiceImpl();

    /**
     * 获取作业总数.
     *
     * @return 作业总数
     */
    @RequestMapping("getJobsTotalCount")
    public int getJobsTotalCount() {
        return jobAPIService.getJobStatisticsAPI().getJobsTotalCount();
    }

    /**
     * 获取作业详情.
     *
     * @return 作业详情集合
     */
    @RequestMapping("getAllJobsBriefInfo")
    public Collection<JobBriefInfo> getAllJobsBriefInfo() {
        return jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
    }

    /**
     * 触发作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping("triggerJob")
    public void triggerJob(@PathParam("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().trigger(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 禁用作业.
     *
     * @param jobName 作业名称
     */
    @POST
    @Path("/{jobName}/disable")
    @Consumes(MediaType.APPLICATION_JSON)
    public void disableJob(@PathParam("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 启用作业.
     *
     * @param jobName 作业名称
     */
    @DELETE
    @Path("/{jobName}/disable")
    @Consumes(MediaType.APPLICATION_JSON)
    public void enableJob(@PathParam("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 终止作业.
     *
     * @param jobName 作业名称
     */
    @POST
    @Path("/{jobName}/shutdown")
    @Consumes(MediaType.APPLICATION_JSON)
    public void shutdownJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.<String>absent());
    }

    /**
     * 获取分片信息.
     *
     * @param jobName 作业名称
     * @return 分片信息集合
     */
    @GET
    @Path("/{jobName}/sharding")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ShardingInfo> getShardingInfo(@PathParam("jobName") final String jobName) {
        return jobAPIService.getShardingStatisticsAPI().getShardingInfo(jobName);
    }

    @POST
    @Path("/{jobName}/sharding/{item}/disable")
    @Consumes(MediaType.APPLICATION_JSON)
    public void disableSharding(@PathParam("jobName") final String jobName, @PathParam("item") final String item) {
        jobAPIService.getShardingOperateAPI().disable(jobName, item);
    }

    @DELETE
    @Path("/{jobName}/sharding/{item}/disable")
    @Consumes(MediaType.APPLICATION_JSON)
    public void enableSharding(@PathParam("jobName") final String jobName, @PathParam("item") final String item) {
        jobAPIService.getShardingOperateAPI().enable(jobName, item);
    }
}
