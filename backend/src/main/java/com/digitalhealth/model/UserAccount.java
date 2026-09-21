package com.digitalhealth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="user_accounts", indexes=@Index(name="idx_user_username", columnList="username"))
public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, unique=true, length=120) private String username;
 @Column(unique=true, length=254) private String email;
 @Column(nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false, length=30) private Role role;
 @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="worker_id", unique=true) private Worker worker;
 @Column(nullable=false) private boolean enabled=true;
 @Column(nullable=false, updatable=false) private LocalDateTime createdAt;
 @PrePersist void created(){createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;} public Worker getWorker(){return worker;} public void setWorker(Worker v){worker=v;}
 public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}
