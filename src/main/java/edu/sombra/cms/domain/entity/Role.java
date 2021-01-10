package edu.sombra.cms.domain.entity;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
