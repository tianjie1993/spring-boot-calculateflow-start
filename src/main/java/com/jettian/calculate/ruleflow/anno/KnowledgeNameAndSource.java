package com.jettian.calculate.ruleflow.anno;

import com.jettian.calculate.usedemo.KnowledgeSupplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 公式中各种可变参数中文名称以及数据来源
 * @author tianjie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KnowledgeNameAndSource {

    //中文名称
    String name() ;

    //类中执行的方法
    String method();

    Class clz() default KnowledgeSupplier.class;
}
