package de.telran.g240123mbelesson331082023.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(x -> x.disable())

                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/customer", "/product").permitAll()

                        .requestMatchers(HttpMethod.GET, "/product/**").hasAnyRole("USER", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/customer/cart/add/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/customer/cart/delete/**", "/customer/cart/clear/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/customer/**", "/customer/count", "/product/count",
                                "/product/totalPrice", "/product/avgPrice", "/customer/totalPrice/**", "/customer/avgPrice/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/customer", "/product").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/product/**", "/customer/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/product/update/**", "/customer/update/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
