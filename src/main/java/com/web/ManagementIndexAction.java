package com.web;

import com.web.common.BusinessCommonAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cheng on 2015/7/4.
 */
@Controller
@RequestMapping("/management")
public class ManagementIndexAction extends BusinessCommonAction {

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (!login(request, response)) {
			return redirect(response, "/login?url=management/index");
		}
		String sql = " select * from task_taskparamkey " ;
		getDbrunner().queryMap(sql);
		return "management/index";
	}
}