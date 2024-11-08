package com.leeknow.summapp.web.constant;

public class SecurityApiConstant {

    public static final String[] SWAGGER_API = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };
    public static final String[] ADMIN_API = {
            "/events/**",
            "/actuator/**",
            "/schedule/**",
            "/module/**"
    };
}
