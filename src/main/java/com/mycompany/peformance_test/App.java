/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.peformance_test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentEntity;
import com.arangodb.mapping.ArangoJack;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Value;



/**
 *
 * @author adria
 */


public class App {
    
    public static void createDatabaseOrientdb(Set<No> nos, Set<String> arestas, int tamanho) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test", "adriano", "1234");
        
        Iterator<No> value_no = nos.iterator();
        while(value_no.hasNext()) {
            OVertex result = db.newVertex("No");
            No no = value_no.next();
            result.setProperty("Id", no.id);
            result.setProperty("Name", no.name);
            result.setProperty("Number", no.number);
            result.save();
        }
        
        Iterator<String> value_relation = arestas.iterator();
        int count = 1;
        while(value_relation.hasNext())
        {
            String query = "CREATE EDGE Relation FROM (SELECT FROM No WHERE Id = ?) TO" +
            " (SELECT FROM No WHERE Id = ?) CONTENT { Id: ?}";
            String[] value_strings = value_relation.next().split("-");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", Integer.parseInt(value_strings[0]));
            params.put("id2", Integer.parseInt(value_strings[1]));
            params.put("id3", count);

            //OResultSet rs = db.command(query, params);
            OResultSet rs = db.command(query, Integer.parseInt(value_strings[0]), Integer.parseInt(value_strings[1]), count);
            count++;
//            if(rs.hasNext())
//            {
//                //OVertex vertex1 = (OVertex) rs.next();
//                //System.out.println(vertex1);
//            }

        }
        db.close();
        orient.close();

    }
    
    public static void createDatabaseOrientdb(List<No> nos, List<String> arestas, int start) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test3", "adriano", "1234");
        
        for(int i = start; i < nos.size(); i++) {
            OVertex result = db.newVertex("No");
            No no = nos.get(i);
            result.setProperty("Id", no.id);
            result.setProperty("Name", no.name);
            result.setProperty("Number", no.number);
            result.save();
        }
        
        for(int i = start; i < arestas.size(); i++)
        {
            String query = "CREATE EDGE Relation FROM (SELECT FROM No WHERE Id = ?) TO" +
            " (SELECT FROM No WHERE Id = ?) CONTENT { Id: ?, `From`: ?, To: ? }";
            String[] value_strings = arestas.get(i).split("-");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", Integer.parseInt(value_strings[0]));
            params.put("id2", Integer.parseInt(value_strings[1]));
            params.put("id3", i + 1);
            

            //OResultSet rs = db.command(query, params);
            int from = Integer.parseInt(value_strings[0]);
            int to = Integer.parseInt(value_strings[1]);
            OResultSet rs = db.command(query, from, to, i + 1, from, to);
//            if(rs.hasNext())
//            {
//                //OVertex vertex1 = (OVertex) rs.next();
//                //System.out.println(vertex1);
//            }
            rs.close();

        }
        db.close();
        orient.close();

    }
    
    
    public static void createVertexOrientdb(No no) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        final long startTime = System.currentTimeMillis();
        OVertex result = db.newVertex("No");
        result.setProperty("Id", no.id);
        result.setProperty("Name", no.name);
        result.setProperty("Number", no.number);
        result.save();
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();

    }
    
    public static void createEdgeOrientdb(int id1, int id2, int id3) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
         String query = "CREATE EDGE Relation FROM (SELECT FROM No WHERE Id = ?) TO" +
        " (SELECT FROM No WHERE Id = ?) CONTENT { Id: ?}";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id1, id2, id3);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();

    }
    
    public static void altVertexOrientdb(No no) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
         String query = "UPDATE No SET Name = ? SET Number = ? WHERE Id = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, no.name, no.number, no.id);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();

    }
    
    public static void altAllEdgeOrientdb(List<String> fromto, int start, int end) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        for(int i = start; i< end; i++)
        {
            String[] strings = fromto.get(i).split("-");
            int from = Integer.parseInt(strings[0]);
            int to = Integer.parseInt(strings[1]);

             String query = "UPDATE EDGE Relation SET `From` = ? SET To = ? WHERE Id = ?";

            //OResultSet rs = db.command(query, params);
            //final long startTime = System.currentTimeMillis();
            OResultSet rs = db.command(query, from, to, i + 1);
            //final long endTime = System.currentTimeMillis();
            //System.out.println(endTime - startTime);
            rs.close();
        }
            
        db.close();
        orient.close();

    }
    
    public static void countVertexOrientdb() {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        final long startTime = System.currentTimeMillis();
        OClass userCls = db.getClass("No");
        userCls.count();
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void countEdgeOrientdb() {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        final long startTime = System.currentTimeMillis();
        OClass userCls = db.getClass("Relation");
        userCls.count();
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
        
        
    }
    
    public static void avgOrientdb() {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test3", "adriano", "1234");
        
        String query = "SELECT AVG(Number) FROM No";
        
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void findVertexOrientdb(int id) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "SELECT FROM No WHERE Id = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void findEdgeOrientdb(int id1, int id2) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "select from Relation where in in (SELECT @rid FROM No WHERE Id = ?) and "
                + "out in (SELECT @rid FROM No WHERE Id = ?)";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id1, id2);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void findEdgeOtmOrientdb(int id1, int id2) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "select from Relation where `From` = ? and To = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id1, id2);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void delVertexOrientdb(int id) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "DELETE VERTEX No WHERE Id = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void delEdgeOrientdb(int id1, int id2) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "DELETE EDGE Relation Where out in (select @rid from No Where Id = ?) and in in (select @rid from No Where Id = ?)";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id1, id2);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void AllNodesOrientDB(int id) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        String query = "select outE('Relation') from No WHERE Id = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void ShortestPathOrientDB(int id1, int id2) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test3", "adriano", "1234");
        
        String query = "select shortestPath($a[0].id, $b[0].id) \n" +
