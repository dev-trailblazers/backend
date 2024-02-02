package com.growth.community.domain.user;

import com.growth.community.domain.AuditingField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;


@AllArgsConstructor
@Getter
@Entity
public class UserAccount extends AuditingField {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    private String name;
    private String nickname;
    private Date birth;
    private char gender;
    private String phoneNumber;

    private String region;
    private int career;
    private String position;

    @Column(columnDefinition = "boolean default false")
    private boolean locked;

    protected UserAccount() {

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
