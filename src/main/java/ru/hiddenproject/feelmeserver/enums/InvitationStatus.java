package ru.hiddenproject.feelmeserver.enums;

import ru.hiddenproject.feelmeserver.model.Invitation;

/**
 * Invitation status
 * <br>
 * See {@link Invitation#getInvitationStatus()}
 */
public enum InvitationStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
