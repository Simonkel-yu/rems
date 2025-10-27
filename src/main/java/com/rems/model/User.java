package com.rems.model;

import com.rems.model.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 64)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public User() {}
    public User(String fullName, String email, String passwordHash, UserRole role) {
        this.fullName = fullName; this.email = email;
        this.passwordHash = passwordHash; this.role = role;
    }

    public int getUserId()                { return userId; }
    public String getFullName()           { return fullName; }
    public void setFullName(String n)     { this.fullName = n; }
    public String getEmail()              { return email; }
    public void setEmail(String e)        { this.email = e; }
    public String getPasswordHash()       { return passwordHash; }
    public void setPasswordHash(String h) { this.passwordHash = h; }
    public UserRole getRole()             { return role; }
    public void setRole(UserRole r)       { this.role = r; }
}
