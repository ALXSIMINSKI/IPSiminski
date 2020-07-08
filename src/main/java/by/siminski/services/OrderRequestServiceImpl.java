package by.siminski.services;

import by.siminski.dao.OrderRequestDao;
import by.siminski.model.request.OrderRequest;
import by.siminski.model.request.Request;
import by.siminski.model.request.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderRequestServiceImpl implements OrderRequestService{

    @Autowired
    private OrderRequestDao orderRequestDao;

    @Override
    public List<OrderRequest> getAllRequests() {
        return orderRequestDao.findAll();
    }

    @Override
    public void registerRequest(Request request) {
        if(request instanceof OrderRequest) {
            ((OrderRequest) request).setStatus(RequestStatus.NEW);
            orderRequestDao.save((OrderRequest) request);
        }
    }
}
