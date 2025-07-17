package com.alessandra_alessandro.ketchapp.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // In una vera applicazione, qui faresti una query al database
    // per recuperare l'utente e i suoi ruoli
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Questo è un esempio. In un'applicazione reale, dovresti recuperare l'utente
        // dal tuo database usando l'username (che in questo caso è un UUID).
        // Poiché non abbiamo un database utenti, creiamo un utente fittizio
        // per ogni username valido che riceviamo dal token JWT.

        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }

        // La password qui non è importante per l'autenticazione basata su token,
        // ma UserDetails richiede che non sia nulla.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(username, "", authorities);
    }
}

