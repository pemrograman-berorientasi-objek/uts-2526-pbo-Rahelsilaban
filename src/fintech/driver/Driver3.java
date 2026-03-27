package fintech.driver;

import fintech.model.Account;
import fintech.model.DepositTransaction;
import fintech.model.Transaction;
import fintech.model.WithdrawTransaction;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S24054 Rahel Juri Elisabet Silaban
 */

public class Driver3 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Account> daftarAkun = new ArrayList<>();
        ArrayList<Transaction> daftarTransaksi = new ArrayList<>();

        int idTransaksi = 0;

        while (true) {
            String perintah = input.nextLine();

            // Berhenti jika menemukan ---
            if (perintah.equals("---")) {
                break;
            }

            String[] bagian = perintah.split("#");
            String aksi = bagian[0];

            // Membuat akun baru
            if (aksi.equals("create-account")) {
                String nama = bagian[1];
                String user = bagian[2];

                Account akunBaru = new Account(nama, user);
                daftarAkun.add(akunBaru);
            }

            // Proses deposit
            else if (aksi.equals("deposit")) {
                String user = bagian[1];
                double jumlah = Double.parseDouble(bagian[2]);
                String waktu = bagian[3];
                String keterangan = bagian[4];

                for (Account akun : daftarAkun) {
                    if (akun.getUsername().equals(user)) {
                        akun.deposit(jumlah);

                        idTransaksi++;
                        Transaction trx = new DepositTransaction(
                            idTransaksi, user, jumlah, waktu, keterangan
                        );
                        daftarTransaksi.add(trx);
                        break;
                    }
                }
            }

            // Proses withdraw
            else if (aksi.equals("withdraw")) {
                String user = bagian[1];
                double jumlah = Double.parseDouble(bagian[2]);
                String waktu = bagian[3];
                String keterangan = bagian[4];

                for (Account akun : daftarAkun) {
                    if (akun.getUsername().equals(user)) {

                        boolean berhasil = akun.withdraw(jumlah);

                        if (berhasil) {
                            idTransaksi++;
                            Transaction trx = new WithdrawTransaction(
                                idTransaksi, user, jumlah, waktu, keterangan
                            );
                            daftarTransaksi.add(trx);
                        }

                        break;
                    }
                }
            }
        }

        // Menampilkan semua akun
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