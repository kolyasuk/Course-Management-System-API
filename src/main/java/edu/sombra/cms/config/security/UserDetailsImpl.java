package edu.sombra.cms.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static edu.sombra.cms.domain.enumeration.Role.ROLE_ADMIN;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private long id;
    private String email;
    private String fullName;
    @JsonIgnore
    private String password;
    private GrantedAuthority authority;

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPassword(),
                new SimpleGrantedAuthority(user.getRole().getRoleName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    public String getRoleName(){
        return getRole().name();
    }

    public Role getRole(){
        return getAuthorities().stream().map(o -> Role.ofName(o.getAuthority())).findFirst().orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(getRole());
    }

}
