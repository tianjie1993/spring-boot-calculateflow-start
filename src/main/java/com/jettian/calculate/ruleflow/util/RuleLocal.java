package com.jettian.calculate.ruleflow.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放方法携带的参数变量
 * @author tianjie
 */
public class RuleLocal {

    private static final ThreadLocal<Map<String,Object>> RULE_LOCAL = new ThreadLocal<>();

    public static void put(String key,Object value){
        Map<String,Object> map = RULE_LOCAL.get();
        if(null==map){
            map = new HashMap<>();
            RULE_LOCAL.set(map);
        }
        map.put(key,value);
    }
    public static Map<String,Object> get(){
        return null==RULE_LOCAL.get()?new HashMap<>():RULE_LOCAL.get();
    }

    public static void remove(){
        RULE_LOCAL.remove();
    }
}
