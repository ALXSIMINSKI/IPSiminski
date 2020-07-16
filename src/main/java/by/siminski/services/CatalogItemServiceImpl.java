package by.siminski.services;

import by.siminski.dao.CatalogItemDao;
import by.siminski.model.catalog.CatalogItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CatalogItemServiceImpl implements CatalogItemService {

    @Autowired
    CatalogItemDao catalogItemDao;

    @Override
    public void addCatalogItem(CatalogItem catalogItem) {
        catalogItemDao.save(catalogItem);
    }

    @Override
    public CatalogItem getCatalogItemByName(String name) {
        return catalogItemDao.findCatalogItemByName(name);
    }

    @Override
    public List<CatalogItem> getCatalogItemsByGroupName(String groupName) {
        return catalogItemDao.findCatalogItemByGroupName(groupName);
    }

    @Override
    public Map<String, List<CatalogItem>> getAllCatalogItemsMap() {
        List<CatalogItem> catalogItems = catalogItemDao.findAll();
        return catalogItems.stream().collect(Collectors.groupingBy(CatalogItem::getGroupName));
    }
}
