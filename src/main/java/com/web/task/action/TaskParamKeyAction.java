package com.web.task.action;

import cheng.lib.lang.PageVO;
import cheng.lib.util.BeanUtil;
import cheng.lib.util.TimeToolkit;
import cheng.lib.validate.Verification;
import com.application.action.vo.AjaxDone;
import com.web.common.BusinessCommonAction;
import com.web.task.model.TaskParamKeyModel;
import org.apache.commons.lang.StringUtils;
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
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		String pk = request.getParameter("pk");
		if(!Verification.isSignlessnumber(pk)){
			return "";
		}
		pageVO.setCondition(" dr=0 and id_taskplugin="+Integer.valueOf(pk));
		pageVO = dataBaseService.queryByPage(TaskParamKeyModel.class,pageVO);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("pk",pk);
		return "management/task/paramkey/index";
	}
	@RequestMapping("paramkey/edit")
	public String edit(HttpServletRequest request, Model model) throws Exception {
		String pk = request.getParameter("pk");
		String pk_taskplugin = request.getParameter("pk_taskplugin");
		if(Verification.isSignlessnumber(pk)){
			TaskParamKeyModel taskParamKeyModel = dataBaseService.queryByPK(TaskParamKeyModel.class, pk);
			if(taskParamKeyModel!=null){
				taskParamKeyModel.setPk_taskplugin(pk_taskplugin);
				model.addAttribute(ITEM,taskParamKeyModel);
			}
		}else {
			model.addAttribute("pk_taskplugin",pk_taskplugin);
		}
		return "management/task/paramkey/edit";
	}

	@RequestMapping("paramkey/save")
	@ResponseBody
	public AjaxDone save(HttpServletRequest request, Model model) throws Exception {
		TaskParamKeyModel taskParamKeyModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskParamKeyModel.class);
		taskParamKeyModel.setTs(TimeToolkit.getCurrentTs());
		taskParamKeyModel.setDr(0);
		if(StringUtils.isEmpty(taskParamKeyModel.getPk_taskparamkey())){
			dataBaseService.insert(taskParamKeyModel);
		}else {
			dataBaseService.update(taskParamKeyModel);
		}
		return AjaxDoneSucc("保存成功");
	}
}
