package microservice.login.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    public static final String RESOURCE_NOT_FOUND_MESSAGE = "%s with id %d does not exist";
    public static final String ROLE_NOT_FOUND_MESSAGE = "Role %s not found";
    public static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    public static final String DUPLICATE_RESOURCE_MESSAGE = "%s with this %s already exists";

    public static final String COMPLETED_OR_CANCELLED_STATUS_MESSAGE = "Cannot change status from COMPLETED or CANCELLED";
    public static final String FROM_PENDING_TO_PROCESSING_STATUS_MESSAGE = "Can only change status from PENDING to PROCESSING";
    public static final String FROM_PROCESSING_TO_COMPLETED_OR_CANCELLED_STATUS_MESSAGE = "Can only change status from PROCESSING to COMPLETED or CANCELLED";

    public static final String ACCESS_DENIED_ERROR_MESSAGE = "You do not have permission to perform this operation";
}
