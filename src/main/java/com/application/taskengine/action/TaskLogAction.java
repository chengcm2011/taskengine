package com.application.taskengine.action;

import com.application.taskengine.itf.ITaskService;
import com.cheng.lang.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
@RequestMapping("/management/task/log")
public class TaskLogAction extends BusinessCommonAction {

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
        String key = request.getParameter("pkTaskdeploy");
        if (StringUtils.isBlank(key)) {
            model.addAttribute("pageVO", pageVO);
            return "/management/task/log/index";
        }
        pageVO = taskService.getTaskLog(key, pageVO);
        model.addAttribute("pageVO", pageVO);
        model.addAttribute(ITEM, request.getParameter("deployname"));
        model.addAttribute("key", key);
        return "/management/task/log/index";
    }


}
