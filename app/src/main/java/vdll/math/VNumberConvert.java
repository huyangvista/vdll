package vdll.math;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;

public class VNumberConvert {


    public static void main(String[] args) {
        v16();
        //vceshi();
    }


    private static void vdic() {
        while (true) {
            System.out.println("进制转换");
            System.out.print("输入整形数据: ");

            Scanner input = new Scanner(System.in);
            String vsin = input.nextLine();
            if (vsin.equals("exit")) break;
            Integer vin = Integer.parseInt(vsin);
            String vs6 = Integer.toString(vin, 6);
            int vi10 = Integer.parseInt(vs6, 6);

            System.out.println("10i -> 6s  " + vs6);
            System.out.println("6s -> 10i  " + vi10);
            System.out.println();
        }
    }

    private static void vdicb() {
        while (true) {
            System.out.println("进制转换");
            System.out.print("输入整形数据: ");

            Scanner input = new Scanner(System.in);
            String vsin = input.nextLine();

            if (vsin.equals("exit")) break;

            String vs6 = new BigInteger(vsin).toString(6);
            String vs10 = new BigInteger(vs6, 6).toString();

            System.out.println("10 -> 6  " + vs6);
            System.out.println("6 -> 10  " + vs10);
            System.out.println();

        }
    }

    private static void v16() {
        while (true) {
            System.out.println("进制转换");
            System.out.print("输入整形数据: ");
            Scanner input = new Scanner(System.in);
            String vsin = input.nextLine();
            Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
            if (!pattern.matcher(vsin).matches()) {
                System.err.println("!=> 输入错误！");
                System.err.println();
                continue;
            }
            if (vsin.equals("exit")) break;
            String vs6 = new BigInteger(vsin, 36).toString(6);
            String vs10 = new BigInteger(vs6, 6).toString(36);
            System.out.println("36 -> 6  " + vs6);
            System.out.println("6 -> 36  " + vs10);
            System.out.println();
        }
    }





    BigInteger bi;

    public BigInteger _jie(BigInteger nbi) {
        if (nbi.equals(BigInteger.ZERO)) {
            return bi;
        }
        bi = bi.multiply(nbi);
        nbi = nbi.subtract(BigInteger.ONE);
        return _jie(nbi);
    }

    public BigInteger jie(String snbi) {
        bi = BigInteger.ONE;
        return _jie(new BigInteger(snbi));
    }

    //endregion

}
