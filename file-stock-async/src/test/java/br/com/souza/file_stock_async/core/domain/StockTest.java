package br.com.souza.file_stock_async.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void testProperties(){
        final String tickerExpected = "ITAS4";
        final String companyExpected = "Holding Itaú SA";
        final BigDecimal valueExpected = new BigDecimal("10.40");
        final LocalDateTime timestampExpected = LocalDateTime.now();
        final StatusCreatorStock statusExpected = StatusCreatorStock.CREATING;
        final List<String> threadsExpected = List.of(Thread.currentThread().getName());

        final Stock actual = new Stock(tickerExpected, companyExpected, valueExpected,
                timestampExpected);

        assertNotNull(tickerExpected);
        assertEquals(tickerExpected, actual.ticker());
        assertEquals(companyExpected, actual.company());
        assertEquals(valueExpected, actual.value());
        assertEquals(timestampExpected, actual.timestamp());
        assertEquals(statusExpected, actual.status());
        assertNotNull(actual.threads());
        assertArrayEquals(threadsExpected.toArray(), actual.threads().toArray());
    }

    @Test
    void whenMarkStatusCreated_shouldStatusEqualsCreated(){
        final Stock stock = buildStock();

        stock.markStatusCreated();

        assertEquals(StatusCreatorStock.CREATED, stock.status());
    }

    @Test
    void whenMarkStatusError_shouldStatusEqualsError(){
        final Stock stock = buildStock();

        stock.markStatusError();

        assertEquals(StatusCreatorStock.ERROR, stock.status());
    }

    @Test
    void whenAddThreadName_shouldAddInList(){
        final String threadNameExpected = "NewThreadAdded";
        final Stock stock = buildStock();
        final int sizeExpected = stock.threads().size() + 1;

        stock.addIdentityThread(threadNameExpected);

        assertEquals(sizeExpected, stock.threads().size());
        assertTrue(stock.threads().stream().anyMatch(e -> Objects.equals(e, threadNameExpected)));
    }

    @Test
    void whenAddThreadNameNull_notShouldAddInList(){
        final String newThreadName = null;
        final Stock stock = buildStock();
        final int sizeExpected = stock.threads().size();

        stock.addIdentityThread(newThreadName);

        assertEquals(sizeExpected, stock.threads().size());
        assertFalse(stock.threads().stream().anyMatch(e -> Objects.equals(e, newThreadName)));
    }

    @Test
    void whenAddThreadNameEmpty_notShouldAddInList(){
        final String newThreadName = "";
        final Stock stock = buildStock();
        final int sizeExpected = stock.threads().size();

        stock.addIdentityThread(newThreadName);

        assertEquals(sizeExpected, stock.threads().size());
        assertFalse(stock.threads().stream().anyMatch(e -> Objects.equals(e, newThreadName)));
    }

    private static Stock buildStock(){
        final String ticker = "ITAS4";
        final String company = "Holding Itaú SA";
        final BigDecimal value = new BigDecimal("10.40");
        final LocalDateTime timestamp = LocalDateTime.now();

        return new Stock(ticker, company, value, timestamp);
    }

}