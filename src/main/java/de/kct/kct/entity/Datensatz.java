package de.kct.kct.entity;

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
public class Datensatz {
    private String leistungsart;
    private Integer aarNummer;
    private Integer aposType;
    private String vertragsnummer;
    private String vertragsbezeichnung;
    private String teilvertragsnummer;
    private String bestellnummer;
    private String bestellnummerKunde;
    private String systelPspElement;
    private String systelPspElementBezeichnung;
    private String aufwandskonto;
    private String aufwandskontobezeichnung;
    private String buchungskreis;
    private String debitornummer;
    private String kunde;
    private String bahnstelle;
    private String organisationseinheit;
    private String kostenstelle;
    private String sapPspElement;
    private String sapPspElementBezeichnung;
    private String anlagenNummer;
    private String anlagenBezeichnung;
    private String belasteterAarAuftrag;
    private String korrekturbuchnung;
    private String produktLeistung;
    private String produktId;
    private String leistendePerson;
    private String detailangabe1;
    private String detailangabe2;
    private String kurzbeschreibung;
    private String nutzer;
    private String nutzerOe;
    private String emailAdresse;
    private String ipPort;
    private String hostname;
    private String seriennummer;
    private String idgNummer;
    private String imeiNummer;
    private String telefonnummer;
    private String ort;
    private String strasseHausnummer;
    private String raum;
    private String leistungszeitraum;
    private String preisinformation;
    private String foerderung;
    private String anteil;
    private Integer menge;
    private String mengeneinheit;
    private Integer einzelpreis;
    private String vkZuschlag;
    private String arbeitsplatzZuschlag;
    private Integer gesamtpreis;
    private Integer monat;
    private Integer jahr;
    private String rechnungsnummer;
    private String rechnungsdatum;
    private String kundenkennung;
    private String leistungssegment;
    private String produktgruppe;
    private String bestandsId;
    @Id
    private Integer id;
    private Integer periode;

    // Zusatzfelder

}
