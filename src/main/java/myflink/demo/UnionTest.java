package myflink.demo;

import myflink.bean.CurrencyType;
import myflink.bean.ExchangeRateInfo;
import myflink.dataSource.ExchangeRateDataSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class UnionTest {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<ExchangeRateInfo> usdToCny = env.addSource(new ExchangeRateDataSource(CurrencyType.USD, CurrencyType.CNY, 7, 6),"USD-CNY");
        //EUR -> CNY 汇率流
        DataStreamSource<ExchangeRateInfo> eurToCny = env.addSource(new ExchangeRateDataSource(CurrencyType.EUR, CurrencyType.CNY, 8, 7),"EUR-CNY");
        //AUD -> CNY 汇率流
        DataStreamSource<ExchangeRateInfo> audToCny = env.addSource(new ExchangeRateDataSource(CurrencyType.AUD, CurrencyType.CNY, 5, 4),"AUD-CNY");
        //三个流合并为一个流
        DataStream<ExchangeRateInfo> allExchangeRate = usdToCny.union(eurToCny).union(audToCny);
        //将流标准输出
        allExchangeRate.print();

        env.execute("Flink Streaming Java API Skeleton");

    }


}
