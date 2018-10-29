package com.iot.api.service;

import com.iot.api.service.connectionTest.InfluxDBConnectionTest;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("server.port", args[0]);
        SpringApplication.run(Application.class, args);

        InfluxDBConnectionTest test = new InfluxDBConnectionTest();
        System.out.println(test.pingServer(test.connectDatabase()));
}

}