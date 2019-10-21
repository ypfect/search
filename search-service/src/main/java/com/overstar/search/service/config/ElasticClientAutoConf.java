package com.overstar.search.service.config;

import com.overstar.search.service.pop.EsPop;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 15:03
 */
@Configuration
@Slf4j
public class ElasticClientAutoConf implements FactoryBean<RestHighLevelClient> , InitializingBean, DisposableBean {

    @Autowired
    private EsPop esPop;

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private int esPort;

    private RestHighLevelClient client;

    @Override
    public void destroy() throws Exception {
        try {
            log.info("try to close els-client ...");
            client.close();
            log.info("close els-client successfully");
        }catch (Throwable e){
            log.error("close els-client failure");
        }
    }

    @Override
    public RestHighLevelClient  getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("create elasticsearch restHeightLevel client...");
        buildClientXPack();
    }


    protected void buildClientXPack(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                //es账号密码
                new UsernamePasswordCredentials(esPop.getXPackSecurityProperties().getUsername(), esPop.getXPackSecurityProperties().getPassword()));
        try {
            client = new RestHighLevelClient(
                    //传入RestClientBuilder
                    RestClient.builder(
                            new HttpHost(esHost, esPort)
                    ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            //这里可以设置一些参数，比如cookie存储、代理等等
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    }).setMaxRetryTimeoutMillis(6000)
            );
        }catch (Exception e)
        {
            //示例方法，不处理异常
            e.printStackTrace();
        }
    }

}
