package uz.pdp.messaging.service;

import java.util.UUID;

public interface BaseService <T>{
    int add(T t);
    T getById(UUID id);
    boolean deleteById(UUID id);
    boolean updateById(T updateT);
}
