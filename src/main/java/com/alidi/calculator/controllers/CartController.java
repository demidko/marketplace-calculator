package com.alidi.calculator.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.alidi.calculator.calculators.CartPriceCalculator;
import com.alidi.calculator.dto.input.UnpricedCartView;
import com.alidi.calculator.dto.output.PricedCartView;
import com.alidi.calculator.models.cart.UnpricedCart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Здесь настраиваем маршрутизацию внешних запросов связанных с корзиной. Предполагается простая
 * валидация на уровне типов, уже осуществляемая spring-boot'ом за нас. Входящие dto раскладываются
 * в удобные модели и отправляются для расчетов дальше по этапу в недра бизнес логики.
 */
@Slf4j
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
  public PricedCartView calculatePrice(@RequestBody UnpricedCartView unpricedCartView) {
    log.info("New request: {}", unpricedCartView);
    var unpricedCart = UnpricedCart.fromView(unpricedCartView);
    var pricedCart = cartPriceCalculator.calculateCart(unpricedCart);
    return PricedCartView.fromPricedCart(pricedCart);
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(BAD_REQUEST)
  public void invalidParameterValue(NullPointerException e) {
    log.warn("Invalid parameter value", e);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(BAD_REQUEST)
  public void invalidParameterValue(IllegalArgumentException e) {
    log.warn("Invalid parameter value", e);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public void otherErrors(RuntimeException e) {
    log.error("Request processing error", e);
  }
}