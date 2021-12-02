package com.alidi.calculator.models.payments;

/**
 * Контракт описывает тип оплаты
 */
public interface PaymentMethod {

  /**
   * @return название типа оплаты
   */
  String getName();
}
