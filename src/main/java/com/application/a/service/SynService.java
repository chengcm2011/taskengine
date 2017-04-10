package com.application.a.service;

import com.application.a.itf.ISynService;
import com.application.a.model.AccountNOModel;
import com.cheng.jdbc.itf.IBaseDAO;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.model.UFBoolean;
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

    public String syn(AccountNOModel accountNOModel) {
        try {
            String msf = httpClientUtils.httpGet("http://inventory.ziroom.com/backend/api/houseaccountno/initmq2?ids=" + accountNOModel.getAid());
            accountNOModel.setSuccess(UFBoolean.TRUE);
            accountNOModel.setRemsg(msf);
        } catch (Exception e) {
            accountNOModel.setRemsg(e.getMessage());
            accountNOModel.setSuccess(UFBoolean.FALSE);
        }
        accountNOModel.setTs(TimeToolkit.getCurrentTs());
        baseDAO.update(accountNOModel);
        return "success";
    }
}