"                            LET $a = (select @rid as id from No where Id = ?), \n" +
"                            $b = (select @rid as id from No where Id = ?)";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, id1, id2);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
            
        db.close();
        orient.close();
    }
    
    public static void findFilterOrientDB(String name) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test", "adriano", "1234");
        
        String query = "select from No WHERE Name = ?";

        //OResultSet rs = db.command(query, params);
        final long startTime = System.currentTimeMillis();
        OResultSet rs = db.command(query, name);
        final long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        
        rs.close();
        db.close();
        orient.close();
    }
    
    public static void createDatabaseOrientdbVertexWorkload(List<No> nos, List<String> arestas, int start) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        for(int i = start; i < nos.size(); i++) {
            OVertex result = db.newVertex("No");
            No no = nos.get(i);
            result.setProperty("Id", no.id);
            result.setProperty("Name", no.name);
            result.setProperty("Number", no.number);
            result.save();
        }
        db.close();
        orient.close();

    }
    
    public static void createDatabaseOrientdbEdgeWorkload(List<No> nos, List<String> arestas, int start) {
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        
        ODatabaseSession db = orient.open("test1", "adriano", "1234");
        
        for(int i = start; i < arestas.size(); i++)
        {
            String query = "CREATE EDGE Relation FROM (SELECT FROM No WHERE Id = ?) TO" +
            " (SELECT FROM No WHERE Id = ?) CONTENT { Id: ?, `From`: ?, To: ? }";
            String[] value_strings = arestas.get(i).split("-");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", Integer.parseInt(value_strings[0]));
            params.put("id2", Integer.parseInt(value_strings[1]));
            params.put("id3", i + 1);
            

            //OResultSet rs = db.command(query, params);
            int from = Integer.parseInt(value_strings[0]);
            int to = Integer.parseInt(value_strings[1]);
            OResultSet rs = db.command(query, from, to, i + 1, from, to);
//            if(rs.hasNext())
//            {
//                //OVertex vertex1 = (OVertex) rs.next();
//                //System.out.println(vertex1);
//            }
            rs.close();

        }
        db.close();
        orient.close();

    }
    
    
    
