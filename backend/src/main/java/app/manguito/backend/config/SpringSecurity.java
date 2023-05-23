package app.manguito.backend.config;

import app.manguito.backend.security.CustomUserDetailsService;
import app.manguito.backend.security.JwtAuthenticationEntryPoint;
import app.manguito.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/login", "/register", "/pre-register", "/donaciones/manguitos", "/donaciones/suscripciones").permitAll()
                .antMatchers(HttpMethod.GET, "/emprendimientos/**", "/donaciones/status/**", "/categorias").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}

//@Configuration
//@EnableWebSecurity
//public class SpringSecurity extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/register").permitAll()
//                .anyRequest().permitAll() //TODO cambiar a authorize()
//                .and().httpBasic()
//                .and().rememberMe()
//                    .alwaysRemember(true)
//                    .tokenValiditySeconds(30*5)
//                    .rememberMeCookieName("mouni")
//                    .key("somesecret")
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).maximumSessions(2).and()
//                .and().logout()
//                    .logoutUrl("/logout")
//                    .permitAll()
//                    .deleteCookies("JSESSIONID");
//    }
//
//    // configure SecurityFilterChain
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/register/**").permitAll()
////                .antMatchers("/index").permitAll()
////                .antMatchers("/user").hasRole("USER")
////                .and()
////                .formLogin(
////                        form -> form
////                                .loginPage("/login")
////                                .loginProcessingUrl("/login")
////                                .defaultSuccessUrl("/user")
////                                .permitAll()
////                ).logout(
////                        logout -> logout
////                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                                .permitAll()
////
////                );
////        http.logout(logout -> logout
////                        .deleteCookies("JSESSIONID")
////                );
////        http.sessionManagement(session -> session
////                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
////                .maximumSessions(1)
////                .maxSessionsPreventsLogin(true)
////        );
////        return http.build();
////    }
//}