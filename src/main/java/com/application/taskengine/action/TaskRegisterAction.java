package com.application.taskengine.action;

import cheng.lib.lang.PageVO;
import cheng.lib.util.BeanUtil;
import cheng.lib.util.TimeToolkit;
import com.application.action.vo.AjaxDone;
import com.application.module.jdbc.SQLParameter;
import com.application.taskengine.action.BusinessCommonAction;
import com.application.taskengine.itf.ITaskService;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskPluginModel;
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
public class TaskRegisterAction extends BusinessCommonAction {

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
        StringBuilder stringBuilder = new StringBuilder(" dr=0 ");
        if(org.apache.commons.lang3.StringUtils.isNotBlank(request.getParameter("pluginname"))){
            stringBuilder.append(" and pluginname like '%"+request.getParameter("pluginname")+"'");
        }
        pageVO.setCondition(stringBuilder.toString());
        pageVO = dataBaseService.queryByPage(TaskPluginModel.class, pageVO);
        List<TaskPluginModel> list = (List<TaskPluginModel>) pageVO.getData();
        List<Map<String, Object>> data = new ArrayList<>();
        for (TaskPluginModel taskPluginModel : list) {
            data.add(BeanUtil.getValueMap(taskPluginModel));
        }
        pageVO.setData(data);
        model.addAttribute("pageVO", pageVO);
        model.addAttribute(ITEM, request.getParameter("pluginname"));
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


}
