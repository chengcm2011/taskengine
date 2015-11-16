package com.web.task.service;


import arch.util.lang.BeanUtil;
import arch.util.toolkit.DBRunner;
import com.application.common.context.ApplicationServiceLocator;
import com.web.task.TaskJobCacheManager;
import com.web.task.itf.ITaskRead;
import com.web.task.model.TaskDeployModel;
import com.web.task.model.TaskParamValueModel;
import com.web.task.model.TaskPluginModel;
import com.web.task.util.ScheduleUtils;
import com.web.task.vo.ScheduleJobVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 从数据库中读取任务信息 包装成任务信息vo
 * @author cheng
 *
 */
@Component
public class TaskReadImpl implements ITaskRead {

	public TaskReadImpl() {
	}
	@Override
	public List<ScheduleJobVo> read() {
		return get(null,null) ;
	}

	@Override
	public ScheduleJobVo read(String jobCode, String groupCode) {
		List<ScheduleJobVo> d =  get(jobCode,groupCode);
		if(d!=null && d.size()>0){
			return d.get(0);
		}
		return null;
	}


	private Map<String,Object> getParams (int id_taskdeploy) throws Exception{
		Map<String,Object> taskunitmap = new HashMap<>();
		List<TaskParamValueModel> taskParamValueModels = ApplicationServiceLocator.getBDRunner().queryBeans2(TaskParamValueModel.class, " dr=0 and id_taskdeploy=?", id_taskdeploy);
		for (int j=0;j<taskParamValueModels.size();j++){
			TaskParamValueModel t = taskParamValueModels.get(j);
			taskunitmap.put(t.getParamkey(),t.getParamvalue());
		}
		taskunitmap.put("id_taskdeploy",id_taskdeploy);
		return taskunitmap ;
	}

	private List<ScheduleJobVo> get(String jobCode,String groupCode){
		List<ScheduleJobVo> data = new ArrayList<>();
		//从数据库加载所以的任务
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(" SELECT p.pluginclass,d.* ");
			stringBuffer.append(" FROM task_taskdeploy as d  ");
			stringBuffer.append(" INNER JOIN task_taskplugin as p ON d.id_taskplugin=p.id_taskplugin ");
			stringBuffer.append(" WHERE  p.dr=0 AND d.dr=0 and d.runnable='Y' ");
			if(StringUtils.isNotBlank(jobCode)){
				stringBuffer.append(" and d.id_taskdeploy=").append(jobCode);
			}
			if(StringUtils.isNotBlank(groupCode)){
				stringBuffer.append(" and d.id_taskplugin=").append(groupCode);
			}
			List<Map<String,Object>> mapList = ApplicationServiceLocator.getBDRunner().queryMapList(stringBuffer.toString());
			for (int i = 0; i < mapList.size(); i++) {
				Map<String,Object> taskunitmap = mapList.get(i);
				TaskPluginModel taskPluginModel = BeanUtil.objMapToBean(taskunitmap, TaskPluginModel.class);
				TaskDeployModel taskDeployModel = BeanUtil.objMapToBean(taskunitmap, TaskDeployModel.class);
				data.add(ScheduleUtils.createScheduleJob(taskDeployModel.getTaskname(), taskDeployModel.getId_taskdeploy() + "", taskDeployModel.getId_taskplugin() + "", taskDeployModel.getTriggerstr(), taskPluginModel.getPluginclass(),taskDeployModel.getRunnable(),getParams(taskDeployModel.getId_taskdeploy())));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return data ;
	}
}
