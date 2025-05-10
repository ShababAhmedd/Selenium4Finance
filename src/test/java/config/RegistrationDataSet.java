package config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDataSet {

    @DataProvider(name = "LoginCSVData")
    public Object[][] getCSVData() throws IOException {
        String csvFilePath="./src/test/resources/users.csv";
        List<Object[]> data=new ArrayList<>();
        CSVParser csvParser= new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        for(CSVRecord csvRecord: csvParser){
            String firstName = csvRecord.get("firstName");
            String email=csvRecord.get("email");
            String password=csvRecord.get("password");
            String phoneNumber=csvRecord.get("phoneNumber");
            data.add(new Object[]{firstName,email,password,phoneNumber});
        }
        return data.toArray(new Object[0][]);
    }

}
