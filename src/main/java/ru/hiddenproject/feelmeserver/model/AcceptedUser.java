package ru.hiddenproject.feelmeserver.model;

import ru.hiddenproject.feelmeserver.enums.InvitationStatus;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity(name = "accepted_user")
public class AcceptedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "original_user_id")
    @NotNull
    @Valid
    private User originalUser;

    @ManyToOne
    @JoinColumn(name = "accepted_user_id")
    @NotNull
    @Valid
    private User acceptedUser;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "invitation_status")
    private InvitationStatus invitationStatus;

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

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }
}
