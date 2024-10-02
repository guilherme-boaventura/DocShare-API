package br.ucsal.docshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DocshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocshareApplication.class, args);
	}

}
