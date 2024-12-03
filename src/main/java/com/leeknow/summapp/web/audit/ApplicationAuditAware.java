package com.leeknow.summapp.web.audit;

import com.leeknow.summapp.user.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    @NonNull
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String)
            return Optional.empty();

        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getUserId() == null ? 0 : user.getUserId());
    }
}
