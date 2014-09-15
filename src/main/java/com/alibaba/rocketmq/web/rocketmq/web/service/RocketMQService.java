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

package com.alibaba.rocketmq.web.rocketmq.web.service;

import com.alibaba.rocketmq.common.MQVersion;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.admin.ConsumeStats;
import com.alibaba.rocketmq.common.admin.OffsetWrapper;
import com.alibaba.rocketmq.common.admin.TopicOffset;
import com.alibaba.rocketmq.common.admin.TopicStatsTable;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.body.ClusterInfo;
import com.alibaba.rocketmq.common.protocol.body.ConsumerConnection;
import com.alibaba.rocketmq.common.protocol.body.TopicList;
import com.alibaba.rocketmq.common.protocol.heartbeat.ConsumeType;
import com.alibaba.rocketmq.common.protocol.route.BrokerData;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import com.alibaba.rocketmq.web.rocketmq.web.bo.ClusterInfoBo;
import com.alibaba.rocketmq.web.rocketmq.web.bo.SingleConsumerProgress;
import com.alibaba.rocketmq.web.rocketmq.web.bo.TopicStatsBo;
import com.alibaba.rocketmq.web.rocketmq.web.bo.TotalConsumerProgress;
import com.alibaba.rocketmq.web.util.LoggerUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 初始化相关业务
 * @author xiangnan.wang@ipinyou.com
 */
@Service
public class RocketMQService {
    
    @Value("${rocketmq.namesrv.addr}")
    private String nameAddr;
    
    private DefaultMQAdminExt adminExt;
    
