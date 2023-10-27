package com.practise.csvTask.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.practise.csvTask.entity.CsvData;
import com.practise.csvTask.service.MyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class MyController {

    @Autowired private MyServiceImpl myService;

    @GetMapping("/getbyaddcode/{value}")
    public List<CsvData> getByAddressTypeCode(@PathVariable String value) throws Exception {
            return myService.getByDeliveryAddressTypeCode("C:\\Users\\amulya-ak\\Downloads\\gaso_scenario_v2_-final-version (1).csv", value );
    }
}
