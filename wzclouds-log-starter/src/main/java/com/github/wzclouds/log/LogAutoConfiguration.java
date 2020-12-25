package com.github.wzclouds.log;


import com.github.wzclouds.jackson.JsonUtil;
import com.github.wzclouds.log.aspect.SysLogAspect;
import com.github.wzclouds.log.event.SysLogListener;
import com.github.wzclouds.log.monitor.PointUtil;
import com.github.wzclouds.log.properties.OptLogProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志自动配置
 * <p>
 * 启动条件：
 * 1，存在web环境
 * 2，配置文件中wzclouds.log.enabled=true 或者 配置文件中不存在：wzclouds.log.enabled 值
 *
 * @author wzclouds
 * @date 2019/2/1
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = OptLogProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

//    /**
//     * gateway 网关模块需要禁用 spring-webmvc 相关配置，必须通过在类上面加限制条件方式来实现， 不能直接Bean上面加
//     */
//    @ConditionalOnProperty(prefix = "wzclouds.webmvc", name = "enabled", havingValue = "true", matchIfMissing = true)
//    public static class WebMvcConfig {
//        @Bean
//        public MdcMvcConfigurer getMdcMvcConfigurer() {
//            return new MdcMvcConfigurer();
//        }
//    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("${wzclouds.log.enabled:true} && 'LOGGER'.equals('${wzclouds.log.type:LOGGER}')")
    public SysLogListener sysLogListener() {
        return new SysLogListener((log) -> {
            PointUtil.debug("0", "OPT_LOG", JsonUtil.toJson(log));
        });
    }
}
