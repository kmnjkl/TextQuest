package com.lkjuhkmnop.textquest.utils;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

public class MathExpressionEvaluator {
    private final DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
    private final StaticVariableSet<Double> variableSet = new StaticVariableSet<>();

    public Double evaluate(String ex) {
        return doubleEvaluator.evaluate(ex, variableSet);
    }

    public void setVariable(String name, double value) {
        variableSet.set(name, value);
    }
}
