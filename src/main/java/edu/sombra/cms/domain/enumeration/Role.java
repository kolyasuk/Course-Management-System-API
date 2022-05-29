package edu.sombra.cms.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_STUDENT("STUDENT"),
    ROLE_INSTRUCTOR("INSTRUCTOR"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    public boolean isEqual(String s){
        return name.equals(s);
    }

    public static Role ofName(String roleName){
        return Arrays.stream(values()).filter(o -> o.name.equals(roleName)).findFirst().orElseThrow(() -> new RuntimeException(""));
    }

}
