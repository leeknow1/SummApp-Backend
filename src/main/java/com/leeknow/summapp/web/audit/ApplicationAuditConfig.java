package com.leeknow.summapp.web.audit;

import com.leeknow.summapp.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ApplicationAuditConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new ApplicationAuditAware();
    }
}
