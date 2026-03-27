package fintech.driver;

import fintech.model.Account;
import fintech.model.DepositTransaction;
import fintech.model.TransferTransaction;
import fintech.model.WithdrawTransaction;
import fintech.model.NegativeBalanceException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S24054 Rahel Juri Elisabet Silaban
 */
public class Driver1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Account> daftarAkun = new ArrayList<>();
        ArrayList<DepositTransaction> daftarTransaksi = new ArrayList<>();

        int idTransaksi = 0;

        while (true) {
            String perintah = input.nextLine();

            
            if (perintah.equals("---")) {
                break;
            }

            String[] bagian = perintah.split("#");
            String aksi = bagian[0];

            //membuat akun
            if (aksi.equals("create-account")) {
                String nama = bagian[1];
                String user = bagian[2];

                daftarAkun.add(new Account(nama, user));
            }

            //proses deposit
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
                        break;
                    }
                }
            }

            //proses withdraw
            else if (aksi.equals("withdraw")) {
                String user = bagian[1];
                double jumlah = Double.parseDouble(bagian[2]);
                String waktu = bagian[3];
                String keterangan = bagian[4];

                for (Account akun : daftarAkun) {
                    if (akun.getUsername().equals(user)) {

                        if (akun.withdraw(jumlah)) {
                            idTransaksi++;
                            daftarTransaksi.add(new Transaksi(
                                idTransaksi, user, jumlah, waktu, keterangan
                            ));
                        }

                        break;
                    }
                }
            }

            // transfer antar akun
            else if (aksi.equals("transfer")) {
                String pengirim = bagian[1];
                String penerima = bagian[2];
                double jumlah = Double.parseDouble(bagian[3]);
                String waktu = bagian[4];
                String keterangan = bagian[5];

                Account akunPengirim = null;
                Account akunPenerima = null;

                //cari akun pengirim dan penerima
                for (Account akun : daftarAkun) {
                    if (akun.getUsername().equals(pengirim)) {
                        akunPengirim = akun;
                    }
                    if (akun.getUsername().equals(penerima)) {
                        akunPenerima = akun;
                    }
                }

                //proses transfer jika kedua akun ditemukan
                if (akunPengirim != null && akunPenerima != null) {
                    if (akunPengirim.withdraw(jumlah)) {

                        akunPenerima.deposit(jumlah);

                        idTransaksi++;
                        daftarTransaksi.add(new Transaksi(
                            idTransaksi, pengirim, penerima, jumlah, waktu, keterangan
                        ));
                    }
                }
            }
        }

        input.close();

    }

}