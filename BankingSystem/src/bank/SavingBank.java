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
            String accNo = df.format(seq);

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


        }catch (NoSuchElementException e){
            //TODO: 오류 throw
            if (owner == null) {
                throw new NoSuchElementException("이름은 null이 될 수 없습니다.");
            }
            if (owner.length() <= 1) {
                throw new NoSuchElementException("이름이 너무 짧습니다.");
            }
            if (account == null) {
                throw new NoSuchElementException("계좌 생성에 실패하였습니다.");
            }
            if (!(owner.matches("[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣+]"))) {
                throw new NoSuchElementException("올바른 이름이 아닙니다.");
            }
        }
        return account;
    }
}