    @PostConstruct
    private void initRocketMQEnv(){
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY,nameAddr);
        adminExt = new DefaultMQAdminExt();
        adminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            adminExt.start();
        } catch (Exception ex) {
            LoggerUtil.getLogger().error("initRocketMQEnv error",ex);
        }
    }
    
    /**
     * 获取集群信息
     * @return 
     */
    public List<ClusterInfoBo> getClusterInfo(){
        List<ClusterInfoBo> cibl=new ArrayList<ClusterInfoBo>();
        try {
            ClusterInfo cinfo = adminExt.examineBrokerClusterInfo();
            for(Map.Entry<String,Set<String>> entry:cinfo.getClusterAddrTable().entrySet()){
                String clusterName=entry.getKey();
                for(String brokerName:entry.getValue()){
                    BrokerData bd=cinfo.getBrokerAddrTable().get(brokerName);
                    for(Map.Entry<Long,String> bentry:bd.getBrokerAddrs().entrySet()){
                        Map<String,String> rmap=adminExt.fetchBrokerRuntimeStats(bentry.getValue()).getTable();
                        double inTps=0d;
                        double outTps=0d;
                        try{
                            {
                               String tpses=rmap.get("putTps");
                               String[] tpsArr=tpses.split(" ");
                               if(tpsArr!=null&&tpsArr.length>0){
                                   inTps=Double.parseDouble(tpsArr[0]);
                               }
                            }
                            {
                               String tpses=rmap.get("getTransferedTps");
                               String[] tpsArr=tpses.split(" ");
                               if(tpsArr!=null&&tpsArr.length>0){
                                   outTps=Double.parseDouble(tpsArr[0]);
                               }
                            }
                        }catch(Exception e){}
                        ClusterInfoBo cib=new ClusterInfoBo();
                        cib.setClusterName(clusterName);
                        cib.setBrokerName(brokerName);
                        cib.setInTps(inTps);
                        cib.setOutTps(outTps);
                        cib.setBid(bentry.getKey().toString());
                        cib.setVersion(rmap.get("brokerVersionDesc"));
                        cib.setAddr(bentry.getValue());
                        cibl.add(cib);
                    }
                }
            }
        } catch (Exception ex) {
            LoggerUtil.getLogger().error("getClusterInfo", ex);
        }
       return cibl;
    }
    
    /**
     * 获取消费进度信息所有的消费组
     * @return 
     */
    public List<TotalConsumerProgress> consumerProgress(){
        List<TotalConsumerProgress> tcprogressl=new ArrayList<TotalConsumerProgress>();
        try {
            TopicList topicl=adminExt.fetchAllTopicList();
            for(String topic:topicl.getTopicList()){
                if(topic.startsWith(MixAll.RETRY_GROUP_TOPIC_PREFIX)){
                    String consumerGroup=topic.substring(MixAll.RETRY_GROUP_TOPIC_PREFIX.length());
                    ConsumeStats consumeStats = null;
                    try {
                        consumeStats = adminExt.examineConsumeStats(consumerGroup);
                    } catch (Exception e) {
                        LoggerUtil.getLogger().warn("examineConsumeStats exception, " + consumerGroup, e);
                    }
                    ConsumerConnection cc = null;
                    try {
                        cc = adminExt.examineConsumerConnectionInfo(consumerGroup);
                    } catch (Exception e) {
                        LoggerUtil.getLogger().warn("examineConsumerConnectionInfo exception, " + consumerGroup, e);
                    }
                    TotalConsumerProgress tcprogress=new TotalConsumerProgress();
                    tcprogress.setGroup(consumerGroup);
                    if(consumeStats!=null){
                        tcprogress.setTps(consumeStats.getConsumeTps());
                        tcprogress.setDiff(consumeStats.computeTotalDiff());
                    }
                    if(cc!=null){
                        tcprogress.setCount(cc.getConnectionSet().size());
                        tcprogress.setType(cc.getConsumeType()==ConsumeType.CONSUME_ACTIVELY?"PULL":"PUSH");
                        tcprogress.setVersion(MQVersion.getVersionDesc(cc.computeMinVersion()));
                    }
                    tcprogressl.add(tcprogress);
                }
            }
        } catch (Exception ex) {
            LoggerUtil.getLogger().error("consumerProgress()",ex);
        }
        
        return tcprogressl;
    }
    
    
    /**
     * 获取某个consumergroup的消费明细
     * @param consumerGroup 消费组
     * @return 
     */
    public SingleConsumerProgress consumerProgress(String consumerGroup){
        SingleConsumerProgress sp=new SingleConsumerProgress();
        List<SingleConsumerProgress.ConsumerDetail> cdList=new ArrayList<SingleConsumerProgress.ConsumerDetail>();
        try {
            ConsumeStats stats=adminExt.examineConsumeStats(consumerGroup);
            Map<MessageQueue, OffsetWrapper> m=stats.getOffsetTable();
            
            List<MessageQueue> mqList = new LinkedList<MessageQueue>();
            mqList.addAll(stats.getOffsetTable().keySet());
            Collections.sort(mqList);
            
            long diffTotal = 0L;
            
            for(MessageQueue mq:mqList){
                OffsetWrapper offsetWrapper = stats.getOffsetTable().get(mq);
                long diff = offsetWrapper.getBrokerOffset() - offsetWrapper.getConsumerOffset();
                diffTotal += diff; 
                
                SingleConsumerProgress.ConsumerDetail cd=new SingleConsumerProgress.ConsumerDetail();
                cd.setBrokerName(mq.getBrokerName());
                cd.setMqId(mq.getQueueId());
                cd.setTopic(mq.getTopic());
                cd.setBrokerOffset(offsetWrapper.getBrokerOffset());
                cd.setConsumerOffset(offsetWrapper.getConsumerOffset());
                cd.setDiff(offsetWrapper.getBrokerOffset()-offsetWrapper.getConsumerOffset());
                cdList.add(cd);
            }
            sp.setCdList(cdList);
            sp.setTotalDiff(diffTotal);
            sp.setTps(stats.getConsumeTps());
        } catch (Exception ex) {
            LoggerUtil.getLogger().warn("consumerProgress single error:"+consumerGroup,ex);
        }
        return sp;
    }
    
    /**
     * 获取topic列表
     * @return
     */
    public Set<String> getTopics(){
        Set<String> topicSet=new LinkedHashSet<String>();
        try {
            TopicList topicList=adminExt.fetchAllTopicList();
            for(String topic:topicList.getTopicList()){
                if(!topic.startsWith(MixAll.RETRY_GROUP_TOPIC_PREFIX)&&
                        !topic.startsWith(MixAll.DLQ_GROUP_TOPIC_PREFIX)){
                    topicSet.add(topic);
                }
            }
        } catch (Exception ex) {
            LoggerUtil.getLogger().error("getTopics error:",ex);
        }
        return topicSet;
    }
    
    /**
     * 获取topic状态信息
     * @param topic topic名称
     * @return 
     */
    public List<TopicStatsBo> getTopicStats(String topicName){
        List<TopicStatsBo> tsbList=new ArrayList<TopicStatsBo>();
        try{
            TopicStatsTable tst=adminExt.examineTopicStats(topicName);
            List<MessageQueue> mqList=new LinkedList<MessageQueue>();
            mqList.addAll(tst.getOffsetTable().keySet());
            Collections.sort(mqList);
            for(MessageQueue mq:mqList){
                TopicOffset offset=tst.getOffsetTable().get(mq);
                TopicStatsBo tsb=new TopicStatsBo();
                tsb.setBrokerName(mq.getBrokerName());
                tsb.setQid(mq.getQueueId());
                tsb.setMinOffset(offset.getMaxOffset());
                tsb.setMaxOffset(offset.getMaxOffset());
                tsb.setLastUpdate(new Date(offset.getLastUpdateTimestamp()));
                tsbList.add(tsb);
            }
        }catch(Exception e){
            LoggerUtil.getLogger().error("getTopicStats error:",e);
        }
        return tsbList;
    }
    
    @PreDestroy
    public void clean(){
        if(adminExt!=null){
            adminExt.shutdown();
        }
    }
    
}
