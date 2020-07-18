package by.siminski.services;

import by.siminski.model.catalog.CatalogItem;

import java.util.List;
import java.util.Map;

public interface CatalogItemService {

    void clearCatalog();

    void addCatalogItem(CatalogItem catalogItem);

    CatalogItem getCatalogItemByName(String name);

    List<CatalogItem> getCatalogItemsByGroupName(String groupName);

    Map<String, List<CatalogItem>>  getAllCatalogItemsMap();
}
