package de.kct.kct.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(it -> {
            it.requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll();
            it.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
            it.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
            it.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
            it.requestMatchers(HttpMethod.POST, "/user/login").permitAll();
            it.anyRequest().authenticated();
        });
        http.sessionManagement(it -> {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}