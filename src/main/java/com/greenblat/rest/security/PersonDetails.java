package com.greenblat.rest.security;

import com.greenblat.rest.models.Person;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PersonDetails implements UserDetails {

    private final Person person;

    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // TODO: return authority
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // аккаунт не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // аккаунт не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // аккаунт не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true; // аккаунт работает
    }

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public Person getPerson() {
        return person;
    }
}
