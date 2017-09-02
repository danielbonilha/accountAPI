package br.com.accounts;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AccountsSvcApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AccountsSvcApplication.class)
	    	.initializers(new ProfileConfigInitializer())
	    	.run(args);
	}
}
