package se.depi.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import se.depi.resource.IssueResource;
import se.depi.resource.TeamResource;
import se.depi.resource.UserResource;
import se.depi.resource.WorkItemResource;

@Configuration
@EnableJpaRepositories("se.depi.repository")
@EnableTransactionManagement
public class Config extends ResourceConfig {
	
	public Config() {
		register(UserResource.class);
		register(TeamResource.class);
		register(WorkItemResource.class);
		register(IssueResource.class);
	}
	

	// Data Source
	@Bean
	DataSource dataSource() {

		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/cms-project?useSSL=false");
		config.setUsername("root");
		config.setPassword("secret");

		return new HikariDataSource(config);
	}

	// Transaction Manager
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	// JPA Vendor
	@Bean
	JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setGenerateDdl(true);
		return adapter;
	}

	// Entity Manager Factory
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setPackagesToScan("se.depi");
		return factory;
	}
}