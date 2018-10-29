package com.iot.api.service.API;

import com.iot.api.service.connectionTest.InfluxDBConnectionTest;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class APIController {


    InfluxDBConnectionTest influxDB = new InfluxDBConnectionTest();

    APIRepository repository = new APIRepository();

    @RequestMapping(value="/bdd/post", method= RequestMethod.GET)
    void postValeur() {

    }
}