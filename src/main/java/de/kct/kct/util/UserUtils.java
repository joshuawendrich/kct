package de.kct.kct.util;

import de.kct.kct.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
