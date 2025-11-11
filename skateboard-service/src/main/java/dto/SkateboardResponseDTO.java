package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkateboardResponseDTO {
    private Long id;
    private String qrCode;
    private Double totalKm;
    private Double usedTime;
    private boolean available;
    private boolean inMaintenance;
    private String stationName;
    private Double latitude;
    private Double lenght;
    private String status;
    private LocalDateTime lastUpdate;
}