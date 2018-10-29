package com.iot.api.service.connectionTest;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;


import java.util.List;
import java.util.concurrent.TimeUnit;


public class InfluxDBConnectionTest {


    public InfluxDB connectDatabase() {

        // Connect to database assumed on the server with default credentials.
        return  InfluxDBFactory.connect("http://51.255.167.53:8086", "admin", "admin");

    }

    public boolean pingServer(InfluxDB influxDB) {
        try {
            // Ping and check for version string
            Pong response = influxDB.ping();
            if (response.getVersion().equalsIgnoreCase("unknown")) {
                System.out.println("Error pinging server.");
                return false;
            } else {
                System.out.println("Database version: {}" + response.getVersion());
                return true;
            }
        } catch (InfluxDBIOException idbo) {
            System.out.println("Exception while pinging database: " + idbo);
            return false;
        }
    }



}