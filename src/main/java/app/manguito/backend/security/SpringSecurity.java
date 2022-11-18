package app.manguito.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().permitAll() //TODO cambiar a authorize()
                .and().httpBasic()
                .and().rememberMe()
                    .alwaysRemember(true)
                    .tokenValiditySeconds(30*5)
                    .rememberMeCookieName("mouni")
                    .key("somesecret")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and().logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .deleteCookies("JSESSIONID");
    }

    // configure SecurityFilterChain
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/register/**").permitAll()
//                .antMatchers("/index").permitAll()
//                .antMatchers("/user").hasRole("USER")
//                .and()
//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/user")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//
//                );
//        http.logout(logout -> logout
//                        .deleteCookies("JSESSIONID")
//                );
//        http.sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//        );
//        return http.build();
//    }
}