//----------------------------------------------------------------------------------
//----------------------------ArangoDB----------------------------------------------
//----------------------------------------------------------------------------------
    
    public static void createDatabaseArangoDB(Set<No> nos, Set<String> arestas, int tamanho) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        
        Iterator<No> value_no = nos.iterator();
        while(value_no.hasNext()) {
            No no = value_no.next();
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(no.id));
            myObject.addAttribute("Name", no.name);
            myObject.addAttribute("Number", no.number);
            try {
              arangoDB.db("teste2").collection("No").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }
        }
        
        Iterator<String> value_relation = arestas.iterator();
        int count = 1;
        while(value_relation.hasNext())
        {
            String[] value_strings = value_relation.next().split("-");
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(count));
            count++;
            myObject.addAttribute("_from", "No/" + value_strings[0]);
            myObject.addAttribute("_to", "No/" + value_strings[1]);
            try {
              arangoDB.db("teste2").collection("Relation").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }

        }
        arangoDB.shutdown();

    }
    
    public static void createDatabaseArangoDB(List<No> nos, List<String> arestas, int start) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        
        for(int i = start; i < nos.size(); i++) {
            No no = nos.get(i);
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(no.id));
            myObject.addAttribute("Name", no.name);
            myObject.addAttribute("Number", no.number);
            try {
              arangoDB.db("teste3").collection("No").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }
        }
        
        for(int i = start; i < arestas.size(); i++)
        {
            String[] value_strings = arestas.get(i).split("-");
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(i+1));
            myObject.addAttribute("_from", "No/" + value_strings[0]);
            myObject.addAttribute("_to", "No/" + value_strings[1]);
            try {
              arangoDB.db("teste3").collection("Relation").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }

        }
        arangoDB.shutdown();

    }
    
    public static void createVertexArangoDB(No no) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(String.valueOf(no.id));
        myObject.addAttribute("Name", no.name);
        myObject.addAttribute("Number", no.number);
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste3").collection("No").insertDocument(myObject);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();

    }
    
    public static void createEdgeArangoDB(int id1, int id2, int id3) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(String.valueOf(id3));
        myObject.addAttribute("_from", "No/" + id1);
        myObject.addAttribute("_to", "No/" + id2);
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste3").collection("Relation").insertDocument(myObject);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();

    }
    
    public static void altVertexArangoDB(No no) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(String.valueOf(no.id));
        myObject.addAttribute("Name", no.name);
        myObject.addAttribute("Number", no.number);
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").collection("No").replaceDocument( "" + no.id, myObject);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();

    }
    
    public static void countVertexArangoDB() {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").collection("No").count();
          final long endTime = System.currentTimeMillis();
          System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void countEdgeArangoDB() {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").collection("Relation").count();
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void avgArangoDB() {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
            String query = "for n in No\n" +
                "    COLLECT\n" +
                "    AGGREGATE num = AVG(n.Number)\n" +
                "    RETURN {num}";
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste3").query(query, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void findVertexArangoDB(int id) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste3").collection("No").getDocument(String.valueOf(id), BaseDocument.class);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void findEdgeArangoDB(int id1, int id2) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
            String query = "FOR rel in Relation\n" +
            "    FILTER rel._from == @id1 AND rel._to == @id2\n" +
            "    RETURN rel";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", id1);
            params.put("id2", id2);
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").query(query, params, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void delVertexArangoDB(int id) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").collection("No").deleteDocument(""+id);
          final long endTime = System.currentTimeMillis();
          System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void delEdgeArangoDB(int id1, int id2) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
            String query = "FOR rel in Relation\n" +
            "    FILTER rel._from == @id1 AND rel._to == @id2\n" +
            "    REMOVE rel in Relation";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", "No/" + id1);
            params.put("id2", "No/" + id2);
            final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").query(query, params, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void allNodesArangoDB(int id) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          String queries = "FOR v IN 1..1 OUTBOUND @id Relation\n" +
            "    RETURN v";
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("id", "No/" + id);
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste2").query(queries, params, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
          System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void shortestPathArangoDB(int id1, int id2) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          String queries = "FOR v, e IN ANY SHORTEST_PATH @id1 TO @id2 GRAPH 'Graph' RETURN e";
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("id1", "No/" + id1);
          params.put("id2", "No/" + id2);
          
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste3").query(queries, params, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
          System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        }
        arangoDB.shutdown();
    }
    
    public static void findFilterArangoDB(String name) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        try {
          String queries = "FOR u in No\n" +
            "    FILTER u.Name == @name\n" +
            "    RETURN u";
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("name", name);
          final long startTime = System.currentTimeMillis();
          arangoDB.db("teste").query(queries, params, BaseDocument.class);
          final long endTime = System.currentTimeMillis();
          System.out.println(endTime - startTime);
        } catch(ArangoDBException e) {
          System.err.println("Failed to create document. " + e.getMessage());
        } finally {
            arangoDB.shutdown();
        }
    }
    
    public static void createDatabaseArangoDBVertexWorkload(List<No> nos, List<String> arestas, int start) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        
        for(int i = start; i < nos.size(); i++) {
            No no = nos.get(i);
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(no.id));
            myObject.addAttribute("Name", no.name);
            myObject.addAttribute("Number", no.number);
            try {
              arangoDB.db("teste2").collection("No").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }
        }
        arangoDB.shutdown();

    }
    
    public static void createDatabaseArangoDBEdgeWorkload(List<No> nos, List<String> arestas, int start) {
        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
            .build();
        
        for(int i = start; i < arestas.size(); i++)
        {
            String[] value_strings = arestas.get(i).split("-");
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(String.valueOf(i+1));
            myObject.addAttribute("_from", "No/" + value_strings[0]);
            myObject.addAttribute("_to", "No/" + value_strings[1]);
            try {
              arangoDB.db("teste2").collection("Relation").insertDocument(myObject);
            } catch(ArangoDBException e) {
              System.err.println("Failed to create document. " + e.getMessage());
            }

        }
        arangoDB.shutdown();

    }
    
    //-----------------------------------------------------------------------------------------
    // -----------------------------------Neoj4------------------------------------------------
    // ----------------------------------------------------------------------------------------
    
    public static void createDatabaseNeo4j(Set<No> nos, Set<String> arestas, int tamanho) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            Iterator<No> value_no = nos.iterator();
            while(value_no.hasNext()) {
                String query = "CREATE (a:No {Id: $id, Name: $name, Number: $number})";
                No no = value_no.next();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", no.id);
                params.put("name", no.name);
                params.put("number", no.number);
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
            }
            Iterator<String> value_relation = arestas.iterator();
            int count = 1;
            while(value_relation.hasNext())
            {
                String[] value_strings = value_relation.next().split("-");
                String query = "MATCH\n" +
                "  (a:No),\n" +
                "  (b:No)\n" +
                "WHERE a.Id = $id1 AND b.Id = $id2\n" +
                "CREATE (a)-[r:Relation {Id: $id3}]->(b)";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id1", Integer.parseInt(value_strings[0]));
                params.put("id2", Integer.parseInt(value_strings[1]));
                params.put("id3", count);
                count++;
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
            }
        }
        finally {
            driver.close();
        }
        

    }
    
    public static void createDatabaseNeo4j(List<No> nos, List<String> arestas, int start) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            for(int i = start; i < nos.size(); i++) {
                String query = "CREATE (a:No {Id: $id, Name: $name, Number: $number})";
                No no = nos.get(i);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", no.id);
                params.put("name", no.name);
                params.put("number", no.number);
                String greeting = session.writeTransaction( tx ->
                {
                        Result result = tx.run( query, params);
                        return "";
                } );
            }
            
            for(int i = start; i < arestas.size(); i++)
            {
                String[] value_strings = arestas.get(i).split("-");
                String query = "MATCH\n" +
                "  (a:No),\n" +
                "  (b:No)\n" +
                "WHERE a.Id = $id1 AND b.Id = $id2\n" +
                "CREATE (a)-[r:Relation {Id: $id3}]->(b)";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id1", Integer.parseInt(value_strings[0]));
                params.put("id2", Integer.parseInt(value_strings[1]));
                params.put("id3", i+1);
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
            }
        }
        finally {
            driver.close();
        }
        

    }
    
    public static void createDatabaseNeo4jOtm(List<No> nos, List<String> arestas, int start) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                session.writeTransaction( tx ->
                {
                    for(int i = start; i < nos.size(); i++) {
                        String query = "CREATE (a:No {Id: $id, Name: $name, Number: $number})";
                        No no = nos.get(i);
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("id", no.id);
                        params.put("name", no.name);
                        params.put("number", no.number);
                        Result result = tx.run( query, params);
                    }
                    return "";
                } );
            
                session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        for(int i =start; i< arestas.size(); i++)
                        {
                            String[] value_strings = arestas.get(i).split("-");
                            String query = "MATCH\n" +
                            "  (a:No),\n" +
                            "  (b:No)\n" +
                            "WHERE a.Id = $id1 AND b.Id = $id2\n" +
                            "CREATE (a)-[r:Relation {Id: $id3}]->(b)";
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("id1", Integer.parseInt(value_strings[0]));
                            params.put("id2", Integer.parseInt(value_strings[1]));
                            params.put("id3", i+1);
                            Result result = tx.run( query, params);
                        }
                        return "";
                    }
                } );
        }
        finally {
            driver.close();
        }
        

    }

    public static void addVertexNeo4j(No no) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "CREATE (a:No {Id: $id, Name: $name, Number: $number})";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", no.id);
            params.put("name", no.name);
            params.put("number", no.number);
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
            
            
        }
        finally {
            driver.close();
        }

    }
    
    public static void addEdgeNeo4j(int id1, int id2, int count) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                String query = "MATCH\n" +
                "  (a:No),\n" +
                "  (b:No)\n" +
                "WHERE a.Id = $id1 AND b.Id = $id2\n" +
                "CREATE (a)-[r:Relation {Id: $id3}]->(b)";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id1", id1);
                params.put("id2", id2);
                params.put("id3", count);
                
                final long startTime = System.currentTimeMillis();
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
                final long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    public static void altVertexNeo4j(No no) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "MATCH (p:No {Id: $id})\n" +
            "SET p.Name = $name\n" +
            "SET p.Number = $number\n" +
            "RETURN p";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", no.id);
            params.put("name", no.name);
            params.put("number", no.number);
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    
    public static void countVertexNeo4j() {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "MATCH (n:No)\n" +
                            "RETURN count(n)";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                { 
                    Result result = tx.run( query);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }
    }
    
    public static void avgNeo4j() {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "MATCH (n:No)\n" +
                            "RETURN AVG(n.Number)";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                { 
                    Result result = tx.run( query);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }
    }
    
    public static void countEdgeNeo4j() {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "MATCH () -[R:Relation] -> ()\n" +
                            "RETURN count(R)";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }
    }
    
    public static void findVertexNeo4j(int id) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            String query = "MATCH (n:No {Id: $id})\n" +
                            "RETURN n";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
