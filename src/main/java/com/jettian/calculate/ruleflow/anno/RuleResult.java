package com.jettian.calculate.ruleflow.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规则引擎结果注解类
 * 用于解析公式及结果赋值
 * @author tianjie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RuleResult {

    String value();
}
