package com.alidi.calculator.models.addresses;

/**
 * Некий известный нам ранее адрес
 */
public interface Address {

  /**
   * @return Идентификатор адреса в базе данных может быть использован для получения дополнительных
   * сведений о нем в других сервисах нашей инфраструктуры
   */
  long getId();
}
