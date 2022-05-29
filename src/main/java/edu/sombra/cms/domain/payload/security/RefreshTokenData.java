package edu.sombra.cms.domain.payload.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RefreshTokenData {

    @NotEmpty
    private String refreshToken;
}
