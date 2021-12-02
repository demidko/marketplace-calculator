package com.alidi.calculator.models.positions;

import com.alidi.calculator.models.products.ProductType;

/**
 * Абстракция представляет собой одну позицию в корзине, содержит тип товара и количество заказанных
 * экземпляров.
 */
public interface Position {

  /**
   * @return тип товара позиции
   */
  ProductType getProductType();

  /**
   * @return количество товаров в позиции
   */
  long getProductsCount();
}
