package com.application.taskengine.action;

import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamKeyModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.application.taskengine.service.TaskSynService;
import com.cheng.common.AjaxDone;
import com.cheng.jdbc.SQLParameter;
import com.cheng.jdbc.opt.Condition;
import com.cheng.jdbc.opt.Query;
import com.cheng.lang.PageVO;
import com.cheng.lang.TimeToolkit;
import com.cheng.web.ApplicationServiceLocator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务参数值管理
 */
@Controller
@RequestMapping("/management/task/")
public class TaskParamValueAction extends BusinessCommonAction {

    @RequestMapping("paramvalue/index")
    public String index(HttpServletRequest request, PageVO pageVO, Model model) throws Exception {
        String pk = request.getParameter("pk");
        if (StringUtils.isBlank(pk)) {
            return "";
        }
        TaskDeployModel deployModel = dataBaseService.queryByPK(TaskDeployModel.class, pk);

        pageVO.setCondition(" dr=0 and pkTaskdeploy ='" + pk + "'");
        pageVO = dataBaseService.queryByPage(TaskParamValueModel.class, pageVO);
        if (pageVO.getData() == null || pageVO.getData().isEmpty()) {
            //初始化参数
            pageVO.setCondition(" dr=0 and pkTaskplugin ='" + deployModel.getPkTaskplugin() + "'");
            pageVO = dataBaseService.queryByPage(TaskParamKeyModel.class, pageVO);
            model.addAttribute(ITEM, deployModel);
            model.addAttribute("pageVO", pageVO);
            model.addAttribute("pk", pk);
            List<TaskParamKeyModel> taskParamKeyModels = (List<TaskParamKeyModel>) pageVO.getData();
            List<TaskParamValueModel> taskParamValueModels = new ArrayList<>();
            for (int i = 0; i < taskParamKeyModels.size(); i++) {
                TaskParamValueModel taskParamValueModel = new TaskParamValueModel();
                taskParamValueModel.setParamkey(taskParamKeyModels.get(i).getParamkey());
                taskParamValueModel.setParamname(taskParamKeyModels.get(i).getParamname());
                taskParamValueModel.setPkTaskdeploy(pk);
                taskParamValueModel.setPkTaskparamkey(taskParamKeyModels.get(i).getPkTaskparamkey());
                taskParamValueModel.setDr(0);
                taskParamValueModel.setTs(TimeToolkit.getCurrentTs());
                taskParamValueModels.add(taskParamValueModel);
            }
            dataBaseService.insert(taskParamValueModels);
            return "management/task/paramvalue/index_tmp";
        }
        model.addAttribute(ITEM, deployModel);
        model.addAttribute("pageVO", pageVO);
        model.addAttribute("pk", pk);
        return "management/task/paramvalue/index";
    }

    @RequestMapping("paramvalue/edit")
    public String edit(HttpServletRequest request, Model model) throws Exception {
        String pk = request.getParameter("pk");
        String pkTaskparamkey = pk.split(";")[0];
        String pkTaskdeploy = pk.split(";")[1];
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(pkTaskdeploy);
        sqlParameter.addParam(pkTaskparamkey);
        TaskParamValueModel item = dataBaseService.queryOneByClause(TaskParamValueModel.class, "pkTaskdeploy=? and dr=0 and pkTaskparamkey  =?", sqlParameter);
        if (item == null) {
            item = new TaskParamValueModel();
        }
        TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class, pkTaskparamkey);
        item.setParamkey(taskParamKeyModel.getParamkey());
        item.setParamname(taskParamKeyModel.getParamname());
        model.addAttribute(ITEM, item);
        model.addAttribute("pk", pk);
        return "management/task/paramvalue/edit";
    }

    @RequestMapping("paramvalue/save")
    @ResponseBody
    public AjaxDone save(HttpServletRequest request, String pk) throws Exception {
        String pkTaskparamkey = pk.split(";")[0];
        String pkTaskdeploy = pk.split(";")[1];
        String paramvalue = request.getParameter("paramvalue");
        Query query = Query.query(Condition.eq(TaskParamValueModel.PK_TASK_DEPLOY, pkTaskdeploy));
        query.eq(TaskParamValueModel.PK_TASK_PARAM_KEY, pkTaskparamkey).eq("dr", 0);

        TaskParamValueModel taskParamValueModel = dataBaseService.queryOneByClause(TaskParamValueModel.class, query);
        if (taskParamValueModel == null) {
            taskParamValueModel = new TaskParamValueModel();
            TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class, pkTaskparamkey);
            taskParamValueModel.setParamkey(taskParamKeyModel.getParamkey());
            taskParamValueModel.setParamname(taskParamKeyModel.getParamname());
            taskParamValueModel.setPkTaskdeploy(pkTaskdeploy);
            taskParamValueModel.setPkTaskparamkey(pkTaskparamkey);
            taskParamValueModel.setParamvalue(paramvalue);
            dataBaseService.insert(taskParamValueModel);
        } else {
            taskParamValueModel.setParamvalue(paramvalue);
            dataBaseService.update(taskParamValueModel);
        }
        //同步参数到注册中心
        ApplicationServiceLocator.getService(TaskSynService.class).synTaskParameter(pkTaskdeploy);
        return AjaxDoneSucc("保存成功");
    }
}
