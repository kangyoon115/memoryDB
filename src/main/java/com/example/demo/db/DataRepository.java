package com.example.demo.db;

import java.util.List;
import java.util.Optional;
//CRUD 저장소에 대한 인터페이스
public interface DataRepository<T,ID> extends Repository<T,ID>{

    //create,update
    //ID를 찾아보고 기존 데이터가 있으면 update 없으면 save(create)
    T save(T data);

    //read
    //데이터저장소에 저장된 데이터의 번호를 원하면 해당 데이터를 꺼내줌
    //but해당 저장소에 데이터가 없다면 이때는 optional로 리턴
    Optional<T> findById(ID id);

    List<T> findAll();

    //delete
    void delete(ID id);




}
