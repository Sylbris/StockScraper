package io.tracker.webscraper.services;

import io.tracker.webscraper.models.StockStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockDataService {

    private static String STOCK_DATA_URL = "https://raw.githubusercontent.com/marcopeix/stock-prediction/master/data/stock_prices_sample.csv";
    //Aggregation
    private List<StockStats> allStats = new ArrayList<>();


    public List<StockStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchStockData() throws IOException, InterruptedException {
        //Create new list populate it, and then populate the main list, for concurrency.
        List<StockStats> newStats = new ArrayList<>();
        // Create a http client.
        HttpClient client = HttpClient.newHttpClient();

        //Create a request using the builder pattern.
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(STOCK_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        StringReader stringReader = new StringReader(httpResponse.body());

        //Configure CSV formatter and Parser.
        CSVFormat format = CSVFormat.DEFAULT.builder().setDelimiter(',')
                .setHeader()
                .setSkipHeaderRecord(true).build();  // skip header

        CSVParser parser = CSVParser.parse(stringReader, format);
        List<CSVRecord> records = parser.getRecords();

        for (CSVRecord record : records) {
            StockStats stockStat = new StockStats();
            //Construct stockStats
            stockStat.setTicker(record.get(0));
            stockStat.setOpen(record.get(5));
            stockStat.setClose(record.get(8));
            stockStat.setDate(LocalDate.parse(record.get(2)));

            newStats.add(stockStat);
        }
        this.allStats = newStats;
    }
}
