package com.iot.api.service.API;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class APIRepository {

    private String urlConnection = "";
    private String username = "";
    private String password= "";

    //fonction permettant d'écrire un point avec la rétention policy choisie
    public void writePoint(Point point, String database, String retentionPolicy){
        InfluxDB influxDB = InfluxDBFactory.connect(urlConnection, username, password);


        BatchPoints batchPoints = BatchPoints
                .database(database)
                .tag("async", "true")
                .retentionPolicy(retentionPolicy)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        batchPoints.point(point);

        influxDB.write(batchPoints);

        influxDB.disableBatch();
        influxDB.close();
    }
    //fonction permettant de récupérer sous forme de liste les résultats d'une requete influxdb
    public List<QueryResult.Result> query(String query, String database){
        InfluxDB influxDB = InfluxDBFactory.connect(urlConnection, username, password);

        Query queryVar = new Query(query, database);

        return influxDB.query(queryVar).getResults();
    }
}
