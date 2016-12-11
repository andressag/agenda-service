package com.andressag.agenda.user.persistence;

import com.andressag.agenda.model.Profile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static java.time.ZoneId.systemDefault;
import static javax.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor(access = PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 128)
    private String login;

    @Column(name = "encryptedPassword", nullable = false)
    private String password;

    @Temporal(TIMESTAMP)
    private Date creationDate;

    @Temporal(TIMESTAMP)
    private Date updateDate;

    @Column(name = "profileId")
    @ElementCollection(targetClass = Profile.class)
    @CollectionTable(name = "userProfiles", joinColumns = @JoinColumn(name = "userId"))
    private List<Profile> profiles;

    @PrePersist
    public void creationTimestamp() {
        this.creationDate = getCurrentDate();
        this.updateDate = this.creationDate;
    }

    @PreUpdate
    public void updateTimestamp() {
        this.updateDate = getCurrentDate();
    }

    private Date getCurrentDate() {
        return Date.from(LocalDateTime.now().atZone(systemDefault()).toInstant());
    }
}
