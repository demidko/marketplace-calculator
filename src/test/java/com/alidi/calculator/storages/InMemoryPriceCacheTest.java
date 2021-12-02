package com.alidi.calculator.storages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Тестируем наш кеш
 */
class InMemoryPriceCacheTest {

  /**
   * Наш кеш. Разумеется его можно проинициализировать через @BeforeTestClass, но мы ведь отказались
   * от null, помните?) <p> В реальном проекте, у {@link InMemoryPriceCache} конечно, были бы
   * зависимости, вроде JDBC драйвера, или иного интерфейса BD, и тогда нам пришлось бы писать для
   * них mock-реализацию, а поскольку в тестовом задании мы используем простой Map, то его и
   * передаем. </p>
   */
  private final InMemoryPriceCache cache = new InMemoryPriceCache(new HashMap<>());

  /**
   * Тест фиксирует работоспособность кеша и одновременно является описанием его методов и примером
   * их использования.
   */
  @Test
  void priceShouldBeFoundCorrectly() {
    cache.updatePrice(1, 475.2);
    var foundedPrice = cache.getPrice(1).orElseThrow();
    assertThat(foundedPrice, equalTo(475.2));
  }
}