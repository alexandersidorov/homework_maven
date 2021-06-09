package ru.smsoft.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.smsoft.SystemInfo;
import ru.smsoft.exceptions.CustomException;
import ru.smsoft.responses.ErrorResponse;

import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = "SystemInfo")
@PropertySource("classpath:application.properties")
public class GetController {
    private final ObjectMapper mapper;
    private final String pathToSysFile;

    GetController(ObjectMapper mapper, @Value("${pathToFile}") String pathToFile){
        this.mapper = mapper;
        this.pathToSysFile = pathToFile;
    }

    @ApiOperation(value = "Method return system info.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = SystemInfo.class),
        @ApiResponse(code = 500, message = "Fail", response = ErrorResponse.class)
    })
    @GetMapping(value = "/system")
    public ResponseEntity<SystemInfo> postApplications(
            @ApiParam(hidden = true)
            @RequestHeader HttpHeaders headers
            ) throws CustomException {

        File sysFile = new File(pathToSysFile);
        if(!sysFile.exists())
            throw new CustomException("Cant find system file.",HttpStatus.INTERNAL_SERVER_ERROR);

        SystemInfo response=null;
        try{
            response = mapper.readValue(sysFile,SystemInfo.class);
        } catch (IOException e) {
            throw new CustomException("Cant parse response.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
