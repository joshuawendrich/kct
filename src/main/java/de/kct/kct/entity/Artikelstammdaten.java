package de.kct.kct.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artikelstammdaten {

    @Id
    String artikelnummer;

    String katalogPositionNr;

    String bezeichnung;

    String katalogPositionBez;

    String modulbezeichnung;

    String katalog;

    String servicelevel;

    String performancelevel;

    String leistungstyp;

    String pms;

    String ilv;

    Double herstellkosten;

    Double zielkosten;

    Double verkaufspreis;

    String oe;

    String oeKurz;

    String einheit;

    String kstid;

    String kst;

    String gueltigVon;

    String gueltigBis;

    String gueltig;

    String bindefrist;

    String status;

    String minMenge;

    String avgMenge;

    String maxMenge;

    String manuelleKalkulation;

    String zuschlag;

    String lizenztyp;

    String lvdb;

    String einmal;

    String bestandsschutz;

    String bestandsschutzAbKatalog;

    String lizenzmodell;

    String paketierung;

    String faktorFArtikel;

    String subartikelliste;

    String artikelart;

    Double preisuntergrenze;

    String preisregel;

    Double bmHerstellkosten;

    Double bmPreis;

    Double kalkulierteHerstellkosten;

    Double kalkulierterPreis;

    String benchmarkArtikel;

    String aufwandsbezogen;

}
