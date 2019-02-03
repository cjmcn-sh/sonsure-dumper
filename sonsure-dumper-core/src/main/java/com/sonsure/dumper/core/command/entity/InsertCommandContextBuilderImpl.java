package com.sonsure.dumper.core.command.entity;


import com.sonsure.dumper.core.command.AbstractCommandExecutor;
import com.sonsure.dumper.core.command.CommandContext;
import com.sonsure.dumper.core.management.CommandField;
import com.sonsure.dumper.core.management.CommandTable;
import com.sonsure.dumper.core.persist.KeyGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyd on 17/4/14.
 */
public class InsertCommandContextBuilderImpl extends AbstractCommandContextBuilder {

    private static final String COMMAND_OPEN = "insert into ";

    public InsertCommandContextBuilderImpl(AbstractCommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public CommandContext doBuild(CommandTable commandTable) {

        CommandContext commandContext = getGenericCommandContext(commandTable);
        KeyGenerator keyGenerator = getCommandExecutor().getKeyGenerator();
        if (keyGenerator != null) {
            String pkField = this.getPkField(commandTable);
            Object generateKeyValue = keyGenerator.generateKeyValue(commandTable.getModelClass());
            commandContext.setPkValue(generateKeyValue);
            CommandField commandField = CommandField.builder()
                    .name(pkField)
                    .value(generateKeyValue)
                    .type(keyGenerator.isPkValueByDb() ? CommandField.Type.INSERT_PK_NATIVE : CommandField.Type.INSERT)
                    .orig(CommandField.Orig.GENERATOR)
                    .build();
            commandTable.addOperationField(commandField);
        }

        StringBuilder command = new StringBuilder(COMMAND_OPEN);
        StringBuilder argsCommand = new StringBuilder("(");
        List<String> paramNames = new ArrayList<String>();
        List<Object> paramValues = new ArrayList<Object>();
        command.append(this.getTableName(commandTable)).append(" (");

        for (CommandField commandField : commandTable.getOperationFields()) {
            String column = this.getColumn(commandTable, commandField.getName());
            //数据库生成值，不能传参
            if (commandField.getType() == CommandField.Type.INSERT_PK_NATIVE) {
                //如果已经设置了主键值，则不用主键生成器生成 主键生成器field在build时上面代码中添加，所以肯定在人为设置之后
                if (paramNames.contains(column)) {
                    continue;
                }
                command.append(column).append(",");
                argsCommand.append(commandField.getValue()).append(",");
            } else {
                command.append(column).append(",");
                argsCommand.append("?").append(",");
                paramNames.add(column);
                paramValues.add(commandField.getValue());
            }
        }
        command.deleteCharAt(command.length() - 1);
        argsCommand.deleteCharAt(argsCommand.length() - 1);
        argsCommand.append(")");
        command.append(")").append(" values ").append(argsCommand);

        commandContext.setCommand(command.toString());
        commandContext.addParameterNames(paramNames);
        commandContext.addParameters(paramValues);

        return commandContext;
    }
}