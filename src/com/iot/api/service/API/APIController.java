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

    @RequestMapping(value="/bdd/post", method= RequestMethod.GET)
    void postValeur() {
        InfluxDB db = influxDB.connectDatabase();

        db.setRetentionPolicy("IMTA");
        db.setDatabase("test");

        Point point1 = Point.measurement("memory")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("name", "server1")
                .addField("free", 4743656L)
                .addField("used", 1015096L)
                .addField("buffer", 1010467L)
                .build();


        db.write(point1);
        System.out.println("Posted");

        db.disableBatch();
        db.close();
    }

    @RequestMapping(value="/bdd/lire", method= RequestMethod.GET)
    List<org.influxdb.dto.QueryResult.Result> query() {
        InfluxDB influxDB = InfluxDBFactory.connect("http://51.255.167.53:8086", "admin", "admin");
        String dbName = "aTimeSeries";
        influxDB.createDatabase(dbName);
        String rpName = "aRetentionPolicy";
        influxDB.createRetentionPolicy(rpName, dbName, "30d", "30m", 2, true);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(rpName)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("idle", 90L)
                .addField("user", 9L)
                .addField("system", 1L)
                .build();
        Point point2 = Point.measurement("disk")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("used", 80L)
                .addField("free", 1L)
                .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        influxDB.query(query).getResults();

        return influxDB.query(query).getResults();
    }




}