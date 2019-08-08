package price.checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("price.checker.domain.rpsy")
@ComponentScan({"price.checker.domain.rpsy.StockPriceRepository"})
@ComponentScan({"price.checker.entrypoint.RequestController"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
