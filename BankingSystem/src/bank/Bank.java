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

    ArrayList<Account> bankList = CentralBank.getInstance().getAccountList();

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
            System.out.println(withMoney + "원이 출금되었습니다. 잔액은 " + account.getBalance() + "원입니다.");
            System.out.println("잔액 " + account.getBalance() + "의 이자는 " + accInt.getInterest(account.getBalance()) + "원 입니다.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deposit() {
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        int count = 0;
        int trial = 0;
        Account account = null;

        while (count == 1 || trial > 5) {
            trial++;
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요. 남은 시도 가능 횟수는 " + (5 - trial) + "회입니다.");
            Scanner scanner = new Scanner(System.in);
            String accNo = scanner.next();

            for (Account acc : bankList) {
                if (acc.getAccNo() == accNo) {
                    count++;
                    account = acc;
                }
            }
        }

        if (trial == 6) {
            System.out.println("가능 횟수를 초과하였습니다.");
            return;
        }

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        Scanner scanner = new Scanner(System.in);
        BigDecimal amount = scanner.nextBigDecimal();
        account.deposit(amount);
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        Account account = null;
        String owner = null;
        BigDecimal balance;

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
        Scanner scan1 = new Scanner(System.in);
        balance = scan1.nextBigDecimal();

        // 계좌 생성
        account = new Account(accNo, owner, balance);


        }catch (InputMismatchException e){
            //TODO: 오류 throw
            if (owner == null) {
                throw new InputMismatchException("이름은 null이 될 수 없습니다.");
            }
            if (owner.length() <= 1) {
                throw new InputMismatchException("이름이 너무 짧습니다.");
            }
            if (account == null) {
                throw new InputMismatchException("계좌 생성에 실패하였습니다.");
            }
            if owner.matches("[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣+]"){
                throw new InputMismatchException("올바른 이름이 아닙니다.");
            }
        }
        return account;
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
