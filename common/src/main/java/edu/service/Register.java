package edu.service;

import edu.dto.RegisterClientRequest;
import edu.dto.RegisterClientResponse;
import edu.dto.RegisterManagerRequest;
import edu.dto.RegisterManagerResponse;

/**
 * service for client to register
 * @author lingxiao
 */
public interface Register {

    public RegisterManagerResponse registerManager(RegisterManagerRequest request);

    public RegisterClientResponse registerClient(RegisterClientRequest request);

}
