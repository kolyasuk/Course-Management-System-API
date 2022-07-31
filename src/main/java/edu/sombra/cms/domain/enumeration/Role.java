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

    private final String roleName;

    public boolean isEqual(String s){
        return roleName.equals(s);
    }

    public static Role ofName(String roleName){
        return Arrays.stream(values()).filter(o -> o.roleName.equals(roleName)).findFirst().orElseThrow(() -> new RuntimeException(""));
    }

}
