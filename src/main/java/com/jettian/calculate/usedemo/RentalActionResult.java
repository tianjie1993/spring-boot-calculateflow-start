package com.jettian.calculate.usedemo;

import com.jettian.calculate.ruleflow.actionresult.ActionResult;
import com.jettian.calculate.ruleflow.anno.RuleResult;

/**
 * 租金计算结果接受类
 * 类型统一为String
 * @author tianjie
 */
public class RentalActionResult implements ActionResult {

    //租金
    @RuleResult("租金")
    private String rentalWithTax;

    //税后租金
    @RuleResult("税后租金")
    private String rentalWithoutTax;

    public String getRentalWithTax() {
        return rentalWithTax;
    }

    public void setRentalWithTax(String rentalWithTax) {
        this.rentalWithTax = rentalWithTax;
    }

    public String getRentalWithoutTax() {
        return rentalWithoutTax;
    }

    public void setRentalWithoutTax(String rentalWithoutTax) {
        this.rentalWithoutTax = rentalWithoutTax;
    }

    @Override
    public String toString() {
        return "RentalActionResult{" +
                "rentalWithTax='" + rentalWithTax + '\'' +
                ", rentalWithoutTax='" + rentalWithoutTax + '\'' +
                '}';
    }
}
