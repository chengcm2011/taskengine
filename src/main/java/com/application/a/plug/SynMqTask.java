package com.application.a.plug;

import com.application.a.itf.ISynService;
import com.application.a.model.AccountNOModel;
import com.application.taskengine.AbstractTaskImpl;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.lang.PageVO;
import com.cheng.lang.exception.BusinessException;
import com.cheng.util.Predef;
import com.cheng.web.ApplicationServiceLocator;
import org.quartz.JobDataMap;

import java.util.List;


/**
 * 同步户号到mq
 */
public class SynMqTask extends AbstractTaskImpl {
    @Override
    public String doexecute(JobDataMap jobDataMap) throws BusinessException {

        ISynService synService = ApplicationServiceLocator.getBean(ISynService.class);
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(Predef.toInt(jobDataMap.getIntValue("pageSize"), 1));
        pageVO.setCondition(" success='N' ");
        ApplicationServiceLocator.getBean(IBaseDAO.class).queryByPage(AccountNOModel.class, pageVO);
        List<AccountNOModel> data = (List<AccountNOModel>) pageVO.getData();
        for (AccountNOModel accountNOModel : data) {
            synService.syn(accountNOModel);
        }

        return "";
    }
}
