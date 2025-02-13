package demo.link_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"demo.link_challenge.mappers", "demo.link_challenge"})
public class LinkChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkChallengeApplication.class, args);
	}

}
