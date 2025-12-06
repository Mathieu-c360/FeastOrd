package be.FeastOrd.FeastOrd.model;
import jakarta.persistence.*;

@Entity
public class Menu{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    // Un Menu peut avoir plusieurs Entrées (Repas)
    @ManyToOne // <--- NOUVEAU
    @JoinColumn(name = "entree_id") // <--- NOUVEAU : Crée la colonne de clé étrangère
    private Repas entree;
    
    // Un Menu peut avoir plusieurs Plats (Repas)
    @ManyToOne // <--- NOUVEAU
    @JoinColumn(name = "plat_id") // <--- NOUVEAU
    private Repas repas; // Il serait préférable de renommer ce champ en 'platPrincipal' pour plus de clarté
    
    // Un Menu peut avoir plusieurs Desserts (Repas)
    @ManyToOne // <--- NOUVEAU
    @JoinColumn(name = "dessert_id") // <--- NOUVEAU
    private Repas dessert;
    
    public Menu(){}
    public Menu(Repas entree,Repas repas,Repas dessert)
    {
        this.entree=entree;
        this.repas=repas;
        this.dessert=dessert;
    }
    public Repas getDessert()
    {
        return dessert;
    }
    public Repas getEntree()
    {
        return entree;
    }
    public Repas getRepas()
    {
        return repas;
    }
}