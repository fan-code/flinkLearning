package myflink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.StreamTokenizer;

public class CheckpointLearn {

    public static void main(String[] args) {
        // 创建 execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 通过连接 socket 获取输入数据，这里连接到本地9000端口，如果9000端口已被占用，请换一个端口
        DataStream<String> text = env.socketTextStream("localhost", 9000, "\n");

    }

}
