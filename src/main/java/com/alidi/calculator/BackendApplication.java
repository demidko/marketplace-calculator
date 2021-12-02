package com.alidi.calculator;

import static org.springframework.boot.SpringApplication.run;

import com.alidi.calculator.calculators.CartPriceCalculator;
import com.alidi.calculator.calculators.PositionPriceCalculator;
import com.alidi.calculator.calculators.ProductPriceCalculator;
import com.alidi.calculator.controllers.CartController;
import com.alidi.calculator.controllers.PriceController;
import com.alidi.calculator.storages.InMemoryPriceCache;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Здесь опишем инициализацию зависимостей, параметры и переменные среды окружения для нашего
 * приложения.
 */
@SpringBootApplication
public class BackendApplication {

  /**
   * Переменная среды окружения PRICE_CALCULATION_URL, может быть задана извне, по умолчанию -
   * заглушка. Это URL внешнего сервиса расчёта цены по идентификатору товара. Будем считать что это
   * простой endpoint, который позволяет отправить GET запрос с параметрами productId и
   * paymentMethod, и получить в ответе цену числом. Также, условимся, что в заданном URL, productId
   * уже шаблонизирован первым %s.
   */
  @Value("${price.service.url:http://localhost:8080/price?productId=%s}")
  private String priceCalculationUrl;


  /**
   * Контроллер отвечающий за наш http endpoint для общения с внешним миром.
   */
  @Bean
  public CartController cartController() {
    return new CartController(cartPriceCalculator());
  }

  /**
   * Калькулятор корзин из неоцененных в оцененные.
   */
  @Bean
  public CartPriceCalculator cartPriceCalculator() {
    return new CartPriceCalculator(positionPriceCalculator());
  }

  /**
   * Калькулятор позиций в корзине.
   */
  @Bean
  public PositionPriceCalculator positionPriceCalculator() {
    return new PositionPriceCalculator(productPriceCalculator());
  }

  /**
   * Калькулятор цен продуктов, в первую очередь обращается к кешу, во вторую ко внешнему сервису.
   */
  @Bean
  public ProductPriceCalculator productPriceCalculator() {
    return new ProductPriceCalculator(priceCache(), priceCalculationUrl);
  }

  /**
   * Горячий кеш для хранения цен.
   */
  @Bean
  public InMemoryPriceCache priceCache() {
    return new InMemoryPriceCache(priceDatabase());
  }

  /**
   * Наша in-memory db =)
   */
  @Bean
  public Map<Long, Double> priceDatabase() {
    return new HashMap<>();
  }

  /**
   * Имитируем внешний сервис расчёта цен. Обратите внимание, что этот бин изолирован от прочих и не
   * используется ими, следовательно, общаться с ним возможно только по HTTP, как с настоящим
   * внешним сервисом.
   */
  @Bean
  public PriceController priceController() {
    return new PriceController();
  }

  /**
   * Ну, полетели
   *
   * @param args не понадобятся, ибо благодаря тотальной контейнеризации, мы живем в мире победивших
   *             ENV-variables (ими передавать параметры удобнее, из k8s дескрипторов, например).
   */
  public static void main(String[] args) {
    run(BackendApplication.class, args);
  }
}
