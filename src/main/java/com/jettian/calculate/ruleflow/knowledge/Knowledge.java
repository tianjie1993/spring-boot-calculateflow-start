package com.jettian.calculate.ruleflow.knowledge;

import com.jettian.calculate.ruleflow.anno.KnowledgeNameAndSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 知识顶层接口
 * @author tianjie
 */
public interface Knowledge {

    /**
     * 获取知识类别
     * @return
     */
    default List<String> getKnowledges(){
        List<String> knowledges = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            KnowledgeNameAndSource knowledgeNameAndSource = field.getAnnotation(KnowledgeNameAndSource.class);
            if(null!=knowledgeNameAndSource){
                knowledges.add(knowledgeNameAndSource.name());
            }
        }

        return knowledges;
    }
}
