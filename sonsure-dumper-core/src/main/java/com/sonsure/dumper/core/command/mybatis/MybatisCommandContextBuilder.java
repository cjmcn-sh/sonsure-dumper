package com.sonsure.dumper.core.command.mybatis;

import com.sonsure.dumper.core.command.CommandContext;
import com.sonsure.dumper.core.command.ExecutorContext;
import com.sonsure.dumper.core.command.entity.AbstractCommandContextBuilder;
import com.sonsure.dumper.core.config.JdbcEngineConfig;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.List;

public class MybatisCommandContextBuilder extends AbstractCommandContextBuilder {

    @Override
    public CommandContext doBuild(ExecutorContext executorContext, JdbcEngineConfig jdbcEngineConfig) {

        MybatisExecutorContext mybatisExecutorContext = (MybatisExecutorContext) executorContext;
        CommandContext commandContext = new CommandContext();

        SqlSessionFactory sqlSessionFactory = jdbcEngineConfig.getMybatisSqlSessionFactory();
        MappedStatement statement = sqlSessionFactory.getConfiguration().getMappedStatement(mybatisExecutorContext.getCommand());
        Configuration configuration = sqlSessionFactory.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        BoundSql boundSql = statement.getBoundSql(mybatisExecutorContext.getParameters());
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    commandContext.addParameter(value);
                }
            }
        }
        commandContext.setCommand(boundSql.getSql());
        return commandContext;
    }
}
