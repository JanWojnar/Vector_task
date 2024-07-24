package com.janwojnar.nameageapp.communication.adapter;

import com.janwojnar.nameageapp.communication.to.ApiAgifyResponse;

/**
 * Adapter service to make requests to Agify API.
 */
public interface ApiAgifyAdapter {

    /**
     * Requests Agify API about given name.
     *
     * @param name given name.
     * @return Response containing estimation of name and person who bears this name.
     */
    ApiAgifyResponse getNameData(String name);
}
