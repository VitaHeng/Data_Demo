package com.vitaheng.mapper;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.vitaheng.pojo.Recommend;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

public class MovieRecDao {
    private MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Recommend> getRateMoreMoviesRecom(int number) {
//        MongoCollection<Document> rateMoreMoviesCollection = mongoClient.getDatabase("recommend").getCollection("ratingMoreMovies");

        MongoCollection<Document> rateMoreMoviesCollection = mongoTemplate.getCollection("ratingMoreMovies");
        FindIterable<Document> rateMoreMoviesResult = rateMoreMoviesCollection.find().sort(Sorts.descending("cnts")).limit(number);
        ArrayList<Recommend> recommends = new ArrayList<>();
        for (Document document : rateMoreMoviesResult) {
            recommends.add(new Recommend(document.getInteger("mid"),0D));
        }
        return recommends;
    }


}
