package de.kct.kct.service;

import de.kct.kct.entity.Artikelstammdaten;
import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.ZusatzInfos;
import de.kct.kct.repository.DatensatzRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
            XSSFSheet sheet = workbook.getSheet("journal");
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
                        case 5 -> datensatz.setRahmenvertragsnummer(cell.getStringCellValue());
                        case 6 -> datensatz.setRahmenvertragsbezeichnung(cell.getStringCellValue());
                        case 7 -> datensatz.setBestellnummer(cell.getStringCellValue());
                        case 8 -> datensatz.setBestellnummerKunde(cell.getStringCellValue());
                        case 9 -> datensatz.setSystelPspElement(cell.getStringCellValue());
                        case 10 -> datensatz.setSystelPspElementBezeichnung(cell.getStringCellValue());
                        case 11 -> datensatz.setAufwandskonto(cell.getStringCellValue());
                        case 12 -> datensatz.setAufwandskontobezeichnung(cell.getStringCellValue());
                        case 13 -> datensatz.setBuchungskreis(cell.getStringCellValue());
                        case 14 -> datensatz.setDebitornummer(cell.getStringCellValue());
                        case 15 -> datensatz.setKunde(cell.getStringCellValue());
                        case 16 -> datensatz.setBahnstelle(cell.getStringCellValue());
                        case 17 -> datensatz.setOrganisationseinheit(cell.getStringCellValue());
                        case 18 -> datensatz.setKostenstelle(cell.getStringCellValue());
                        case 19 -> datensatz.setSapPspElement(cell.getStringCellValue());
                        case 20 -> datensatz.setSapPspElementBezeichnung(cell.getStringCellValue());
                        case 21 -> datensatz.setAnlagenNummer(cell.getStringCellValue());
                        case 22 -> datensatz.setAnlagenBezeichnung(cell.getStringCellValue());
                        case 23 -> datensatz.setBelasteterAarAuftrag(cell.getStringCellValue());
                        case 24 -> datensatz.setKorrekturbuchnung(cell.getStringCellValue());
                        case 25 -> datensatz.setProduktLeistung(cell.getStringCellValue());
                        case 26 -> datensatz.setProduktId(cell.getStringCellValue());
                        case 27 -> datensatz.setLeistendePerson(cell.getStringCellValue());
                        case 28 -> datensatz.setDetailangabe1(cell.getStringCellValue());
                        case 29 -> datensatz.setDetailangabe2(cell.getStringCellValue());
                        case 30 -> datensatz.setKurzbeschreibung(cell.getStringCellValue());
                        case 31 -> datensatz.setNutzer(cell.getStringCellValue());
                        case 32 -> datensatz.setNutzerOe(cell.getStringCellValue());
                        case 33 -> datensatz.setEmailAdresse(cell.getStringCellValue());
                        case 34 -> datensatz.setIpPort(cell.getStringCellValue());
                        case 35 -> datensatz.setHostname(cell.getStringCellValue());
                        case 36 -> datensatz.setSeriennummer(cell.getStringCellValue());
                        case 37 -> datensatz.setIdgNummer(cell.getStringCellValue());
                        case 38 -> datensatz.setImeiNummer(cell.getStringCellValue());
                        case 39 -> datensatz.setTelefonnummer(cell.getStringCellValue());
                        case 40 -> datensatz.setOrt(cell.getStringCellValue());
                        case 41 -> datensatz.setStrasseHausnummer(cell.getStringCellValue());
                        case 42 -> datensatz.setRaum(cell.getStringCellValue());
                        case 43 -> datensatz.setLeistungszeitraum(cell.getStringCellValue());
                        case 44 -> datensatz.setPreisinformation(cell.getStringCellValue());
                        case 45 -> datensatz.setFoerderung(cell.getStringCellValue());
                        case 46 -> datensatz.setAnteil(cell.getStringCellValue());
                        case 47 -> datensatz.setMenge((int) cell.getNumericCellValue());
                        case 48 -> datensatz.setMengeneinheit(cell.getStringCellValue());
                        case 49 -> datensatz.setEinzelpreis((int) cell.getNumericCellValue());
                        case 50 -> datensatz.setVkZuschlag(cell.getStringCellValue());
                        case 51 -> datensatz.setArbeitsplatzZuschlag(cell.getStringCellValue());
                        case 52 -> datensatz.setGesamtpreis(cell.getNumericCellValue());
                        case 53 -> datensatz.setMonat((int) cell.getNumericCellValue());
                        case 54 -> datensatz.setJahr((int) cell.getNumericCellValue());
                        case 55 -> datensatz.setRechnungsnummer(cell.getStringCellValue());
                        case 56 -> datensatz.setRechnungsdatum(cell.getStringCellValue());
                        case 57 -> datensatz.setKundenkennung(cell.getStringCellValue());
                        case 58 -> datensatz.setLeistungssegment(cell.getStringCellValue());
                        case 59 -> datensatz.setProduktgruppe(cell.getStringCellValue());
                        case 60 -> datensatz.setBestandsId(cell.getStringCellValue());
                        case 61 -> datensatz.setId(Integer.parseInt(cell.getStringCellValue()));
                        case 62 -> datensatz.setPeriode((int) cell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                Optional<Datensatz> alreadyExistingDatensatz = datensatzRepository.findById(datensatz.getId());
                if (alreadyExistingDatensatz.isEmpty()) {
                    List<Datensatz> previousDatensaetze = datensatzRepository.findDatensaetzeForDetailAndNutzer(datensatz.getDetailangabe1(), datensatz.getNutzer());
                    if (!previousDatensaetze.isEmpty()) {
                        ZusatzInfos zusatzInfos = new ZusatzInfos();
                        zusatzInfos.setPspElement(previousDatensaetze.get(0).getZusatzInfos().getPspElement());
                        zusatzInfos.setAbgerechnetMonat(LocalDate.now().getMonthValue());
                        zusatzInfos.setAbgerechnetJahr(LocalDate.now().getYear());
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

    public List<Artikelstammdaten> getArtikelstammdatenFromExcel(InputStream inputStream) {
        List<Artikelstammdaten> artikelstammdatenList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("3800_Artikelstammdaten");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Artikelstammdaten artikelstammdaten = new Artikelstammdaten();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> artikelstammdaten.setArtikelnummer(cell.getStringCellValue());
                        case 1 -> artikelstammdaten.setKatalogPositionNr(cell.getStringCellValue());
                        case 2 -> artikelstammdaten.setBezeichnung(cell.getStringCellValue());
                        case 3 -> artikelstammdaten.setKatalogPositionBez(cell.getStringCellValue());
                        case 4 -> artikelstammdaten.setModulbezeichnung(cell.getStringCellValue());
                        case 5 -> artikelstammdaten.setKatalog(cell.getStringCellValue());
                        case 6 -> artikelstammdaten.setServicelevel(cell.getStringCellValue());
                        case 7 -> artikelstammdaten.setPerformancelevel(cell.getStringCellValue());
                        case 8 -> artikelstammdaten.setLeistungstyp(cell.getStringCellValue());
                        case 9 -> artikelstammdaten.setPms(cell.getStringCellValue());
                        case 10 -> artikelstammdaten.setIlv(cell.getStringCellValue());
                        case 11 -> artikelstammdaten.setHerstellkosten(cell.getNumericCellValue());
                        case 12 -> artikelstammdaten.setZielkosten(cell.getNumericCellValue());
                        case 13 -> artikelstammdaten.setVerkaufspreis(cell.getNumericCellValue());
                        case 14 -> {
                            var oe = cell.getStringCellValue();
                            artikelstammdaten.setOe(oe);
                            if (oe.length() > 3) {
                                artikelstammdaten.setOeKurz(oe.substring(3));
                            }
                        }
                        case 16 -> artikelstammdaten.setEinheit(cell.getStringCellValue());
                        case 17 -> artikelstammdaten.setKstid(getStringValueFromStringOrNumericCell(cell));
                        case 19 -> artikelstammdaten.setGueltigVon(getStringValueFromStringOrNumericCell(cell));
                        case 20 -> artikelstammdaten.setGueltigBis(getStringValueFromStringOrNumericCell(cell));
                        case 21 -> artikelstammdaten.setGueltig(cell.getStringCellValue());
                        case 22 -> artikelstammdaten.setBindefrist(getStringValueFromStringOrNumericCell(cell));
                        case 23 -> artikelstammdaten.setStatus(getStringValueFromStringOrNumericCell(cell));
                        case 24 -> artikelstammdaten.setMinMenge(getStringValueFromStringOrNumericCell(cell));
                        case 25 -> artikelstammdaten.setAvgMenge(getStringValueFromStringOrNumericCell(cell));
                        case 26 -> artikelstammdaten.setMaxMenge(getStringValueFromStringOrNumericCell(cell));
                        case 27 ->
                                artikelstammdaten.setManuelleKalkulation(getStringValueFromStringOrNumericCell(cell));
                        case 28 -> artikelstammdaten.setZuschlag(getStringValueFromStringOrNumericCell(cell));
                        case 29 -> artikelstammdaten.setLizenztyp(getStringValueFromStringOrNumericCell(cell));
                        case 30 -> artikelstammdaten.setLvdb(getStringValueFromStringOrNumericCell(cell));
                        case 31 -> artikelstammdaten.setEinmal(getStringValueFromStringOrNumericCell(cell));
                        case 32 -> artikelstammdaten.setBestandsschutz(getStringValueFromStringOrNumericCell(cell));
                        case 33 ->
                                artikelstammdaten.setBestandsschutzAbKatalog(getStringValueFromStringOrNumericCell(cell));
                        case 34 -> artikelstammdaten.setLizenzmodell(getStringValueFromStringOrNumericCell(cell));
                        case 35 -> artikelstammdaten.setPaketierung(getStringValueFromStringOrNumericCell(cell));
                        case 36 -> artikelstammdaten.setFaktorFArtikel(getStringValueFromStringOrNumericCell(cell));
                        case 37 -> artikelstammdaten.setSubartikelliste(getStringValueFromStringOrNumericCell(cell));
                        case 38 -> artikelstammdaten.setArtikelart(getStringValueFromStringOrNumericCell(cell));
                        case 39 -> artikelstammdaten.setPreisuntergrenze(cell.getNumericCellValue());
                        case 40 -> artikelstammdaten.setPreisregel(getStringValueFromStringOrNumericCell(cell));
                        case 41 -> artikelstammdaten.setBmHerstellkosten(cell.getNumericCellValue());
                        case 42 -> artikelstammdaten.setBmPreis(cell.getNumericCellValue());
                        case 43 -> artikelstammdaten.setKalkulierteHerstellkosten(cell.getNumericCellValue());
                        case 44 -> artikelstammdaten.setKalkulierterPreis(cell.getNumericCellValue());
                        case 45 -> artikelstammdaten.setBenchmarkArtikel(getStringValueFromStringOrNumericCell(cell));
                        case 46 -> artikelstammdaten.setAufwandsbezogen(getStringValueFromStringOrNumericCell(cell));
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                if (artikelstammdaten.getBezeichnung().toLowerCase().startsWith("ilv")) {
                    artikelstammdatenList.add(artikelstammdaten);
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return artikelstammdatenList;
    }

    private String getStringValueFromStringOrNumericCell(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : cell.getStringCellValue();
    }


}
