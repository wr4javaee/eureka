package me.wangran.springcloud.gateway.zuul.conf;

import me.wangran.springcloud.gateway.zuul.locator.ZullDynamicRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.ZuulProxyMarkerConfiguration;
import org.springframework.cloud.netflix.zuul.ZuulServerAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.CompositeRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态路由配置文件
 */
@Configuration
public class ZuulDynamicConfiguration {

    @Autowired
    protected ZuulProperties zuulProperties;
    @Autowired
    protected ServerProperties server;

    @Bean
    public ZullDynamicRouteLocator dynamicRouteLocator() {
        ZullDynamicRouteLocator zullDynamicRouteLocator = new ZullDynamicRouteLocator(null, null, null);
        return zullDynamicRouteLocator;
    }



}