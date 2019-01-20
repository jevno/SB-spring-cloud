package com.spdata.oauth2.configurer;

import com.spdata.oauth2.account.service.AccountService;
import com.spdata.oauth2.configurer.converter.SpDataTokenEnhancer;
import com.spdata.oauth2.configurer.exceptiontranslator.SpdataWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * 授权服务器--配置
 *
 * @author yangqifang
 */
@Configuration
@ComponentScan(basePackages = {"com.spdata.common"})
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    /**
     * 注入认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;
    /**
     * 注入token增强器
     */
    @Autowired
    private SpDataTokenEnhancer spDataTokenEnhancer;
    @Autowired
    private SpdataWebResponseExceptionTranslator translator;
    @Autowired
    private AccountService accountService;

    /**
     * token保存在数据库中
     *
     * @return
     */
    @Bean
    public JdbcTokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * token保存在内存里
     *
     * @return
     */
    @Bean
    public TokenStore InMemorytokenStore() {
        InMemoryTokenStore tokenStore = new InMemoryTokenStore();
        return tokenStore;
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(InMemorytokenStore());
        tokenServices.setTokenEnhancer(spDataTokenEnhancer);
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }


    /**
     * json web token
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("5e6374c3-2cf9-4b08-a251-e7706b03cece");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                //检查token接口 全部人可以访问
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * clien 配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                // client_id
                .withClient("spdata")
                // client_secret
                .secret("secret")
                // 该client允许的授权类型
                .authorizedGrantTypes("authorization_code", "password")
                // 允许的授权范围
                .scopes("all");
    }

    /**
     * 端点设置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenServices(tokenServices())
                .userDetailsService(accountService)
                .exceptionTranslator(translator)
                .authenticationManager(authenticationManager);
    }
}
