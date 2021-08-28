package ru.hiddenproject.feelmeserver.enums;

import ru.hiddenproject.feelmeserver.model.AcceptedUser;

/**
 * Invitation status
 * <br>
 * See {@link AcceptedUser#getInvitationStatus()}
 */
public enum InvitationStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
