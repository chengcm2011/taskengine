package com.web.task.action;

import cheng.lib.lang.PageVO;
import cheng.lib.util.BeanUtil;
import cheng.lib.util.TimeToolkit;
import com.application.action.vo.AjaxDone;
import com.application.module.jdbc.SQLParameter;
import com.web.common.BusinessCommonAction;
import com.web.task.itf.ITaskService;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskPluginModel;
import com.web.task.vo.TaskPluginVO;
import org.apache.commons.lang.StringUtils;
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
 * 任务中心 处理所以的后台定时任务
 *
 * @author cheng
 */
@Controller
@RequestMapping("/management/task/")
public class TaskAction extends BusinessCommonAction {

    @Resource
    ITaskService taskService;

    /**
     * 加载所以的注册的任务
     *
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, PageVO pageVO, Model model) throws Exception {
        pageVO.setCondition(" dr=0 ");
        pageVO = dataBaseService.queryByPage(TaskPluginModel.class, pageVO);
        List<TaskPluginModel> list = (List<TaskPluginModel>) pageVO.getData();
        List<Map<String, Object>> data = new ArrayList<>();
        for (TaskPluginModel taskPluginModel : list) {
            data.add(BeanUtil.getValueMap(taskPluginModel));
        }
        pageVO.setData(data);
        model.addAttribute("pageVO", pageVO);
        return "/management/task/index";
    }


    @RequestMapping("edit")
    public String listadd(String action, String pk, Model model) throws Exception {
        if (StringUtils.isNotBlank(pk)) {
            model.addAttribute(ITEM, dataBaseService.queryByPK(TaskPluginModel.class, pk));
        }
        return "/management/task/edit";
    }

    @RequestMapping("save")
    @ResponseBody
    public AjaxDone listsave(HttpServletRequest request, Model model) throws Exception {
        TaskPluginModel taskPluginModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskPluginModel.class);
        if (StringUtils.isBlank(taskPluginModel.getPk_taskplugin())) {
            dataBaseService.insert(taskPluginModel);
        } else {
            dataBaseService.update(taskPluginModel);
        }
        return AjaxDoneSucc("保存成功");
    }

    @RequestMapping("del")
    @ResponseBody
    public AjaxDone del(HttpServletRequest request, String pk, Model model) throws Exception {
        if (StringUtils.isNotEmpty(pk)) {
            TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, pk);
            SQLParameter sqlParameter = new SQLParameter();
            sqlParameter.addParam(taskPluginModel.getPrimaryKey());
            List<TaskDeployModel> list = dataBaseService.queryByClause(TaskDeployModel.class, " dr=0 and pk_taskplugin=?", sqlParameter);
            if (list != null && list.size() > 0) {
                return AjaxDoneError("删除失败，该插件已部署。");
            }
            taskPluginModel.setDr(1);
            taskPluginModel.setTs(TimeToolkit.getCurrentTs());
            dataBaseService.update(taskPluginModel, new String[]{"dr", "ts"});
        }
        return AjaxDoneSuccNotcloseCurrent("保存成功");
    }

    @RequestMapping("deploy/index")
    public String deployindex(HttpServletRequest request, PageVO pageVO, Model model) throws Exception {
        ;
        pageVO.setCondition(" dr=0 ");
        pageVO = dataBaseService.queryByPage(TaskDeployModel.class, pageVO);
        List<TaskDeployModel> list = (List<TaskDeployModel>) pageVO.getData();
        List<Map<String, Object>> data = new ArrayList<>();
        for (TaskDeployModel taskDeployModel : list) {
            Map<String, Object> item = BeanUtil.getValueMap(taskDeployModel);
            TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, taskDeployModel.getPk_taskplugin());
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
    public String detail(HttpServletRequest request, String pk, Model model) throws Exception {
        if (StringUtils.isNotBlank(pk)) {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, taskDeployModel.getPk_taskplugin());
            taskDeployModel.setVdef1(taskPluginModel.getPluginname());

            model.addAttribute(ITEM, taskDeployModel);
        }
        return "/management/task/deploy/edit";
    }

    @RequestMapping("deploy/save")
    @ResponseBody
    public AjaxDone deploysave(HttpServletRequest request, TaskPluginVO vo, Model model) throws Exception {
        TaskDeployModel taskDeployModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskDeployModel.class);
        try {
            if(StringUtils.isBlank(taskDeployModel.getRunnable())){
                taskDeployModel.setRunnable("Y");
            }
            taskDeployModel.setDr(0);
            taskService.addTask(taskDeployModel);
            return AjaxDoneSucc("保存成功");
        } catch (Exception e) {
            return AjaxDoneError("保存失败");
        }
    }


    @RequestMapping("deploy/del")
    @ResponseBody
    public AjaxDone deploydel(HttpServletRequest request, String pk, Model model) {
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
    public AjaxDone disable(HttpServletRequest request, String pk, Model model) {
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
    public AjaxDone enable(HttpServletRequest request, String pk, Model model) {
        try {
            TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);
            taskService.disableTask(taskDeployModel);
            return AjaxDoneSuccNotcloseCurrent("禁用成功");
        } catch (Exception e) {
            return AjaxDoneSuccNotcloseCurrent("禁用失败");
        }
    }
}
