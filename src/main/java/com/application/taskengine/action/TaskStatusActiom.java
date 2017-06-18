package com.application.taskengine.action;

import com.application.console.service.JobAPIService;
import com.application.console.service.impl.JobAPIServiceImpl;
import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.cheng.common.AjaxDone;
import com.cheng.util.BeanUtil;
import com.cheng.util.Predef;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by cheng on 16/5/14.
 */
@Controller
@RequestMapping("/management/task/status")
public class TaskStatusActiom extends BusinessCommonAction {

    private JobAPIService jobAPIService = new JobAPIServiceImpl();

    @Resource
    ITaskService taskService;

    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) throws Exception {

        Collection<JobBriefInfo> jobBriefInfos = jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
        if (jobBriefInfos == null || jobBriefInfos.isEmpty()) {
            return "/management/task/status/index";
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for (JobBriefInfo jobBriefInfo : jobBriefInfos) {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, Predef.toStr(jobBriefInfo.getJobName()));
            if (taskDeployModel == null) {
                continue;
            }
            Map<String, Object> item = BeanUtil.getValueMap(jobBriefInfo);
            item.put("jobCode", jobBriefInfo.getJobName());
            item.put("jobName", taskDeployModel.getTaskName());
            data.add(item);
        }
        model.addAttribute(DATA, data);
        return "/management/task/status/index1";
    }

    @RequestMapping("shardinginfo")
    public String shardinginfo(HttpServletRequest request, String pk, Model model) throws Exception {

        Collection<ShardingInfo> shardingInfos = jobAPIService.getShardingStatisticsAPI().getShardingInfo(pk);

        model.addAttribute(DATA, shardingInfos);
        return "/management/task/status/shardinginfo";
    }


    @RequestMapping("/pause")
    @ResponseBody
    public AjaxDone pause(HttpServletRequest request, String pk, Model model) throws Exception {

        TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
        if (taskDeployModel == null) {
            return AjaxDoneSuccNotcloseCurrent("要停止的任务有误！");
        }
        try {
            taskService.pause(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("暂停成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("暂停失败");
        }


    }

    @RequestMapping("/resume")
    @ResponseBody
    public AjaxDone resume(HttpServletRequest request, String pk, Model model) throws Exception {
        TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
        if (taskDeployModel == null) {
            return AjaxDoneSuccNotcloseCurrent("任务有误！");
        }
        try {
            taskService.resume(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("恢复成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("恢复失败");
        }
    }

    @RequestMapping("/trigger")
    @ResponseBody
    public AjaxDone trigger(HttpServletRequest request, String pk, Model model) throws Exception {
        TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
        if (taskDeployModel == null) {
            return AjaxDoneSuccNotcloseCurrent("任务有误！");
        }
        try {
            taskService.runOnceTask(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("执行成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("执行失败");
        }
    }
}
