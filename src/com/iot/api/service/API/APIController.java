package com.iot.api.service.API;

import com.iot.api.service.connectionTest.InfluxDBConnectionTest;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class APIController {




    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    static SecureRandom rnd = new SecureRandom();
    //SECURITE pas important pour le TP
    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }



    InfluxDBConnectionTest influxDB = new InfluxDBConnectionTest();
    //SECURITE pas important pour le TP
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
    //Fonction permettant d'écrire un point dans la base de donnée : entrée : le capteur envoie le channel et les fields avec la clé de sécurité et la fonction écrit ce point dans le channel
    @RequestMapping(value="/bdd/post", method= RequestMethod.GET)
    String postPoint(@RequestParam String dbName, @RequestParam String SecurityKey, @RequestParam String param1, @RequestParam String param2, @RequestParam String param3,
                     @RequestParam String param4, @RequestParam String param5, @RequestParam String param6, @RequestParam String param7, @RequestParam String param8) {
        String retour="";
        InfluxDB connection = influxDB.connectDatabase();
        //SECURITE pas important pour le TP
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
            //fonction permettant de choisir la base de donnée ou écrire
            BatchPoints batchPoints = BatchPoints.database(dbName).build();
           //fonction permettant d'écrire le point en mettant les valeurs pour chaque field (intéressant pour le TP)
            Point pointToWrite = Point.measurement("minute").addField("field1", param1)
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
    //Fonction de l'API permettant d'envoyer les données des capteurs sur l'outil de visualisation, pas important pour le TP
    @RequestMapping(value="/bdd/data", method = RequestMethod.GET)
    List<List<Object>> getData2 (@RequestParam String dbName){
        InfluxDB connection = influxDB.connectDatabase();
        Query queryObject = new Query("select * from minute", dbName);
        QueryResult queryResult = connection.query(queryObject);
        connection.close();
        for (List<Object> o : queryResult.getResults().get(0).getSeries().get(0).getValues()){
            String toConvert = (String)o.get(0);
            toConvert = toConvert.replace("T", " ").replace("Z", "");
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date parsedDate = dateFormat.parse(toConvert);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                o.set(0, timestamp.getTime());
            } catch(Exception e) { //this generic but you can control another types of exception
                // look the origin of excption
            }

        }

        return queryResult.getResults().get(0).getSeries().get(0).getValues();
    }
    @RequestMapping(value="/bdd/getdblist", method= RequestMethod.GET)
    List<String> getdbList() {
        // Partie peu importante pour le TP : création des tables de sécurité
        InfluxDB connection = influxDB.connectDatabase();
        Query query = new Query("SHOW DATABASES", "security");
        QueryResult result = connection.query(query);
        List<List<Object>> databases = result.getResults().get(0).getSeries().get(0).getValues();
        List<String> ret = new ArrayList<String>();
        for (List<Object> e : databases){
            for (Object p : e){
                ret.add((String)p);
            }
        }
        return ret;
    }
    //fonction permettant de supprimer un point dans une base de donnée influx
    @RequestMapping(value="/bdd/delete", method= RequestMethod.GET)
    String deletePoint(@RequestParam String SecurityKey, @RequestParam String dbName, @RequestParam String pointName){

        String retour="";
        //SECURITE pas important pour le TP
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

    //fonction permettant d'écrire un channel
    @RequestMapping(value="/bdd/createChannel", method = RequestMethod.GET)
    String createChannel(@RequestParam String name){

        String retour = "";

        InfluxDB connection = influxDB.connectDatabase();
        connection.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        //SECURITE pas important pour le TP
        boolean contains = false;
        for (String e :connection.describeDatabases()){
            if (e.equals(name)){
                contains = true;
                break;
            }
        }
        if (!contains){
            retour = this.randomString(25);
            //fonction permettant d'executer une requete influx en l'occurence ici create database pour creer la base de données
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