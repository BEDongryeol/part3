package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Bank{
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static Boolean flag = false;
    public static DecimalFormat df = new DecimalFormat("#,###");
    public static HashMap<String, InterestCalculator> bankHash = new HashMap<>();
    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        bankHash.put("N", new BasicInterestCalculator());
        bankHash.put("S", new SavingInterestCalculator());
        // 계좌번호 입력
        Account account = null;
        InterestCalculator intCalc = null;
        while(!flag){
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            do {
                System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
                account = findAccount(scanner.next());
            } while (account == null);
            if (account.getCategory().equals("S")) {
                intCalc = bankHash.get("S");
                System.out.println("적금 계좌에서 출금이 실행됩니다.");
                new SavingBank().withdraw((SavingAccount) account);
            } else {
                intCalc = bankHash.get("N");
                System.out.println("일반 예금 계좌에서 출금이 실행됩니다.");
                flag = true;
            }
        }

        // 출금처리
        // TODO: interestCalculators 이용하여 이자 조회 및 출금
        BigDecimal interest;
        try {
            // 출금액이 0원 이하일 경우 다시 입력
            BigDecimal withMoney = checkAmountOut();

            while (account.getBalance().compareTo(withMoney) < 0) {
                System.out.println("잔고가 부족합니다. 출금할 금액을 다시 입력하세요.");
                withMoney = checkAmountOut();
                if (account.getBalance().compareTo(withMoney) >= 0) break;
            }

            account.withdraw(withMoney);

            // 잔액이 없는 경우 이자 미지급
            if (account.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("잔액이 없어 이자는 지급되지않습니다.");
            } else {
                interest = intCalc.getInterest(account.getBalance());
                System.out.printf("잔액 " + account.getBalance() + "의 이자는 %s원 입니다.\n", df.format(interest));
            }
        } catch (Exception e){
            throw e;
        }
    }

    public void deposit() throws Exception {
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        Account account;
        try {
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            account = findAccount(scanner.next());
        } catch (Exception e) {
            this.deposit();
            throw new Exception("존재하지 않는 계좌입니다.\n계좌를 입력하세요");
        }

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        account.deposit(scanner.nextBigDecimal());
    }

    public Account createAccount() throws Exception {
        //TODO: 계좌 생성하는 메서드 구현
        Account account;
        String owner;
        BigDecimal balance;
        seq++;
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            String accNo = "0000"+seq;
            // 이름 입력
            owner = checkName();
            // 입금액 입력
            balance = checkAmountIn();
            // 계좌 생성
            account = new Account(accNo, owner, balance);
            System.out.println(owner + "님의 일반 계좌가 생성되었습니다. (계좌번호 : " +accNo+")");

        } catch (Exception e){
            //TODO: 오류 throw
            throw e;
        }
        return account;
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        for (Account acc : CentralBank.getInstance().getAccountList()) {
            if (acc.getAccNo().equals(accNo)) {
                return acc;
            }
        }
        return null;
    }

    public void transfer() throws NullPointerException, InputMismatchException{
        Account accountF;
        Account accountT;
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        // 송금 계좌번호 입력
        accountF = accountFrom();
        //TODO
        accountT = accountTo();
        //TODO
        if (accountF.getAccNo().equals(accountT.getAccNo())) {
            System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
            accountTo();
        }
        //TODO
        System.out.println("\n송금할 금액을 입력하세요.");
        BigDecimal amount = scanner.nextBigDecimal();
        if (accountF.getBalance().compareTo(amount) >= 0) {
            try {
                accountF.withdraw(amount);
                accountT.deposit(amount);
                System.out.println("\n" + accountF.getOwner() +"님이 " + accountT.getOwner() + "님에게 " + amount +"원을 송금하였습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("잔액이 모자랍니다. 다시 시도해주세요");
        }
    }

    public Account accountFrom() {
        Account account = null;
        try {
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            account = findAccount(scanner.next());
            if (account.getCategory().equals("S")) {
                account = (SavingAccount)account;
                if (account.getBalance().compareTo(((SavingAccount) account).getGoalAmount()) < 0) {
                    System.out.println("목표 금액에 미달되어 출금할 수 없습니다.");
                    accountFrom();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("유효하지 않은 계좌입니다.");
            accountFrom();
        } catch (InputMismatchException e) {
            System.out.println("올바른 계좌를 입력해주세요.");
            accountFrom();
        }
        return account;
    }

    public Account accountTo() {
        Account account = null;
        try {
            System.out.println("\n어느 계좌번호로 보내시려나요?");
            account = findAccount(scanner.next());
            if (account.getCategory().equals("S")) {
                System.out.println("\n적금 계좌로는 송금이 불가합니다.");
                accountTo();
            }
        } catch (NullPointerException e) {
            System.out.println("\n유효하지 않은 계좌입니다.");
            accountTo();
        } catch (InputMismatchException e) {
            System.out.println("\n올바른 계좌를 입력해주세요.");
            accountTo();
        }

        return account;
    }

    public String checkName(){
        String name;
        do {
            // 이름 등록
            System.out.println("이름을 한글로 입력하세요");
            name = scanner.next();
            if (name.matches("^[가-힣]+")) return name;
        } while (true);
    }

    public BigDecimal checkAmountIn(){
        BigDecimal balance = null;
        try {
            // 입금액
            System.out.println("금액을 숫자로 입력하세요");
            balance = scanner.nextBigDecimal();

            if (balance.compareTo(BigDecimal.ZERO) <= 0) {
                scanner.nextLine();
                System.out.println("0원 이하는 입금할 수 없습니다.");
                this.checkAmountIn();
            }

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("\n숫자로만 입력해주세요");
            this.checkAmountIn();
        }
        return balance;
    }

    public BigDecimal checkAmountOut(){
        BigDecimal balance = null;
        try {
            // 입금액
            System.out.println("금액을 숫자로 입력하세요");
            balance = scanner.nextBigDecimal();

            if (balance.compareTo(BigDecimal.ZERO) <= 0) {
                scanner.nextLine();
                System.out.println("0원 이하는 출금할 수 없습니다.");
                this.checkAmountIn();
            }

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("\n숫자로만 입력해주세요");
            this.checkAmountIn();
        }
        return balance;
    }
}
