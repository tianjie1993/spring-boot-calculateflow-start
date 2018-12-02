package com.jettian.calculate.usedemo;

import com.jettian.calculate.ruleflow.anno.KnowledgeNameAndSource;
import com.jettian.calculate.ruleflow.knowledge.Knowledge;

/**
 * 在规则引擎中使用到的可变参数
 * 封装所有公式中用到的变量
 * 如：月销售额，累计销售额等
 * @author  tianjie
 * @date 2018-11-30
 */
public class RentalRuleKnowledge implements Knowledge {

    @KnowledgeNameAndSource(name="当月销售额",method = "getTheMonthSales")
    private Double theMonthSales;

    public Double getTheMonthSales() {
        return theMonthSales;
    }

    public void setTheMonthSales(Double theMonthSales) {
        this.theMonthSales = theMonthSales;
    }
}
