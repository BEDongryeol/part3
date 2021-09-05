package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스
public class BasicInterestCalculator implements InterestCalculator{
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;
        // TODO:
        //  예금 계좌의 경우 잔액이 1000만원 이상은 이자율이 50%,
        //  500만원 이상은 7%, 100만원 이상은 4%, 1만원 이상은 2%, 그 외에는 1% 입니다.
        if (balance.compareTo(BigDecimal.valueOf(10000000)) >= 0) {
            interest = BigDecimal.valueOf(50);
        } else if (balance.compareTo(BigDecimal.valueOf(5_000_000)) >= 0) {
            interest = BigDecimal.valueOf(7);
        } else if (balance.compareTo(BigDecimal.valueOf(1_000_000)) >= 0) {
            interest = BigDecimal.valueOf(4);
        } else if (balance.compareTo(BigDecimal.valueOf(10_000)) >= 0) {
            interest = BigDecimal.valueOf(2);
        } else {
            interest = BigDecimal.valueOf(1);
        }
        return balance.multiply(interest.multiply(BigDecimal.valueOf(0.01)));
    }
}
