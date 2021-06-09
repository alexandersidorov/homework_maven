package ru.smsoft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FsData {
    private String path;
    private Long memoryTotal;
    private Long memoryFree;
}
