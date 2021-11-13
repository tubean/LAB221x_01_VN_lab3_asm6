package vn.edu.funix.lab6.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_USER")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long userId;

    @Column(name = "USER_NAME", length = 36, nullable = false)
    private String userName;

    @Column(name = "PASSWORD", length = 128)
    private String encrytedPassword;

    @Column(name = "FIRST_LOGIN", length = 1, nullable = false)
    private boolean firstLogin;

    @Column(name = "IS_LOCK", length = 1, nullable = false)
    private boolean isLocked;

    @Column(name = "FAILURE_LOGIN_TEMP", length = 1, nullable = false)
    private int failureLoginTemp;
}
