package fintech.driver;

import fintech.model.Account;
import fintech.model.DepositTransaction;
import fintech.model.TransferTransaction;
import fintech.model.WithdrawTransaction;
import fintech.model.NegativeBalanceException;
import java.util.ArrayList;
import java.util.Scanner;
import fintech.model.Transaction;
/**
 * @author 12S24054 Rahel Juri Elisabet Silaban
 */

class fintechmodelTransaction {
    int id;
    String username;
    double amount;
    String timestamp;
    String description;

    public fintechmodelTransaction(int id, String username,
                    double amount, String timestamp, String description) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }
}

public class Driver2 {

    public static void main(String[] _args) {

        // codes
    Scanner input = new Scanner(System.in);

        ArrayList<Account> daftarAkun = new ArrayList<>();
        ArrayList<Transaction> daftarTransaksi = new ArrayList<>();
        ArrayList<fintechmodelTransaction> catatan = new ArrayList<>();

        int idTransaksi = 0;

        while (true) {
            String perintah = input.nextLine();
            if (perintah.equals("---")) break;

            String[] bagian = perintah.split("#");
            String aksi = bagian[0];

            try {

                // Membuat akun baru
                if (aksi.equals("create-account")) {
                    daftarAkun.add(new Account(bagian[1], bagian[2]));
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

                            daftarTransaksi.add(new DepositTransaction(
                                idTransaksi, user, jumlah, waktu, keterangan
                            ));

                            catatan.add(new fintechmodelTransaction(
                                idTransaksi, user,
                                jumlah, waktu, keterangan
                            ));
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

                            if (!akun.withdraw(jumlah)) {
                                throw new NegativeBalanceException("Saldo tidak cukup");
                            }

                            idTransaksi++;

                            daftarTransaksi.add(new WithdrawTransaction(
                                idTransaksi, user, jumlah, waktu, keterangan
                            ));

                            catatan.add(new fintechmodelTransaction(
                                idTransaksi, user,
                                jumlah, waktu, keterangan
                            ));
                            break;
                        }
                    }
                }

                // Proses transfer
                else if (aksi.equals("transfer")) {
                    String pengirim = bagian[1];
                    String penerima = bagian[2];
                    double jumlah = Double.parseDouble(bagian[3]);
                    String waktu = bagian[4];
                    String keterangan = bagian[5];

                    Account akunPengirim = null;
                    Account akunPenerima = null;

                    for (Account akun : daftarAkun) {
                        if (akun.getUsername().equals(pengirim)) akunPengirim = akun;
                        if (akun.getUsername().equals(penerima)) akunPenerima = akun;
                    }

                    if (akunPengirim != null && akunPenerima != null) {

                        if (!akunPengirim.withdraw(jumlah)) {
                            throw new NegativeBalanceException("Saldo tidak cukup");
                        }

                        akunPenerima.deposit(jumlah);

                        idTransaksi++;

                        daftarTransaksi.add(new TransferTransaction(
                            idTransaksi, pengirim, penerima, jumlah, waktu, keterangan
                        ));

                        catatan.add(new fintechmodelTransaction(
                            idTransaksi, pengirim,
                            jumlah, waktu, keterangan
                        ));
                    }
                }

                // Menampilkan akun dan riwayat transaksi
                else if (aksi.equals("show-account")) {
                    String user = bagian[1];

                    Account targetAkun = null;
                    for (Account akun : daftarAkun) {
                        if (akun.getUsername().equals(user)) {
                            targetAkun = akun;
                            break;
                        }
                    }

                    if (targetAkun != null) {

                        System.out.println(
                            targetAkun.getUsername() + "|" +
                            targetAkun.getName() + "|" +
                            targetAkun.getBalance()
                        );

                        ArrayList<fintechmodelTransaction> transaksiUser = new ArrayList<>();
                        for (fintechmodelTransaction t : catatan) {
                            if (t.username.equals(user)) {
                                transaksiUser.add(t);
                            }
                        }

                        fintechmodelTransaction.sort(transaksiUser, new <fintechmodelTransaction>() {
                            public int compare(fintechmodelTransaction a, fintechmodelTransaction b) {
                                return a.timestamp.compareTo(b.timestamp);
                            }
                        });

                        for (fintechmodelTransaction t : transaksiUser) {
                            System.out.println(
                                t.id + "|" +
                                t.amount + "|" +
                                t.timestamp + "|" +
                                t.description
                            );
                        }
                    }
                }

            } catch (NegativeBalanceException e) {
                // Abaikan error saldo tidak cukup
            }
        }

        input.close();

    }

}