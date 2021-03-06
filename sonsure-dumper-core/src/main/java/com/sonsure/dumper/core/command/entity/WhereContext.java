package com.sonsure.dumper.core.command.entity;

import com.sonsure.dumper.core.management.ClassField;

import java.util.ArrayList;
import java.util.List;

public abstract class WhereContext extends EntityExecutorContext {

    /**
     * where的属性/列
     */
    protected List<ClassField> whereFields;

    public WhereContext() {
        whereFields = new ArrayList<>();
    }

    public void addWhereField(String logicalOperator, String name, String fieldOperator, Object value) {
        this.addWhereField(logicalOperator, name, fieldOperator, value, null);
    }

    public void addWhereField(String logicalOperator, String name, String fieldOperator, Object value, ClassField.Type type) {
        boolean analyseTableAlias = ClassField.Type.isAnalyseTableAlias(type);
        ClassField classField = new ClassField(name, analyseTableAlias);
        classField.setLogicalOperator(logicalOperator);
        classField.setFieldOperator(fieldOperator);
        classField.setValue(value);
        classField.setType(type);
        this.whereFields.add(classField);
    }

    public void addWhereFields(List<ClassField> classFields) {
        this.whereFields.addAll(classFields);
    }

    public List<ClassField> getWhereFields() {
        return whereFields;
    }

}
