package me.wangran.springcloud.gateway.zuul.locator;

import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.util.RequestUtils;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ZullDynamicRouteLocator implements RefreshableRouteLocator, Ordered {

    private static final int DEFAULT_ORDER = 1;

    private AtomicReference<Map<String, ZuulRoute>> routes = new AtomicReference();
    private PathMatcher pathMatcher = new AntPathMatcher();
    private String dispatcherServletPath = "/";
    private String zuulServletPath = "/zuul";
    private String prefix = "";
    private Set<String> ignoredPatterns = Collections.emptySet();

    public ZullDynamicRouteLocator() {

    }

    public ZullDynamicRouteLocator(String servletPath, String zuulServletPath, String prefix) {
        if (StringUtils.hasText(servletPath)) {
            this.dispatcherServletPath = servletPath;
        }
        if (StringUtils.hasText(zuulServletPath)) {
            this.zuulServletPath = zuulServletPath;
        }
        if (StringUtils.hasText(prefix)) {
            this.prefix = prefix;
        }
    }

    @Override
    public void refresh() {
        this.routes.set(this.locateRoutes());
    }

    @Override
    public Collection<String> getIgnoredPaths() {
        return this.ignoredPatterns;
    }

    @Override
    public List<Route> getRoutes() {
        List<Route> values = new ArrayList();
        Iterator var2 = this.getRoutesMap().entrySet().iterator();
        while(var2.hasNext()) {
            Map.Entry<String, ZuulRoute> entry = (Map.Entry)var2.next();
            ZuulRoute route = entry.getValue();
            String path = route.getPath();
            values.add(this.getRoute(route, path, prefix));
        }
        return values;
    }

    @Override
    public Route getMatchingRoute(String path) {
        this.getRoutesMap();
        String adjustedPath = this.adjustPath(path);
        ZuulRoute route = this.getZuulRoute(adjustedPath);
        return this.getRoute(route, adjustedPath, prefix);
    }

    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap();
//        Iterator var2 = this.properties.getRoutes().values().iterator();
//
        // 从缓存中加载路由信息
//        while(var2.hasNext()) {
            ZuulRoute route = new ZuulRoute();
            route.setId("dynamic");
            route.setServiceId("spring-cloud-provider");
            route.setPath("/dynamic/**");
            routesMap.put(route.getPath(), route);
//        }

        return routesMap;
    }

    protected Map<String, ZuulRoute> getRoutesMap() {
        if (this.routes.get() == null) {
            this.routes.set(this.locateRoutes());
        }
        return this.routes.get();
    }

    protected ZuulRoute getZuulRoute(String adjustedPath) {
        if (!this.matchesIgnoredPatterns(adjustedPath)) {
            Iterator var2 = this.getRoutesMap().entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, ZuulRoute> entry = (Map.Entry)var2.next();
                String pattern = (String)entry.getKey();
                if (this.pathMatcher.match(pattern, adjustedPath)) {
                    return (ZuulRoute)entry.getValue();
                }
            }
        }

        return null;
    }

    protected boolean matchesIgnoredPatterns(String path) {
        Iterator var2 = ignoredPatterns.iterator();
        String pattern;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            pattern = (String)var2.next();
        } while(!this.pathMatcher.match(pattern, path));
        return true;
    }

    protected Route getRoute(ZuulRoute route, String path, String prefix) {
        if (route == null) {
            return null;
        } else {
            String targetPath = path;
            if (prefix.endsWith("/")) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }

            if (path.startsWith(prefix + "/") && route.isStripPrefix()) {
                targetPath = path.substring(prefix.length());
            }

            if (route.isStripPrefix()) {
                int index = route.getPath().indexOf("*") - 1;
                if (index > 0) {
                    String routePrefix = route.getPath().substring(0, index);
                    targetPath = targetPath.replaceFirst(routePrefix, "");
                    prefix = prefix + routePrefix;
                }
            }

            Boolean retryable = route.getRetryable();
            if (route.getRetryable() != null) {
                retryable = route.getRetryable();
            }

            return new Route(route.getId(), targetPath, route.getLocation(), prefix, retryable, route.isCustomSensitiveHeaders() ? route.getSensitiveHeaders() : null, route.isStripPrefix());
        }
    }

    private String adjustPath(String path) {
        String adjustedPath = path;
        if (RequestUtils.isDispatcherServletRequest() && StringUtils.hasText(this.dispatcherServletPath)) {
            if (!this.dispatcherServletPath.equals("/")) {
                adjustedPath = path.substring(this.dispatcherServletPath.length());
            }
        } else if (RequestUtils.isZuulServletRequest() && StringUtils.hasText(this.zuulServletPath) && !this.zuulServletPath.equals("/")) {
            adjustedPath = path.substring(this.zuulServletPath.length());
        }
        return adjustedPath;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
