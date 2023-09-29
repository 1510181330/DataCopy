package com.mingke.java.params;

/**
 * 操作所需要的基础参数
 */
public abstract class OperationBaseParams {

    /**
     * 备份策略，1表示数据库备份，2表示磁盘备份
     * 销毁策略，1表示永久删除，2表示回收站删除
     */
    private Integer operationStrategy;

    /**
     * 执行模式，1表示备份，2表示销毁
     */
    private Integer mode;
    /**
     * 源数据库连接信息
     */
    private String sourceDBURL;

    private String sourceUser;

    private String sourcePassword;

    /**
     * 备份表名
     */
    private String tableName;

    /**
     * 目标数据库连接信息 -- 数据库备份用
     */
    private String targetDBURL;

    private String targetUser;

    private String targetPassword;

    /**
     * 备份的磁盘路径 -- 磁盘备份用
     */
    private String backupPath;

    public Integer getOperationStrategy() {
        return operationStrategy;
    }

    public void setOperationStrategy(Integer operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public String getSourceDBURL() {
        return sourceDBURL;
    }

    public void setSourceDBURL(String sourceDBURL) {
        this.sourceDBURL = sourceDBURL;
    }

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTargetDBURL() {
        return targetDBURL;
    }

    public void setTargetDBURL(String targetDBURL) {
        this.targetDBURL = targetDBURL;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public OperationBaseParams() {
    }
}
