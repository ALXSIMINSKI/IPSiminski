package by.siminski.services;

import by.siminski.model.catalog.CatalogItem;

import java.util.List;

public interface CatalogItemService {

    void addCatalogItem(CatalogItem catalogItem);

    CatalogItem getCatalogItemByName(String name);

    List<CatalogItem> getCatalogItemsByGroupName(String groupName);
}
