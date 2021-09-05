package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        BigDecimal bal = account.getBalance();
        BigDecimal goal = account.getGoalAmount();
        if (bal.compareTo(goal) < 0) {
            throw new Exception("목표 금액에 미달되어 출금할 수 없습니다.");
        }
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
        SavingAccount account = null;
        String owner = null;
        BigDecimal balance;
        BigDecimal goalAmount;

        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            seq++;
            String accNo = "0000"+seq;

            // 이름 등록
            System.out.println("이름을 입력하세요");
            Scanner scan = new Scanner(System.in);
            owner = scan.next();

            // 입금액
            System.out.println("입금액을 입력하세요");
            balance = scan.nextBigDecimal();

            // 목표 금액
            System.out.println("목표 금액을 입력하세요");
            goalAmount = scan.nextBigDecimal();

            // 계좌 생성
            account = new SavingAccount(accNo, owner, balance, goalAmount);
            System.out.println(owner + "님의 적금 계좌가 생성되었습니다. (계좌번호 : " +accNo+")");

        }catch (NoSuchElementException e){
            //TODO: 오류 throw
           throw e;
        }
        return account;
    }
}