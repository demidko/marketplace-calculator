package com.alidi.calculator.dto.output;

import com.alidi.calculator.models.positions.PricedPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Пользовательское представление позиции товара фиксирует имена в итоговом json.
 */
@Value
public class PricedProductView {

  /**
   * Идентификатор товара
   */
  @JsonProperty("id")
  long productId;

  /**
   * Количество заказанных экземпляров
   */
  @JsonProperty("count")
  long productsCount;

  /**
   * Цена позиции целиком
   */
  @JsonProperty("price")
  double positionPrice;

  public static PricedProductView fromPosition(PricedPosition p) {
    var productId = p.getProductType().getId();
    var productsCount = p.getProductsCount();
    var oneProductPrice = p.getProductType().getPrice();
    var positionPrice = oneProductPrice * productsCount;
    return new PricedProductView(productId, productsCount, positionPrice);
  }
}
