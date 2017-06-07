package com.application.b.plug;

import com.application.b.itf.IPayment;
import com.application.taskengine.AbstractTaskImpl;
import com.application.taskengine.model.TaskConfModel;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.exception.BusinessException;
import com.cheng.web.ApplicationServiceLocator;
import org.quartz.JobDataMap;


/**
 *
 */
public class PaymentSynTask extends AbstractTaskImpl {
    @Override
    public String doexecute(JobDataMap jobDataMap) throws BusinessException {
        IPayment payment = ApplicationServiceLocator.getBean(IPayment.class);
        TaskConfModel taskConfModel = ApplicationServiceLocator.getBean(IDataBaseService.class).queryByPK(TaskConfModel.class, "0001AA00000000000KFX");
        payment.syn(taskConfModel);
        return "";
    }
}
