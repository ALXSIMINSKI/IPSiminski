package by.siminski.dao;

import by.siminski.model.request.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface OrderRequestDao extends JpaRepository<OrderRequest, BigInteger> {
    List<OrderRequest> findAll();
}
