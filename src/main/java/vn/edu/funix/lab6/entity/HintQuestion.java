package vn.edu.funix.lab6.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TBL_HINT_QUESTION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HintQuestion {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private long questionId;

    @Column(name = "CONTENT", length = 256, nullable = false)
    private String content;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private int displayOrder;

    @Column(name = "IS_DELETED", length = 1, nullable = false)
    private boolean isDeleted;

}