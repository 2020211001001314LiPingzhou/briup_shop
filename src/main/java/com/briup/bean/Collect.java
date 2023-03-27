package com.briup.bean;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class Collect {
    private Long id;
    private User user;
    private Shop shop;
}
