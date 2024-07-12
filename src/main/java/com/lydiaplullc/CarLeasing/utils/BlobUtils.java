package com.lydiaplullc.CarLeasing.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.sql.Blob;
import java.sql.SQLException;

public class BlobUtils {
    /**
     * Converts a SQL Blob into a byte array.
     *
     * @param blob The Blob to convert.
     * @return A byte array representing the Blob's data, or null if the blob is null.
     */
    public static byte[] converBlobToBytes(Blob blob) {
        if (blob != null) {
            try {
                return blob.getBytes(1, (int) blob.length());
            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving photo", e);
            }
        }

        return null;
    }

    /**
     * Converts a SQL Blob into String.
     * @param blob The Blob to convert.
     * @return A string representing the Blob's data, or null if the blob is null.
     */
    public static String converBlobToString(Blob blob) {
        byte[] bytes = converBlobToBytes(blob);
        return bytes != null ? Base64.encodeBase64String(bytes) : null;
    }
}
