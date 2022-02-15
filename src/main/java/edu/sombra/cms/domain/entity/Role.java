package edu.sombra.cms.domain.entity;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> user;
}
