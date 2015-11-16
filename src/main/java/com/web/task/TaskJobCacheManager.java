package com.web.task;

import com.web.task.vo.ScheduleJobVo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by cheng on 2015/10/23.
 */
public class TaskJobCacheManager {

	private ConcurrentMap<String,ScheduleJobVo> cacheScheduleJobVo = new ConcurrentHashMap();
	private static TaskJobCacheManager taskJobCacheManager = new TaskJobCacheManager();
	public static TaskJobCacheManager getInstance(){
		return taskJobCacheManager ;
	}

	public ScheduleJobVo get(String key){
		return cacheScheduleJobVo.get(key);
	}

	public ScheduleJobVo set(String key,ScheduleJobVo scheduleJobVo){
		return cacheScheduleJobVo.put(key,scheduleJobVo);
	}
}
