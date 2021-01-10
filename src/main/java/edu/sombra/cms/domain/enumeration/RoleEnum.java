package edu.sombra.cms.domain.enumeration;

public enum RoleEnum {

    ROLE_STUDENT("STUDENT"),
    ROLE_INSTRUCTOR("INSTRUCTOR"),
    ROLE_ADMIN("ADMIN");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }


}
