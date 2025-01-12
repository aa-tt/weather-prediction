package life.outorin.myday;

import life.outorin.myday.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MydayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydayApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			WeatherService weatherService = ctx.getBean(WeatherService.class);
			weatherService.listCollections();
		};
	}
}
