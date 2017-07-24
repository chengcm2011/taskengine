package com.application.b.action;

import com.application.b.TaskStatusModel;
import com.application.taskengine.action.BusinessCommonAction;
import com.cheng.jdbcspring.IDataBaseService;
import com.cheng.lang.TimeToolkit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengys4
 *         2017-07-24 10:15
 **/
@RestController
public class RentPaymentStatusAction extends BusinessCommonAction {

    @Resource
    private IDataBaseService dataBaseService;

    @RequestMapping("/management/b/save")
    public AjaxMsgVO save(long tid) {

        AjaxMsgVO ajaxMsgVO = new AjaxMsgVO();

        if (tid == 0) {
            ajaxMsgVO.setCodeAndMsg(999, "参数为空");
            return ajaxMsgVO;
        }
        TaskStatusModel taskStatusModel = new TaskStatusModel();
        taskStatusModel.setTid(tid);
        taskStatusModel.setTs(TimeToolkit.getCurrentTs());
        try {
            String pk = dataBaseService.insert(taskStatusModel);
            ajaxMsgVO.setCodeAndMsg(0, "保存成功");
            ajaxMsgVO.setData(pk);
        } catch (Exception e) {
            ajaxMsgVO.setCodeAndMsg(999, "保存失败：" + e.getMessage());
        }
        return ajaxMsgVO;
    }
}
