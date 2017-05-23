package com.application.taskengine.action;

import com.application.taskengine.model.TaskConfModel;
import com.cheng.common.AjaxDone;
import com.cheng.lang.TimeToolkit;
import com.cheng.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 任务设置
 */
@Controller
@RequestMapping("/management/task/")
public class TaskConfigAction extends BusinessCommonAction {
    @RequestMapping("conf/index")
    public String index(HttpServletRequest request, Model model) throws Exception {

        TaskConfModel taskConfModel = dataBaseService.queryOneByClause(TaskConfModel.class, "dr=0");
        model.addAttribute(ITEM, taskConfModel);
        return "management/task/conf/edit";
    }


    @RequestMapping("conf/save")
    @ResponseBody
    public AjaxDone save(HttpServletRequest request, Model model) throws Exception {
        TaskConfModel taskConfModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskConfModel.class);
        taskConfModel.setTs(TimeToolkit.getCurrentTs());
        taskConfModel.setDr(0);
        if (StringUtils.isEmpty(taskConfModel.getPrimaryKey())) {
            dataBaseService.insert(taskConfModel);
        } else {
            dataBaseService.update(taskConfModel);
        }
        return AjaxDoneSuccNotcloseCurrent("保存成功");
    }
}
