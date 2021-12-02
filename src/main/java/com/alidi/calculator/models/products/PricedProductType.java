package com.alidi.calculator.models.products;

import lombok.Value;

/**
 * Модель описывает тип товара с известной ценой
 */
@Value
public class PricedProductType implements ProductType {

  /**
   * Идентификатор товара
   */
  long id;

  /**
   * Известная цена товара
   */
  double price;
}
