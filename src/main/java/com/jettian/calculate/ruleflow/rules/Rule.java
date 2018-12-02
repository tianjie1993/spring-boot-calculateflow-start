package com.jettian.calculate.ruleflow.rules;

/**
 * 计算规则封装类，封装条件与执行动作
 * @author tianjie
 */
public class Rule {

    private String condition;

    private String action;

    public Rule(String condition, String action) {
        this.condition = condition;
        this.action = action;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
