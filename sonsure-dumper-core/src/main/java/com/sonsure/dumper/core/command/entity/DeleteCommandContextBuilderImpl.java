package com.sonsure.dumper.core.command.entity;


import com.sonsure.dumper.core.command.CommandContext;
import com.sonsure.dumper.core.command.ExecutorContext;
import com.sonsure.dumper.core.config.JdbcEngineConfig;

/**
 * Created by liyd on 17/4/14.
 */
public class DeleteCommandContextBuilderImpl extends AbstractCommandContextBuilder {

    private static final String COMMAND_OPEN = "delete from ";

    public CommandContext doBuild(ExecutorContext executorContext, JdbcEngineConfig jdbcEngineConfig) {
        DeleteContext deleteContext = (DeleteContext) executorContext;
        StringBuilder command = new StringBuilder(COMMAND_OPEN);
        command.append(this.getModelAliasName(deleteContext.getModelClass(), null));

        CommandContext commandContext = getCommonCommandContext(deleteContext);

        CommandContext whereCommandContext = this.buildWhereSql(deleteContext);
        if (whereCommandContext != null) {
            command.append(whereCommandContext.getCommand());
            commandContext.addParameters(whereCommandContext.getParameters());
        }
        commandContext.setCommand(command.toString());
        return commandContext;
    }
}
