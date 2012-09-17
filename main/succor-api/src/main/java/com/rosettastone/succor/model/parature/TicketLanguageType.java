package com.rosettastone.succor.model.parature;

/**
 * This class represent values for language field in Super Ticket.
 */
public enum TicketLanguageType implements Identifiable {

    ARABIC("parature.field.language.arabic", "language.arabic"),
    MANDARIN_CHINES("parature.field.language.mandarinChines", "language.mandarinChines"),
    DUTCH("parature.field.language.dutch", "language.dutch"),
    AMERICAN_ENGLISH("parature.field.language.americanEnglish", "language.americanEnglish"),
    BRITISH_ENGLISH("parature.field.language.britishEnglish", "language.britishEnglish"),
    TAGALOG_FILIPINO("parature.field.language.tagalogFilipino", "language.tagalogFilipino"),
    FRENCH("parature.field.language.french", "language.french"),
    GERMAN("parature.field.language.german", "language.german"),
    GREEK("parature.field.language.greek", "language.greek"),
    HEBREW("parature.field.language.hebrew", "language.hebrew"),
    HINDI("parature.field.language.hindi", "language.hindi"),
    IRISH("parature.field.language.irish", "language.irish"),
    ITALIAN("parature.field.language.italian", "language.italian"),
    JAPANESE("parature.field.language.japanese", "language.japanese"),
    KOREAN("parature.field.language.korean", "language.korean"),
    FARSI_PERSIAN("parature.field.language.farsiPersian", "language.farsiPersian"),
    POLISH("parature.field.language.polish", "language.polish"),
    BRAZIL_PORTUGUESE("parature.field.language.brazilPortuguese", "language.brazilPortuguese"),
    RUSSIAN("parature.field.language.russian", "language.russian"),
    LATINAMERICA_SPANISH("parature.field.language.latinAmericaSpanish", "language.latinAmericaSpanish"),
    SPAIN_SPANISH("parature.field.language.spainSpanish", "language.spainSpanish"),
    SWEDISH("parature.field.language.swedish", "language.swedish"),
    TURKISH("parature.field.language.turkish", "language.turkish"),
    VIETNAMESE("parature.field.language.vietnamese", "language.vietnamese");

    private final String key;
    private final String labelKey;

    private TicketLanguageType(String key, String labelKey) {
        this.key = key;
        this.labelKey = labelKey;
    }

    public String getKey() {
        return key;
    }

    public String getLabelKey() {
        return labelKey;
    }
}