//                    List<String> names = new LinkedList<>();
//                    for(Value user :  result.single().values())
//                    {
//                        System.out.println(user.get("Id").asInt());
//                    }
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

        }
        finally {
            driver.close();
        }
    }
    
    
    public static void findEdgeNeo4j(int id1, int id2) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id1", id1);
            params.put("id2", id2);
            String query = "MATCH (:No {Id: $id1}) -[R:Relation]-> (:No {Id: $id2})\n" +
                            "RETURN R";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }
    }
    
    public static void delVertexNeo4j(int id) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            String query = "MATCH (a:No {Id: $id}) DELETE a";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    public static void delEdgeNeo4j(int id1, int id2) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                String query = "MATCH (:No {Id: $id1}) -[R:Relation]-> (:No {Id: $id2})\n" +
                                "DELETE R";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id1", id1);
                params.put("id2", id2);
                
                final long startTime = System.currentTimeMillis();
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
                final long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    public static void AllNodesNeo4j(int id) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                String query = "MATCH (:No {Id: $id}) -[R:Relation]-> (n:No)\n" +
                                "RETURN R, n";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", id);
                
                final long startTime = System.currentTimeMillis();
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
                final long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    public static void ShortestPathNeo4j(int id1, int id2) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                String query = "MATCH (a:No {Id: $id1} ),\n" +
                                "      (b:No {Id: $id2} ),\n" +
                                "      p = shortestPath((a)-[:Relation*]-(b))\n" +
                                "RETURN p";
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id1", id1);
                params.put("id2", id2);
                
                final long startTime = System.currentTimeMillis();
                String greeting = session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        Result result = tx.run( query, params);
                        return "";
                    }
                } );
                final long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }

    }
    
    public static void findFilterNeo4j(String string) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name", string);
            String query = "MATCH (n:No {Name: $name})\n" +
                            "RETURN n";
            
            final long startTime = System.currentTimeMillis();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( query, params);
