package com.application.taskengine.action;

import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskPluginModel;
import com.cheng.common.AjaxDone;
import com.cheng.lang.PageVO;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务部署
 */
@Controller
@RequestMapping("/management/task/")
public class TaskDeployAction extends BusinessCommonAction {

    @Resource
    ITaskService taskService;

    @RequestMapping("deploy/index")
    public String deployindex(HttpServletRequest request, PageVO pageVO, Model model) throws Exception {

        StringBuilder con = new StringBuilder(" dr=0 ");

        String pkTaskplugin = request.getParameter("pkTaskplugin");
        if (StringUtils.isNotBlank(pkTaskplugin)) {
            con.append(" and pkTaskplugin='" + pkTaskplugin + "'");
        }
        String taskname = request.getParameter("taskname");
        if (StringUtils.isNotBlank(taskname)) {
            con.append(" and taskname like '%" + taskname + "'");

        }
        pageVO.setCondition(con.toString());
        pageVO = dataBaseService.queryByPage(TaskDeployModel.class, pageVO);
        List<TaskDeployModel> list = (List<TaskDeployModel>) pageVO.getData();
        List<Map<String, Object>> data = new ArrayList<>();
        for (TaskDeployModel taskDeployModel : list) {
            Map<String, Object> item = BeanUtil.getValueMap(taskDeployModel);
            TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, taskDeployModel.getPkTaskplugin());
            if (taskPluginModel != null) {
                item.put("pluginname", taskPluginModel.getPluginname());
            }
            data.add(item);
        }
        pageVO.setData(data);
        model.addAttribute("pageVO", pageVO);
        return "/management/task/deploy/index";
    }

    @RequestMapping("deploy/edit")
    public String detail(String pk, Model model) throws Exception {
        if (StringUtils.isNotBlank(pk)) {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, taskDeployModel.getPkTaskplugin());
            taskDeployModel.setVdef1(taskPluginModel.getPluginname());

            model.addAttribute(ITEM, taskDeployModel);
        }
        return "/management/task/deploy/edit";
    }

    @RequestMapping("deploy/save")
    @ResponseBody
    public AjaxDone deploysave(HttpServletRequest request) throws Exception {
        TaskDeployModel taskDeployModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskDeployModel.class);
        try {
            if (StringUtils.isBlank(taskDeployModel.getRunnable())) {
                taskDeployModel.setRunnable("N");
            }
            taskDeployModel.setDr(0);
            taskDeployModel.setTs(TimeToolkit.getCurrentTs());
            taskService.saveTask(taskDeployModel);
            return AjaxDoneSucc("保存成功");
        } catch (Exception e) {
            return AjaxDoneError("保存失败");
        }
    }


    @RequestMapping("deploy/del")
    @ResponseBody
    public AjaxDone deploydel(String pk) {
        try {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            taskService.removeTask(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("删除成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("删除失败");
        }
    }

    @RequestMapping("deploy/disable")
    @ResponseBody
    public AjaxDone disable(String pk) {
        try {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            taskService.disableTask(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("禁用成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("禁用失败");
        }
    }

    @RequestMapping("deploy/enable")
    @ResponseBody
    public AjaxDone enable(String pk) {
        try {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            taskService.enableTask(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("启用成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("启用失败");
        }
    }
}
