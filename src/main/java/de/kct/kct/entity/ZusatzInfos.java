package de.kct.kct.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ZusatzInfos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Datensatz datensatz;
    private String bemerkung;
    private Integer abgerechnetMonat;
    private String vergleichIlv;
    private String pspElement;
}
