package configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(2)
@Configuration
@ComponentScan({ "controllers", "forms.util" })
public class AppConfig {}
