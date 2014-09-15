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

package com.alibaba.rocketmq.web.rocketmq.web.controller;

import com.alibaba.rocketmq.web.rocketmq.web.bo.ClusterInfoBo;
import com.alibaba.rocketmq.web.rocketmq.web.bo.SingleConsumerProgress;
import com.alibaba.rocketmq.web.rocketmq.web.bo.TopicStatsBo;
import com.alibaba.rocketmq.web.rocketmq.web.bo.TotalConsumerProgress;
import com.alibaba.rocketmq.web.rocketmq.web.service.RocketMQService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author xiangnan.wang@ipinyou.com
 */
@Controller
public class RocketMQController {

    @Autowired
    private RocketMQService rmqService;
    
    @RequestMapping("/clusterList")
    public ModelAndView getClusterList(){
        List<ClusterInfoBo> cibl=rmqService.getClusterInfo();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("clusterlist");
        mv.addObject("cibl", cibl);
        return mv;
    }
    
    @RequestMapping("/consumerProgress/{group}")
    public ModelAndView consumerProgress(@PathVariable String group){
        ModelAndView mv=new ModelAndView();
        SingleConsumerProgress scp=rmqService.consumerProgress(group);
        mv.addObject("result", scp);
        mv.addObject("all",false);
        mv.setViewName("consumerprogress");
        return mv;
    }
    
    @RequestMapping("/consumerProgress")
    public ModelAndView consumerProgress(){
        ModelAndView mv=new ModelAndView();
        List<TotalConsumerProgress> tcpList=rmqService.consumerProgress();
        Collections.sort(tcpList);
        mv.addObject("result", tcpList);
        mv.addObject("all",true);
        mv.setViewName("consumerprogress");
        return mv;
    }
    
    @RequestMapping("topicList")
    public ModelAndView topicList(){
        ModelAndView mv=new ModelAndView();
        Set<String> topicSet=rmqService.getTopics();
        mv.addObject("result",topicSet);
        mv.setViewName("topiclist");
        return mv;
    }
    
    @RequestMapping("topicStats/{topic}")
    public ModelAndView topicStats(@PathVariable String topic){
        ModelAndView mv=new ModelAndView();
        List<TopicStatsBo> tsbList=rmqService.getTopicStats(topic);
        mv.addObject("result", tsbList);
        mv.setViewName("topicstats");
        return mv;
    }
    
}
