package com.example.progettolso.ui.drinks;

import android.util.Log;

import com.example.progettolso.model.CategoryDomain;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;

public class DaoCategoryDatabase implements DaoCategory {

    String uri = "bolt://172.20.10.2:7687";
    String username = "neo4j";
    String password = "password";
    Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));

    public ArrayList<CategoryDomain> getDrinkCategory() {

        ArrayList<CategoryDomain> categoryDomains = new ArrayList<>();

        try (Session session = driver.session()) {

            Result result = session.run("MATCH (t:Tipologia) RETURN t.tipo AS tipo, t.pic AS pic");

            System.out.println(result);

            while (result.hasNext()) {

                Record record = result.next();

                String title = (record.get("tipo").asString());
                String pic = (record.get("pic").asString());

                categoryDomains.add(new CategoryDomain(title, pic));
            }

            System.out.println(categoryDomains);

        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("Error prelievo ", "Impossibile prelevare dati");
            return null;
        }
        return categoryDomains;
    }
}
