package myflink.demo;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

public class RedisSinkDemo {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.readTextFile("D:\\IdeaProjects\\my-flink-project-master\\src\\main\\resources\\redisDate.txt");

        SingleOutputStreamOperator<Tuple2<String, String>> map = source.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String value) throws Exception {
                String[] split = value.split(",");
//                System.out.println(split[0]);

                return new Tuple2<String, String>(split[0], split[1]);
            }
        });

//        map.print();
        FlinkJedisPoolConfig jedisSink = new FlinkJedisPoolConfig.Builder().setHost("r-uf65o1de3317wyw7se.redis.rds.aliyuncs.com").setPort(6379).setPassword("uls4sZPDoDfGbHlI").build();
        map.addSink(new RedisSink<>(jedisSink,new MyRedisMapper()));

        env.execute("redisSink");


    }
}

class MyRedisMapper implements RedisMapper<Tuple2<String, String>> {


    @Override
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(RedisCommand.HSET,"sensor_wendu");
    }

    @Override
    public String getKeyFromData(Tuple2<String, String> stringStringTuple2) {
        return stringStringTuple2.f0;
    }

    @Override
    public String getValueFromData(Tuple2<String, String> stringStringTuple2) {
        return stringStringTuple2.f1;
    }
}
