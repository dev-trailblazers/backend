package com.growth.community.domain.user;

import com.growth.community.domain.AuditingField;
import com.growth.community.domain.user.dto.Principal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@AllArgsConstructor
@Builder
@Getter
@Entity
public class UserAccount extends AuditingField {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String email;
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Principal.RoleType role;

    private String name;
    @Setter private String nickname;
    private Date birth;
    private char gender;
    private String phoneNumber;

    @Setter private String region;
    @Setter private int career;

    @Enumerated(EnumType.STRING)
    @Setter private Position position;

    @Column(columnDefinition = "boolean default false")
    private boolean deactivated;

    public UserAccount() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
