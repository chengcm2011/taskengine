package com.application.taskengine.action;

import cheng.lib.lang.PageVO;
import cheng.lib.util.BeanUtil;
import com.application.taskengine.LogMap;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskLogModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2015/10/23.
 */
@Controller
@RequestMapping("/management/task/log")
public class TaskLogAction extends BusinessCommonAction {
	private  boolean ispersistence =false;
	/**
	 * 加载所以的注册的任务
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		String key = request.getParameter("pk_taskdeploy");
		if(StringUtils.isBlank(key)){
			model.addAttribute("pageVO",pageVO);
			return "/management/task/log/index";
		}
		if(!ispersistence){
			pageVO =  init(key);
		}else {
			pageVO.setCondition(" dr=0 ");
			pageVO = dataBaseService.queryByPage(TaskLogModel.class,pageVO);
		}
		List<TaskLogModel> list = (List<TaskLogModel>)pageVO.getData();
		List<Map<String,Object>> data = new ArrayList<>();
		for(TaskLogModel taskLogModel:list){
			Map<String,Object> mdata = BeanUtil.getValueMap(taskLogModel);
			TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class,taskLogModel.getPk_taskdeploy());
			mdata.put("taskname",taskDeployModel.getTaskname());
			data.add(mdata);
		}
		pageVO.setData(data);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute(ITEM,request.getParameter("deployname"));
		model.addAttribute("key",key);
		return "/management/task/log/index";
	}

	private PageVO init(String key) {
		PageVO pageVO =new PageVO(1,20);
		List<TaskLogModel> data = LogMap.getLog(key);
		pageVO.setData(data);
		return pageVO;
	}
}
