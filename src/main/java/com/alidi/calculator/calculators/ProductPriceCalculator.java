package com.alidi.calculator.calculators;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.OK;

import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.products.PricedProductType;
import com.alidi.calculator.models.products.UnpricedProductType;
import com.alidi.calculator.storages.InMemoryPriceCache;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * Калькулятор цен для одного продукта. Несет ответственность за учет коэффициентов цены для таких
 * параметров как метод оплаты и адрес доставки (если есть). На данном этапе они фактически
 * опускаются. Мы могли бы возложить ответственность за расчёт прибавки к цене на внешний сервис,
 * однако это сильно усложнит схему кеширования (например, не будем же кешировать цену для
 * конкретного адреса?), и потребует погрузиться в более низкоуровневые детали.
 */
@Slf4j
@AllArgsConstructor
public class ProductPriceCalculator {

  /**
   * Кеш уже известных нам цен, используется в первую очередь.
   */
  private final InMemoryPriceCache priceCache;

  /**
   * URL внешнего сервиса расчёта цены по идентификатору продукта, который шаблонизирован символом
   * %s.
   */
  private final String priceCalculationUrl;


  /**
   * Рассчитать цену одного товара
   *
   * @param product товар с неизвестной ценой
   * @param __      метод оплаты, цена может незначительно варьироваться в зависимости от него.
   * @return Рассчитанный продукт или отсутствующее значение, если рассчитать цену не удалось.
   */
  public Optional<PricedProductType> calculateProduct(UnpricedProductType product,
      PaymentMethod __) {
    var cachedPrice = priceCache.getPrice(product);
    if (cachedPrice.isPresent()) {
      return cachedPrice;
    }
    log.warn("Cache for product id {} wasn't found", product.getId());
    return requestForPricedProduct(product).map(this::updateCache);
  }

  /**
   * Рассчитать цену одного товара
   *
   * @param product товар с неизвестной ценой
   * @param __      метод оплаты, цена может незначительно варьироваться в зависимости от него.
   * @param ___     адрес доставки
   * @return Рассчитанный продукт или отсутствующее значение, если рассчитать цену не удалось.
   */
  public Optional<PricedProductType> calculateProduct(
      UnpricedProductType product,
      PaymentMethod __,
      Address ___) {
    return calculateProduct(product, __);
  }

  /**
   * @param pricedProduct продукт с ценой для сохранения в кеше
   * @return продукт с ценой
   */
  private PricedProductType updateCache(PricedProductType pricedProduct) {
    priceCache.updatePrice(pricedProduct);
    return pricedProduct;
  }

  /**
   * Обратиться за ценой ко внешнему сервису
   *
   * @param product продукт для которого запрашиваем цену
   * @return продукт с ценой или отсутствующее значение, если внешний сервис недоступен или
   * возвращает некорректный ответ
   */
  private Optional<PricedProductType> requestForPricedProduct(UnpricedProductType product) {
    var productId = product.getId();
    return requestForPrice(productId).map(price -> new PricedProductType(productId, price));
  }

  /**
   * Обратиться за ценой ко внешнему сервису
   *
   * @param productId идентификатор продукта для которого необходимо узнать цену
   * @return цена или отсутствующее значение, если сервис недоступен или возвращает некорректный
   * ответ
   */
  private Optional<Double> requestForPrice(long productId) {
    var preparedUrl = format(priceCalculationUrl, productId);
    var restService = new RestTemplate();
    var response = restService.getForEntity(preparedUrl, Double.class);
    if (response.getStatusCode().equals(OK)) {
      return ofNullable(response.getBody());
    }
    log.warn("Failed to calculate the price for the product id {}", productId);
    return empty();
  }
}
