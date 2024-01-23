package tw.niq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class NiqApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiqApplication.class, args);
	}

}
