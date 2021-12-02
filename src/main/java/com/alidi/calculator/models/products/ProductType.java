package com.alidi.calculator.models.products;

/**
 * Контракт описывающий абстрактный тип товара в вакууме
 */
public interface ProductType {

  /**
   * @return идентификатор типа товара может быть использован для расчёта цены или иных сведений из
   * базы данных.
   */
  long getId();
}
