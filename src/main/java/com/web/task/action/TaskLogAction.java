package com.web.task.action;

import cheng.lib.lang.PageVO;
import cheng.lib.util.BeanUtil;
import com.web.common.BusinessCommonAction;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskLogModel;
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
	/**
	 * 加载所以的注册的任务
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request,PageVO pageVO, Model model) throws Exception {
		pageVO.setCondition(" dr=0 ");
		pageVO = dataBaseService.queryByPage(TaskLogModel.class,pageVO);
		List<TaskLogModel> list = (List<TaskLogModel>)pageVO.getData();
		List<Map<String,Object>> data = new ArrayList<>();
		for(TaskLogModel taskLogModel:list){
			Map<String,Object> mdata = BeanUtil.getValueMap(taskLogModel);
			TaskDeployModel taskDeployModel = dataBaseService.queryByPK(TaskDeployModel.class,taskLogModel.getPk_taskdeploy());
			mdata.put("taskname",taskDeployModel.getTaskname());
			data.add(mdata);
		}
		pageVO.setData(data);
		model.addAttribute("pageVO",pageVO);
		return "/management/task/log/index";
	}
}
