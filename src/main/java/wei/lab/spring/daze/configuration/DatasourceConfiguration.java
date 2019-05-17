package wei.lab.spring.daze.configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import wei.lab.spring.daze.configuration.ext.TypeSafeConfigFactory;
import wei.lab.spring.daze.db.ext.CustomInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * created by weixuecai on 2018/6/7
 */
@Configuration
@EnableTransactionManagement
//@ComponentScan(basePackages = "com.qq.qd.platform.daze.configuration.ext")
public class DatasourceConfiguration {
    @Autowired
    Environment env;

    /*数据源汇总*/
    @Bean(name = "routingDataSource")
    public RoutingDataSource dataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("one", dataSource_one());
        dataSourceMap.put("two", dataSource_two());
        dataSourceMap.put("three", dataSource_three());

        RoutingDataSource dataSource = new RoutingDataSource(dataSourceMap);
        return dataSource;
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager txManager(@Qualifier("routingDataSource") DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        return txManager;
    }

    /*sql session工厂*/
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(parsePathWithWildcard("classpath:mappers/*.xml"));
        factoryBean.setTypeAliasesPackage("wei.lab.spring.daze.db.entity");

        org.apache.ibatis.session.Configuration innerConfig =
                new org.apache.ibatis.session.Configuration();
        innerConfig.setMapUnderscoreToCamelCase(true);
//        innerConfig.setCacheEnabled(true);  //开启二级缓存
        factoryBean.setConfiguration(innerConfig);
        //加上自己设计的一个插件
        factoryBean.setPlugins(new Interceptor[]{new CustomInterceptor()});

        return factoryBean;
    }

    private Resource[] parsePathWithWildcard(String path) {
        try {
            PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
            return pathResolver.getResources(path);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Resource[]{};
        }
    }

    /*扫描DAO类*/
    @Bean
    public static MapperScannerConfigurer mapperScanner() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("wei.lab.spring.daze.db.dao");
        configurer.setAnnotationClass(Repository.class);

        return configurer;
    }


    /*第一个数据源*/
    @Bean(name = "dataSource_one")
    public DataSource dataSource_one() {
        HikariConfig hikariConfig = createDataSourceConfig("first-mysql");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    /*第二个数据源*/
    @Bean(name = "dataSource_two")
    public DataSource dataSource_two() {
        HikariConfig hikariConfig = createDataSourceConfig("second-mysql");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    /*第三个数据源*/
    @Bean(name = "dataSource_three")
    public DataSource dataSource_three() {
        HikariConfig hikariConfig = createDataSourceConfig("third-mysql");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }


    /**
     * 生成一个数据源的配置
     * @param suffix
     * @return
     */
    private HikariConfig createDataSourceConfig(String suffix) {
        HikariConfig hikariConfig = new HikariConfig();
        String poolName = "dataSource_" + suffix;

        System.out.println("--env is null?" + (env == null));

        String driverClassName = env.getProperty("db.driverName");
        int connectionTimeout = env.getProperty("db.connectTimeout", Integer.class,60000);
        int maxPoolSize = env.getProperty("db.maxTotal", Integer.class,4);
        int minIdle = env.getProperty("db.minIdle", Integer.class, 1);
        String testSql = env.getProperty("db.testSql", "SELECT 1");
        boolean autoCommit = env.getProperty("db.autoCommit", Boolean.class, true);

        ConfigObject propertyObj = env.getProperty("db.properties" + TypeSafeConfigFactory.CONFIG_OBJ_SUFFIX, ConfigObject.class);
        Set<Map.Entry<String, ConfigValue>> propertiesSet = propertyObj.entrySet();
        Properties properties = new Properties();
        propertiesSet.forEach(t -> {
            String key = t.getKey();
            Object value = t.getValue().unwrapped();
            properties.put(key, value);
        });

        Config tmpConfig = env.getProperty("db." + suffix + TypeSafeConfigFactory.CONFIG_SUFFIX, Config.class);
        String jdbcUrl = tmpConfig.getString("url");
        String userName = tmpConfig.getString("userName");
        String password = tmpConfig.getString("password");

        //
        hikariConfig.setPoolName(poolName);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setMinimumIdle(minIdle);
        hikariConfig.setConnectionTestQuery(testSql);
        hikariConfig.setAutoCommit(autoCommit);

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);

        hikariConfig.setDataSourceProperties(properties);

        return hikariConfig;
    }

    public final static ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

    class RoutingDataSource extends AbstractRoutingDataSource {
        RoutingDataSource(Map<Object, Object> dataSourceMap) {
            super();
            super.setTargetDataSources(dataSourceMap);
        }

        @Override
        protected Object determineCurrentLookupKey() {
            return dataSourceKey.get();
        }
    }
}
