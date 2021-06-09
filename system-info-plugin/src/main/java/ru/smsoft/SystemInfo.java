package ru.smsoft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfo {
    private String dateTime;
    private String osName;
    private Integer countOfProc;
    private JvmData jvmData;
    private List<FsData> fsData;
}
