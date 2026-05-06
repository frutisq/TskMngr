package entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "createdAt")
    private LocalDate createdAt;

    private enum status {OPEN, IN_PROGRESS, DONE}

    private enum priority {LOW, MEDIUM, HIGH}

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private priority priority;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public Task() {}
}