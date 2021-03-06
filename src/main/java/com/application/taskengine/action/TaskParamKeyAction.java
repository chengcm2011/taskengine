package com.application.taskengine.action;

import com.application.taskengine.model.TaskParamKeyModel;
import com.application.taskengine.model.TaskPluginModel;
import com.cheng.common.AjaxDone;
import com.cheng.lang.PageVO;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.BeanUtil;
import com.cheng.util.Verification;
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
public class TaskParamKeyAction extends BusinessCommonAction {
    @RequestMapping("paramkey/index")
    public String index(HttpServletRequest request, PageVO pageVO, Model model) throws Exception {
        String pk = request.getParameter("pk");
        if (StringUtils.isEmpty(pk)) {
            throw new BusinessException("");
        }
        pageVO.setCondition(" dr=0 and pkTaskplugin='" + pk + "'");
        pageVO = dataBaseService.queryByPage(TaskParamKeyModel.class, pageVO);
        TaskPluginModel taskPluginModel = dataBaseService.queryByPK(TaskPluginModel.class, pk);
        model.addAttribute("pageVO", pageVO);
        model.addAttribute(ITEM, taskPluginModel);
        model.addAttribute("pk", pk);
        return "management/task/paramkey/index";
    }

    @RequestMapping("paramkey/edit")
    public String edit(HttpServletRequest request, Model model) throws Exception {
        String pk = request.getParameter("pk");
        String pkTaskplugin = request.getParameter("pkTaskplugin");
        if (Verification.isSignlessnumber(pk)) {
            TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class, pk);
            if (taskParamKeyModel != null) {
                taskParamKeyModel.setPkTaskplugin(pkTaskplugin);
                model.addAttribute(ITEM, taskParamKeyModel);
            }
        } else {
            model.addAttribute("pkTaskplugin", pkTaskplugin);
        }
        return "management/task/paramkey/edit";
    }

    @RequestMapping("paramkey/save")
    @ResponseBody
    public AjaxDone save(HttpServletRequest request, Model model) throws Exception {
        TaskParamKeyModel taskParamKeyModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskParamKeyModel.class);
        taskParamKeyModel.setTs(TimeToolkit.getCurrentTs());
        taskParamKeyModel.setDr(0);
        if (StringUtils.isEmpty(taskParamKeyModel.getPkTaskparamkey())) {
            dataBaseService.insert(taskParamKeyModel);
        } else {
            dataBaseService.update(taskParamKeyModel);
        }
        return AjaxDoneSucc("保存成功");
    }
}
