package yeri.csphub.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Users {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String username;

    @Column(columnDefinition = "TEXT")
    private String email;

    @Column(columnDefinition = "TEXT", name = "password_hash")
    private String password;

    @Column(name = "created_at")
    private Timestamp createdAt;



}
