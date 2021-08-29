package ru.hiddenproject.feelmeserver.model;

import org.springframework.transaction.annotation.Transactional;
import ru.hiddenproject.feelmeserver.enums.InvitationStatus;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Invitation model
 */
@Entity(name = "accepted_user")
public class Invitation {

    /**
     * Invitation id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Original user (Who invites)
     * <br>
     * See {@link ru.hiddenproject.feelmeserver.model.User}
     */
    @ManyToOne
    @JoinColumn(name = "original_user_id")
    @NotNull
    @Valid
    private User originalUser;

    /**
     * Invited user
     * <br>
     * See {@link ru.hiddenproject.feelmeserver.model.User}
     */
    @ManyToOne
    @JoinColumn(name = "accepted_user_id")
    @NotNull
    @Valid
    private User acceptedUser;

    /**
     * Invitation status
     * <br>
     * See {@link ru.hiddenproject.feelmeserver.enums.InvitationStatus}
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "invitation_status")
    private InvitationStatus invitationStatus;

    public Invitation() {}

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
