package com.example.hotel.configuratoins;
import com.example.hotel.controllers.StringToDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToDTOConverter stringToDTOConverter;

    public WebConfig(StringToDTOConverter stringToDTOConverter) {
        this.stringToDTOConverter = stringToDTOConverter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(stringToDTOConverter);
    }
}