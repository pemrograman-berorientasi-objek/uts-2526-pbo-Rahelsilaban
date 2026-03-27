package fintech.driver;

import fintech.model.Account;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author 12S24054 Rahel Juri Elisabet Silaban
 */
public class Driver4 {


public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
        ArrayList<Account> daftarAkun = new ArrayList<>();

        while (true) {
            String perintah = input.nextLine();

            // Berhenti jika menemukan ---
            if (perintah.equals("---")) {
                break;
            }

            String[] bagian = perintah.split("#");

            // Proses pembuatan akun
            if (bagian[0].equals("create-account")) {
                String nama = bagian[1];
                String user = bagian[2];

                Account akunBaru = new Account(nama, user);
                daftarAkun.add(akunBaru);
            }
        }

        // Menampilkan seluruh akun
        for (Account akun : daftarAkun) {
            System.out.println(
                akun.getUsername() + "|" +
                akun.getName() + "|" +
                akun.getBalance()
            );
        }

        input.close();
    }
}

