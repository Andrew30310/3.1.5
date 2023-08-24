package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "realName")
    private String realName;

    @Column(name = "age")
    private int age;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String username, String realName, int age, String password, Collection<Role> roles) {
        this.username = username;
        this.realName = realName;
        this.age = age;
        this.password = password;
        this.roles = roles;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String name) {
        this.username = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String lastName) {
        this.realName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}
