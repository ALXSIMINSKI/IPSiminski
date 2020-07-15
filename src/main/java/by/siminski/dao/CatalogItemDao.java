package by.siminski.dao;

import by.siminski.model.catalog.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface CatalogItemDao extends JpaRepository<CatalogItem, BigInteger> {

    CatalogItem findCatalogItemByName(String name);

    List<CatalogItem> findCatalogItemByGroupName(String group);
}
