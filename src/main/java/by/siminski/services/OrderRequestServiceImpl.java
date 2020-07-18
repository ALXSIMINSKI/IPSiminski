package by.siminski.services;

import by.siminski.dao.OrderRequestDao;
import by.siminski.model.request.OrderRequest;
import by.siminski.model.request.Request;
import by.siminski.model.request.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OrderRequestServiceImpl implements OrderRequestService {

    @Autowired
    private OrderRequestDao orderRequestDao;

    @Override
    @Cacheable(value = "requestsCache")
    public List<OrderRequest> getAllRequests() {
        return orderRequestDao.findAll();
    }

    @Override
    @CacheEvict(value = "requestsCache", allEntries = true)
    public void registerRequest(Request request) {
        if(request instanceof OrderRequest) {
            ((OrderRequest) request).setStatus(RequestStatus.NEW);
            orderRequestDao.save((OrderRequest) request);
        }
    }

    @Override
    @CacheEvict(value = "requestsCache", allEntries = true)
    public void closeRequest(BigInteger requestIdToClose) {
        OrderRequest orderRequest = orderRequestDao.getOne(requestIdToClose);
        orderRequest.setStatus(RequestStatus.CLOSED);
        orderRequestDao.save(orderRequest);
    }
}
