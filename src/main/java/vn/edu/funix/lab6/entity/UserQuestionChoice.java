package vn.edu.funix.lab6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_HINT_QUESTION_CHOICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long userQuestionChoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private HintQuestion hintQuestion;

    @Column(name = "ANSWER", length = 256, nullable = false)
    private String answer;
}
