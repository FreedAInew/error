//poneleuncometario

package only.get.springdoc;




import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de swagger")
                .version("1.0")
                .description("Documentación de la API con Swagger y OpenAPI"))
            .addSecurityItem(new SecurityRequirement().addList("bearer-key"))  // 🔥 Requiere JWT
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));  // 🔥 Define que es un token JWT
    }
}
