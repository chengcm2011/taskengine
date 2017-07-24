package com.application.a.plug;

import com.application.taskengine.AbstractElasticTaskImpl;
import com.cheng.db.DBRunner;
import com.cheng.lang.TimeToolkit;
import com.dangdang.ddframe.job.api.ShardingContext;


/**
 * 同步户号到mq
 */
class SynRentNumDayTask extends AbstractElasticTaskImpl {

    @Override
    public void execute(ShardingContext shardingContext) {

        try {
            DBRunner db = DBRunner.of("pro", "jdbc:oracle:thin:@10.16.26.79:1521:svdp", "HLASSET", "GTIfZ8RnBY");
            int newc = db.queryInt("select count(1) from bz_rent_contract a where a.lessee_sign_date=to_date(?,'yyyy-mm-dd')", TimeToolkit.getCurrentDate());
        } catch (Exception e) {

        }

    }
}
