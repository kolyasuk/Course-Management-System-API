package edu.sombra.cms.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 500)
    private String info;

    @ManyToMany(mappedBy = "instructors")
    Set<Course> courses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
