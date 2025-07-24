package ru.improve.itcamp.auth.service.util;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;

public final class DatabaseUtil {

    public static final String UNIQUE_CONSTRAINT_SQL_STATE = "23505";

    public static boolean isUniqueConstraintException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException constraintViolationException) {
            if (constraintViolationException.getCause() instanceof SQLException sqlException) {
                String sqlState = sqlException.getSQLState();
                if (sqlState.equals(UNIQUE_CONSTRAINT_SQL_STATE)) {
                    return true;
                }
            }
        }
        return false;
    }
}
