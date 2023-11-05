package com.example.bookstoring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.bookstoring.security.ApplicationUserRole.AUTHOR;
import static com.example.bookstoring.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/", "index", "/css/*", "/js/*")
                                        .permitAll()
                                        .requestMatchers("/api/**").hasRole(STUDENT.name())
//                                .requestMatchers(HttpMethod.DELETE,"/author/api/**").hasAuthority(AUTHOR_DELETE_BOOKS.getPermission())
//                                .requestMatchers(HttpMethod.POST,"/author/api/**").hasAuthority(AUTHOR_CREATE_BOOKS.getPermission())
                                        .anyRequest()
                                        .authenticated()
                )
                .formLogin(fL -> fL.loginPage("/login").permitAll()
                        .defaultSuccessUrl("/bookstore", true))
                .rememberMe(Customizer.withDefaults())
                .logout((logout) -> logout.logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails rashadSuleymanovUser = User.builder()
                .username("Rashad")
                .password(passwordEncoder.encode("rashadpassword"))
                .roles(STUDENT.name())
                .build();

        UserDetails aftlatunValibayli = User.builder()
                .username("Aflatun")
                .password(passwordEncoder.encode("aflatunpassword"))
                .authorities(AUTHOR.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                rashadSuleymanovUser,
                aftlatunValibayli
        );
    }

//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                .password(bCryptPasswordEncoder.encode("userPass"))
//                .roles("USER")
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(bCryptPasswordEncoder.encode("adminPass"))
//                .roles("USER", "ADMIN")
//                .build());
//        return manager;


}
