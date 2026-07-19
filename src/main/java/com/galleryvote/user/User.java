package com.galleryvote.user;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "users")
public class User {
    public enum Role { USER, MODERATOR, ADMIN }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=50) private String username;
    @Column(nullable=false, unique=true, length=254) private String email;
    @Column(name="password_hash", nullable=false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role = Role.USER;
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    protected User() {}
    public User(String username, String email, String passwordHash) { this.username=username; this.email=email; this.passwordHash=passwordHash; }
    public Long getId(){return id;} public String getUsername(){return username;} public String getEmail(){return email;}
    public String getPasswordHash(){return passwordHash;} public Role getRole(){return role;} public Instant getCreatedAt(){return createdAt;}
}
