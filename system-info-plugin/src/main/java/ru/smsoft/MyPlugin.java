package ru.smsoft;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Mojo(name = "get_system", defaultPhase = LifecyclePhase.INSTALL, threadSafe = true)
public class MyPlugin extends AbstractMojo {

    @Parameter(property = "pathToProps", defaultValue = "/.")
    private String pathToProps;

    @Override
    public void execute() {
        var props = getProperties();
        String pathToFile = props.getProperty("pathToFile",null);

        var systemInfo = formSystemData();
        getLog().info(systemInfo.toString());

        writeSystemInfoToFile(systemInfo,pathToFile);
    }

    private SystemInfo formSystemData(){

        SystemInfo info = new SystemInfo();

        info.setCountOfProc(Runtime.getRuntime().availableProcessors());
        info.setOsName(System.getProperty("os.name"));

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String dateTime = LocalDateTime.now().format(formatter);
        info.setDateTime(dateTime);

        long freeMem =  Runtime.getRuntime().freeMemory();
        long maxMem = Runtime.getRuntime().maxMemory();
        info.setJvmData(new JvmData(maxMem,freeMem));

        File[] roots = File.listRoots();
        List<FsData> fsInfo = new ArrayList<>();
        for (var root : roots) {
            var fsData = new FsData(
                    root.getAbsolutePath(),
                    root.getTotalSpace(),
                    root.getFreeSpace());
            fsInfo.add(fsData);
        }
        info.setFsData(fsInfo);

        return info;
    }

    private void writeSystemInfoToFile(SystemInfo systemInfo, String path){
        File f = new File(path);
        if(f.exists())
            f.delete();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try{
            writer.writeValue(new File(path), systemInfo);
        } catch (IOException e) {
            getLog().error("Error with write json with system data.");
        }
    }

    private Properties getProperties(){

        String basePath = new File(".").getAbsolutePath();
        String pathToProps = basePath+this.pathToProps;

        getLog().info("Path to pros: "+pathToProps);

        Properties props = new Properties();

        try(FileInputStream in = new FileInputStream(pathToProps);
            InputStreamReader rrr = new InputStreamReader(in, Charset.forName("Windows-1251"))){
            props.load(rrr);
        }catch (IOException e) {
            getLog().error("Can't load properties.");
        }

        return props;
    }
}
