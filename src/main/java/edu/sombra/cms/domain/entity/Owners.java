package edu.sombra.cms.domain.entity;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.messages.SomethingWentWrongException;

import java.util.List;

import static edu.sombra.cms.messages.UserMessage.ACCESS_DENIED;


public abstract class Owners {
    public abstract List<Long> getOwnersIds();

    public boolean isOwner(Long userId) {
        return getOwnersIds().contains(userId);
    }

    public void checkAccess(UserDetailsImpl userDetails) throws SomethingWentWrongException {
        if(!isOwner(userDetails.getId()) && !userDetails.isAdmin()){
            throw ACCESS_DENIED.ofException();
        }
    }
}
