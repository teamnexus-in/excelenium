package in.teamnexus.excelenium.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import in.teamnexus.excelenium.service.ExceleniumSuiteService;
import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.executor.ScriptExecutor;

// TODO: Auto-generated Javadoc
/**
 * The Class RootConfig.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "in.teamnexus.excelenium.controller" })
public class RootConfig implements WebMvcConfigurer, ApplicationContextAware
{
    
    /** The application context. */
    @Autowired
    /** The application context. */
    ApplicationContext applicationContext;

    /**
     * Adds the resource handlers.
     *
     * @param registry the registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("file:/usr/local/pages/excelenium/static/", "classpath:/static/");
    }

    /**
     * Template resolver.
     *
     * @return the spring resource template resolver
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver()
    {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * File template resolver.
     *
     * @return the file template resolver
     */
    @Bean
    public FileTemplateResolver fileTemplateResolver()
    {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("/usr/local/pages/excelenium/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setOrder(0);
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * Template engine.
     *
     * @return the spring template engine
     */
    @Bean
    public SpringTemplateEngine templateEngine()
    {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        Set<ITemplateResolver> templateResolvers = new HashSet<ITemplateResolver>();
        templateResolvers.add(templateResolver());
        templateResolvers.add(fileTemplateResolver());
        // templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setTemplateResolvers(templateResolvers);
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * Configure view resolvers.
     *
     * @param registry the registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    /**
     * Script executor.
     *
     * @return the script executor
     */
    @Bean
    ScriptExecutor scriptExecutor()
    {
        return new ScriptExecutor();
    }
    
    /**
     * Suite service.
     *
     * @return the suite service
     */
    @Bean
    SuiteService suiteService()
    {
        ExceleniumSuiteService service = new ExceleniumSuiteService();
        service.setExecutor(scriptExecutor());
        return service;
    }

    /**
     * Sets the application context.
     *
     * @param applicationContext the new application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

}
