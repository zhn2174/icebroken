/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
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
 *
 */
package com.icebroken.api;

import com.icebroken.R;
import com.icebroken.app.AppApplication;


/**
 * 接口地址
 */
public class ApiConstants {

    /* config.xml baseIP */
    public static final String baseIP = AppApplication.getAppResources().getString(R.string.baseIP);

    public static final String ServerAddress = "http://154.8.150.17:10001/api/";//线上环境



    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.MAIN:
                host = ServerAddress;
                break;
            case HostType.MAIN_ADDRESS:
                host = ServerAddress;
                break;
            case HostType.MAIN_ADDRESS31:
                host = ServerAddress;
                break;
            case HostType.OTHER:
                host = ServerAddress;
                break;

            default:
                host = "";
                break;
        }
        return host;
    }
}
