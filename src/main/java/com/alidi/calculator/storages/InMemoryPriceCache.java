package com.alidi.calculator.storages;

import static java.util.Optional.ofNullable;

import com.alidi.calculator.models.products.PricedProductType;
import com.alidi.calculator.models.products.ProductType;
import com.alidi.calculator.models.products.UnpricedProductType;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;

/**
 * Абстракция описывает горячий кеш для цены по id. Изменяема. Самый быстрый кеш, это in-memory кеш,
 * будем считать этот класс оберткой над BDB или над аналогичным решением. Из внешних БД хорошим
 * вариантом для горячего кеша также был бы Redis. Подобная сущность в доменной области у нас
 * предвидится всего одна, выносить контракт во внешний интерфейс прежде времени не имеет смысла.
 * Обратите внимание, здесь намерено используются примитивы, с зачином под бущее быстрое хранение с
 * оптимизациями для большого количества товаров.
 */
@AllArgsConstructor
public class InMemoryPriceCache {

  /**
   * Наша in-memory база данных =)
   */
  private final Map<Long, Double> db;

  /**
   * Метод для поиска цены товара в горячем кеше
   *
   * @param id идентификатор товара
   * @return цена товара
   */
  public Optional<Double> getPrice(long id) {
    var foundedPrice = db.get(id);
    return ofNullable(foundedPrice);
  }

  /**
   * Метод для поиска цены товара в горячем кеше
   *
   * @param product продукт для которого мы не знаем цену
   * @return продукт с ценой
   */
  public Optional<PricedProductType> getPrice(UnpricedProductType product) {
    var productId = product.getId();
    return getPrice(productId).map(foundedPrice -> new PricedProductType(productId, foundedPrice));
  }

  /**
   * Обновить цену товара в кеше
   *
   * @param id       идентификатор товара
   * @param newPrice цена товара
   */
  public void updatePrice(long id, double newPrice) {
    db.put(id, newPrice);
  }

  /**
   * Обновить цену товара в кеше
   *
   * @param product  тип продукта
   * @param newPrice новая цена
   */
  public void updatePrice(ProductType product, double newPrice) {
    updatePrice(product.getId(), newPrice);
  }

  /**
   * Обновить цену товара в кеше
   *
   * @param product продукт с ценой
   */
  public void updatePrice(PricedProductType product) {
    updatePrice(product, product.getPrice());
  }
}
