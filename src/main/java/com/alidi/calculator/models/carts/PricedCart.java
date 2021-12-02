package com.alidi.calculator.models.carts;

import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.positions.PricedPosition;
import java.util.List;
import java.util.Optional;
import lombok.Value;

/**
 * Модель описывает рассчитанную корзину
 */
@Value
public class PricedCart implements Cart<PricedPosition> {

  /**
   * Рассчитанные позиции по цене
   */
  List<PricedPosition> positions;

  /**
   * Выбранный пользователем метод оплаты
   */
  PaymentMethod paymentMethod;

  /**
   * Адрес доставки
   */
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  Optional<Address> address;
}
