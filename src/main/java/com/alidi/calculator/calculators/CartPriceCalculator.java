package com.alidi.calculator.calculators;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.carts.PricedCart;
import com.alidi.calculator.models.carts.UnpricedCart;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.positions.PricedPosition;
import com.alidi.calculator.models.positions.UnpricedPosition;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

/**
 * Калькулятор корзин
 */
@AllArgsConstructor
public class CartPriceCalculator {

  private final PositionPriceCalculator positionPriceCalculator;

  /**
   * Рассчитать корзину
   * @param unpricedCart нерассчитанная корзина
   * @return рассчитанная корзина
   */
  public PricedCart calculateCart(UnpricedCart unpricedCart) {
    var paymentMethod = unpricedCart.getPaymentMethod();
    var unpricedPositions = unpricedCart.getPositions();
    var address = unpricedCart.getAddress();
    var pricedPositions =
        address.map(a -> calculateAllPositions(unpricedPositions, paymentMethod, a))
            .orElseGet(() -> calculateAllPositions(unpricedPositions, paymentMethod));
    return new PricedCart(pricedPositions, paymentMethod, address);
  }

  /**
   * Рассчитать все позиции. Неудачи будут залогированы и отброшены.
   * @param unpricedPositions нерассчитанные позиции
   * @param paymentMethod платежный метод
   * @return позиции которые удалось рассчитать
   */
  private List<PricedPosition> calculateAllPositions(
      List<UnpricedPosition> unpricedPositions,
      PaymentMethod paymentMethod) {
    return unpricedPositions.stream()
        .map(p -> positionPriceCalculator.calculatePosition(p, paymentMethod))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toUnmodifiableList());
  }

  /**
   * Рассчитать все позиции. Неудачи будут залогированы и отброшены.
   * @param unpricedPositions нерассчитанные позиции
   * @param paymentMethod платежный метод
   * @param address адрес доставки
   * @return позиции которые удалось рассчитать
   */
  private List<PricedPosition> calculateAllPositions(
      List<UnpricedPosition> unpricedPositions,
      PaymentMethod paymentMethod,
      Address address) {
    return unpricedPositions.stream()
        .map(p -> positionPriceCalculator.calculatePosition(p, paymentMethod, address))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toUnmodifiableList());
  }
}
