package com.jettian.calculate.usedemo;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 知识提供类
 * 主要用于计算公式中各可变参数值
 * 类中所有方法以Map为参数返回为Double
 * @author tianjie
 */
@Service
public class KnowledgeSupplier {

    //获取当月销售额
    public Double getTheMonthSales(Map<String,Object> map){
        return 2323D;
    }
}
