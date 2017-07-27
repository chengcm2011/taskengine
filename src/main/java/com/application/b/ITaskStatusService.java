package com.application.b;

import com.cheng.lang.IService;

import java.util.List;
import java.util.Map;

/**
 * @author chengys4
 *         2017-07-24 10:13
 **/
public interface ITaskStatusService extends IService {

    boolean close(List<TaskStatusModel> taskStatusModelList, Map<String, Object> jobParameter);
}
