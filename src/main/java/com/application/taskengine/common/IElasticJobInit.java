package com.application.taskengine.common;

import com.cheng.lang.IService;
import com.cheng.lang.exception.BusinessException;

/**
 *
 */
public interface IElasticJobInit extends IService {

    void JobInit() throws BusinessException;

    void JobInit(String pkJob) throws BusinessException;
}
