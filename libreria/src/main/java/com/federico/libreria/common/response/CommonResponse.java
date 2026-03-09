package com.federico.libreria.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper generico per le risposte delle API REST.
 * <p>
 * Contiene le informazioni di esito dell’operazione, un messaggio descrittivo,
 * lo status HTTP corrispondente e, se presente, il payload di tipo generico {@code T}.
 * </p>
 *
 * @param <T> il tipo del payload restituito (può essere {@code Void})
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    /**
     * Indica se l’operazione è andata a buon fine ({@code true}) o meno ({@code false}).
     */
    private boolean success;

    /**
     * Messaggio descrittivo relativo all’esito dell’operazione,
     * utile per comunicare eventuali errori o informazioni aggiuntive.
     */
    private String message;

    /**
     * Codice di stato HTTP associato alla risposta.
     * Ad esempio 200 per OK, 404 per Not Found, 500 per Internal Server Error, ecc.
     */
    private int status;

    /**
     * Il payload di risposta di tipo generico {@code T}.
     * Può essere {@code null} se non è presente alcun dato da restituire
     * (ad esempio nelle risposte di creazione o eliminazione).
     */
    private T data;
}