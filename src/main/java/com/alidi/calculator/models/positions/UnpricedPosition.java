package com.alidi.calculator.models.positions;

import com.alidi.calculator.dto.input.UnpricedProductView;
import com.alidi.calculator.models.products.UnpricedProductType;
import lombok.Value;

/**
 * Позиция в корзине c неизвестной ценой
 */
@Value
public class UnpricedPosition implements Position {

  /**
   * Тип товара
   */
  UnpricedProductType productType;

  /**
   * Количество товаров
   */
  long productsCount;

  public static UnpricedPosition fromView(UnpricedProductView v) {
    var product = new UnpricedProductType(v.getProductId());
    return new UnpricedPosition(product, v.getProductsCount());
  }
}
