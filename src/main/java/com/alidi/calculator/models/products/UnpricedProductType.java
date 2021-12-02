package com.alidi.calculator.models.products;

import lombok.Value;

/**
 * Товар с неизвестной ценой
 */
@Value
public class UnpricedProductType implements ProductType {

  /**
   * Идентификатор типа товара
   */
  long id;
}
