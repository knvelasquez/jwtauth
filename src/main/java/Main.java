import com.lab.authority.adapter.factory.AuthorityFactory;
import com.lab.authority.adapter.repository.AuthorityRepository;
import com.lab.authority.adapter.repository.UserAuthorityRepository;
import com.lab.authority.dto.AuthorityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@ComponentScan({"com.*"})
@EntityScan(basePackages = {"com.*","com.lab.authority.entity"})
@EnableJpaRepositories(basePackages = {"com.lab.authority.adapter.repository"})
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserAuthorityRepository userAuthorityRepository, AuthorityRepository authorityRepository) {
        return args -> {
            for (AuthorityDTO authorityDto : AuthorityFactory.getAuthority().findAll(1010)) {
                logger.info(new StringBuilder("idUser:")
                        .append(authorityDto.getIdUser())
                        .append(";authority:")
                        .append(authorityDto.getAuthority().getDescription())
                        .append(";created:")
                        .append(authorityDto.getCreated())
                        .toString()
                );
            }
        };
    }

}
