package com.web.task.action;

import arch.util.lang.BeanUtil;
import arch.util.lang.PageVO;
import arch.util.lang.Predef;
import arch.util.lang.TimeToolkit;
import com.application.action.vo.AjaxDone;
import com.application.util.validate.Verification;
import com.web.common.BusinessCommonAction;
import com.web.task.model.TaskParamKeyModel;
import com.web.task.model.TaskPluginModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cheng on 2015/8/28.
 */
@Controller
@RequestMapping("/management/task/")
public class TaskParamKeyAction extends BusinessCommonAction {
	@RequestMapping("paramkey/index")
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		String pk = request.getParameter("pk");
		if(!Verification.isSignlessnumber(pk)){
			return "";
		}
		pageVO.setCondition(" dr=0 and id_taskplugin="+Integer.valueOf(pk));
		pageVO = getDbrunner().queryBeanByPage(TaskParamKeyModel.class,pageVO);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("pk",pk);
		return "management/task/paramkey/index";
	}
	@RequestMapping("paramkey/edit")
	public String edit(HttpServletRequest request, Model model) throws Exception {
		String pk = request.getParameter("pk");
		String id_taskplugin = request.getParameter("id_taskplugin");
		if(Verification.isSignlessnumber(pk)){
			TaskParamKeyModel taskParamKeyModel = getDbrunner().queryBeanById(TaskParamKeyModel.class,Integer.valueOf(pk));
			if(taskParamKeyModel!=null){
				taskParamKeyModel.setId_taskplugin(Predef.toInt(id_taskplugin));
				model.addAttribute(ITEM,taskParamKeyModel);
			}
		}else {
			model.addAttribute("id_taskplugin",id_taskplugin);
		}
		return "management/task/paramkey/edit";
	}

	@RequestMapping("paramkey/save")
	@ResponseBody
	public AjaxDone save(HttpServletRequest request, Model model) throws Exception {
		TaskParamKeyModel taskParamKeyModel = BeanUtil.objMapToBean(getParamFromReq(request),TaskParamKeyModel.class);
		taskParamKeyModel.setTs(TimeToolkit.getCurrentTs());
		taskParamKeyModel.setDr(0);
		if(taskParamKeyModel.getId_taskparamkey()<=0){
			getDbrunner().insertModel(taskParamKeyModel);
		}else {
			getDbrunner().update(taskParamKeyModel);
		}
		return AjaxDoneSucc("保存成功");
	}
}
