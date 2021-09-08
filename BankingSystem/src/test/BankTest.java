package test;

import account.Account;
import bank.Bank;
import bank.CentralBank;
import bank.SavingBank;

import java.util.ArrayList;
import java.util.Scanner;

public class BankTest {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();
    public static void main(String[] args) throws Exception {

        CentralBank centralBank = CentralBank.getInstance();
        // 예금 계좌와 적금 계좌 생성
        Bank bank1 = new Bank();
        SavingBank bank2 = new SavingBank();
        ArrayList<Account> accountList = new ArrayList<>();

        accountList.add(bank1.createAccount());
        accountList.add(bank2.createAccount());
        centralBank.setAccountList(accountList);

        boolean isActive = true;
        while (isActive) {
            System.out.println("\n1. 계좌 목록 | 2. 출금 | 3. 입금 | 4. 송금 | 5. 종료");
            int menuNo = scanner.nextInt();
            switch (menuNo) {
                case 1:
                    int sizeOfBank = centralBank.getAccountList().size();
                    for (int i=0; i<sizeOfBank; i++) {
                        centralBank.getAccountList().get(i).getAccountInfo(centralBank.getAccountList().get(i));
                        System.out.println();
                    }
                    break;
                case 2:
                    bank2.withdraw();
                    break;
                case 3:
                    bank2.deposit();
                    break;
                case 4:
                    bank2.transfer();
                    break;
                case 5:
                    isActive = false;
                    break;
            }
        }
        System.out.println("뱅킹 프로그램을 종료합니다.");
    }
}
