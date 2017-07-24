package com.application.b;

import com.cheng.lang.IService;

import java.util.List;

/**
 * @author chengys4
 *         2017-07-24 10:13
 **/
public interface ITaskStatusService extends IService {

    boolean close(List<TaskStatusModel> taskStatusModelList, String jobParameter);
}
