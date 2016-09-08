package configs;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import play.Play;

import com.google.common.base.Preconditions;

@Order(1)
@Configuration
@EnableTransactionManagement
@ComponentScan({ "persistence.dao", "persistence.service" })
public class PersistenceJpaConfig {
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    	final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    	emf.setPersistenceUnitName(Play.application().configuration().getString("jpa.default"));
    	emf.setDataSource(dataSource());
    	emf.setPackagesToScan(new String[]{"models"});
    	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	emf.setJpaVendorAdapter(vendorAdapter);
    	emf.setJpaProperties(hibernateProperties());
    	return emf;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(Play.application().configuration().getString("db.default.driver")));
        dataSource.setUrl(Preconditions.checkNotNull(Play.application().configuration().getString("db.default.url")));
        dataSource.setUsername(Preconditions.checkNotNull(Play.application().configuration().getString("db.default.user")));
        dataSource.setPassword(Preconditions.checkNotNull(Play.application().configuration().getString("db.default.password")));
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
    	final JpaTransactionManager transactionManager = new JpaTransactionManager();
    	transactionManager.setEntityManagerFactory(emf);
    	return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    	final PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
    	return persistenceExceptionTranslationPostProcessor;
    }

    final Properties hibernateProperties() {
        return new Properties() {
			private static final long serialVersionUID = -243029401562335321L;
			{
                setProperty("hibernate.hbm2ddl.auto", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.hbm2ddl.auto")));
                setProperty("hibernate.dialect", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.dialect")));
                setProperty("hibernate.show_sql", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.show_sql")));
                setProperty("hibernate.connection.useUnicode", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.connection.useUnicode")));
                setProperty("hibernate.connection.characterEncoding", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.connection.characterEncoding")));
                setProperty("hibernate.connection.autoReconnect", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.connection.autoReconnect")));
                setProperty("hibernate.cache.use_second_level_cache", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.cache.use_second_level_cache")));
                setProperty("hibernate.search.default.indexBase", Preconditions.checkNotNull(Play.application().configuration().getString("hibernate.search.default.indexBase")));
            }
        };
    }
    
}
