package com.jettian.calculate.ruleflow.util;

import com.jettian.calculate.ruleflow.excption.RuleExcption;
import com.jettian.calculate.ruleflow.rules.Rule;
import com.jettian.calculate.ruleflow.rules.RuleContainer;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 负责执行规则
 * @author tianjie
 */
public class RuleDriverUtil {

    private static final String rn = "\r\n";

    //执行规则
    public static void excute(RuleContainer ruleContainer){
        KieSession kSession = null;
        try {
            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            List<Rule> rules = ruleContainer.getRules();
            StringBuffer rule = new StringBuffer();
            rule.append("package rules").append(rn);
            rule.append("import "+RuleContainer.class.getName()+";").append(rn);
            rule.append("import "+ruleContainer.getRuleKnowledge().getClass().getName()+";").append(rn);
            rule.append("import "+ruleContainer.getActionResult().getClass().getName()+";").append(rn);
            rule.append("global "+ruleContainer.getActionResult().getClass().getSimpleName()+" actionResult").append(rn);
            for(int i = 0; i<rules.size() ;i++){
                rule.append("rule \"rule"+i+"\"").append(rn);
                rule.append("when").append(rn);
                rule.append("supplier:"+ruleContainer.getRuleKnowledge().getClass().getSimpleName()+"("+rules.get(i).getCondition()+")").append(rn);
                rule.append("then").append(rn);
                rule.append(rules.get(i).getAction()).append(rn);
                rule.append("end").append(rn);
            }
            kb.add(ResourceFactory.newByteArrayResource(rule.toString()
                    .getBytes("utf-8")), ResourceType.DRL);
            //错误的类型化集合。
            KnowledgeBuilderErrors errors = kb.getErrors();
            if(!errors.isEmpty()){
                StringBuffer errorMsg = new StringBuffer();
                for (KnowledgeBuilderError error : errors) {
                    errorMsg.append(error.getMessage());
                }
                throw new RuleExcption(errorMsg.toString());
            }
            InternalKnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addPackages(kb.getKnowledgePackages());
            kSession = kBase.newKieSession();
            kSession.insert(ruleContainer.getRuleKnowledge());
            kSession.setGlobal( "actionResult", ruleContainer.getActionResult() );
            //执行规则计算
            kSession.fireAllRules();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (kSession != null)
                kSession.dispose();
        }
    }
}
