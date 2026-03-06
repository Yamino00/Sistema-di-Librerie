package com.federico.producerxml.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.federico.producerxml.dto.CopialibroDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xml")
public class XmlProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final XmlMapper xmlMapper;

    public XmlProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.xmlMapper = new XmlMapper();
    }

    @PostMapping("/invia")
    public String inviaMessaggioXml(@RequestBody CopialibroDTO messaggio) {
        try {
            // 1. Convertiamo l'oggetto DTO in una Stringa formattata come XML
            String payloadXml = xmlMapper.writeValueAsString(messaggio);

            // 2. Inviamo la stringa grezza al topic "nuovi-arrivi"
            kafkaTemplate.send("censimento", payloadXml);

            return "Messaggio inviato con successo a Kafka in formato XML:\n" + payloadXml;

        } catch (Exception e) {
            return "Errore durante la conversione in XML: " + e.getMessage();
        }
    }
}