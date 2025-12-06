package be.FeastOrd.FeastOrd;
import be.FeastOrd.FeastOrd.model.Role;
import be.FeastOrd.FeastOrd.model.TypeRole;
import be.FeastOrd.FeastOrd.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRole(TypeRole.CLIENT).isEmpty()) {
                roleRepository.save(new Role(TypeRole.CLIENT));
            }
            if (roleRepository.findByRole(TypeRole.GESTIONNAIRE).isEmpty()) {
                roleRepository.save(new Role(TypeRole.GESTIONNAIRE));
            }
            if (roleRepository.findByRole(TypeRole.UTILISATEUR).isEmpty()) {
                roleRepository.save(new Role(TypeRole.UTILISATEUR));
            }
        };
    }
}
