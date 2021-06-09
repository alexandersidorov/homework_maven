package ru.smsoft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JvmData {
    private Long memoryTotal;
    private Long memoryFree;
}
