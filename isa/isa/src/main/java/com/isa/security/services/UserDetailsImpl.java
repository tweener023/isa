package com.isa.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isa.model.Gender;
import com.isa.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private Integer jmbg;
    private Gender gender;
    private String job;
    private String workplace;
    private Integer pointsCollected;
    private boolean filledQuestionnaire;
    private boolean accountVerified;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password, String firstName, String lastName, String address, String city, String zipCode, String country, String phoneNumber, Integer jmbg, Gender gender, String job, String workplace, Integer pointsCollected, boolean filledQuestionnaire, boolean accountVerified, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.jmbg = jmbg;
        this.gender = gender;
        this.job = job;
        this.workplace = workplace;
        this.pointsCollected = pointsCollected;
        this.filledQuestionnaire = filledQuestionnaire;
        this.accountVerified = accountVerified;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getCity(),
                user.getZipCode(),
                user.getCountry(),
                user.getPhoneNumber(),
                user.getJmbg(),
                user.getGender(),
                user.getJob(),
                user.getWorkplace(),
                user.getPointsCollected(),
                user.isFilledQuestionnaire(),
                user.isAccountVerified(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getJmbg() {
        return jmbg;
    }

    public Gender getGender() {
        return gender;
    }

    public String getJob() {
        return job;
    }

    public String getWorkplace() {
        return workplace;
    }

    public Integer getPointsCollected() {
        return pointsCollected;
    }

    public boolean isFilledQuestionnaire() { return filledQuestionnaire; }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

}
