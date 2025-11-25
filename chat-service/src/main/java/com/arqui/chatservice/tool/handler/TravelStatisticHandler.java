package com.arqui.chatservice.tool.handler;

import com.arqui.chatservice.dto.response.AccountBalanceResponseDTO;
import com.arqui.chatservice.feignClients.TravelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TravelStatisticHandler implements ToolHandler {

    @Autowired
    private TravelClient travelClient;

    @Override
    public boolean supports(String toolName) {
        return "get_account_travel_balance".equals(toolName);
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> args) {
        Long accountId = Long.valueOf(args.get("accountId").toString());

        // Extraemos los valores de fromDate y toDate
        Object fromObj = args.get("fromDate");
        Object toObj = args.get("toDate");

        LocalDateTime fromDate;
        LocalDateTime toDate;

        // Si vienen en String (lenguaje natural) los parseamos
        if (fromObj instanceof String) {
            fromDate = parseNaturalDate(fromObj.toString()).atStartOfDay();
        } else if (fromObj instanceof LocalDateTime) {
            fromDate = (LocalDateTime) fromObj;
        } else {
            fromDate = LocalDate.now().atStartOfDay(); // por defecto hoy
        }

        if (toObj instanceof String) {
            toDate = parseNaturalDate(toObj.toString()).atTime(23, 59, 59);
        } else if (toObj instanceof LocalDateTime) {
            toDate = (LocalDateTime) toObj;
        } else {
            toDate = LocalDate.now().atTime(23, 59, 59);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fromIso = fromDate.format(formatter);
        String toIso = toDate.format(formatter);

        AccountBalanceResponseDTO report = travelClient.getBalanceReportByAccount(fromIso, toIso, accountId);

        Map<String, Object> toolMessage = new HashMap<>();
        toolMessage.put("content", "{ \"totalSpent\": " + report.getTotalSpent() + " }");

        return toolMessage;
    }

    private LocalDate parseNaturalDate(String text) {
        text = text.trim().toLowerCase(Locale.ROOT);

        // Caso ISO yyyy-MM-dd
        try {
            return LocalDate.parse(text);
        } catch (Exception ignored) { }

        // Caso "10 de noviembre de 2025"
        Pattern pattern = Pattern.compile("(\\d{1,2}) de (\\w+) de (\\d{4})");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return parseSpanishDate(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        // Por defecto hoy
        return LocalDate.now();
    }

    private LocalDate parseSpanishDate(String day, String month, String year) {
        Map<String, Integer> months = Map.ofEntries(
                Map.entry("enero", 1), Map.entry("febrero", 2), Map.entry("marzo", 3),
                Map.entry("abril", 4), Map.entry("mayo", 5), Map.entry("junio", 6),
                Map.entry("julio", 7), Map.entry("agosto", 8), Map.entry("septiembre", 9),
                Map.entry("octubre", 10), Map.entry("noviembre", 11), Map.entry("diciembre", 12)
        );
        int d = Integer.parseInt(day);
        int m = months.get(month.toLowerCase());
        int y = Integer.parseInt(year);
        return LocalDate.of(y, m, d);
    }
}
