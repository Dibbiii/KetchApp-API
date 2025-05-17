package com.alessandra_alessandro.ketchapp.routes;

import com.alessandra_alessandro.ketchapp.controllers.UsersControllers;
import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRoute {

    private final UsersControllers usersControllers;

    @Autowired
    public AuthenticationRoute(UsersControllers usersControllers) {
        this.usersControllers = usersControllers;
    }

    @PostMapping("/google")
    public ResponseEntity<UserDto> loginWithGoogle(@RequestBody String idToken) {
        // idToken viene passato direttamente dal frontend dopo l'autenticazione con Google/Firebase
        UserDto user = usersControllers.loginWithGoogleToken(idToken);
        return ResponseEntity.ok(user);
    }
}