package com.example.demo;

import lombok.Data;

import java.util.List;

@Data
public class PersonListResponse {
    List<Person> personList;
}
