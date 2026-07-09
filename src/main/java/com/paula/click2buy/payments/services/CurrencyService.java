package com.paula.click2buy.payments.services;

import com.paula.click2buy.exceptions.ExchangeRateUnavailable;
import com.paula.click2buy.payments.endpoints.dtos.BacenResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();

    private static final String BACEN_URL = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/" +
            "CotacaoMoedaDia(moeda=@moeda,dataCotacao=@data)" +
            "?@moeda='%s'&@data='%s'&$format=json";

    public Double convert(String currency, Double amount){
        try {
            BacenResponseDTO response = null;

            int attemps = 0;
            do {
                LocalDate date = null;
                if(attemps == 0){
                     date = lastBusinessDay(); // ultimo dia útil (sem contar feriado)
                }else{
                    date = lastBusinessDay().minusDays(attemps); // tenta dias anteriores
                }
                //MM-dd-yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String dateFormated = date.format(formatter);

                String url = String.format(BACEN_URL, currency, dateFormated);
                System.out.println("URL: " + url);
                System.out.println("Tentativas " + (attemps + 1) + ": Fetching exchange rate for " + currency + " on " + dateFormated);

                response = restTemplate.getForObject(url, BacenResponseDTO.class);
                System.out.println("Response: " + response.getValue());
                if (response != null && response.getValue() != null && !response.getValue().isEmpty()) {
                    Double exchangeRate = response.getValue().get(0).getCotacaoVenda();
                    return amount / exchangeRate;
                }
                attemps++;
            }while(response == null && attemps < 5); // tenta até 5 dias anteriores

           // throw new ExchangeRateUnavailable("Exchange rate for " + currency + " is unavailable after multiple attempts.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch exchange rate: " + e.getMessage(), e);
        }
        return null;
    }

    private LocalDate lastBusinessDay() {
        //para garantir que a data seja um dia útil, verificamos se é sábado ou domingo e ajustamos para sexta-feira
        LocalDate date = LocalDate.now();
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) { // Sábado
            date = date.minusDays(1);
        } else if (date.getDayOfWeek().getValue() == 7) { // Domingo
            date = date.minusDays(2);
        }



        return date; // formato YYYY-MM-DD
    }
}
