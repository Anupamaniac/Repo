package com.rosettastone.succor.utils.mapping;

import javax.annotation.PostConstruct;

import com.rosettastone.succor.model.parature.TicketLanguageType;

public class LanguageConverter extends MapConverter<TicketLanguageType> {

    @PostConstruct
    public void init() {
        add("ar", TicketLanguageType.ARABIC);
        add("zh-CN", TicketLanguageType.MANDARIN_CHINES);
        add("nl-NL", TicketLanguageType.DUTCH);
        add("en-US", TicketLanguageType.AMERICAN_ENGLISH);
        add("en-GB", TicketLanguageType.BRITISH_ENGLISH);
        add("tl-PH", TicketLanguageType.TAGALOG_FILIPINO);
        add("fr-FR", TicketLanguageType.FRENCH);
        add("de-DE", TicketLanguageType.GERMAN);
        add("el-GR", TicketLanguageType.GREEK);
        add("he-IL", TicketLanguageType.HEBREW);
        add("hi-IN", TicketLanguageType.HINDI);
        add("ga-IE", TicketLanguageType.IRISH);
        add("it-IT", TicketLanguageType.ITALIAN);
        add("ja-JP", TicketLanguageType.JAPANESE);
        add("ko-KR", TicketLanguageType.KOREAN);
        add("fa-IR", TicketLanguageType.FARSI_PERSIAN);
        add("pl-PL", TicketLanguageType.POLISH);
        add("pt-BR", TicketLanguageType.BRAZIL_PORTUGUESE);
        add("ru-RU", TicketLanguageType.RUSSIAN);
        add("es-419", TicketLanguageType.LATINAMERICA_SPANISH);
        add("es-ES", TicketLanguageType.SPAIN_SPANISH);
        add("sv-SE", TicketLanguageType.SWEDISH);
        add("tr-TR", TicketLanguageType.TURKISH);
        add("vi-VN", TicketLanguageType.VIETNAMESE);
        add("en-KR", TicketLanguageType.AMERICAN_ENGLISH);
        add("en-JP", TicketLanguageType.AMERICAN_ENGLISH);
    }
}
