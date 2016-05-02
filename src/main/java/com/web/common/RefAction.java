package com.web.common;

import cheng.lib.lang.PageVO;
import com.web.task.model.TaskPluginModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2015/8/14.
 */
@Controller
public class RefAction extends BusinessCommonAction {
	@RequestMapping("/management/uicomponent/ref/index")
	public String index(HttpServletRequest request, HttpServletResponse response,PageVO pageVO, Model model) throws Exception {
		String reftype = request.getParameter("reftype");
		String itemkey = request.getParameter("itemkey");
		model.addAttribute("itemkey",itemkey);
		List<TaskPluginModel> data = dataBaseService.queryByClause(TaskPluginModel.class,"dr=0");
		model.addAttribute(DATA,data);
		return "management/uicomponent/ref/index";
	}
}
