package com.application.taskengine.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Controller
@RequestMapping("/management")
public class ManagementIndexAction extends BusinessCommonAction {


    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		if (!login(request, response)) {
//			return redirect(response, "/login?url=management/index");
//		}
//		String sql = " select * from task_taskparamkey " ;
        return "management/index";
    }
}