package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity //pour représenter une table en bd
public class Repas{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private double prix;

    @Enumerated(EnumType.STRING)
    private TypeRepas type;
}