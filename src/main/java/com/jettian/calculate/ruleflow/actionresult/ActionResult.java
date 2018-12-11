package com.jettian.calculate.ruleflow.actionresult;

import com.jettian.calculate.ruleflow.anno.KnowledgeNameAndSource;
import com.jettian.calculate.ruleflow.anno.RuleResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 接受结果顶层接口
 * @author tianjie
 */
public interface ActionResult {

    /**
     * 获取结果类别
     * @return
     */
    default List<String> getKnowledges(){
        List<String> actionResults = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            RuleResult ruleResult = field.getAnnotation(RuleResult.class);
            if(null!=ruleResult){
                actionResults.add(ruleResult.value());
            }
        }

        return actionResults;
    }
}
