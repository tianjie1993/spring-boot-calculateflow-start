package com.jettian.calculate;

import com.jettian.calculate.ruleflow.rules.Rule;
import com.jettian.calculate.ruleflow.rules.RuleContainer;
import com.jettian.calculate.ruleflow.util.RuleDriverUtil;
import com.jettian.calculate.usedemo.RentalActionResult;
import com.jettian.calculate.usedemo.RentalRuleKnowledge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsApplicationTests {

    @Test
    public void ruleTest() {
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(new Rule("当月销售额>20000","租金=当月销售额*0.1"));
        ruleList.add(new Rule("当月销售额<=20000","租金=当月销售额/3*1.06"));
        RentalActionResult rentalActionResult = new RentalActionResult();
        RuleContainer ruleContainer = new RuleContainer(ruleList,new RentalRuleKnowledge(),rentalActionResult);
        RuleDriverUtil.excute(ruleContainer);
        System.out.println(rentalActionResult);
    }

}
