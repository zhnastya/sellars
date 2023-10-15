package com.example.sellars.service.rabbit;

import com.example.sellars.configurations.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@Qualifier("RabbitService")
@RequiredArgsConstructor
public class RabbitServiceImpl implements RabbitService {
    private final RabbitTemplate template;

    @Override
    public void createProviders() {
        List<String> cities = List.of("Москва", "Санкт - Петербург", "Самара", "Воронеж", "Казань");
        Map<String, Object> map = new HashMap<>();
        for (String city : cities) {
            map.put("city", city);
            map.put("phone", "89898989898");
            map.put("check", "4229497465555");
            template.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.QUEUE_NAME, map);
        }
    }

    @Override
    public void createDelivery() {
        List<String> cities = List.of("Москва", "Санкт - Петербург", "Самара", "Воронеж", "Казань");
        Random random = new Random();
        for (int i = 1; i < 5; i++) {
            HashMap<String, Object> map = new HashMap<>();
            String city = cities.get(random.nextInt(cities.size()));
            String city2 = cities.get(random.nextInt(cities.size()));
            map.put("userId", i);
            map.put("addresFrom", city);
            map.put("providerId", 3L);
            map.put("addresTo", city2);
            map.put("price", random.nextInt(50000));
            map.put("dateOfDelivery", LocalDate.of(2023, 10, 14));
            map.put("duration", Duration.of(2, ChronoUnit.HOURS));
            map.put("isDanger", true);
            map.put("weight", 5 * i);
            map.put("km", 100 * i);
            template.convertAndSend(RabbitConfig.EXCHANGE_NAME2, RabbitConfig.QUEUE_NAME2, map);
        }
    }
}
