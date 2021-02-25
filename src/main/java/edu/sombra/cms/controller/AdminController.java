package edu.sombra.cms.controller;

import edu.sombra.cms.domain.enumeration.RoleEnum;
import edu.sombra.cms.domain.payload.UserView;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/pending")
    public List<UserView> getPendingUsers(){
        return userService.findUsersByRole(RoleEnum.ROLE_PENDING);
    }

    @PutMapping("/role/{userId}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public void assignUserRole(@PathVariable Integer userId, @PathVariable RoleEnum role){
        userService.assignUserRole(userId, role);
    }

}