//                    for(Value user :  result.single().values())
//                    {
//                        System.out.println(user.get("Id").asInt());
//                    }
                    return "";
                }
            } );
            final long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        finally {
            driver.close();
        }
    }
    
    public static void createDatabaseNeo4jWorkloadVertex(List<No> nos, List<String> arestas, int start) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                session.writeTransaction( tx ->
                {
                    for(int i = start; i < nos.size(); i++) {
                        String query = "CREATE (a:No {Id: $id, Name: $name, Number: $number})";
                        No no = nos.get(i);
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("id", no.id);
                        params.put("name", no.name);
                        params.put("number", no.number);
                        Result result = tx.run( query, params);
                    }
                    return "";
                } );
            
        }
        finally {
            driver.close();
        }
        

    }
    
    public static void createDatabaseNeo4jWorkloadEdge(List<No> nos, List<String> arestas, int start) {
        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1234" ) );
        try ( Session session = driver.session() )
        {
                session.writeTransaction( new TransactionWork<String>()
                {
                    @Override
                    public String execute( Transaction tx )
                    {
                        for(int i =start; i< arestas.size(); i++)
                        {
                            String[] value_strings = arestas.get(i).split("-");
                            String query = "MATCH\n" +
                            "  (a:No),\n" +
                            "  (b:No)\n" +
                            "WHERE a.Id = $id1 AND b.Id = $id2\n" +
                            "CREATE (a)-[r:Relation {Id: $id3}]->(b)";
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("id1", Integer.parseInt(value_strings[0]));
                            params.put("id2", Integer.parseInt(value_strings[1]));
                            params.put("id3", i+1);
                            Result result = tx.run( query, params);
                        }
                        return "";
                    }
                } );
            
        }
        finally {
            driver.close();
        }
        

    }
    
