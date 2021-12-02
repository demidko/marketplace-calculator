package com.alidi.calculator.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
import lombok.Value;

/**
 * Json-объект с нерассчитанной корзиной который мы получаем на вход извне.
 */
@Value
public class UnpricedCartView {

  /**
   * Массив продуктов (идентификатор и количество для каждого товара)
   */
  @JsonProperty("products")
  List<UnpricedProductView> products;

  /**
   * Выбранный пользователем тип оплаты (в зависимости от него, скорее всего, немного варьируется
   * итоговая цена в выставленном счете)
   */
  @JsonProperty("payment")
  String paymentMethod;

  /**
   * Адрес, если был указан. Если нет, то, по-видимому, не страшно, поскольку заказ, скорее всего,
   * можно забрать в одном из пунктов самовывоза.
   */
  @JsonProperty("addressId")
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  Optional<Long> addressId;
}
