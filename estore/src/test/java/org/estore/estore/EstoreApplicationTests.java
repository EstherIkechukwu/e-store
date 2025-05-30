package org.estore.estore;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EstoreApplicationTests {


	@Test
	void contextLoads() {

	}

	@Test
	void testConnectToDatabase() {
		try(HikariDataSource dataSource = new HikariDataSource();){
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://localhost/estore_db?createDatabaseIfNotExist=true");
			dataSource.setUsername("root");
			dataSource.setPassword("Rehoboth18!");
			assertThat(dataSource).isNotNull();
		}
	}

}
