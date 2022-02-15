package edu.sombra.cms.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.sombra.cms.domain.enumeration.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Instructor instructor;

    public RoleEnum getRoleEnum() {
        return role.getRole();
    }

    @JsonIgnore
    public boolean isAdmin() {
        return checkRole(RoleEnum.ROLE_ADMIN);
    }

    @JsonIgnore
    public boolean isInstructor() {
        return checkRole(RoleEnum.ROLE_INSTRUCTOR);
    }

    @JsonIgnore
    public boolean isStudent() {
        return checkRole(RoleEnum.ROLE_STUDENT);
    }

    @JsonIgnore
    public boolean checkRole(RoleEnum roleEnum){
        return getRoleEnum().equals(roleEnum);
    }

}
