package com.jettian.calculate.ruleflow.config;

import com.jettian.calculate.utils.RuleContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianjie
 * @version V1.0
 * @email tj_work@yeah.net
 */
@Configuration
public class RuleAutoConfig {

    @Bean
    public RuleContext getRuleContext() {
        return new RuleContext();
    }
}
