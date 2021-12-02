package com.alidi.calculator.models.addresses;

import lombok.Value;

/**
 * Адрес с произвольно заданным идентификатором
 */
@Value
public class RandomAddress implements Address {

  long id;
}
