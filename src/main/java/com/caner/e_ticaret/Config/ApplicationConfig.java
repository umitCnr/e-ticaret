package com.caner.e_ticaret.Config;

import com.caner.e_ticaret.repository.ICustomerRepository;
import com.caner.e_ticaret.repository.ISellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ISellerRepository sellerRepository;
    private final ICustomerRepository customerRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        /** AuthenticationConfiguration nesnesi alır. Kimlik doğrulaması yapmasını sağlıyoruz **/
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        /** Bu nesne UserDetailService ve PasswordEncoder ın özellikleriini setliyoruz ve kullanıcı bilgilerini doğrulayan bir sınıftır **/

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(){

        /** burada kullanıcıyı username ine göre arama yapıyoruz **/

        return username -> {

            var seller = sellerRepository.findByName(username);

            if (seller.isPresent()) {
                return seller.get();
            }

            var customer = customerRepository.findByName(username);

            if (customer.isPresent()) {
                return (UserDetails) customer.get();
            }

            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        };
    }
}
