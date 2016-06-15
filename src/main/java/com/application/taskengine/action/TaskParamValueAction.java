package com.application.taskengine.action;

import cheng.lib.lang.PageVO;
import com.application.action.vo.AjaxDone;
import com.application.module.jdbc.SQLParameter;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamKeyModel;
import com.application.taskengine.model.TaskParamValueModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cheng on 2015/8/28.
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

        pageVO.setCondition(" dr=0 and pk_taskdeploy ='" + pk + "'");
        pageVO = dataBaseService.queryByPage(TaskParamValueModel.class, pageVO);
        if (pageVO.getData() == null || pageVO.getData().isEmpty()) {
            pageVO.setCondition(" dr=0 and pk_taskplugin ='" + deployModel.getPk_taskplugin() + "'");
            pageVO = dataBaseService.queryByPage(TaskParamKeyModel.class, pageVO);
            model.addAttribute(ITEM, deployModel);
            model.addAttribute("pageVO", pageVO);
            model.addAttribute("pk", pk);
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
        String pk_taskparamkey = pk.split(";")[0];
        String pk_taskdeploy = pk.split(";")[1];
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(pk_taskdeploy);
        sqlParameter.addParam(pk_taskparamkey);
        TaskParamValueModel item = dataBaseService.queryOneByClause(TaskParamValueModel.class, "pk_taskdeploy=? and dr=0 and pk_taskparamkey  =?", sqlParameter);
        if(item==null){
            item = new TaskParamValueModel();
        }
        TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class,pk_taskparamkey);
        item.setParamkey(taskParamKeyModel.getParamkey());
        item.setParamname(taskParamKeyModel.getParamname());
        model.addAttribute(ITEM, item);
        model.addAttribute("pk", pk);
        return "management/task/paramvalue/edit";
    }

    @RequestMapping("paramvalue/save")
    @ResponseBody
    public AjaxDone save(HttpServletRequest request, String pk, Model model) throws Exception {
        String pk_taskparamkey = pk.split(";")[0];
        String pk_taskdeploy = pk.split(";")[1];
        String paramvalue = request.getParameter("paramvalue");
        SQLParameter sqlParameter = new SQLParameter();
        sqlParameter.addParam(pk_taskdeploy);
        sqlParameter.addParam(pk_taskparamkey);
        TaskParamValueModel taskParamValueModel = dataBaseService.queryOneByClause(TaskParamValueModel.class, "pk_taskdeploy=? and pk_taskparamkey=? and dr=0 ", sqlParameter);
        if (taskParamValueModel == null) {
            taskParamValueModel = new TaskParamValueModel();
            TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class,pk_taskparamkey);
            taskParamValueModel.setParamkey(taskParamKeyModel.getParamkey());
            taskParamValueModel.setParamname(taskParamKeyModel.getParamname());
            taskParamValueModel.setTsDr();
            taskParamValueModel.setPk_taskdeploy(pk_taskdeploy);
            taskParamValueModel.setPk_taskparamkey(pk_taskparamkey);
            taskParamValueModel.setParamvalue(paramvalue);
            dataBaseService.insert(taskParamValueModel);
        } else {
            taskParamValueModel.setParamvalue(paramvalue);
            dataBaseService.update(taskParamValueModel);
        }
        return AjaxDoneSucc("保存成功");
    }
}
