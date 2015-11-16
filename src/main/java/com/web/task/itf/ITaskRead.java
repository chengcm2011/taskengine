package com.web.task.itf;

import com.application.itf.IService;
import com.web.task.vo.ScheduleJobVo;

import java.util.List;

/**
 * Created by cheng on 2015/10/23.
 */
public interface ITaskRead extends IService{
//	public List<ScheduleJobVo> init() ;
	public List<ScheduleJobVo> read() ;
	public ScheduleJobVo read(String jobCode,String groupCode);
}
