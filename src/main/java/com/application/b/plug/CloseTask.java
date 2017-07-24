package com.application.b.plug;

import com.application.b.ITaskStatusService;
import com.application.b.TaskStatusModel;
import com.application.taskengine.AbstractElasticTaskImpl;
import com.application.taskengine.common.JobConfig;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.PageVO;
import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.api.ShardingContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chengys4
 *         2017-07-24 10:20
 **/
@Component
@JobConfig(value = "0 0/30 * * * ?", shardingTotalCount = 1)
public class CloseTask extends AbstractElasticTaskImpl {

    @Override
    public void execute(ShardingContext shardingContext) {

        String jobParameter = shardingContext.getJobParameter();
        if (StringUtils.isBlank(jobParameter)) {
            return;
        }
        IDataBaseService dataBaseService = ApplicationServiceLocator.getService(IDataBaseService.class);
        ITaskStatusService taskStatusService = ApplicationServiceLocator.getService(ITaskStatusService.class);
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(500);
        pageVO.setCondition("close='N'");
        pageVO = dataBaseService.queryByPage(TaskStatusModel.class, pageVO);
        if (pageVO.getData().size() == 0) {
            return;
        }
        taskStatusService.close((List<TaskStatusModel>) pageVO.getData(), jobParameter);
    }

}
