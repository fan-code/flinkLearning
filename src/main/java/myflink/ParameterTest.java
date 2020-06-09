package myflink;

import org.apache.flink.api.java.utils.ParameterTool;

public class ParameterTest {

    public static void main(String[] args) {
        ParameterTool fromArgs = ParameterTool.fromArgs(args);
        String name = fromArgs.get("name");
        String location = fromArgs.get("location");
        String title = fromArgs.get("title");
        System.out.println(name+"----"+location+"++++"+title);
    }
}
