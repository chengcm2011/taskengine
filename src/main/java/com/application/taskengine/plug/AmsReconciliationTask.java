package com.application.taskengine.plug;

import arch.util.lang.Predef;
import arch.util.toolkit.DBRunner;
import cheng.lib.exception.BusinessException;
import com.application.taskengine.AbstractTaskImpl;
import org.quartz.JobDataMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 监控资产 收款单线下对账任务异常任务
 */
public class AmsReconciliationTask extends AbstractTaskImpl {
    @Override
    public String doexecute(JobDataMap jobDataMap) throws BusinessException {

        DBRunner db = DBRunner.of("test", "jdbc:oracle:thin:@10.16.26.42:1521:svdp", "HLASSET", "GTIfZ8RnBY");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" select a.state, a.document_code, b.errormessage, b.afamessage, b.rent_code ");
        stringBuilder.append(" from bz_rent_payment_voucher a ");
        stringBuilder.append(" left join bz_payment_voucher_log b ");
        stringBuilder.append(" on a.id = b.payment_voucher_id ");
        stringBuilder.append(" where a.serial_no is null ");
        stringBuilder.append(" and a.state != 2 ");
        stringBuilder.append(" and b.is_close = 2 ");
        stringBuilder.append(" and b.payment_type = 'xx_dz' ");

        try {

            List<Map<String, Object>> data = db.queryMapList(stringBuilder.toString());
            if (Predef.size(data) > 0) {
                //发送邮件
            }
        } catch (SQLException e) {
            throw new BusinessException(e);
        }
        return "true";
    }
}
