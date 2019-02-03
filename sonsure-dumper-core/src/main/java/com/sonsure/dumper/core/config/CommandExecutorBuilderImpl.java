package com.sonsure.dumper.core.config;

import com.sonsure.dumper.core.command.AbstractCommandExecutor;
import com.sonsure.dumper.core.command.CommandContextBuilder;
import com.sonsure.dumper.core.command.CommandExecutor;
import com.sonsure.dumper.core.command.entity.*;
import com.sonsure.dumper.core.command.mybatis.MybatisCommandContextBuilder;
import com.sonsure.dumper.core.command.mybatis.MybatisExecutor;
import com.sonsure.dumper.core.command.mybatis.MybatisExecutorImpl;
import com.sonsure.dumper.core.command.natives.NativeExecutor;
import com.sonsure.dumper.core.command.natives.NativeExecutorImpl;
import com.sonsure.dumper.core.command.simple.SimpleCommandContextBuilder;

import java.util.Arrays;
import java.util.List;

public class CommandExecutorBuilderImpl extends AbstractCommandExecutorBuilder {

    protected List<Class<?>> commandExecutorClasses;

    public CommandExecutorBuilderImpl() {
        commandExecutorClasses = Arrays.asList(new Class<?>[]{Insert.class, Select.class, Update.class, Delete.class, NativeExecutor.class, MybatisExecutor.class});
    }

    @Override
    public boolean support(Class<?> modelClass, Class<?> commandExecutorClass, JdbcEngineConfig jdbcEngineConfig) {
        return commandExecutorClasses.contains(commandExecutorClass);
    }

    @Override
    public CommandExecutor build(Class<?> modelClass, Class<?> commandExecutorClass, JdbcEngineConfig jdbcEngineConfig) {

        AbstractCommandExecutor commandExecutor = null;
        CommandContextBuilder commandContextBuilder = null;

        if (commandExecutorClass == Insert.class) {
            commandExecutor = new InsertImpl<>(
                    getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new InsertCommandContextBuilderImpl(commandExecutor);
        } else if (commandExecutorClass == Select.class) {
            commandExecutor = new SelectImpl<>(
                    getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new SelectCommandContextBuilderImpl(commandExecutor);
        } else if (commandExecutorClass == Update.class) {
            commandExecutor = new UpdateImpl<>(
                    getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new UpdateCommandContextBuilderImpl(commandExecutor);
        } else if (commandExecutorClass == Delete.class) {
            commandExecutor = new DeleteImpl<>(
                    getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new DeleteCommandContextBuilderImpl(commandExecutor);
        } else if (commandExecutorClass == NativeExecutor.class) {
            commandExecutor = new NativeExecutorImpl(getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new SimpleCommandContextBuilder(commandExecutor, jdbcEngineConfig.getCommandResolver());
        } else if (commandExecutorClass == MybatisExecutor.class) {
            commandExecutor = new MybatisExecutorImpl(getExecutorMappingHandler(modelClass, jdbcEngineConfig),
                    getExecutorPageHandler(modelClass, jdbcEngineConfig),
                    getExecutorKeyGenerator(modelClass, jdbcEngineConfig),
                    getExecutorPersistExecutor(modelClass, jdbcEngineConfig),
                    jdbcEngineConfig.isCommandUppercase());
            commandContextBuilder = new MybatisCommandContextBuilder(commandExecutor, jdbcEngineConfig.getCommandResolver(), jdbcEngineConfig.getMybatisSqlSessionFactory());
        }

        commandExecutor.setModelClass(modelClass);
        commandExecutor.setCommandContextBuilder(commandContextBuilder);
        return commandExecutor;
    }
}