package ru.bmstu.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.bmstu.interceptor.CredentialsInterceptor;

@Configuration
@ComponentScan("ru.bmstu")
@EnableAspectJAutoProxy
@EnableWebMvc
@PropertySource("classpath:application.properties")
@Import(SwaggerConfig.class)
public class AppConfig implements WebMvcConfigurer { }
