package br.com.souza.file_stock_async.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record Stock(String ticker,
                    String company,
                    BigDecimal value,
                    LocalDateTime timestamp) {

    private static StatusCreatorStock STATUS = StatusCreatorStock.CREATING;
    private static List<String> THREADS = List.of(Thread.currentThread().getName());

    public StatusCreatorStock status(){
        return STATUS;
    }

    public List<String> threads(){
        return THREADS;
    }

    public void markStatusCreated(){
        STATUS = StatusCreatorStock.CREATED;
    }

    public void markStatusError(){
        STATUS = StatusCreatorStock.ERROR;
    }

    public void addIdentityThread(final String threadName){
        if(Objects.isNull(threadName) || threadName.isEmpty()){
            return;
        }
        THREADS = Stream
                .concat(THREADS.stream(), Stream.of(threadName))
                .toList();
    }

}
