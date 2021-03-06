package com.sonsure.dumper.core.command.entity;

import com.sonsure.commons.utils.ClassUtils;
import com.sonsure.dumper.core.annotation.Transient;
import com.sonsure.dumper.core.command.AbstractCommandExecutor;
import com.sonsure.dumper.core.config.JdbcEngineConfig;
import com.sonsure.dumper.core.management.ClassField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liyd on 17/4/11.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractConditionCommandExecutor<T extends ConditionCommandExecutor<T>> extends AbstractCommandExecutor implements ConditionCommandExecutor<T> {

    public AbstractConditionCommandExecutor(JdbcEngineConfig jdbcEngineConfig) {
        super(jdbcEngineConfig);
    }

    public T where() {
        this.getWhereContext().addWhereField("where", null, null, null);
        return (T) this;
    }

    public T where(String field, Object[] value) {
        this.where(field, value == null ? "is" : "=", value);
        return (T) this;
    }

    public T where(String field, Object value) {
        Object[] values = value == null ? null : (value instanceof Object[] ? (Object[]) value : new Object[]{value});
        this.where(field, value == null ? "is" : "=", values);
        return (T) this;
    }

    public T where(String field, String operator, Object... values) {
        this.getWhereContext().addWhereField("where", field, operator, values);
        return (T) this;
    }

    public T condition(String field, Object value) {
        Object[] values = value instanceof Object[] ? (Object[]) value : new Object[]{value};
        return this.condition(field, value == null ? "is" : "=", values);
    }

    public T condition(String field, Object[] value) {
        return this.condition(field, value == null ? "is" : "=", value);
    }

    public T condition(String field, String operator, Object... values) {
        this.getWhereContext().addWhereField(null, field, operator, values);
        return (T) this;
    }

    public T conditionEntity(Object entity) {
        return conditionEntity(entity, null, "and");
    }

    @Override
    public T andConditionEntity(Object entity) {
        return conditionEntity(entity, "and", "and");
    }

    @Override
    public T conditionEntity(Object entity, String wholeLogicalOperator, String fieldLogicalOperator) {

        Map<String, Object> beanPropMap = ClassUtils.getSelfBeanPropMap(entity, Transient.class);

        int count = 1;
        List<ClassField> fieldList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : beanPropMap.entrySet()) {
            //忽略掉null
            if (entry.getValue() == null) {
                continue;
            }
            ClassField classField = new ClassField(entry.getKey(), false);
            classField.setLogicalOperator(count > 1 ? fieldLogicalOperator : null);
            classField.setFieldOperator("=");
            classField.setValue(entry.getValue());
            fieldList.add(classField);
            count++;
        }
        //防止属性全为null的情况
        if (!fieldList.isEmpty()) {
            this.getWhereContext().addWhereField(wholeLogicalOperator, null, null, null);
            this.begin();
            this.getWhereContext().addWhereFields(fieldList);
            this.end();
        }
        return (T) this;
    }

    public T and() {
        this.getWhereContext().addWhereField("and", null, null, null);
        return (T) this;
    }

    public T and(String field, Object value) {
        Object[] values = value instanceof Object[] ? (Object[]) value : new Object[]{value};
        this.and(field, value == null ? "is" : "=", values);
        return (T) this;
    }

    public T and(String field, Object[] value) {
        this.and(field, value == null ? "is" : "=", value);
        return (T) this;
    }

    public T and(String field, String operator, Object... values) {
        this.getWhereContext().addWhereField("and", field, operator, values);
        return (T) this;
    }

    public T or() {
        this.getWhereContext().addWhereField("or", null, null, null);
        return (T) this;
    }

    public T or(String field, Object value) {
        Object[] values = value instanceof Object[] ? (Object[]) value : new Object[]{value};
        return this.or(field, value == null ? "is" : "=", values);
    }

    public T or(String field, Object[] values) {
        return this.or(field, values == null ? "is" : "=", values);
    }

    public T or(String field, String operator, Object... values) {
        this.getWhereContext().addWhereField("or", field, operator, values);
        return (T) this;
    }

    public T begin() {
        this.getWhereContext().addWhereField("(", null, null, null);
        return (T) this;
    }

    public T end() {
        this.getWhereContext().addWhereField(")", null, null, null);
        return (T) this;
    }

    @Override
    public T append(String segment, Object... params) {
        this.getWhereContext().addWhereField(null, segment, null, params, ClassField.Type.WHERE_APPEND);
        return (T) this;
    }

    protected abstract WhereContext getWhereContext();
}
