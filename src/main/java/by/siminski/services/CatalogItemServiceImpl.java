package by.siminski.services;

import by.siminski.dao.CatalogItemDao;
import by.siminski.model.catalog.CatalogItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogItemServiceImpl implements CatalogItemService {

    @Autowired
    CatalogItemDao catalogItemDao;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "catalogItemsMapCache", allEntries = true),
            @CacheEvict(value = "catalogItemsByGroupCache", allEntries = true),
            @CacheEvict(value = "catalogItemsByNameCache", allEntries = true)
    })
    public void clearCatalog() {
        catalogItemDao.deleteAll();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "catalogItemsMapCache", allEntries = true),
            @CacheEvict(value = "catalogItemsByGroupCache", allEntries = true),
            @CacheEvict(value = "catalogItemsByNameCache", allEntries = true)
    })
    public void addCatalogItem(CatalogItem catalogItem) {
        catalogItemDao.save(catalogItem);
    }

    @Override
    @Cacheable(value = "catalogItemsByNameCache")
    public CatalogItem getCatalogItemByName(String name) {
        return catalogItemDao.findCatalogItemByName(name);
    }

    @Override
    @Cacheable(value = "catalogItemsByGroupCache")
    public List<CatalogItem> getCatalogItemsByGroupName(String groupName) {
        return catalogItemDao.findCatalogItemByGroupName(groupName);
    }

    @Override
    @Cacheable(value = "catalogItemsMapCache")
    public Map<String, List<CatalogItem>> getAllCatalogItemsMap() {
        List<CatalogItem> catalogItems = catalogItemDao.findAll();
        return catalogItems.stream().collect(Collectors.groupingBy(CatalogItem::getGroupName));
    }
}
