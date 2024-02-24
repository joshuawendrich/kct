package de.kct.kct.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer12 {

    //private Integer customerId;


    private String Leistungsart;
    private Integer AAR_Nummer;
    private Integer Apos_Type;
    private String Vertragsnummer;
    private String Vertragsbezeichnung;
    private String Teilvertragsnummer;
    private String Bestellnummer;
    private String BestellnummerKunde;
    private String Systel_PSP_Element;
    private String Systel_PSP_Element_Bezeichnung;
    private String Aufwandskonto;
    private String Aufwandskontobezeichnung;
    private String Buchungskreis;
    private String Debitornummer;
    private String Kunde;
    private String Bahnstelle;
    private String Organisationseinheit;
    private String Kostenstelle;
    private String SAP_PSP_Element;
    private String SAP_PSP_Element_Bezeichnung;
    private String Anlagennummer;
    private String Anlagenbezeichnung;
    private String Belasteter_AAR_Auftrag;
    private String Korrekturbuchung;
    private String Produkt_Leistung;
    private String Produkt_Id;
    private String Leistende_Person;
    private String Detailangabe1;
    private String Detailangabe2;
    private String Kurzbeschreibung;
    private String Nutzer;
    private String Nutzer_Oe;
    private String Email_Adresse;
    private String Ip_Port;
    private String Hostname;
    private String Seriennummer;
    private String IDG_Nummer;
    private String Imei_Nummer;
    private String Telefonnummer;
    private String Ort;
    private String Strasse_Hausnummer;
    private String Raum;
    private String Leistungszeitraum;
    private String Preisinformation;
    private String Foerderung;
    private String Anteil;
    private Integer Menge;
    private String Mengeneinheit;
    private Integer Einzelpreis;
    private String VK_Zuschlag;
    private String Arbeitsplatz_Zuschlag;
    private Integer Gesamtpreis;
    private Integer Monat;
    private Integer Jahr;
    private String Rechnungsnummer;
    private String Rechnungsdatum;
    private String Kundenkennung;
    private String Leistungssegment;
    private String Produktgruppe;
    private String Bestands_Id;
    @Id
    private String Datensatz_Id;
    private Integer Periode;




    //private String telephone;

}
