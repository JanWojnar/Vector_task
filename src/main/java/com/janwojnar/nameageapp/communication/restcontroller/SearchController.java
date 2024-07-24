package com.janwojnar.nameageapp.communication.restcontroller;

import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.communication.to.SearchHistoryResponse;
import com.janwojnar.nameageapp.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller.
 */
@RestController
@RequestMapping("/rest")
@AllArgsConstructor
@Slf4j
public class SearchController {

    private SearchService searchService;

    /**
     * Endpoint for searching in Agify API for data abut name.
     *
     * @param name given name.
     * @return Response with name information or status of request when problem occurred.
     */
    @GetMapping("/searchForNameData/{name}")
    public ResponseEntity<Object> searchForNameData(@PathVariable String name) {

        log.debug(LogPreparer.prepareLog("Incoming REST request on method 'searchForNameData' with variable: name=",
                name));

        ApiAgifyResponse apiAgifyResponse = this.searchService.searchForNameData(name);
        return ResponseEntity.ok(apiAgifyResponse);
    }

    /**
     * Endpoint to return search history.
     *
     * @param sorted true or false whether user wants to sort search history.
     * @param typ    optional parameter containing char which means age or name sort type.
     * @return Response with search history or status of request when problem occurred.
     */
    @GetMapping("/searchHistory/sorted/{sorted}")
    public ResponseEntity<Object> getSearchHistory(@PathVariable Boolean sorted, @RequestParam(value = "sortTyp",
            required = false) Character typ) {

        log.debug(LogPreparer.prepareLog("Incoming REST request on method 'getSearchHistory' with variable: sorted=",
                sorted.toString(), ", params: sortTyp=", typ));

        SearchHistoryResponse searchHistory = this.searchService.getSearchHistory(sorted, typ);
        return ResponseEntity.ok(searchHistory);
    }
}
