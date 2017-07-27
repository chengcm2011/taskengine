package com.application.taskengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.application.taskengine.model.TaskParamValueModel;
import com.cheng.jdbc.opt.Condition;
import com.cheng.jdbc.opt.Query;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 弹性任务父类
 * cheng
 * 2017-06-18
 */
public abstract class AbstractElasticTaskImpl implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        String jobParameter = shardingContext.getJobParameter();
        if (StringUtils.isBlank(jobParameter)) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(jobParameter);

        Map<String, Object> taskParams = new HashMap<>();
        Query query = Query.query(Condition.eq("pkTaskdeploy", jsonObject.getString("pkTaskdeploy")));
        query.eq("dr", 0);
        List<TaskParamValueModel> taskParamValueModels = ApplicationServiceLocator.getService(IDataBaseService.class).queryByClause(TaskParamValueModel.class, query);
        for (int j = 0; j < taskParamValueModels.size(); j++) {
            TaskParamValueModel t = taskParamValueModels.get(j);
            taskParams.put(t.getParamkey(), t.getParamvalue());
        }
        taskParams.put("pkTaskdeploy", jsonObject.getString("pkTaskdeploy"));
        execute(shardingContext, jsonObject.getString("pkTaskdeploy"), taskParams);
    }

    public abstract void execute(ShardingContext shardingContext, String taskKey, Map<String, Object> taskParams);
}
