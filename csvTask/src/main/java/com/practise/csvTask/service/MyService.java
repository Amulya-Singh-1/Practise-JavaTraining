package com.practise.csvTask.service;

import com.practise.csvTask.entity.CsvData;

import java.util.List;

public interface MyService {

    List<CsvData> getByDeliveryAddressTypeCode(String filepath, String value) throws Exception;

}
