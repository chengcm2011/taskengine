package com.application.a.service;

import com.application.a.itf.ISynService;
import com.application.a.model.RentContractModel;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.util.HttpClientUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class SynService implements ISynService {

    @Resource
    HttpClientUtils httpClientUtils;
    @Resource
    IBaseDAO baseDAO;

    public String syn(RentContractModel accountNOModel) {
//        String msf = httpClientUtils.httpGet("http://inventory.ziroom.com/backend/api/houseaccountno/initmq2?ids=" + accountNOModel.getTableName());
//        accountNOModel.setTs(TimeToolkit.getCurrentTs());
//        baseDAO.update(accountNOModel);
        return "success";
    }
}
