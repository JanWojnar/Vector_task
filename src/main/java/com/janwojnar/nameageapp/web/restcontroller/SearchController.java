package com.janwojnar.nameageapp.web.restcontroller;

import com.janwojnar.nameageapp.common.LogPreparer;
import com.janwojnar.nameageapp.core.SearchUseCase;
import com.janwojnar.nameageapp.web.to.ApiAgifyResponse;
import com.janwojnar.nameageapp.web.to.SearchHistoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/rest")
@AllArgsConstructor
@Slf4j
public class SearchController {

    private SearchUseCase searchUseCase;

    @GetMapping("/searchForNameData/{askedName}")
    public ResponseEntity<Object> searchForNameData(@PathVariable String askedName) throws IOException,
            InterruptedException {

        log.debug(LogPreparer.prepareLog("Incoming REST request on method 'searchForNameData' with variable: name=", askedName));

        ApiAgifyResponse apiAgifyResponse = this.searchUseCase.searchForNameData(askedName);
        return ResponseEntity.ok(apiAgifyResponse);
    }

    @GetMapping("/searchHistory/sorted/{sorted}")
    public ResponseEntity<Object> getSearchHistory(@PathVariable Boolean sorted, @RequestParam(value = "sortTyp",
            required = false) Character typ) {

        log.debug(LogPreparer.prepareLog("Incoming REST request on method 'getSearchHistory' with variable: sorted=",
                sorted.toString(), ", params: sortTyp=", typ));

        SearchHistoryResponse searchHistory = this.searchUseCase.getSearchHistory(sorted, typ);
        return ResponseEntity.ok(searchHistory);
    }
}
