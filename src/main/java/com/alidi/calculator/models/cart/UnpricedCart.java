package com.alidi.calculator.models.cart;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.alidi.calculator.dto.input.UnpricedCartView;
import com.alidi.calculator.models.addresses.Address;
import com.alidi.calculator.models.addresses.RandomAddress;
import com.alidi.calculator.models.payments.PaymentMethod;
import com.alidi.calculator.models.payments.RandomPaymentMethod;
import com.alidi.calculator.models.positions.UnpricedPosition;
import java.util.List;
import java.util.Optional;
import lombok.Value;

/**
 * Модель описывает входящие сведения о нерассчитанной корзине.
 */
@Value
public class UnpricedCart implements Cart {

  /**
   * Нерассчитанные позиции
   */
  List<UnpricedPosition> positions;

  /**
   * Выбранный пользователем метод оплаты может быть любым
   */
  PaymentMethod paymentMethod;

  /**
   * Адрес доставки может быть любым
   */
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  Optional<Address> address;

  public static UnpricedCart fromView(UnpricedCartView v) {
    var address = v.getAddressId().map(a -> (Address) new RandomAddress(a));
    var paymentMethod = new RandomPaymentMethod(v.getPaymentMethod());
    var positions =
        v.getProducts().stream()
            .map(UnpricedPosition::fromView)
            .collect(toUnmodifiableList());
    return new UnpricedCart(positions, paymentMethod, address);
  }
}
