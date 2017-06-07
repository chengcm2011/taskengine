package com.application.b.itf;

import com.application.taskengine.model.TaskConfModel;
import com.cheng.lang.IService;
import com.cheng.lang.exception.BusinessException;

/**
 *
 */
public interface IPayment extends IService {
    boolean syn(TaskConfModel taskConfModel) throws BusinessException;
}
