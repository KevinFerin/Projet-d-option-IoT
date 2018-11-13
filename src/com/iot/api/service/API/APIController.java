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

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class APIController {




    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }



    InfluxDBConnectionTest influxDB = new InfluxDBConnectionTest();

    APIRepository repository = new APIRepository();
    @RequestMapping(value="/bdd/post1", method= RequestMethod.GET)
    String selectChannelKey(@RequestParam String dbName){
        String retour = "";
        InfluxDB connection = influxDB.connectDatabase();
        connection.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(dbName)){
                contains = true;
                break;
            }
        }
        if (contains){
            QueryResult result = connection.query(new Query("SELECT * from "+ dbName, "security"));
            List<List<Object>> databases = result.getResults().get(0).getSeries().get(0).getValues();
            for (List<Object> e : databases){
                System.out.println("------------------");
                for (Object p : e){
                    String key = (String)p;
                    if (key.length()==25){
                        return key;
                    }
                }
            }
        }
        else{
            retour = "Database doesn't exist !";
        }
        connection.close();
        return retour;
}

    @RequestMapping(value="/bdd/post", method= RequestMethod.GET)
    String postPoint(@RequestParam String pointName, @RequestParam String dbName, @RequestParam String SecurityKey, @RequestParam String param1, @RequestParam String param2, @RequestParam String param3,
                     @RequestParam String param4, @RequestParam String param5, @RequestParam String param6, @RequestParam String param7, @RequestParam String param8) {
        String retour="";
        InfluxDB connection = influxDB.connectDatabase();
        if (!this.selectChannelKey(dbName).equals(SecurityKey)){
            return "Key Error !";
        }
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(dbName)){
                contains = true;
                break;
            }
        }

        if (contains){
            BatchPoints batchPoints = BatchPoints.database(dbName).build();
            Point pointToWrite = Point.measurement(pointName).addField("field1", param1)
                    .addField("field2", param2)
                    .addField("field3", param3)
                    .addField("field4", param4)
                    .addField("field5", param5)
                    .addField("field6", param6)
                    .addField("field7", param7)
                    .addField("field8", param8)
                    .build();
            batchPoints.point(pointToWrite);
            connection.write(batchPoints);
        }
        else{
            retour = "Database doesn't exist !";
        }
        connection.close();
        return "Point writed in Channel : " + dbName;
    }

    @RequestMapping(value="/bdd/delete", method= RequestMethod.GET)
    String deletePoint(@RequestParam String SecurityKey, @RequestParam String dbName, @RequestParam String pointName){

        String retour="";
        InfluxDB connection = influxDB.connectDatabase();
        if (!this.selectChannelKey(dbName).equals(SecurityKey)){
            return "Key Error !";
        }
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(dbName)){
                contains = true;
                break;
            }
        }

        /*if (contains){
            connection.query(new Query("DELETE FROM "+ name, dbName));
        }
        else{
            retour = "Database doesn't exist !";
        }*/
        connection.close();
        return "Point writed in Channel : " + dbName;
    }

    @RequestMapping(value="/bdd/deleteChannel", method= RequestMethod.GET)
    String resetBdd(@RequestParam String SecurityKey, @RequestParam String dbName){

        String retour="";
        InfluxDB connection = influxDB.connectDatabase();

        if (!this.selectChannelKey(dbName).equals(SecurityKey)){
            return "Key Error !";
        }
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(dbName)){
                contains = true;
                break;
            }
        }

        if (contains){
            connection.deleteDatabase(dbName);
        }
        else{
            retour = "Database doesn't exist !";
        }
        connection.close();
        return "Channel Deleted : " + dbName;
    }


    @RequestMapping(value="/bdd/createChannel", method = RequestMethod.GET)
    String createChannel(@RequestParam String name){

        String retour = "";

        InfluxDB connection = influxDB.connectDatabase();
        connection.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(name)){
                contains = true;
                break;
            }
        }
        if (!contains){
            retour = this.randomString(25);
            connection.query(new Query("CREATE DATABASE "+ name, name));
            BatchPoints batchPoints = BatchPoints.database("security").build();
            Point pointSecurityKey = Point.measurement(name).addField("key", retour).build();
            batchPoints.point(pointSecurityKey);
            connection.write(batchPoints);

            connection.disableBatch();
        }
        else{
            retour = "Database already exist !";
        }
        connection.close();
        return retour + " Clé de Sécurité associée au Channel : nécessaire à l'insertion / suppression / modification du Channel " + name;
    }
}