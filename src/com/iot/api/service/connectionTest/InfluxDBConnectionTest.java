package com.iot.api.service.connectionTest;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;


import java.util.List;
import java.util.concurrent.TimeUnit;


public class InfluxDBConnectionTest {

    // Fonction permettant de lier l'application donc l'API à la base influxdb
    public InfluxDB connectDatabase() {
        //identifiants influx de connexion
        InfluxDB connection = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
        connection.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        boolean contains = false;

        // Partie peu importante pour le TP : création des tables de sécurité
        Query query = new Query("SHOW DATABASES", "security");
        QueryResult result = connection.query(query);
        List<List<Object>> databases = result.getResults().get(0).getSeries().get(0).getValues();
        for (List<Object> e : databases){
            for (Object p : e){
                if (p.toString().equals("security")){
                    contains = true;
                    break;
                }
            }
        }
        if (!contains){
            connection.query(new Query("CREATE DATABASE security", "security"));

        }
        connection.disableBatch();
        // Connect to database assumed on the server with default credentials.
        //retourne la connexion si la connexion s'est bien déroulée
        return  connection;

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