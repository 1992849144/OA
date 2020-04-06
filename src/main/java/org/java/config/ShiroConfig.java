package org.java.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.java.realm.AuthRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean,在创建时，给方法注入securityManager
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);//设置安全管理器
        shiroFilterFactoryBean.setLoginUrl("/login");//如果过滤器拦到用户没有经过认证，发出login请求，找过认证的控制器

        //创建一个map，用于指定，哪一些请求路径，shiro如何拦截
        Map<String,String> shiroFilterDefinitionMap = new LinkedHashMap<>();
        shiroFilterDefinitionMap.put("/css/**","anon"); //允许匿名访问
        shiroFilterDefinitionMap.put("/js/**","anon");
        shiroFilterDefinitionMap.put("/img/**","anon");
        shiroFilterDefinitionMap.put("/images/**","anon");
        shiroFilterDefinitionMap.put("/logout","logout");//退出认证
        shiroFilterDefinitionMap.put("/favicon.ico","anon");
        shiroFilterDefinitionMap.put("/**","authc");//必须认证以后，才允许访问资源

        //装载拦截路径
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 该方法，用于创建安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //在安全管理器中，注入realm
        securityManager.setRealm(authRealm());
        //引用EhcacheManager
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;

    }

    /**
     * 该方法，用于创建我们声明的AuthRealm
     * @return
     */
    @Bean
    public AuthRealm authRealm(){
        AuthRealm authRealm = new AuthRealm();

        //指定，加密规则
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authRealm;
    }

    /**
     * 指定加密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){

        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//采用哪一种加密算法
        hashedCredentialsMatcher.setHashIterations(3);//加密次数

        return hashedCredentialsMatcher;
    }

    /**
     * 注意，必须配置该方法，shiro才可以执行授权操作
     * @return
     */

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 配置EhcacheManager
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:conf/shiro-ehcache.xml");
        return ehCacheManager;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean

    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
