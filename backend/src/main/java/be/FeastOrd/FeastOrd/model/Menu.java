package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //pour représenter une table en bd
public class Menu{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Repas entree;

    @ManyToOne
    private Repas repas;

    @ManyToOne
    private Repas dessert;


}