// -----------------------------------------------------------------------------------    
// -------------------------------Main------------------------------------------------
// -----------------------------------------------------------------------------------
    
    public static void main(String[] args) throws InterruptedException, IOException {
        
        System.out.println("Rodando...");
        
        List<No> nos;
        nos = new ArrayList<No>();
        int count = 1000000;
        for(int i =0; i< count; i++)
        {
            No novo = new No(i+1);
            nos.add(novo);
        }
        Set<String> arestas;
        arestas = new HashSet<String>();
        List<String> a = new ArrayList<String>();
        Random r = new Random(1);
        int count_inc = 100000;
        
        while(count != arestas.size())
        {
            if(arestas.size()%100000 == 0 && arestas.size() !=0)
            {
                count_inc += 100000;
            }
            int from = r.nextInt(count_inc-1) + 1;
            int to = r.nextInt(count_inc-1) + 1;
            if(from != to)
            {
                if(arestas.contains(from + "-" + to) == false)
                {
                    arestas.add(from + "-" + to);
                    a.add(from + "-" + to);
                }
            }
        }
        int start = 800000;
        
//-------------------Geração iterativa das bases------------------------------
        //createDatabaseNeo4jOtm(nos, a, start);
        //createDatabaseArangoDB(nos, a, start);
        //createDatabaseOrientdb(nos, a, start);
        
//------------------Teste de Workload----------------------------------------
        
//        final long startTime = System.currentTimeMillis();

//        createDatabaseArangoDBVertexWorkload(nos, a, start);
//        createDatabaseArangoDBEdgeWorkload(nos, a, start);

//        createDatabaseNeo4jWorkloadVertex(nos, a, start);
//        createDatabaseNeo4jWorkloadEdge(nos, a, start);

//        createDatabaseOrientdbVertexWorkload(nos, a, start);
//        createDatabaseOrientdbEdgeWorkload(nos, a, start);

//        final long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);

//---------------------------------Operações Paralelas-----------------------
        
//        Random generator = new Random();
//        
//        ArrayList<String> ids = new ArrayList<String>();
//        
//        for(int i = 0; i < 12; i++)
//        {
//            ids.add(nos.get(generator.nextInt(1000000)).name);
//        }
//
//        
//        Thread firstThread = new Thread(() -> {
//            findFilterOrientDB(ids.get(0));
//    });
//        Thread secondThread = new Thread(() -> {
//            findFilterOrientDB(ids.get(1));
//    });
//        Thread thirdThread = new Thread(() -> {
//            findFilterOrientDB(ids.get(2));
//    });
//        Thread thread4 = new Thread(() -> {
//            findFilterOrientDB(ids.get(3));
//    });
//        Thread thread5 = new Thread(() -> {
//            findFilterOrientDB(ids.get(4));
//    });
//        Thread thread6 = new Thread(() -> {
//            findFilterOrientDB(ids.get(5));
//    });
//        
//        Thread thread7 = new Thread(() -> {
//            findFilterOrientDB(ids.get(6));
//    });
//        Thread thread8 = new Thread(() -> {
//            findFilterOrientDB(ids.get(7));
//    });
//        Thread thread9 = new Thread(() -> {
//            findFilterOrientDB(ids.get(8));
//    });
//        Thread thread10 = new Thread(() -> {
//            findFilterOrientDB(ids.get(9));
//    });
//        Thread thread11 = new Thread(() -> {
//            findFilterOrientDB(ids.get(10));
//    });
//        Thread thread12 = new Thread(() -> {
//            findFilterOrientDB(ids.get(11));
//    });
//        Thread thread13 = new Thread(() -> {
//            findFilterOrientDB(ids.get(12));
//    });
        
//        final long startTime = System.currentTimeMillis();
//        firstThread.start();
//        secondThread.start();
//        thirdThread.start();
//        thread4.start();
//        thread5.start();
//        thread6.start();
//        thread7.start();
//        thread8.start();
//        thread9.start();
//        thread10.start();
//        thread11.start();
//        thread12.start();
//        
//        firstThread.join();
//        secondThread.join();
//        thirdThread.join();
//        thread4.join();
//        thread5.join();
//        thread6.join();
//        thread7.join();
//        thread8.join();
//        thread9.join();
//        thread10.join();
//        thread11.join();
//        thread12.join();
//        final long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);

//            final long startTime = System.currentTimeMillis();
//            findFilterNeo4j("9DxPcC0D7Vn3i87oSEOYoqyE9X95DFSuau2");
//            findFilterNeo4j("mXb6Tn0acrA8bGjHPEjMDZ5Z2bXJjZU6yK");
//            findFilterNeo4j("mgRMWyKsImUfhQ27likubdV7UhgJRIOzqL");
//            findFilterNeo4j("7voMYhLaatQqnrJ9xkzhcREOG7pOQqQbYp");
//            findFilterNeo4j("V82PQNd9r9o5JlmSGFhEevRPTEU8ZYa2qs0ZAWTV0yhbelaP");
//            findFilterNeo4j("mJhaAhN89lPoRIyXO8AxDQqVelLFZu047PF");
//            findFilterNeo4j("KpelnvmAVqTxB5oyQhaiG3Hp5ohTss1aWzO3DYOCchqzVlZ");
//            findFilterNeo4j("brurNilW6CsknKSicav8H1C9CDOsi4n");
//            findFilterNeo4j("0Dup6Ny7xf3HxX1KUkaq2F6NQINGdIRMnA");
//            findFilterNeo4j("T17gktHRkIxrC0p22jahvzvm9SWCNe0DjhTjrIo8iMU");
//            findFilterNeo4j("cZKpadkkTmioGuLU5dPXPDiYCBdARtGI0nkYdGcv3QIA");
//            findFilterNeo4j("3y9xYGxy2RUir5TAj4Dlk6jzJW5S8RzN0AoMVETW2V5SJY");
//            final long endTime = System.currentTimeMillis();
//            System.out.println(endTime - startTime);

        
//        createDatabaseOrientdb(nos, arestas, count);
//        createDatabaseArangoDB(nos, arestas, count);
//        createDatabaseNeo4j(nos, arestas, count);

//--------------------------Operações Basicas---------------------------

//        No no = new No(5000101);

//--------------Usado para tirar o custo fixo das operações------------------        
        
//        findVertexNeo4j(1);
//        findVertexArangoDB(1);
//        findVertexOrientdb(1);
        

//        addVertexNeo4j(no);
//        createVertexArangoDB(no);
//        createVertexOrientdb(no);
//        
//        delVertexNeo4j(5000101);
//        delVertexOrientdb(5000101);
//        delVertexArangoDB(5000101);

//--------------------------------------

//        addEdgeNeo4j(1877, 255, 5000800);
//        createEdgeArangoDB(1877, 255, 5000800);
//        createEdgeOrientdb(1877, 255, 5000800);
//        
//        delEdgeNeo4j(1877, 255);
//        delEdgeArangoDB(1877, 255);
//        delEdgeOrientdb(1877, 255);

//---------------------------------------


//        int id = generator.nextInt(1000000);    
//        no.id = nos.get(id).id;
//
//        altVertexNeo4j(no);
//        altVertexArangoDB(no);
//        altVertexOrientdb(no);
//        
//        no.name = nos.get(id).name;
//        no.number = nos.get(id).number;
//        
//        altVertexNeo4j(no);
//        altVertexArangoDB(no);
//        altVertexOrientdb(no);
        
//----------------------------------------

//            avgNeo4j();
//            avgArangoDB();
//            avgOrientdb();

//----------------------------------------

//            int id = generator.nextInt(1000000);

//            findVertexOrientdb(id);
//            findVertexNeo4j(id);
//            findVertexArangoDB(id);
            
//----------------------------------------

//            int num = generator.nextInt(1000000);
//            String[] ids = a.get(num).split("-");
//            int id1 = Integer.parseInt(ids[0]);
//            int id2 = Integer.parseInt(ids[1]);
//
//            findEdgeOrientdb(id1, id2);
//            findEdgeNeo4j(id1, id2);
//            findEdgeArangoDB(id1, id2);

//-----------------------------------------

//            int id = generator.nextInt(1000000);
//            No aux = nos.get(id);
            
//            delVertexNeo4j(aux.id);
//            delVertexArangoDB(aux.id);
//            delVertexOrientdb(aux.id);
//            
//            addVertexNeo4j(aux);
//            createVertexArangoDB(aux);
//            createVertexOrientdb(aux);

//-----------------------------------------

//            int num = generator.nextInt(1000000);
//            String[] ids = a.get(num).split("-");
//            int id1 = Integer.parseInt(ids[0]);
//            int id2 = Integer.parseInt(ids[1]);

//            delEdgeOrientdb(id1, id2);
//            delEdgeNeo4j(id1, id2);
//            delEdgeArangoDB(id1, id2);
//            
//            addEdgeNeo4j(id1, id2, num+1);
//            createEdgeArangoDB(id1, id2, num+1);
//            createEdgeOrientdb(id1, id2, num+1);
            
//-----------------------------------------

//    int id = generator.nextInt(1000000);
//
//        AllNodesOrientDB(id);
//        AllNodesNeo4j(id);
//        allNodesArangoDB(id);
        
//------------------------------------------

//    int id1 = generator.nextInt(1000000);
//    int id2 = generator.nextInt(1000000);

//        System.out.println(id1);
//        System.out.println(id2);  

//        ShortestPathNeo4j(id1, id2);
//        ShortestPathOrientDB(id1, id2);
//        shortestPathArangoDB(id1, id2);
        
//------------------------------------------



//    int id = generator.nextInt(1000000);


//          findFilterNeo4j(nos.get(id).name);
//          findFilterArangoDB(nos.get(id).name);
//          findFilterOrientDB(nos.get(id).name);

//------------------------------------------

        

//        altAllEdgeOrientdb(a, 0, 900000);


        //No no = new No(10000);

        //findVertexNeo4j(50);
        
//        for(int i =0; i< 40000; i++)
//        {
//            int from = r.nextInt(40000);
//            int to = r.nextInt(40000);
//            arestas.add(from + "-" + to);
//            //System.out.println(from + "-" + to);
//            
//        }
        //arestas.add("40000-40000");
        //arestas.add("40000-40000");
        //System.out.println(nos.size());
//        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
//        
//        ODatabaseSession db = orient.open("demodb", "root", "root");
//        String query = "select * from `Countries`";
//        OResultSet rs = db.query(query);
//        rs.stream().forEach(x -> System.out.println( "" + x.getProperty("Name")));
//        rs.close();
//        
//        db.close();
//        
//        orient.close();
        
//        ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("1234")
//            //.serializer(new ArangoJack())
//            .build();
//        
//        try {
//            BaseDocument myDocument = arangoDB.db("_system").collection("frenchCity").getDocument("Lyon", BaseDocument.class);
//            //System.out.println("teste");
//            System.out.println("Key: " + myDocument.getAttribute("geometry"));
//        } catch(ArangoDBException e) {
//            System.out.println("deu ruim");
//        }
//        finally {
//            arangoDB.shutdown();
//        }
    }
}
