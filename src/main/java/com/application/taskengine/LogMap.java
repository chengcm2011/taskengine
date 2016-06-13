package com.application.taskengine;

import com.application.taskengine.model.TaskLogModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cheng on 16/6/8.
 */
public class LogMap {

    private static final int maxsize = 20 ;
    public static boolean ispersistence =false;
    private static Map<String,List<TaskLogModel>> tasklogmap = new HashMap<>();


    public static boolean addLog(String taskkey,TaskLogModel taskLogModel){

        List<TaskLogModel> item = tasklogmap.get(taskkey);
        if(item==null){
            item = new ArrayList<>();
        }
        if(item.size()==maxsize){
            item.remove(maxsize-1);
            item.add(0,taskLogModel);
        }else {
            item.add(0,taskLogModel);
        }
        tasklogmap.put(taskkey,item);
        return true;
    }

    public static List<TaskLogModel> getLog(String taskkey){

        List<TaskLogModel> item = tasklogmap.get(taskkey);
       if(item==null){
           return new ArrayList<>();
       }
        return item;
    }

}
