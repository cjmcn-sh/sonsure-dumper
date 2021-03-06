package com.sonsure.dumper.core.config;

import com.sonsure.dumper.core.command.CommandExecutor;

public interface CommandExecutorFactory {


    /**
     * 获取commandExecutor
     *
     * @param commandExecutorClass the command executor class
     * @param param                the param
     * @param jdbcEngineConfig     the jdbc engine config
     * @return command executor
     */
    CommandExecutor getCommandExecutor(Class<? extends CommandExecutor> commandExecutorClass, Object param, JdbcEngineConfig jdbcEngineConfig);

}
