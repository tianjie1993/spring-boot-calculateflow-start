package com.jettian.calculate.ruleflow.rules;

import com.jettian.calculate.ruleflow.actionresult.ActionResult;
import com.jettian.calculate.ruleflow.anno.RuleResult;
import com.jettian.calculate.ruleflow.excption.RuleExcption;
import com.jettian.calculate.ruleflow.knowledge.Knowledge;
import com.jettian.calculate.utils.SpringUtil;
import com.jettian.calculate.ruleflow.anno.KnowledgeNameAndSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租金规则封装类
 * @author tianjie
 */
public class RuleContainer {

    //门店租金规则集合
    private List<Rule> rules;

    //规则知识，用于获取公式中的变量
    private Knowledge ruleKnowledge;

    //最终输出的结果
    private ActionResult actionResult;

    public RuleContainer(List<Rule> rules, Knowledge ruleKnowledge,ActionResult actionResult) {
        this.rules = rules;
        this.ruleKnowledge = ruleKnowledge;
        this.actionResult = actionResult;
        init();
    }

    /**
     * 初始化rule字符串，替换当中变量为实际数据库获取值
     */
    private void init(){
        resolveRule();
    }

    //解析规则字符串
    private void resolveRule(){
        if(null== rules || rules.isEmpty() || null==ruleKnowledge){
            throw new RuleExcption("rule string is null or knowledge is null,please check data");
        }
        try {
            Map<String,String> conditionValues = new HashMap<>();
            Field[] knowFields = ruleKnowledge.getClass().getDeclaredFields();
            Field[] resultFields = actionResult.getClass().getDeclaredFields();
            for(Field field : knowFields){
                KnowledgeNameAndSource knowledgeNameAndSource = field.getAnnotation(KnowledgeNameAndSource.class);
                if(null!=knowledgeNameAndSource){
                    if(isExist(rules,knowledgeNameAndSource.name())){
                        Method method = knowledgeNameAndSource.clz().getMethod(knowledgeNameAndSource.method(),Map.class);
                        Object supplier = SpringUtil.getBean(knowledgeNameAndSource.clz());
                        if(null==supplier){
                            throw new RuleExcption("can not get "+knowledgeNameAndSource.clz().getSimpleName());
                        }
                        //获取公式参数值
                        String value = String.valueOf(method.invoke(supplier,new HashMap<String,Object>()));
                        //对知识赋值
                        Method setmethod = ruleKnowledge.getClass().getMethod("set"+toUpperCaseFirstOne(field.getName()),Double.class);
                        setmethod.invoke(ruleKnowledge,Double.parseDouble(value));
                        conditionValues.put(knowledgeNameAndSource.name(),field.getName());

                    }
                }
            }
            for(Rule rule : rules){
                //解析condition 公式
                for(String key : conditionValues.keySet()){
                    rule.setCondition(rule.getCondition().replace(key,conditionValues.get(key)));
                    if(rule.getAction().indexOf(key)>-1){
                        rule.setAction(rule.getAction().replace(key,"supplier.get"+toUpperCaseFirstOne(conditionValues.get(key))+"()"));
                    }
                }
                //解析结果公式
                String []actions = rule.getAction().split(",");
                StringBuffer actionStr = new StringBuffer();
                for(String action : actions){
                    if(action.indexOf("=")>-1){
                        String resultStr = action.split("=")[0];
                        for(Field field : resultFields){
                            RuleResult ruleResult = field.getAnnotation(RuleResult.class);
                            if(null!=ruleResult && resultStr.equals(ruleResult.value())){
                                action = action.replace(resultStr,"actionResult.set"+toUpperCaseFirstOne(field.getName())+"(String.valueOf(")+"));";
                                action = action.replace("=","");
                            }
                        }
                        actionStr.append(action);
                    }
                }
                if(rule.getAction().contains("=")){
                    rule.setAction(actionStr.toString());
                }
                if(!rule.getAction().endsWith(";")){
                    rule.setAction(rule.getAction()+";");

                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuleExcption(e.getMessage());
        }

    }

    /**
     * 校验规则中是否存在某个条件
     * @param rules
     * @param name
     * @return
     */
    private  boolean isExist(List<Rule> rules,String name){
        for(Rule rule : rules){
            System.out.println(rule.getCondition().indexOf(name));
            if(rule.getAction().indexOf(name)>=0 || rule.getCondition().indexOf(name)>=0){
                return true;
            }
        }
        return false;
    }

    //首字母转大写
    public  String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Knowledge getRuleKnowledge() {
        return ruleKnowledge;
    }

    public void setRuleKnowledge(Knowledge ruleKnowledge) {
        this.ruleKnowledge = ruleKnowledge;
    }
}
