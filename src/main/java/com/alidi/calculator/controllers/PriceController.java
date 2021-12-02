package com.alidi.calculator.controllers;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер имитирующий внешний сервис расчёта цены по id. Мы "по-честному" притворяемся что
 * ничего не знаем о нем, и обращаемся к нему исключительно по переданному в параметрах http-адресу.
 * По-настоящему, надо бы сделать отдельный maven модуль, со своими отдельными артефактами и
 * Dockerfile, чуточку усложнить CI...  Но ради заглушки этого делать конечно не стоит =)
 */
@RestController
public class PriceController {

  /**
   * Метод расчёта цены по идентификатору товара
   *
   * @param productId идентификатор товара
   * @return итоговая цена товара
   */
  @GetMapping("/price")
  public double calculatePrice(@RequestParam("productId") long productId) {
    var minKnownPrice = 5_000;
    var maxKnownPrice = 5_000_000;
    return ThreadLocalRandom.current().nextDouble(minKnownPrice, maxKnownPrice);
  }
}
