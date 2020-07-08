package by.siminski.services;

import by.siminski.model.request.OrderRequest;
import by.siminski.model.request.Request;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderRequestService {

    List<OrderRequest> getAllRequests();

    void registerRequest(Request request);

    void closeRequest(Request request);
}
