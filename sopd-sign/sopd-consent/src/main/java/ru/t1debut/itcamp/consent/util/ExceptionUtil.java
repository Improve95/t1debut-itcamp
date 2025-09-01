package ru.t1debut.itcamp.consent.util;

public final class ExceptionUtil {

    public static String createExceptionMessage(Exception ex, String localizedMessage) {
        String logErrorMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return "Error " + ex.getClass().getSimpleName()
                + " in " + ex.getStackTrace()[0].getClassName()
                + ": " + (logErrorMessage == null ? "" : logErrorMessage)
                + (localizedMessage == null ? "" : localizedMessage);
    }
}
