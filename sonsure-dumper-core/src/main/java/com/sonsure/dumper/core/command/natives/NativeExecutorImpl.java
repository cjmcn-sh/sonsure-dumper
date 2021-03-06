package com.sonsure.dumper.core.command.natives;


import com.sonsure.dumper.core.command.simple.AbstractSimpleCommandExecutor;
import com.sonsure.dumper.core.command.simple.SimpleExecutorContext;
import com.sonsure.dumper.core.config.JdbcEngineConfig;

/**
 * Created by liyd on 17/4/25.
 */
public class NativeExecutorImpl extends AbstractSimpleCommandExecutor<NativeExecutor> implements NativeExecutor {

    protected NativeExecutorContext nativeExecutorContext;

    public NativeExecutorImpl(JdbcEngineConfig jdbcEngineConfig) {
        super(jdbcEngineConfig);
        nativeExecutorContext = new NativeExecutorContext();
    }

    public NativeExecutor parameters(Object... values) {
        for (Object value : values) {
            this.nativeExecutorContext.addParameter(value);
        }
        return this;
    }

    @Override
    protected SimpleExecutorContext getSimpleExecutorContext() {
        return nativeExecutorContext;
    }
}
