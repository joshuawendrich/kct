package de.kct.kct.dto;


import de.kct.kct.entity.ZusatzInfos;

public record ZusatzInfosDto(
        Integer id,
        String bemerkung,
        Integer abgerechnetMonat,
        String vergleichIlv,
        String pspElement,
        Integer abgerechnetJahr
) {
    public static ZusatzInfosDto fromZusatzInfos(ZusatzInfos zusatzInfos) {
        if(zusatzInfos == null) return null;
        return new ZusatzInfosDto(zusatzInfos.getId(), zusatzInfos.getBemerkung(), zusatzInfos.getAbgerechnetMonat(), zusatzInfos.getVergleichIlv(), zusatzInfos.getPspElement(), zusatzInfos.getAbgerechnetJahr());
    }
}
