package edu.sombra.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
public class Instructor extends Owners implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String info;

    @ManyToMany(mappedBy = "instructors", fetch = FetchType.LAZY)
    List<Course> courses = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public List<Long> getOwnersIds() {
        return Collections.singletonList(user.getId());
    }
}
