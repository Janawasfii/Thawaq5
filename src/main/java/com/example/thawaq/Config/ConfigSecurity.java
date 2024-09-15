package com.example.thawaq.Config;

import com.example.thawaq.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(getDaoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/user/register-client/**").permitAll()
                .requestMatchers("api/v1/user/register-expert/**").permitAll()
                .requestMatchers("api/v1/user/register-store/**").permitAll()
                .requestMatchers("api/v1/user/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/branch/add/**").hasAuthority("STORE")

                //Rating
                .requestMatchers("api/v1/rating/get-all").permitAll()
                .requestMatchers("api/v1/rating/get-my-client").hasAuthority("CLIENT")
                .requestMatchers("api/v1/rating/get-my-expert").hasAuthority("EXPERT")
                .requestMatchers("api/v1/rating/expert/add-rating/{storeId}").hasAuthority("EXPERT")
                .requestMatchers("api/v1/rating/client/add-rating/{storeId}").hasAuthority("CLIENT")
                .requestMatchers("api/v1/rating/update-rating/{ratingId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/rating/delete-rating/{ratingId}").hasAuthority("ADMIN")

                .requestMatchers("api/v1/rating/average-rating-store-service/{storeId}").permitAll()
                .requestMatchers("api/v1/rating//top-4-cafes").permitAll()
                .requestMatchers("api/v1/rating/top-4-restaurant").permitAll()


                //Category
                .requestMatchers("api/v1/category/apply-discount/{categoryName}/{branchId}/{discountPercentage}").permitAll()

                //Menu
                .requestMatchers("api/v1/menu/get-By-price-range/{priceMin}/{priceMax}").permitAll()

                //Request
                .requestMatchers("api/v1/request/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/request/get-my-request").hasAuthority("EXPERT")
                .requestMatchers("api/v1/request/add-request/{expertId}").hasAuthority("STORE")
                .requestMatchers("api/v1/request/update-request/{requestId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/request/delete-request/{requestId}").hasAuthority("ADMIN")

                //Store
                .requestMatchers("api/v1/store/get-typeOfActivity/{typeOfActivity}").permitAll()

                .requestMatchers("api/v1/store/get-best-service-cafes-name/{name}").permitAll()
                .requestMatchers("api//v1/store/get-best-service-restaurant-name/{name}").permitAll()
                .requestMatchers("api/v1/store/get-best-service-both-name/{name}").permitAll()

                .requestMatchers("api/v1/store/get-best-service-cafes-categoryName/{categoryName}").permitAll()
                .requestMatchers("api/v1/store/get-best-service-restaurant-categoryName/{categoryName}").permitAll()
                .requestMatchers("api/v1/store/get-best-service-both-categoryName/{categoryName}").permitAll()


                .requestMatchers("api/v1/store/get-best-service-cafe-cityName/{cityName}").permitAll()
                .requestMatchers("api/v1/store/get-best-service-restaurant-cityName/{cityName}").permitAll()
                .requestMatchers("api/v1/store/get-best-service-both-cityName/{cityName}").permitAll()

                //USER
                .requestMatchers("api/v1/user/activate-storeAdmin/{storeAdminId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/user/deactivate-storeAdmin/{storeAdminId}").hasAuthority("ADMIN")











                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
