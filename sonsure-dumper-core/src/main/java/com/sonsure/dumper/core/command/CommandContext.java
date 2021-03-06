package com.sonsure.dumper.core.command;


import java.util.ArrayList;
import java.util.List;

/**
 * 执行的命令内容
 * <p>
 * Created by liyd on 17/4/12.
 */
public class CommandContext {

    /**
     * 命令，一般指sql
     */
    private String command;

    private List<String> parameterNames;

    /**
     * 参数名称列表
     */
    private List<Object> parameters;

    /**
     * 返回值类型，如果是native操作又不指定，可能为null
     */
    private Class<?> resultType;

    /**
     * 主键值，pkValueByDb=false才有
     */
    private GenerateKey generateKey;

    public CommandContext() {
        parameterNames = new ArrayList<>();
        parameters = new ArrayList<>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void addParameter(Object parameter) {
        this.parameters.add(parameter);
    }

    public void addParameters(List<Object> parameters) {
        this.parameters.addAll(parameters);
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public GenerateKey getGenerateKey() {
        return generateKey;
    }

    public void setGenerateKey(GenerateKey generateKey) {
        this.generateKey = generateKey;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

}
