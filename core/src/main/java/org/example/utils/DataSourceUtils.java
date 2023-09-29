package com.mingke.java.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 创建一个工具类方便数据连接和执行SQL
 * 2. 因为实际上每次的连接数据源信息可能并不相同，所以这边并没有什么必要创建单例对象
 */
public class DataSourceUtils {

    /**
     * 数据库连接池对象
     */
    private ComboPooledDataSource comboPooledDataSource = null;

    /**
     * 数据库连接对象
     */
    private Connection connection = null;

    private PreparedStatement statement = null;

    private PreparedStatement batchStatement = null;

    //生成SQL用的PreparedStatement，因为要重复使用，所以需要一个单独的和之前的执行器的PreparedStatement区分开
    private PreparedStatement generateStatement = null;

    private ResultSet resultSet = null;

    public static String testSQL = "SELECT COUNT(*) as num FROM information_schema.TABLES WHERE table_schema=? AND table_name=?";

    /**
     * 批量读入速度
     */
    public static Integer fetchSize = 1000;
    /**
     * 初始化池化数据库连接信息--如果要执行多个SQL使用此方法
     * @param Url JDBC数据库连接信息
     * @param username 数据库访问用户名
     * @param password 数据库访问密码
     * @return 数据库连接
     */
    public void initPoolConnection(String Url, String username, String password) {
        if(comboPooledDataSource == null) {
            //创建池对象，并且设置连接信息
            comboPooledDataSource = new ComboPooledDataSource();
            try {
                comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
            comboPooledDataSource.setJdbcUrl(Url);
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
        }
        //返回连接信息
        try {
            connection = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化普通数据库连接--少量SQL时使用此接口能够起到节省资源的效果
     * @param Url JDBC数据库连接信息
     * @param username 数据库访问用户名
     * @param password 数据库访问密码
     * @return 数据库连接
     */
    public void initSimpleConnection(String Url, String username, String password) {
        try {
            //注册驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //创建JDBC连接
            connection = DriverManager.getConnection(Url, username, password);
            //自动提交关闭
            //connection.setAutoCommit(false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SQL执行器
     * @param sql sql本体
     * @param objs sql中占位符对应的参数
     * @return 结果集，可以从中解析出SQL执行的结果
     * select * from sys_user where name = ?
     */
    public ResultSet doSQL(String sql, Object ...objs) {
        if(connection != null) {
            try {

                //设置预处理SQL Statement
                statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //设置FetchSize为一千行，满足程序运行空间局部性原理
                statement.setFetchSize(Integer.MIN_VALUE);
                //设置参数
                for(int i = 0; i < objs.length; i++) {
                    statement.setObject(i+1, objs[i]);
                }
                //执行SQL
                statement.execute();
                //返回结果
                resultSet = statement.getResultSet();
                return resultSet;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return null;
        }
        return null;
    }

    /**
     * 批量SQL执行器的初始化函数
     * @param rawSQL 要执行的模板SQL
     */
    public void initBatchExecutor(String rawSQL) {
        if(connection != null) {
            try {
                //创建批处理要用到的Statement
                if(batchStatement == null) {
                    batchStatement = connection.prepareStatement(rawSQL);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 填充模板SQL中占位符对应参数的方法
     * @param objs SQL参数
     */
    public void fillBatchSQLParams(Object ...objs) {
        if(batchStatement != null) {
            try {
                //设置参数
                for(int i = 0; i < objs.length; i++) {
                    batchStatement.setObject(i+1, objs[i]);
                }

                //加入批量执行器
                batchStatement.addBatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量代码执行器，执行上一批需要批量执行的方法
     */
    public void doBatchSQL() {
        if(batchStatement != null) {
            try {
                //批量执行
                batchStatement.executeBatch();
                //清空
                batchStatement.clearBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void commit() {
        if(connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int[] batchExecuteSQL() {
        if(batchStatement != null) {
            try {
                return batchStatement.executeBatch();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new int[10];
    }


    /**
     * SQL生成器，主要功能为:
     * 根据带占位符的SQL和参数生成完整SQL的方法，注意此处仅生成方便生成备份sql文件，而并不需要执行
     * @param rawSql
     * @param objs
     * @return 完整SQL语句
     */
    public String generateSQL(String rawSql, Object ...objs) {
        if(connection != null) {
            try {
                //设置预处理SQL Statement
                if(generateStatement == null) {
                    generateStatement = connection.prepareStatement(rawSql);
                }

                //设置参数
                for(int i = 0; i < objs.length; i++) {
                    generateStatement.setObject(i+1, objs[i]);
                }
                System.out.println(generateStatement.toString());
                //使用PreparedStatement的预览功能得到SQL
                return generateStatement.toString().split("Statement: ")[1];
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return null;
        }
        return null;
    }

    public String generateRawSQL() {
        return "";
    }

    /**
     * 关闭连接，避免消耗
     */
    public void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (generateStatement != null) {
            try {
                generateStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(batchStatement != null) {
            try {
                batchStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(comboPooledDataSource != null) {
            comboPooledDataSource.close();
        }
    }

    /**
     * 构建查询SQL，最开始使用了占位符来标识表名和数据库名，但是发现占位符实现的SQL中表名会带上引号，导致SQL执行失败
     * @param tableName
     * @return
     */
    public String createSelectSQL(String tableName) {
        String sql = "select * from " + tableName + " where create_time < ?";
        return sql;
    }



    /**
     * 构建近线备份的SQL，即将原表中数据备份到另外一个表中
     * @param tableName
     * @return
     */
    public String createCopySQL(String tableName) {
        String now = LocalDateTime.now().toString();
        System.out.println(now);
        String sql = "CREATE TABLE zzuli_user_2023_6_16 SELECT * FROM " + tableName + "";
        return sql;
    }

    public String createDiskCopySQL(String tableName) {
        return "";
    }
}
