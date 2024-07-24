package com.janwojnar.nameageapp.persistance.dao;

import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.persistance.entity.SearchHistoryEty;

import java.io.IOException;
import java.util.Set;

/**
 * Interface of Data Access Object.
 */
public interface SearchHistoryDao {

    /**
     * Adds record to database.
     *
     * @param apiAgifyResponse record to be saved.
     */
    void addRecord(ApiAgifyResponse apiAgifyResponse) throws IOException;

    /**
     * Reads records from database.
     *
     * @return set of records.
     */
    Set<SearchHistoryEty> read() throws InterruptedException, IOException;
}
