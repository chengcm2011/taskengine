package com.application.b.plug;

import com.application.b.ITaskStatusService;
import com.application.b.TaskStatusModel;
import com.application.taskengine.AbstractElasticTaskImpl;
import com.application.taskengine.common.JobConfig;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.PageVO;
import com.cheng.util.ApplicationLogger;
import com.cheng.util.Predef;
import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author chengys4
 *         2017-07-24 10:20
 **/
@Component
@JobConfig(value = "0 0/30 * * * ?", shardingTotalCount = 1)
public class CloseTask extends AbstractElasticTaskImpl {

    @Override
    public void execute(ShardingContext shardingContext, String taskKey, Map<String, Object> taskParams) {
        IDataBaseService dataBaseService = ApplicationServiceLocator.getService(IDataBaseService.class);
        ITaskStatusService taskStatusService = ApplicationServiceLocator.getService(ITaskStatusService.class);
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(Predef.toInt(taskParams.get("pageSize"), 1));
        pageVO.setCondition("close='N'");
        pageVO = dataBaseService.queryByPage(TaskStatusModel.class, pageVO);
        if (pageVO.getData().size() == 0) {
            return;
        }
        ApplicationLogger.info("查询数据：" + pageVO.getData().size());
        taskStatusService.close((List<TaskStatusModel>) pageVO.getData(), taskParams);
        ApplicationLogger.info("处理完成：" + pageVO.getData().size());
    }

}
