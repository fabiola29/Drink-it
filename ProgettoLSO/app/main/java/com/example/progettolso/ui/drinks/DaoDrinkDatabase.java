package com.example.progettolso.ui.drinks;

import android.util.Log;

import com.example.progettolso.model.DrinkDomain;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;

public class DaoDrinkDatabase implements DaoDrink {

    String uri = "bolt://172.20.10.2:7687";
    String username = "neo4j";
    String password = "password";
    Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));

    public ArrayList<DrinkDomain> getDrinkCocktail() {

        ArrayList<DrinkDomain> drinkDomains = new ArrayList<>();

        try (Session session = driver.session()) {

            Result result = session.run("MATCH (d:drink)-[rel:DI_TIPO]->(t:Tipologia {tipo:\"Cocktail\"}) RETURN d.name AS name, d.descriptor AS descriptor, d.fee AS fee, d.pic AS pic");

            System.out.println(result);

            while (result.hasNext()) {

                Record record = result.next();

                String title = (record.get("name").asString());
                String descriptor = (record.get("descriptor").asString());
                Double fee = (record.get("fee").asDouble());
                String pic = (record.get("pic").asString());

                drinkDomains.add(new DrinkDomain(title, descriptor, fee, pic));
            }

            System.out.println(drinkDomains);

        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("Error prelievo ", "Impossibile prelevare dati");
            return null;
        }
        return drinkDomains;
    }

    public ArrayList<DrinkDomain> getDrinkShake() {

        ArrayList<DrinkDomain> drinkDomains = new ArrayList<>();

        try (Session session = driver.session()) {

            Result result = session.run("MATCH (d:drink)-[rel:DI_TIPO]->(t:Tipologia {tipo:\"Shake\"}) RETURN d.name AS name, d.descriptor AS descriptor, d.fee AS fee, d.pic AS pic");

            System.out.println(result);

            while (result.hasNext()) {

                Record record = result.next();

                String title = (record.get("name").asString());
                String descriptor = (record.get("descriptor").asString());
                Double fee = (record.get("fee").asDouble());
                String pic = (record.get("pic").asString());

                drinkDomains.add(new DrinkDomain(title, descriptor, fee, pic));
            }

            System.out.println(drinkDomains);

        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("Error prelievo ", "Impossibile prelevare dati");
            return null;
        }
        return drinkDomains;
    }
}
