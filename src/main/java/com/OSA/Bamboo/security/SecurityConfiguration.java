package com.OSA.Bamboo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfiguration() {
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
            throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter
                .setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().cacheControl().disable();
        httpSecurity.cors().configurationSource(corsConfigurationSource());
        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/{username}").permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/sellers").permitAll()
                .antMatchers(HttpMethod.POST, "/users/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/users/buyer/register").permitAll()
                .antMatchers(HttpMethod.POST, "/users/seller/register").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/edit").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/changePass/{username}").permitAll()
                .antMatchers(HttpMethod.GET, "/articles").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/articles/elastic").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/search/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/sellerArticles/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/discounts/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/postOrder").permitAll()
                .antMatchers(HttpMethod.POST, "/postDiscount").permitAll()
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(this.authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        CorsConfiguration defaultConfiguration = new CorsConfiguration();
        defaultConfiguration.applyPermitDefaultValues();

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration = configuration.combine(defaultConfiguration);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

