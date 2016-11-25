package com.application.taskengine.action;

import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.util.DynamicSchedulerFactory;
import com.cheng.common.AjaxDone;
import com.cheng.util.Predef;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Created by cheng on 16/5/14.
 */
@Controller
@RequestMapping("/management/task/status")
public class TaskStatusActiom extends BusinessCommonAction {

    @Resource
    ITaskService taskService;

    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model) throws Exception {

        List<Map<String,Object>> data = DynamicSchedulerFactory.getJobList();
        if(data==null || data.isEmpty()){
            return "/management/task/status/index";
        }
        for(Map<String,Object> item:data){
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, Predef.toStr(item.get("jobCode")));
            item.put("jobName",taskDeployModel.getTaskname());
        }
        model.addAttribute(DATA,data);
        return "/management/task/status/index";
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
