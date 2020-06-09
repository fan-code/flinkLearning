package myflink.demo;

import myflink.bean.UserAction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;
import java.util.Map;

public class CepLearn02 {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Pattern<String, String> pattern = Pattern.<String>begin("start").where(
                new SimpleCondition<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        return value.equals("c");
                    }
                }
        ).followedBy("middle").where(new SimpleCondition<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return value.equals("a");
            }
        }).oneOrMore().consecutive().followedBy("end1").where(new SimpleCondition<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return value.equals("b");
            }
        });

        DataStreamSource<String> input = env.fromElements(WORDS);
        PatternStream<String> patternStream = CEP.pattern(input, pattern);
        SingleOutputStreamOperator<String> select = patternStream.select(new PatternSelectFunction<String, String>() {

            @Override
            public String select(Map<String, List<String>> map) throws Exception {
                List<String> start = map.get("start");
                List<String> middle = map.get("middle");
                List<String> end1 = map.get("end1");
                return start.toString()+"****"+middle.toString()+"++++"+end1.toString();
            }
        });
        select.print();
        env.execute("jjjj");
    }

    public static final String[] WORDS = new String[]{
            "c",
            "d",
            "a",
            "a",
            "a",
            "d",
            "a",
            "b"
    };
}