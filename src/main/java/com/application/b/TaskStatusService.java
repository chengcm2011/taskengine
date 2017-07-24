package com.application.b;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cheng.jdbc.impl.BaseDAO;
import com.cheng.lang.model.UFBoolean;
import com.cheng.util.ApplicationLogger;
import com.cheng.util.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengys4
 *         2017-07-24 10:13
 **/
@Component
public class TaskStatusService implements ITaskStatusService {

    @Resource
    HttpClientUtils httpClientUtils;

    @Resource
    BaseDAO baseDAO;


    @Override
    public boolean close(List<TaskStatusModel> taskStatusModelList, String jobParameter) {
        JSONObject jsonObject = JSON.parseObject(jobParameter);
        //
        String url = "http://ams.ziroom.com/AMS/receipt/fundsTask!closeFunds.action?ids=";
        StringBuilder stringBuilder = new StringBuilder(url);
        for (TaskStatusModel taskStatusModel : taskStatusModelList) {
            stringBuilder.append(taskStatusModel.getTid()).append(",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);

        Map<String, String> cookies = new HashMap<>();
        cookies.put("CURRENT_CITY_CODE", "110000");
        cookies.put("CURRENT_CITY_NAME", "%E5%8C%97%E4%BA%AC");
        cookies.put("Hm_lpvt_038002b56790c097b74c818a80e3a68e", "1500860398");
        cookies.put("JSESSIONID", jsonObject.getString("sessionid"));
        cookies.put("gr_user_id", "f427633d-cdd9-4e8a-852f-dc6306eafa47");
        try {
            Document document = Jsoup.connect(stringBuilder.toString()).cookies(cookies).timeout(30000).get();
            if (!document.outerHtml().contains("操作成功")) {
                ApplicationLogger.info(document.outerHtml());
            }
        } catch (Exception e) {
            ApplicationLogger.error("关闭任务异常", e);
        }
        for (TaskStatusModel taskStatusModel : taskStatusModelList) {
            taskStatusModel.setClose(UFBoolean.TRUE);
        }
        baseDAO.update(taskStatusModelList);
        return true;
    }
}
