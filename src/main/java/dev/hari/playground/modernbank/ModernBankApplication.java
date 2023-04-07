package dev.hari.playground.modernbank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
        title = "Modern Bank Server",
        version = "1.0",
        description = "Modern Bank APIs",
        contact = @Contact(url = "https://github.com/hariharansubramanian", name = "Hari", email = "hariharan1990.s@gmail.com")
)
)
public class ModernBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModernBankApplication.class, args);
    }

}
