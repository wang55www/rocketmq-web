/*
 * Copyright 2014 wangxiangnan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.rocketmq.web.rocketmq.web.bo;

/**
 *
 * @author xiangnan.wang@ipinyou.com
 */
public class TotalConsumerProgress implements Comparable<TotalConsumerProgress>{
    
    private String group;
    
    private int count;
    
    private String version;
    
    private String type;
    
    private long tps;
    
    private long diff;
    

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTps() {
        return tps;
    }

    public void setTps(long tps) {
        this.tps = tps;
    }

    public long getDiff() {
        return diff;
    }

    public void setDiff(long diff) {
        this.diff = diff;
    }
    
    @Override
    public int compareTo(TotalConsumerProgress o) {
        if (this.count != o.count) {
            return o.count - this.count;
        }

        return (int) (o.diff - diff);
    }

}
