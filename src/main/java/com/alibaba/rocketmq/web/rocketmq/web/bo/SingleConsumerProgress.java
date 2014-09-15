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

import java.util.List;

/**
 *
 * @author xiangnan.wang@ipinyou.com
 */
public class SingleConsumerProgress {

    private long tps;

    private long totalDiff;
    
    private List<ConsumerDetail> cdList;

    public long getTps() {
        return tps;
    }

    public void setTps(long tps) {
        this.tps = tps;
    }

    public long getTotalDiff() {
        return totalDiff;
    }

    public void setTotalDiff(long totalDiff) {
        this.totalDiff = totalDiff;
    }

    public List<ConsumerDetail> getCdList() {
        return cdList;
    }

    public void setCdList(List<ConsumerDetail> cdList) {
        this.cdList = cdList;
    }
    
    public static class ConsumerDetail {

        private String topic;

        private String brokerName;

        private int mqId;

        private long brokerOffset;

        private long consumerOffset;

        private long diff;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getBrokerName() {
            return brokerName;
        }

        public void setBrokerName(String brokerName) {
            this.brokerName = brokerName;
        }

        public int getMqId() {
            return mqId;
        }

        public void setMqId(int mqId) {
            this.mqId = mqId;
        }

        public long getBrokerOffset() {
            return brokerOffset;
        }

        public void setBrokerOffset(long brokerOffset) {
            this.brokerOffset = brokerOffset;
        }

        public long getConsumerOffset() {
            return consumerOffset;
        }

        public void setConsumerOffset(long consumerOffset) {
            this.consumerOffset = consumerOffset;
        }

        public long getDiff() {
            return diff;
        }

        public void setDiff(long diff) {
            this.diff = diff;
        }
    }

}
