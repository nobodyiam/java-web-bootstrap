package com.nobodyiam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Jason on 7/5/15.
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource("classpath:bootstrap-web.properties")
public class AppConfig {
    @Autowired
    private Environment env;

    @Bean
    public ExecutorService executorService() throws Exception {
        return threadPoolExecutorFactoryBean().getObject();
    }

    @Bean
    public ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean() {
        int max_threads = Integer.parseInt(env.getProperty("threadpool.max_threads", "20"));
        int blocking_queue_size = Integer.parseInt(env.getProperty("threadpool.blocking_queue_size", "40"));
        ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean = new ThreadPoolExecutorFactoryBean();
        threadPoolExecutorFactoryBean.setMaxPoolSize(max_threads);
        threadPoolExecutorFactoryBean.setCorePoolSize(max_threads);
        threadPoolExecutorFactoryBean.setQueueCapacity(blocking_queue_size);
        threadPoolExecutorFactoryBean.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        return threadPoolExecutorFactoryBean;
    }
}
