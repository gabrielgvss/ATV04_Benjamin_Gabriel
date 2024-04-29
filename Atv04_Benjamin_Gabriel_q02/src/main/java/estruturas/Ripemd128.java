package estruturas;

import java.nio.charset.StandardCharsets;

public class Ripemd128 {
    public static String ripemd128(String input) {
        int[] buffer = new int[4];
        int a = 0x67452301;
        int b = 0xefcdab89;
        int c = 0x98badcfe;
        int d = 0x10325476;

        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        int[] x = new int[16];

        for (int i = 0; i < bytes.length; i += 64) {
            for (int j = 0; j < 16; j++) {
                int index = i + j * 4;
                if (index + 3 < bytes.length) {
                    x[j] = (bytes[index] & 0xFF) | ((bytes[index + 1] & 0xFF) << 8) | ((bytes[index + 2] & 0xFF) << 16) | ((bytes[index + 3] & 0xFF) << 24);
                } else {
                    int value = 0;
                    for (int k = 0; k < 4; k++) {
                        if (index + k < bytes.length) {
                            value |= (bytes[index + k] & 0xFF) << (k * 8);
                        }
                    }
                    x[j] = value;
                }
            }

            int aa = a;
            int bb = b;
            int cc = c;
            int dd = d;

            a = ff(a, b, c, d, x[0], 11);
            d = ff(d, a, b, c, x[1], 14);
            c = ff(c, d, a, b, x[2], 15);
            b = ff(b, c, d, a, x[3], 12);
            a = ff(a, b, c, d, x[4], 5);
            d = ff(d, a, b, c, x[5], 8);
            c = ff(c, d, a, b, x[6], 7);
            b = ff(b, c, d, a, x[7], 9);
            a = ff(a, b, c, d, x[8], 11);
            d = ff(d, a, b, c, x[9], 13);
            c = ff(c, d, a, b, x[10], 14);
            b = ff(b, c, d, a, x[11], 15);
            a = ff(a, b, c, d, x[12], 6);
            d = ff(d, a, b, c, x[13], 7);
            c = ff(c, d, a, b, x[14], 9);
            b = ff(b, c, d, a, x[15], 8);

            a = gg(a, b, c, d, x[7], 7);
            d = gg(d, a, b, c, x[4], 6);
            c = gg(c, d, a, b, x[13], 8);
            b = gg(b, c, d, a, x[1], 13);
            a = gg(a, b, c, d, x[10], 11);
            d = gg(d, a, b, c, x[6], 9);
            c = gg(c, d, a, b, x[15], 7);
            b = gg(b, c, d, a, x[3], 15);
            a = gg(a, b, c, d, x[12], 7);
            d = gg(d, a, b, c, x[0], 12);
            c = gg(c, d, a, b, x[9], 15);
            b = gg(b, c, d, a, x[5], 9);
            a = gg(a, b, c, d, x[2], 11);
            d = gg(d, a, b, c, x[14], 7);
            c = gg(c, d, a, b, x[11], 13);
            b = gg(b, c, d, a, x[8], 12);

            a = hh(a, b, c, d, x[3], 11);
            d = hh(d, a, b, c, x[10], 13);
            c = hh(c, d, a, b, x[14], 6);
            b = hh(b, c, d, a, x[4], 7);
            a = hh(a, b, c, d, x[9], 14);
            d = hh(d, a, b, c, x[15], 9);
            c = hh(c, d, a, b, x[8], 13);
            b = hh(b, c, d, a, x[1], 15);
            a = hh(a, b, c, d, x[2], 14);
            d = hh(d, a, b, c, x[7], 8);
            c = hh(c, d, a, b, x[0], 13);
            b = hh(b, c, d, a, x[6], 6);
            a = hh(a, b, c, d, x[13], 5);
            d = hh(d, a, b, c, x[11], 12);
            c = hh(c, d, a, b, x[5], 7);
            b = hh(b, c, d, a, x[12], 5);

            a = ii(a, b, c, d, x[1], 11);
            d = ii(d, a, b, c, x[9], 12);
            c = ii(c, d, a, b, x[11], 14);
            b = ii(b, c, d, a, x[10], 15);
            a = ii(a, b, c, d, x[0], 14);
            d = ii(d, a, b, c, x[8], 15);
            c = ii(c, d, a, b, x[12], 9);
            b = ii(b, c, d, a, x[4], 8);
            a = ii(a, b, c, d, x[13], 9);
            d = ii(d, a, b, c, x[3], 14);
            c = ii(c, d, a, b, x[7], 5);
            b = ii(b, c, d, a, x[15], 6);
            a = ii(a, b, c, d, x[14], 8);
            d = ii(d, a, b, c, x[5], 6);
            c = ii(c, d, a, b, x[6], 5);
            b = ii(b, c, d, a, x[2], 12);

            a += aa;
            b += bb;
            c += cc;
            d += dd;
        }

        return toHexString(a) + toHexString(b) + toHexString(c) + toHexString(d);
    }

    private static String toHexString(int value) {
        return String.format("%08x", value);
    }

    private static int ff(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + f(b, c, d) + x, s);
    }

    private static int gg(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + g(b, c, d) + x + 0x5a827999, s);
    }

    private static int hh(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + h(b, c, d) + x + 0x6ed9eba1, s);
    }

    private static int ii(int a, int b, int c, int d, int x, int s) {
        return rotateLeft(a + i(b, c, d) + x + 0x8f1bbcdc, s);
    }

    private static int f(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private static int g(int x, int y, int z) {
        return (x & z) | (y & ~z);
    }

    private static int h(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private static int i(int x, int y, int z) {
        return y ^ (x | ~z);
    }

    private static int rotateLeft(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

}
