package de.kct.kct.service;

import de.kct.kct.domain.Customer12;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
   public static List<Customer12> getCustomersDataFromExcel(InputStream inputStream){
        List<Customer12> customers = new ArrayList<>();
       try {
           XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
           XSSFSheet sheet = workbook.getSheet("customers");
           int rowIndex =0;
           for (Row row : sheet){
               if (rowIndex ==0){
                   rowIndex++;
                   continue;
               }
               Iterator<Cell> cellIterator = row.iterator();
               int cellIndex = 0;
               Customer12 customer1 = new Customer12();
               while (cellIterator.hasNext()){
                   Cell cell = cellIterator.next();
                   switch (cellIndex){


                       case 0 -> customer1.setLeistungsart(cell.getStringCellValue());
                       case 1 -> customer1.setAAR_Nummer((int)cell.getNumericCellValue());
                       case 2 -> customer1.setApos_Type((int) cell.getNumericCellValue());
                       case 3 -> customer1.setVertragsnummer(cell.getStringCellValue());
                       case 4 -> customer1.setVertragsbezeichnung(cell.getStringCellValue());
                       case 5 -> customer1.setTeilvertragsnummer(cell.getStringCellValue());
                       case 6 -> customer1.setBestellnummer(cell.getStringCellValue());
                       case 7 -> customer1.setBestellnummerKunde(cell.getStringCellValue());
                       case 8 -> customer1.setSystel_PSP_Element(cell.getStringCellValue());
                       case 9 -> customer1.setSystel_PSP_Element_Bezeichnung(cell.getStringCellValue());
                       case 10 -> customer1.setAufwandskonto(cell.getStringCellValue());
                       case 11 -> customer1.setAufwandskontobezeichnung(cell.getStringCellValue());
                       case 12 -> customer1.setBuchungskreis(cell.getStringCellValue());
                       case 13 -> customer1.setDebitornummer(cell.getStringCellValue());
                       case 14 -> customer1.setKunde(cell.getStringCellValue());
                       case 15 -> customer1.setBahnstelle(cell.getStringCellValue());
                       case 16 -> customer1.setOrganisationseinheit(cell.getStringCellValue());
                       case 17 -> customer1.setKostenstelle(cell.getStringCellValue());
                       case 18 -> customer1.setSAP_PSP_Element(cell.getStringCellValue());
                       case 19 -> customer1.setSAP_PSP_Element_Bezeichnung(cell.getStringCellValue());
                       case 20 -> customer1.setAnlagennummer(cell.getStringCellValue());
                       case 21 -> customer1.setAnlagenbezeichnung(cell.getStringCellValue());
                       case 22 -> customer1.setBelasteter_AAR_Auftrag(cell.getStringCellValue());
                       case 23 -> customer1.setKorrekturbuchung(cell.getStringCellValue());
                       case 24 -> customer1.setProdukt_Leistung(cell.getStringCellValue());
                       case 25 -> customer1.setProdukt_Id(cell.getStringCellValue());
                       case 26 -> customer1.setLeistende_Person(cell.getStringCellValue());
                       case 27 -> customer1.setDetailangabe1(cell.getStringCellValue());
                       case 28 -> customer1.setDetailangabe2(cell.getStringCellValue());
                       case 29 -> customer1.setKurzbeschreibung(cell.getStringCellValue());
                       case 30 -> customer1.setNutzer(cell.getStringCellValue());
                       case 31 -> customer1.setNutzer_Oe(cell.getStringCellValue());
                       case 32 -> customer1.setEmail_Adresse(cell.getStringCellValue());
                       case 33 -> customer1.setIp_Port(cell.getStringCellValue());
                       case 34 -> customer1.setHostname(cell.getStringCellValue());
                       case 35 -> customer1.setSeriennummer(cell.getStringCellValue());
                       case 36 -> customer1.setIDG_Nummer(cell.getStringCellValue());
                       case 37 -> customer1.setImei_Nummer(cell.getStringCellValue());
                       case 38 -> customer1.setTelefonnummer(cell.getStringCellValue());
                       case 39 -> customer1.setOrt(cell.getStringCellValue());
                       case 40 -> customer1.setStrasse_Hausnummer(cell.getStringCellValue());
                       case 41 -> customer1.setRaum(cell.getStringCellValue());
                       case 42 -> customer1.setLeistungszeitraum(cell.getStringCellValue());
                       case 43 -> customer1.setPreisinformation(cell.getStringCellValue());
                       case 44 -> customer1.setFoerderung(cell.getStringCellValue());
                       case 45 -> customer1.setAnteil(cell.getStringCellValue());
                       case 46 -> customer1.setMenge((int)cell.getNumericCellValue());
                       case 47 -> customer1.setMengeneinheit(cell.getStringCellValue());
                       case 48 -> customer1.setEinzelpreis((int)cell.getNumericCellValue());
                       case 49 -> customer1.setVK_Zuschlag(cell.getStringCellValue());
                       case 50 -> customer1.setArbeitsplatz_Zuschlag(cell.getStringCellValue());
                       case 51 -> customer1.setGesamtpreis((int)cell.getNumericCellValue());
                       case 52 -> customer1.setMonat((int)cell.getNumericCellValue());
                       case 53 -> customer1.setJahr((int)cell.getNumericCellValue());
                       case 54 -> customer1.setRechnungsnummer(cell.getStringCellValue());
                       case 55 -> customer1.setRechnungsdatum(cell.getStringCellValue());
                       case 56 -> customer1.setKundenkennung(cell.getStringCellValue());
                       case 57 -> customer1.setLeistungssegment(cell.getStringCellValue());
                       case 58 -> customer1.setProduktgruppe(cell.getStringCellValue());
                       case 59 -> customer1.setBestands_Id(cell.getStringCellValue());
                       case 60 -> customer1.setDatensatz_Id(cell.getStringCellValue());
                       case 61 -> customer1.setPeriode((int)cell.getNumericCellValue());
















                       //case 4 -> customer1.setVertragsbezeichnung((int) cell.getNumericCellValue());
                       //case 2 -> customer1.setLastName(cell.getStringCellValue());
                       //case 3 -> customer1.setCountry(cell.getStringCellValue());
                       //case 4 -> customer1.setTelephone((int) cell.getNumericCellValue());
                       //case 4 -> customer1.setTelephone(cell.getStringCellValue());
                       default -> {
                       }
                   }
                   cellIndex++;
               }
               customers.add(customer1);
           }
       } catch (IOException e) {
           e.getStackTrace();
       }
       return customers;
   }

}
