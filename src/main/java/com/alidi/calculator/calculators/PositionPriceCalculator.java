package com.alidi.calculator.calculators;

import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.positions.PricedPosition;
import com.alidi.calculator.models.positions.UnpricedPosition;
import java.util.Optional;
import lombok.AllArgsConstructor;

/**
 * Калькулятор позиций
 */
@AllArgsConstructor
public class PositionPriceCalculator {

  private final ProductPriceCalculator productPriceCalculator;

  /**
   * Рассчитать позицию. Неудача будет залогирована.
   *
   * @param unpricedPosition нерассчитанная позиция
   * @param paymentMethod    платежный метод
   * @return рассчитанная позиция или отсутствующее значение в случае неудачи.
   */
  public Optional<PricedPosition> calculatePosition(
      UnpricedPosition unpricedPosition,
      PaymentMethod paymentMethod) {
    var product = unpricedPosition.getProductType();
    var count = unpricedPosition.getProductsCount();
    var pricedProduct = productPriceCalculator.calculateProduct(product, paymentMethod);
    return pricedProduct.map(p -> new PricedPosition(p, count));
  }

  /**
   * Рассчитать позицию. Неудача будет залогирована.
   *
   * @param unpricedPosition нерассчитанная позиция
   * @param paymentMethod    платежный метод
   * @param address          адрес доставки
   * @return рассчитанная позиция или отсутствующее значение в случае неудачи.
   */
  public Optional<PricedPosition> calculatePosition(
      UnpricedPosition unpricedPosition,
      PaymentMethod paymentMethod,
      Address address) {
    var product = unpricedPosition.getProductType();
    var count = unpricedPosition.getProductsCount();
    var pricedProduct = productPriceCalculator.calculateProduct(product, paymentMethod, address);
    return pricedProduct.map(p -> new PricedPosition(p, count));
  }
}
