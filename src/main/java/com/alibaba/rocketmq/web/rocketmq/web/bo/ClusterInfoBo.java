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
public class ClusterInfoBo {
    
    private String clusterName;
    
    private String brokerName;
    
    private String bid;
    
    private String addr;
    
    private String version;
    
    private double inTps;
    
    private double outTps;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getInTps() {
        return inTps;
    }

    public void setInTps(double inTps) {
        this.inTps = inTps;
    }

    public double getOutTps() {
        return outTps;
    }

    public void setOutTps(double outTps) {
        this.outTps = outTps;
    }

    @Override
    public String toString() {
        return "ClusterInfoBo{" + "clusterName=" + clusterName + ", brokerName=" + brokerName + ", bid=" + bid + ", addr=" + addr + ", version=" + version + ", inTps=" + inTps + ", outTps=" + outTps + '}';
    }

    
}
