package com.application.b.service;

import com.application.b.itf.IPayment;
import com.application.b.model.PaymentModel;
import com.application.taskengine.model.TaskConfModel;
import com.cheng.db.DBRunner;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.TimeToolkit;
import com.cheng.lang.exception.BusinessException;
import com.cheng.lang.model.UFBoolean;
import com.cheng.util.ApplicationLogger;
import com.cheng.util.Predef;
import com.cheng.web.ApplicationServiceLocator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Transactional(rollbackFor = Exception.class)
public class PaymentService implements IPayment {
    @Resource
    IDataBaseService dataBaseService;

    /**
     * 从资产库抓数
     *
     * @param taskConfModel
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean syn(TaskConfModel taskConfModel) throws BusinessException {

        if (Predef.toInt(taskConfModel.getNamespace()) <= Predef.toInt(taskConfModel.getZkAddressList())) {
            return false;
        }
        int next = Predef.toInt(taskConfModel.getZkAddressList()) + Predef.toInt(taskConfModel.getVdef1());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" select  c.id                    rentid,");
        stringBuilder.append(" c.rent_contract_code,");
        stringBuilder.append("         c.commission,");
        stringBuilder.append("         c.deposit,");
        stringBuilder.append("         c.month_rent,");
        stringBuilder.append("         c.collection_cycle_code,");
        stringBuilder.append("         b.id                    paymentid,");
        stringBuilder.append("         b.ought_payment_price");
        stringBuilder.append(" from bz_rent_contract_payment b");
        stringBuilder.append(" inner join bz_rent_contract c");
        stringBuilder.append(" on c.id = b.rent_contract_id");
        stringBuilder.append(" where b.is_del = 0");
        stringBuilder.append(" and b.pay_num = 1");
        stringBuilder.append(" and b.id>=" + taskConfModel.getZkAddressList() + "  and b.id < ").append(next);

        taskConfModel.setZkAddressList(Predef.toStr(next));
        ApplicationServiceLocator.getService(IDataBaseService.class).update(taskConfModel, new String[]{"zkAddressList"});

        DataSource amsdataSource = ApplicationServiceLocator.getDataSource("ams");
        DBRunner dbRunner = DBRunner.of("ams", amsdataSource);
        try {
            List<Map<String, Object>> data = dbRunner.queryMapList(stringBuilder.toString());
            if (Predef.size(data) <= 0) {
                return false;
            }
            ApplicationLogger.info("size=" + data.size());
            List<PaymentModel> dd = new ArrayList<>();
            for (Map<String, Object> item : data) {
                PaymentModel paymentModel = new PaymentModel();
                paymentModel.setRentContractCode(item.get("rent_contract_code").toString());
                paymentModel.setCollectionCycleCode(item.get("collection_cycle_code").toString());
                paymentModel.setCommission(Predef.toDouble(item.get("commission")));
                paymentModel.setDeposit(Predef.toDouble(item.get("deposit")));
                paymentModel.setIsfinish(UFBoolean.FALSE);
                paymentModel.setMonthRent(Predef.toDouble(item.get("month_rent")));
                paymentModel.setOughtPaymentPrice(Predef.toDouble(item.get("ought_payment_price")));
                paymentModel.setPaymentid(Predef.toInt(item.get("paymentid")));
                paymentModel.setRentid(Predef.toInt(item.get("rentid")));
                paymentModel.setTs(TimeToolkit.getCurrentTs());
                dd.add(paymentModel);
            }
            ApplicationServiceLocator.getService(IDataBaseService.class).insert(dd);

        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return true;
    }
}
