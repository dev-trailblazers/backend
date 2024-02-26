package com.growth.community.domain.user;

import com.growth.community.domain.AuditingField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@Getter
@Builder
@AllArgsConstructor
@Entity
public class UserAccount extends AuditingField {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(updatable = false, nullable = false, length = 16)
    private String username;
    @Column(nullable = false, length = 68)
    private String password;
    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(updatable = false, nullable = false, length = 18)
    private String name;
    @Column(updatable = false, nullable = false)
    private Date birth;
    @Column(updatable = false, nullable = false)
    private boolean gender;

    @Setter
    @Column(nullable = false, length = 30)
    private String nickname;
    @Setter
    @Column(nullable = false)
    private byte career;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Region workingArea;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RoleType role;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isDeactivated;

    protected UserAccount() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
