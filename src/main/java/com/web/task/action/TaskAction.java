package com.web.task.action;

import arch.util.lang.BeanUtil;
import arch.util.lang.PageVO;
import arch.util.lang.TimeToolkit;
import com.application.action.vo.AjaxDone;
import com.application.util.validate.Verification;
import com.web.common.BusinessCommonAction;
import com.web.task.itf.ITaskService;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskParamKeyModel;
import com.web.task.model.TaskParamValueModel;
import com.web.task.model.TaskPluginModel;
import com.web.task.vo.TaskPluginVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务中心 处理所以的后台定时任务
 * @author cheng
 *
 */
@Controller
@RequestMapping("/management/task/")
public class TaskAction extends BusinessCommonAction{

	@Resource
	ITaskService taskService ;
	/**
	 * 加载所以的注册的任务
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		pageVO.setCondition(" dr=0 ");
		pageVO = getDbrunner().queryBeanByPage(TaskPluginModel.class,pageVO);
		List<TaskPluginModel> list = (List<TaskPluginModel>)pageVO.getData();
		List<Map<String,Object>> data = new ArrayList<>();
		for(TaskPluginModel taskPluginModel:list){
			data.add(BeanUtil.getValueMap(taskPluginModel));
		}
		pageVO.setData(data);
		model.addAttribute("pageVO",pageVO);
		return "/management/task/index";
	}


	@RequestMapping("edit")
	public String listadd(String action,String pk,Model model) throws Exception {
		if(Verification.isSignlessnumber(pk)){
			model.addAttribute(ITEM,getDbrunner().queryBeanById(TaskPluginModel.class,Integer.valueOf(pk)));
		}
		return "/management/task/edit";
	}
	@RequestMapping("save")
	@ResponseBody
	public AjaxDone listsave(HttpServletRequest request,Model model) throws Exception {
		TaskPluginModel taskPluginModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskPluginModel.class);
		if(taskPluginModel.getId_taskplugin()<=0){
			getDbrunner().insertModel(taskPluginModel);
		}else {
			getDbrunner().update(taskPluginModel);
		}
		return AjaxDoneSucc("保存成功");
	}
	@RequestMapping("del")
	@ResponseBody
	public AjaxDone del(HttpServletRequest request,String pk,Model model) throws Exception {
		if(Verification.isSignlessnumber(pk)){
			TaskPluginModel taskPluginModel = getDbrunner().queryBeanById(TaskPluginModel.class,Integer.valueOf(pk));
			List<TaskDeployModel> list = getDbrunner().queryBeans2(TaskDeployModel.class, " dr=0 and id_taskplugin=?", taskPluginModel.getPrimaryKey());
			if(list!=null && list.size()>0 ){
				return AjaxDoneError("删除失败，该插件已部署。");
			}
			taskPluginModel.setDr(1);
			taskPluginModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskPluginModel,new String[]{"dr","ts"});
		}
		return AjaxDoneSuccNotcloseCurrent("保存成功");
	}
	@RequestMapping("deploy/index")
	public String deployindex(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		;pageVO.setCondition(" dr=0 ");
		pageVO = getDbrunner().queryBeanByPage(TaskDeployModel.class,pageVO);
		List<TaskDeployModel> list = (List<TaskDeployModel>)pageVO.getData();
		List<Map<String,Object>> data = new ArrayList<>();
		for(TaskDeployModel taskDeployModel:list){
			Map<String,Object> item = BeanUtil.getValueMap(taskDeployModel);
			TaskPluginModel taskPluginModel = getDbrunner().queryBeanById(TaskPluginModel.class, taskDeployModel.getId_taskplugin());
			if(taskPluginModel!=null){
				item.put("pluginname",taskPluginModel.getPluginname());
			}
			data.add(item);
		}
		pageVO.setData(data);
		model.addAttribute("pageVO",pageVO);
		return "/management/task/deploy/index";
	}
	
	@RequestMapping("deploy/edit")
	public String detail(HttpServletRequest request,String pk,Model model) throws Exception {
		if(Verification.isSignlessnumber(pk)){
			TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
			TaskPluginModel taskPluginModel = getDbrunner().queryBeanById(TaskPluginModel.class, taskDeployModel.getId_taskplugin());
			taskDeployModel.setVdef1(taskPluginModel.getPluginname());

			model.addAttribute(ITEM,taskDeployModel);
		}
		return "/management/task/deploy/edit";
	}
	@RequestMapping("deploy/save")
	@ResponseBody
	public AjaxDone deploysave(HttpServletRequest request,TaskPluginVO vo,Model model) throws Exception {
		TaskDeployModel taskDeployModel = BeanUtil.objMapToBean(getParamFromReq(request), TaskDeployModel.class);
		if(taskDeployModel.getId_taskdeploy()<=0){
			taskService.createScheduleJob(taskDeployModel);
		}else {
			taskService.updateScheduleJob(taskDeployModel);
		}
		return AjaxDoneSucc("保存成功");
	}

	@RequestMapping("deploy/start")
	@ResponseBody
	public AjaxDone deploystart(HttpServletRequest request,String pk ,Model model) throws Exception {
		TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
		if("N".equals(taskDeployModel.getRunnable())){
			taskDeployModel.setRunnable("Y");
			taskDeployModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskDeployModel,new String[]{"ts","runnable"});
			taskService.startScheduleJob(taskDeployModel);
		}
		return AjaxDoneSuccNotcloseCurrent("启动成功");
	}
	@RequestMapping("deploy/stop")
	@ResponseBody
	public AjaxDone deploystop(HttpServletRequest request,String pk ,Model model) throws Exception {
		TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
		if("Y".equals(taskDeployModel.getRunnable())){
			taskDeployModel.setRunnable("N");
			taskDeployModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskDeployModel,new String[]{"ts","runnable"});
			taskService.deleteScheduleJob(taskDeployModel);
		}
		return AjaxDoneSuccNotcloseCurrent("停止成功");
	}
	@RequestMapping("deploy/pause")
	@ResponseBody
	public AjaxDone deploypause(HttpServletRequest request,String pk ,Model model) throws Exception {
		TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
		if("Y".equals(taskDeployModel.getRunnable())){
			taskDeployModel.setRunnable("N");
			taskDeployModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskDeployModel,new String[]{"ts","runnable"});
			taskService.pauseJob(taskDeployModel);
		}
		return AjaxDoneSuccNotcloseCurrent("停止成功");
	}
	@RequestMapping("deploy/resume")
	@ResponseBody
	public AjaxDone deployresume(HttpServletRequest request,String pk ,Model model) throws Exception {
		TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
		if("Y".equals(taskDeployModel.getRunnable())){
			taskDeployModel.setRunnable("N");
			taskDeployModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskDeployModel,new String[]{"ts","runnable"});
			taskService.pauseJob(taskDeployModel);
		}
		return AjaxDoneSuccNotcloseCurrent("停止成功");
	}
	@RequestMapping("deploy/del")
	@ResponseBody
	public AjaxDone deploydel(HttpServletRequest request,String pk ,Model model) throws Exception {
		TaskDeployModel taskDeployModel = getDbrunner().queryBeanById(TaskDeployModel.class,Integer.valueOf(pk));
		if(taskDeployModel!=null){
			taskDeployModel.setRunnable("N");
			taskDeployModel.setDr(1);
			taskDeployModel.setTs(TimeToolkit.getCurrentTs());
			getDbrunner().update(taskDeployModel,new String[]{"dr","ts","runnable"});
			taskService.deleteScheduleJob(taskDeployModel);
		}
		return AjaxDoneSuccNotcloseCurrent("删除成功");
	}


}
