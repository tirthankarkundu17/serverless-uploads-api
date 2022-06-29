package in.bitmaskers.dto;

import lombok.Data;

@Data
public class CreatePresignedURLDto {
    private String fileName;
    private long ttlInSeconds;
}
