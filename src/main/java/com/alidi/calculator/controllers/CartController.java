package com.alidi.calculator.controllers;

import com.alidi.calculator.calculators.CartPriceCalculator;
import com.alidi.calculator.dto.input.UnpricedCartView;
import com.alidi.calculator.dto.output.PricedCartView;
import com.alidi.calculator.models.cart.UnpricedCart;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Здесь настраиваем маршрутизацию внешних запросов связанных с корзиной. Предполагается простая
 * валидация на уровне типов, уже осуществляемая spring-boot'ом за нас. Входящие dto раскладываются
 * в удобные модели и отправляются для расчетов дальше по этапу в недра бизнес логики.
 */
@RestController
@AllArgsConstructor
public class CartController {

  /**
   * Калькулятор используется для всех расчётов.
   */
  private final CartPriceCalculator cartPriceCalculator;


  /**
   * Наш ключевой endpoint. Принимает нерассчитанную корзину, возвращает рассчитанную. Используем
   * POST, чтобы принять json объект. Конечно, с одной стороны, кто-то скажет что это не совсем уж
   * канонично (надо отдельно использовать POST, и отдельно получать GET), но мне по душе больше
   * простота функционального решения. К тому же, так мы сведем обмен данных по сети к минимуму, что
   * хорошо повлияет на связность нашей инфраструктуры и в некоторых случаях значительно ускорит
   * работу сервисов.
   */
  @PostMapping("/calculate-cart-price")
  public PricedCartView calculatePrice(UnpricedCartView unpricedCartView) {
    var unpricedCart = UnpricedCart.fromView(unpricedCartView);
    var pricedCart = cartPriceCalculator.calculateCart(unpricedCart);
    return PricedCartView.fromPricedCart(pricedCart);
  }
}