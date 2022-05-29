//package edu.sombra.cms.config.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//import static edu.sombra.cms.domain.enumeration.Role.*;
//
//@Configuration
//@EnableResourceServer
//@RequiredArgsConstructor
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private final JwtAuthEntryPoint jwtAuthEntryPoint;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId("api");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .antMatcher("/api/**")
//                .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll()
//                .antMatchers("/api/student/**").hasAnyAuthority(ROLE_STUDENT.getName(), ROLE_ADMIN.getName())
//                .antMatchers("/api/instructor/**", "/api/lesson/**", "/api/course/**").hasAnyAuthority(ROLE_INSTRUCTOR.getName(), ROLE_ADMIN.getName())
//                .antMatchers("/api/admin/**").hasAuthority(ROLE_ADMIN.getName())
//                .antMatchers("/api/homework/**").authenticated()
//                .anyRequest().hasAuthority(ROLE_ADMIN.getName())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).accessDeniedHandler(new CustomAccessDeniedHandler());
//    }
//
//}
