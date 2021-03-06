package com.sonsure.dumper.core.command;

import com.sonsure.dumper.core.config.JdbcEngineConfig;

/**
 * CommandContext构建
 * <p>
 * Created by liyd on 17/4/11.
 */
public interface CommandContextBuilder {

    /**
     * 构建执行内容
     *
     * @param executorContext  the executor context
     * @param jdbcEngineConfig the jdbc engine config
     * @return command context
     */
    CommandContext build(ExecutorContext executorContext, JdbcEngineConfig jdbcEngineConfig);

}
