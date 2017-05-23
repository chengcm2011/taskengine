package com.application.taskengine.model;

import com.cheng.lang.model.SuperModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 注册中心配置
 */
@Getter
@Setter
@NoArgsConstructor
public class TaskConfModel extends SuperModel {

    private String pkTaskConf;
    private String registryCenterName;
    private String zkAddressList;

    private String namespace;

    private int dr;
    private String ts;

    private String vdef1;
    private String vdef2;
    private String vdef3;
    private String vdef4;
    private String vdef5;


    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public String getPKFieldName() {
        return "pkTaskConf";
    }

    @Override
    public String getTableName() {
        return "task_conf";
    }
}
