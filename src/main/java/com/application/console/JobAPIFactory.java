/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.application.console;

import com.cheng.web.ApplicationServiceLocator;
import com.dangdang.ddframe.job.lite.lifecycle.api.*;
import com.dangdang.ddframe.job.lite.lifecycle.internal.operate.JobOperateAPIImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.operate.ShardingOperateAPIImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.settings.JobSettingsAPIImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.statistics.JobStatisticsAPIImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.statistics.ServerStatisticsAPIImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.statistics.ShardingStatisticsAPIImpl;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 作业API工厂.
 *
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JobAPIFactory {

    /**
     * 创建作业配置API对象.
     *
     * @return 作业配置API对象
     */
    public static JobSettingsAPI createJobSettingsAPI() {

        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        return new JobSettingsAPIImpl(regCenter);
    }

    /**
     * 创建操作作业API对象.
     *
     * @return 操作作业API对象
     */
    public static JobOperateAPI createJobOperateAPI() {
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");

        return new JobOperateAPIImpl(regCenter);
    }

    /**
     * 创建操作分片API对象.
     *
     * @return 操作分片API对象
     */
    public static ShardingOperateAPI createShardingOperateAPI() {
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");

        return new ShardingOperateAPIImpl(regCenter);
    }

    /**
     * 创建作业状态展示API对象.
     *
     * @return 作业状态展示API对象
     */
    public static JobStatisticsAPI createJobStatisticsAPI() {
        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");

        return new JobStatisticsAPIImpl(regCenter);
    }

    /**
     * 创建作业服务器状态展示API对象.
     *
     * @return 作业服务器状态展示API对象
     */
    public static ServerStatisticsAPI createServerStatisticsAPI() {

        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        return new ServerStatisticsAPIImpl(regCenter);
    }

    /**
     * 创建作业分片状态展示API对象.
     *
     * @return 分片状态展示API对象
     */
    public static ShardingStatisticsAPI createShardingStatisticsAPI() {

        CoordinatorRegistryCenter regCenter = ApplicationServiceLocator.getBean("zookeeperRegistryCenter");
        return new ShardingStatisticsAPIImpl(regCenter);
    }
}
