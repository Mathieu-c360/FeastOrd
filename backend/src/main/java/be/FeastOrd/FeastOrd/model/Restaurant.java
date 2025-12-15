package be.FeastOrd.FeastOrd.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity //pour représenter une table en bd
public class Restaurant{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private int etoiles;
    private String ville;
    private String rue;
    private int codePostal;
    @ManyToOne
    @JsonIgnore
    private Utilisateur utilisateur;
}