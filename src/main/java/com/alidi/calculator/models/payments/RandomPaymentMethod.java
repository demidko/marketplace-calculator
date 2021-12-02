package com.alidi.calculator.models.payments;

import lombok.Value;

/**
 * Произвольный метод оплаты может быть с любым именем
 */
@Value
public class RandomPaymentMethod implements PaymentMethod {

  String name;
}
