package bank;

import account.Account;
import account.SavingAccount;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Bank{
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");


    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> bankHash = new HashMap<>();
        BasicInterestCalculator basic = new BasicInterestCalculator();
        SavingInterestCalculator saving = new SavingInterestCalculator();
        bankHash.put("N", basic);
        bankHash.put("S", saving);

        ArrayList<Account> bankList = CentralBank.getInstance().getAccountList();

        // 계좌번호 입력
        Account account = null;
        InterestCalculator accInt = null;

        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            for (Account acc: bankList) {
                if (acc.getAccNo() == accNo){
                    account = acc;
                }
            }

            for (Map.Entry<String, InterestCalculator> accHash: bankHash.entrySet()) {
                if (account.getCategory() == accHash.getKey()) {
                   accInt = accHash.getValue();
                }
            }
            break;
        }

        // 출금처리
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        System.out.println("\n출금할 금액을 입력하세요.");
        Scanner scanner = new Scanner(System.in);
        BigDecimal withMoney = scanner.nextBigDecimal();

        try {
            account.withdraw(withMoney);
            accInt.getInterest(account.getBalance());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");

    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        }catch (){
            //TODO: 오류 throw
        }
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현

        return account;
    }

    public void transfer() throws Exception{
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
        //TODO
        System.out.println("\n어느 계좌번호로 보내시려나요?");
        //TODO
        System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
        //TODO
        System.out.println("\n적금 계좌로는 송금이 불가합니다.");
        //TODO
        System.out.println("\n송금할 금액을 입력하세요.");
        //TODO
        }


}
