package config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Microservice send encrypted private key",
        version = "2.0",
        contact = @Contact(name = "Kevin Velásquez", email = "knvelasquez@hotmail.com")),
        servers = {
                @Server(
                        description = "Api Gateway Server URL",
                        url = "http://{serverName}.api.gateway",
                        variables = {
                                @ServerVariable(name = "serverName", allowableValues = {"kenobi", "skywalker", "sato"}, defaultValue = "kenobi", description = "ApiGateWay Server NAme")
                        }
                ),
                @Server(
                        url = "http://{serverName}:{port}",
                        description = "Local Server Url",
                        variables = {
                                @ServerVariable(name = "serverName", allowableValues = {"localhost"}, defaultValue = "localhost", description = "Localhost Server Name"),
                                @ServerVariable(name = "port", allowableValues = {"8787"}, defaultValue = "8787", description = "Localhost Server Port"),
                        }
                )
        }
)

@Configuration
public class OpenApi30SwaggerConfig {
}