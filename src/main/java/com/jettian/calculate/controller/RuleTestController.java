package com.jettian.calculate.controller;

import com.jettian.calculate.ruleflow.rules.Rule;
import com.jettian.calculate.usedemo.RentalActionResult;
import com.jettian.calculate.usedemo.RentalRuleKnowledge;
import com.jettian.calculate.ruleflow.rules.RuleContainer;
import com.jettian.calculate.ruleflow.util.RuleDriverUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RuleTestController {

    @RequestMapping("/test")
    public Object test(){
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(new Rule("当月销售额>20000","租金=当月销售额*0.1"));
        ruleList.add(new Rule("当月销售额<=20000","租金=当月销售额/3*1.06"));
        RentalActionResult rentalActionResult = new RentalActionResult();
        RuleContainer ruleContainer = new RuleContainer(ruleList,new RentalRuleKnowledge(),rentalActionResult);
        RuleDriverUtil.excute(ruleContainer);
        return rentalActionResult;
    }
}
