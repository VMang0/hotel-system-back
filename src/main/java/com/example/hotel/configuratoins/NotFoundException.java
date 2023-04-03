package com.example.hotel.configuratoins;

public class NotFoundException extends  RuntimeException{
    public NotFoundException(Long id){
        super("Could not found the user with id " + id);
    }

    public NotFoundException(String str){
        super(str);
    }
}
