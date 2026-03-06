package com.federico.producerordinelibro;

import com.federico.producerordinelibro.dto.CopialibroDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class ProducerController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/invia")
    public String inviaMessaggio(@RequestBody CopialibroDTO copialibroDTO) {
        kafkaTemplate.send("censimento", copialibroDTO);
        return "Messaggio inviato dal Producer";
    }
}