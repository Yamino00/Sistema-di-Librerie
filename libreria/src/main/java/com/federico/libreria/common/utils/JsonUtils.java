package com.federico.libreria.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.federico.libreria.common.exception.exceptions.JsonProcessingException;

/**
 * Utility per la conversione tra oggetti Java e rappresentazioni JSON
 * utilizzando {@link ObjectMapper} di Jackson.
 * Tutti i metodi sono statici e lanciano {@link JsonProcessingException}
 * in caso di errori di serializzazione o deserializzazione.
 */
public class JsonUtils {

    private JsonUtils() {}

    /**
     * Serializza un oggetto in una stringa JSON.
     *
     * @param objectMapper l'istanza di {@link ObjectMapper} da utilizzare
     * @param obj          l'oggetto da serializzare in JSON
     * @return la rappresentazione JSON dell'oggetto
     * @throws JsonProcessingException se si verifica un errore durante la serializzazione
     */
    public static String toJson(ObjectMapper objectMapper, Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JsonProcessingException("Error serializing object to JSON", e);
        }
    }

    /**
     * Deserializza una stringa JSON in un oggetto del tipo specificato.
     *
     * @param <T>          il tipo di ritorno
     * @param objectMapper l'istanza di {@link ObjectMapper} da utilizzare
     * @param json         la stringa JSON da deserializzare
     * @param clazz        la classe del tipo di ritorno
     * @return l'oggetto deserializzato
     * @throws JsonProcessingException se si verifica un errore durante la deserializzazione
     */
    public static <T> T fromJson(ObjectMapper objectMapper, String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonProcessingException("Error deserializing JSON to object", e);
        }
    }

    /**
     * Deserializza una stringa JSON in un oggetto complesso definito da {@link TypeReference},
     * ad esempio per collezioni o tipi generici.
     *
     * @param <T>          il tipo di ritorno
     * @param objectMapper l'istanza di {@link ObjectMapper} da utilizzare
     * @param json         la stringa JSON da deserializzare
     * @param typeRef      il riferimento di tipo che descrive la struttura del dato di ritorno
     * @return l'oggetto deserializzato
     * @throws JsonProcessingException se si verifica un errore durante la deserializzazione
     */
    public static <T> T fromJson(ObjectMapper objectMapper, String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception ex) {
            throw new JsonProcessingException("Error deserializing JSON to object", ex);
        }
    }
}