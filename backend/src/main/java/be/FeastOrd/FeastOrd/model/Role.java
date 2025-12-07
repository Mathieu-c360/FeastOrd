
package be.FeastOrd.FeastOrd.model;

import jakarta.persistence.*;

@Entity //pour représenter une table en bd
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeRole role;
    public Role(TypeRole role) {
       this.role=role;
    }
    public Role(){}
}
