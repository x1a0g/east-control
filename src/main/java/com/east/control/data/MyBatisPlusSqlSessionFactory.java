package com.east.control.data;

import com.east.control.mapper.FlowItemMapper;
import com.east.control.mapper.FlowMapper;
import com.east.control.mapper.UserMapper;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class MyBatisPlusSqlSessionFactory {

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getInstance() {
        if (sqlSessionFactory == null) {
            synchronized (MyBatisPlusSqlSessionFactory.class) {
                if (sqlSessionFactory == null) {
                    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                    DataSource dataSource = SQLiteDataSource.getInstance();

                    // 创建Environment
                    Configuration config = new Configuration();
                    config.setLogImpl(Slf4jImpl.class);
                    Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
                    config.setEnvironment(environment);

                    config.addMapper(UserMapper.class);
                    config.addMapper(FlowMapper.class);
                    config.addMapper(FlowItemMapper.class);


                    SqlSessionFactory factory = builder.build(config);
                    sqlSessionFactory = factory;
                }
            }
        }
        return sqlSessionFactory;
    }
}