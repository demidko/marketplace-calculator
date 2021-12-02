package com.alidi.calculator.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Вложенный json содержащий сведения о товаре, который мы получаем на вход извне в составе {@link
 * UnpricedCartView}.
 */
@Value
public class UnpricedProductView {

  /**
   * Идентификатор продукта
   */
  @JsonProperty("id")
  long productId;

  /**
   * Количество заказанных экземпляров продукта
   */
  @JsonProperty("count")
  long productsCount;
}
