package com.example.demo.db;

import com.example.demo.entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

abstract public class SimpleDataRepository<T extends Entity,ID extends Long> implements DataRepository<T,ID> {

    private List<T> datalist= new ArrayList<>();//자신만의 저장공간

    private static long index=0;

    //데이터 정렬
    private Comparator<T> sort=new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    };

    //create //update
    @Override
    public T save(T data) {

        if(Objects.isNull(data)){
            throw new RuntimeException("Data is null");//데이터가 null이면 null로 리턴
        }

        //db에 데이터가 있는가?
        var prevData=datalist.stream()//이전 데이터 가져오기
                .filter(it->{
                    return it.getId().equals(data.getId());//현재 들어있는 데이터id=가져온 데이터id인 경우
                })
                .findFirst();//가장 먼저 찾기

        if(prevData.isPresent()){
            //기존 데이터 있는 경우
            datalist.remove(prevData);//이전 데이터를 지우기
            datalist.add(data);//그 지운 위치에 다시 저장(업데이트)
        }else{
            //없는 경우
            index++;
            data.setId(index);//들어가고자하는 공간에 집어넣음
            datalist.add(data); //데이터 저장
        }


        //unique id 를 지정해줘야 함
        data.setId(index);
        datalist.add(data);

        index++;
        return null;
    }

    //read
    @Override
    public Optional<T> findById(ID id){//처음에 long타입으로 제한뒀기때문에 id는 long타입
        return datalist.stream()
                .filter(it->{
                    return (it.getId().equals(id));//현재 리스트에 들어있는 id=찾고자 하는 id 한 경우
                })
                .findFirst();//처음것을 찾아 리턴
        //해당 데이터가 있을 수도 있고 없을 수도 있기때문에 optional을 사용
    }

    @Override
    public List<T> findAll(){
        return datalist
                .stream()
                .sorted(sort) //정렬
                .collect(Collectors.toList());//stream을 list로 변환
    }

    //delete
    @Override
    public void delete(ID id){
        //찾고자 하는 Entity 가져오기
        var deleteEntity=datalist.stream()//deleteEntity 데이터 가져오기
                .filter(it->{
                    return (it.getId().equals(id));
                })
                .findFirst();//가장 먼저 찾기

        if(deleteEntity.isPresent()){//데이터 엔티티가 있는경우 삭제
            datalist.remove(deleteEntity);
        }

    }
}
