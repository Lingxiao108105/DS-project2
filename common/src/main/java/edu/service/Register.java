package edu.service;

import edu.dto.RegisterManagerRequest;
import edu.dto.RegisterManagerResponse;

public interface Register {

    public RegisterManagerResponse registerManager(RegisterManagerRequest request);

}
