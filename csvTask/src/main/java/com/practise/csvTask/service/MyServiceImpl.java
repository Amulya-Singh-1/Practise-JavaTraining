package com.practise.csvTask.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.practise.csvTask.entity.CsvData;
//import com.practise.csvTask.repository.MyRepository;
import com.practise.csvTask.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyServiceImpl implements MyService {


    public List<String[]> readCsv() throws IOException, CsvException {
        List<String[]> csvContent;
        String filepath="C:\\Users\\amulya-ak\\Downloads\\gaso_scenario_v2_-final-version (1).csv";
        try(CSVReader csvReader=new CSVReader(new FileReader(filepath))){
            csvContent=csvReader.readAll();
             return csvContent;
        }
    }

    public List<CsvData> getByDeliveryAddressTypeCode(String filepath, String value) throws Exception {
        List<CsvData> result=new ArrayList<>();
        try(CSVReader csvReader=new CSVReader(new FileReader(filepath))){
            String[] nextRecord;
            while((nextRecord=csvReader.readNext())!=null){
//                if(nextRecord.length>=2 && nextRecord[1].equals("DeliveryAddressTypeCode") ){
                  if(nextRecord[1].equals( value )){
                    CsvData csvData=new CsvData();
                    csvData.setDeliveryAddressType(nextRecord[0]);
                    csvData.setDeliveryAddressTypeCode(nextRecord[1]);
                    csvData.setIdentMethod(nextRecord[2]);
                    csvData.setStandardDeliveryIndicator(nextRecord[3]);
                    csvData.setCarrierID(nextRecord[4]);
                    csvData.setCarrierName(nextRecord[5]);
                    csvData.setCurrency(nextRecord[6]);
                    csvData.setDeliveryServiceDescription(nextRecord[7]);
                    csvData.setDeliveryServiceID(nextRecord[8]);
                    csvData.setShippingCondition(nextRecord[9]);
                    csvData.setShippingConditionDescription(nextRecord[10]);
                    csvData.setShippingGrossFee(nextRecord[11]);
                    csvData.setShippingGrossFeeFormat(nextRecord[12]);
                    csvData.setShippingNetFee(nextRecord[13]);
                    csvData.setShippingNetFeeFormat(nextRecord[14]);
                    result.add(csvData);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(result.size()==0){
            throw new ItemNotFoundException("objects with value as " + value + " for DeliveyAddressTypeCode are not present in this csv file.");
        }

        return result;
    }
}
