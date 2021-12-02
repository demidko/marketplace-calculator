package com.alidi.calculator.dto.output;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.alidi.calculator.models.cart.PricedCart;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Value;

/**
 * DTO описывающий рассчитанную корзину фиксируют имена в json для пользовательского представления.
 */
@Value
public class PricedCartView {

  /**
   * Массив заказанных товаров
   */
  @JsonProperty("products")
  List<PricedProductView> products;

  /**
   * Итоговая цена за всю корзину
   */
  @JsonProperty("price")
  double totalPrice;

  public static PricedCartView fromPricedCart(PricedCart cart) {
    var products =
        cart.getPositions().stream()
            .map(PricedProductView::fromPosition)
            .collect(toUnmodifiableList());
    var totalPrice = products.stream().mapToDouble(PricedProductView::getPositionPrice).sum();
    return new PricedCartView(products, totalPrice);
  }
}
