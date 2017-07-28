package com.application.taskengine.service;

import com.alibaba.fastjson.JSON;
import com.application.taskengine.model.TaskDeployModel;
import com.application.taskengine.model.TaskParamValueModel;
import com.cheng.jdbc.impl.BaseDAO;
import com.cheng.jdbc.opt.Condition;
import com.cheng.jdbc.opt.Query;
import com.cheng.lang.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务参数服务
 *
 * @author chengys4
 *         2017-07-28 17:01
 **/
@Component
@Transactional(rollbackFor = Exception.class)
public class TaskParamService {

    @Resource
    private BaseDAO baseDAO;

    /**
     * 获取任务的参数
     *
     * @param pkTaskdeploy 任务主键
     * @return json格式的参数值
     * @throws BusinessException
     */
    public String getTaskParameter(String pkTaskdeploy) throws BusinessException {
        Map<String, Object> taskParameter = new HashMap<>();
        Query query = Query.query(Condition.eq(TaskDeployModel.PK_TASK_DEPLOY, pkTaskdeploy));
        query.eq("dr", 0);
        List<TaskParamValueModel> taskParamValueModels = baseDAO.queryByClause(TaskParamValueModel.class, query);
        for (int j = 0; j < taskParamValueModels.size(); j++) {
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskParameter.put(t.getParamkey(), t.getParamvalue());
        }
        taskParameter.put(TaskDeployModel.PK_TASK_DEPLOY, pkTaskdeploy);
        return JSON.toJSONString(taskParameter);
    }

}
