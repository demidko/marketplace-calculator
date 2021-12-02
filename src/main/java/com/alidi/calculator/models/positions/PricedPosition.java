package com.alidi.calculator.models.positions;

import com.alidi.calculator.dto.input.UnpricedProductView;
import com.alidi.calculator.models.products.PricedProductType;
import com.alidi.calculator.models.products.UnpricedProductType;
import lombok.Value;

/**
 * Позиция с известной ценой товара
 */
@Value
public class PricedPosition implements Position {

  /**
   * Тип товара в позиции
   */
  PricedProductType productType;

  /**
   * Количество товаров
   */
  long productsCount;
}
