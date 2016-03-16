package com.rest.client;

import com.rest.bean.Message;
import com.rest.repository.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rest"})
@EnableJpaRepositories("com.rest.repository")
@EntityScan("com.rest.bean")
public class Application implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Autowired
  private Storage db;

  public static void main(String args[]) {
    SpringApplication.run(Application.class);
  }

  @Override
  public void run(String... args) throws Exception {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    try {
      Resource resource = new ClassPathResource("/api.properties");
      Properties props = PropertiesLoaderUtils.loadProperties(resource);
      String[] cities = props.getProperty("api.list").split(",");

      for (String city : cities) {
        UriComponentsBuilder builder =
            UriComponentsBuilder.fromHttpUrl(props.getProperty("api.url")).queryParam("q", city)
                .queryParam("units", "metric").queryParam("appid", props.getProperty("api.id"));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),
            HttpMethod.GET, entity, String.class);

        log.debug(response.getBody());

        Message msg = restTemplate.getForObject(builder.build().encode().toUri(), Message.class);
        log.info(msg.toString());

        db.write(msg.getName(), msg.getTemp().getTemp());

      }

      // fetch results
      db.fetch();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
