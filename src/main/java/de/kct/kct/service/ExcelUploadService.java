package de.kct.kct.service;

import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.ZusatzInfos;
import de.kct.kct.repository.DatensatzRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class ExcelUploadService {

    private DatensatzRepository datensatzRepository;

    public boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Datensatz> getDataFromExcel(InputStream inputStream) {
        List<Datensatz> customers = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("customers");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Datensatz datensatz = new Datensatz();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> datensatz.setLeistungsart(cell.getStringCellValue());
                        case 1 -> datensatz.setAarNummer((int) cell.getNumericCellValue());
                        case 2 -> datensatz.setAposType((int) cell.getNumericCellValue());
                        case 3 -> datensatz.setVertragsnummer(cell.getStringCellValue());
                        case 4 -> datensatz.setVertragsbezeichnung(cell.getStringCellValue());
                        case 5 -> datensatz.setTeilvertragsnummer(cell.getStringCellValue());
                        case 6 -> datensatz.setBestellnummer(cell.getStringCellValue());
                        case 7 -> datensatz.setBestellnummerKunde(cell.getStringCellValue());
                        case 8 -> datensatz.setSystelPspElement(cell.getStringCellValue());
                        case 9 -> datensatz.setSystelPspElementBezeichnung(cell.getStringCellValue());
                        case 10 -> datensatz.setAufwandskonto(cell.getStringCellValue());
                        case 11 -> datensatz.setAufwandskontobezeichnung(cell.getStringCellValue());
                        case 12 -> datensatz.setBuchungskreis(cell.getStringCellValue());
                        case 13 -> datensatz.setDebitornummer(cell.getStringCellValue());
                        case 14 -> datensatz.setKunde(cell.getStringCellValue());
                        case 15 -> datensatz.setBahnstelle(cell.getStringCellValue());
                        case 16 -> datensatz.setOrganisationseinheit(cell.getStringCellValue());
                        case 17 -> datensatz.setKostenstelle(cell.getStringCellValue());
                        case 18 -> datensatz.setSapPspElement(cell.getStringCellValue());
                        case 19 -> datensatz.setSapPspElementBezeichnung(cell.getStringCellValue());
                        case 20 -> datensatz.setAnlagenNummer(cell.getStringCellValue());
                        case 21 -> datensatz.setAnlagenBezeichnung(cell.getStringCellValue());
                        case 22 -> datensatz.setBelasteterAarAuftrag(cell.getStringCellValue());
                        case 23 -> datensatz.setKorrekturbuchnung(cell.getStringCellValue());
                        case 24 -> datensatz.setProduktLeistung(cell.getStringCellValue());
                        case 25 -> datensatz.setProduktId(cell.getStringCellValue());
                        case 26 -> datensatz.setLeistendePerson(cell.getStringCellValue());
                        case 27 -> datensatz.setDetailangabe1(cell.getStringCellValue());
                        case 28 -> datensatz.setDetailangabe2(cell.getStringCellValue());
                        case 29 -> datensatz.setKurzbeschreibung(cell.getStringCellValue());
                        case 30 -> datensatz.setNutzer(cell.getStringCellValue());
                        case 31 -> datensatz.setNutzerOe(cell.getStringCellValue());
                        case 32 -> datensatz.setEmailAdresse(cell.getStringCellValue());
                        case 33 -> datensatz.setIpPort(cell.getStringCellValue());
                        case 34 -> datensatz.setHostname(cell.getStringCellValue());
                        case 35 -> datensatz.setSeriennummer(cell.getStringCellValue());
                        case 36 -> datensatz.setIdgNummer(cell.getStringCellValue());
                        case 37 -> datensatz.setImeiNummer(cell.getStringCellValue());
                        case 38 -> datensatz.setTelefonnummer(cell.getStringCellValue());
                        case 39 -> datensatz.setOrt(cell.getStringCellValue());
                        case 40 -> datensatz.setStrasseHausnummer(cell.getStringCellValue());
                        case 41 -> datensatz.setRaum(cell.getStringCellValue());
                        case 42 -> datensatz.setLeistungszeitraum(cell.getStringCellValue());
                        case 43 -> datensatz.setPreisinformation(cell.getStringCellValue());
                        case 44 -> datensatz.setFoerderung(cell.getStringCellValue());
                        case 45 -> datensatz.setAnteil(cell.getStringCellValue());
                        case 46 -> datensatz.setMenge((int) cell.getNumericCellValue());
                        case 47 -> datensatz.setMengeneinheit(cell.getStringCellValue());
                        case 48 -> datensatz.setEinzelpreis((int) cell.getNumericCellValue());
                        case 49 -> datensatz.setVkZuschlag(cell.getStringCellValue());
                        case 50 -> datensatz.setArbeitsplatzZuschlag(cell.getStringCellValue());
                        case 51 -> datensatz.setGesamtpreis(cell.getNumericCellValue());
                        case 52 -> datensatz.setMonat((int) cell.getNumericCellValue());
                        case 53 -> datensatz.setJahr((int) cell.getNumericCellValue());
                        case 54 -> datensatz.setRechnungsnummer(cell.getStringCellValue());
                        case 55 -> datensatz.setRechnungsdatum(cell.getStringCellValue());
                        case 56 -> datensatz.setKundenkennung(cell.getStringCellValue());
                        case 57 -> datensatz.setLeistungssegment(cell.getStringCellValue());
                        case 58 -> datensatz.setProduktgruppe(cell.getStringCellValue());
                        case 59 -> datensatz.setBestandsId(cell.getStringCellValue());
                        case 60 -> datensatz.setId(Integer.parseInt(cell.getStringCellValue()));
                        case 61 -> datensatz.setPeriode((int) cell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                Optional<Datensatz> alreadyExistingDatensatz = datensatzRepository.findById(datensatz.getId());
                if(alreadyExistingDatensatz.isEmpty()) {
                    List<Datensatz> previousDatensaetze = datensatzRepository.findDatensaetzeForDetailAndNutzer(datensatz.getDetailangabe1(), datensatz.getNutzer());
                    if (!previousDatensaetze.isEmpty()) {
                        ZusatzInfos zusatzInfos = new ZusatzInfos();
                        zusatzInfos.setPspElement(previousDatensaetze.get(0).getZusatzInfos().getPspElement());
                        zusatzInfos.setAbgerechnetMonat(LocalDate.now().getMonthValue());
                        zusatzInfos.setDatensatz(datensatz);
                        datensatz.setZusatzInfos(zusatzInfos);
                    }
                }
                customers.add(datensatz);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return customers;
    }


}
