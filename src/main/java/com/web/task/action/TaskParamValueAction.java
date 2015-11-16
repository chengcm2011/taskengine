package com.web.task.action;

import arch.util.lang.BeanUtil;
import arch.util.lang.PageVO;
import arch.util.lang.Predef;
import com.application.action.vo.AjaxDone;
import com.application.common.util.SqlUtil;
import com.application.util.validate.Verification;
import com.web.common.BusinessCommonAction;
import com.web.task.model.TaskParamValueModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by cheng on 2015/8/28.
 */
@Controller
@RequestMapping("/management/task/")
public class TaskParamValueAction extends BusinessCommonAction {

	@RequestMapping("paramvalue/index")
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		String pk = request.getParameter("pk");
		if (!Verification.isSignlessnumber(pk)) {
			return "";
		}
		pageVO.setCondition(" dr=0 and id_taskdeploy ="+pk );
		pageVO = getDbrunner().queryBeanByPage(TaskParamValueModel.class,pageVO);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("pk",pk);
		return "management/task/paramvalue/index";
	}
	@RequestMapping("paramvalue/edit")
	public String edit(HttpServletRequest request , Model model) throws Exception {
		String pk = request.getParameter("pk");
		String id_taskparamkey =  pk.split(";")[0];
		String id_taskdeploy = pk.split(";")[1];
		if(Verification.isSignlessnumber(id_taskparamkey) && Verification.isSignlessnumber(id_taskdeploy)){
			TaskParamValueModel item = getDbrunner().queryBean2(TaskParamValueModel.class,"id_taskparamkey=? and dr=0 and id_taskdeploy =?",id_taskparamkey,id_taskdeploy);
			model.addAttribute(ITEM,item);
			model.addAttribute("pk",pk);
		}
		return "management/task/paramvalue/edit";
	}
	@RequestMapping("paramvalue/save")
	@ResponseBody
	public AjaxDone save(HttpServletRequest request,String pk, Model model) throws Exception {
		String id_taskparamkey =  pk.split(";")[0];
		String id_taskdeploy = pk.split(";")[1];
		String paramvalue = request.getParameter("paramvalue");
		TaskParamValueModel taskParamValueModel = getDbrunner().queryBean2(TaskParamValueModel.class, "id_taskdeploy=? and id_taskparamkey=? and dr=0 ", id_taskdeploy, id_taskparamkey);
		if(taskParamValueModel==null){
			taskParamValueModel = new TaskParamValueModel();
			taskParamValueModel.setTsDr();
			taskParamValueModel.setId_taskdeploy(Predef.toInt(id_taskdeploy));
			taskParamValueModel.setId_taskparamkey(Predef.toInt(id_taskparamkey));
			taskParamValueModel.setParamvalue(paramvalue);
			getDbrunner().insertModel(taskParamValueModel);
		}else {
			taskParamValueModel.setParamvalue(paramvalue);
			getDbrunner().update(taskParamValueModel);
		}
		return AjaxDoneSucc("保存成功");
	}
}
