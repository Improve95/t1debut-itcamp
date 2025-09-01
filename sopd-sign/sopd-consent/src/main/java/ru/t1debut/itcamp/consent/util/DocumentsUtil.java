package ru.t1debut.itcamp.consent.util;

import org.jsoup.Jsoup;

public final class DocumentsUtil {

    public static final String EMAIL_FORM_NAME = "email_form_v";

    public static final String SOPD_DOCUMENT_NAME = "sopd_document_v";

    public static final String HTML_EXTENSION = ".html";

    public static boolean isValidHtml(String htmlBody) {
        try {
            Jsoup.parse(htmlBody);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
