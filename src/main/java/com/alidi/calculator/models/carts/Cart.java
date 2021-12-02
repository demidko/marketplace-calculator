package com.alidi.calculator.models.carts;

import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.positions.Position;
import java.util.List;
import java.util.Optional;

/**
 * Базовый контракт подходит для любой корзины.
 */
public interface Cart<T extends Position> {

  /**
   * @return список позиций в корзине с типом товара и количеством заказанных экземпляров.
   */
  List<T> getPositions();

  /**
   * @return выбранный пользователем метод оплаты.
   */
  PaymentMethod getPaymentMethod();

  /**
   * @return выбранный пользователем адрес доставки товаров.
   */
  Optional<Address> getAddress();
}
