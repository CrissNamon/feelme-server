package ru.hiddenproject.feelmeserver.model;

import javax.persistence.*;

@Entity(name = "accepted_user")
public class AcceptedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "original_user_id")
    private User originalUser;

    @ManyToOne
    @JoinColumn(name = "accepted_user_id")
    private User acceptedUser;

    public AcceptedUser() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOriginalUser() {
        return originalUser;
    }

    public void setOriginalUser(User originalUser) {
        this.originalUser = originalUser;
    }

    public User getAcceptedUser() {
        return acceptedUser;
    }

    public void setAcceptedUser(User acceptedUser) {
        this.acceptedUser = acceptedUser;
    }
}
