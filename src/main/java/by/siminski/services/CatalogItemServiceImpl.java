package by.siminski.services;

import by.siminski.dao.CatalogItemDao;
import by.siminski.model.catalog.CatalogItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
