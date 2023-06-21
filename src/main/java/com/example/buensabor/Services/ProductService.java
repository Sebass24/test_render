package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Product;
import com.example.buensabor.Models.FixedEntities.ProductCategory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product,Long> {
    //Agregamos todos los metodos que solo pertenecen a User, lo agregamos a la interfaz para mantener el orden

    /**
     * Filtra los productos por categoria.
     *
     * @param category string con el nombre de la categoria a filtrar
     * @return Devuelve una lista de productos filtrado por categoria.
     */
    public List<Product> getByCategory(String category);

    public List<Product> getByName(String name);
    public Map<String,Integer> getProductRanking(String category);
    public List<Product> getBestSellersFromToByCategory(int top, ProductCategory category, Date from, Date to);
    public void updatePrices();
    public List<Product> getRandom(int quantity);
    public List<Object> getTopProducts(String category, Date startDate, Date endDate);

}
