package com.shinsegae_inc.sitims.common.util;

public class Base64
{

    static char base64Map[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte codes[];

    static 
    {
        codes = new byte[256];
        for(int i = 65; i <= 90; i++)
            codes[i] = (byte)(i - 65);

        for(int j = 97; j <= 122; j++)
            codes[j] = (byte)((26 + j) - 97);

        for(int k = 48; k <= 57; k++)
            codes[k] = (byte)((52 + k) - 48);

        codes[43] = 62;
        codes[47] = 63;
    }

    /*
    public static void main(String[] arg) {
    	String enc = encode(arg[0]);
    	String dec = decode(enc);
    	
    	System.out.println( enc );
    	System.out.println( dec );
    }
    */

    public static String decode(String a)
    {
        return new String(decode(a.toCharArray()));
    }

    public static byte[] decode(char ac[])
    {
        return decode(ac, 0, ac.length);
    }

    public static byte[] decode(char ac[], int i, int j)
    {
        int k = j;
        for(int l = 0; l < j; l++)
            if(ac[l] > '\377' || codes[ac[l]] < 0)
                k--;

        int i1 = (k / 4) * 3;
        if(k % 4 == 3)
            i1 += 2;
        if(k % 4 == 2)
            i1++;
        byte abyte0[] = new byte[i1];
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        for(int i2 = 0; i2 < j; i2++)
        {
            byte byte0 = ac[i2] <= '\377' ? codes[ac[i2]] : -1;
            if(byte0 >= 0)
            {
                k1 <<= 6;
                j1 += 6;
                k1 |= byte0;
                if(j1 >= 8)
                {
                    j1 -= 8;
                    abyte0[l1++] = (byte)(k1 >> j1 & 0xff);
                }
            }
        }

        return abyte0;
    }


    public static String encode(String a)
    {
        return new String(encode(a.getBytes()));
    }

    public static char[] encode(byte abyte0[])
    {
        return encode(abyte0, 0, abyte0.length);
    }

    public static String decodeUTF8(String a) throws Exception {
        return new String(decode(a.toCharArray()), "UTF-8");
    }

    public static String encodeUTF8(String a) throws Exception {
        return new String(encode(a.getBytes("UTF-8")));
    }


    public static char[] encode(byte abyte0[], int i, int j)
    {
        char ac[] = new char[((j + 2) / 3) * 4];
//        boolean flag = false;
//        boolean flag2 = false;
        int k = 0;
        for(int l = 0; k < j; l += 4)
        {
            boolean flag1 = false;
            boolean flag3 = false;
            int i1 = 0xff & abyte0[k];
            i1 <<= 8;
            if(k + 1 < j)
            {
                i1 |= 0xff & abyte0[k + 1];
                flag1 = true;
            }
            i1 <<= 8;
            if(k + 2 < j)
            {
                i1 |= 0xff & abyte0[k + 2];
                flag3 = true;
            }
            ac[l + 3] = base64Map[flag3 ? i1 & 0x3f : 64];
            i1 >>= 6;
            ac[l + 2] = base64Map[flag1 ? i1 & 0x3f : 64];
            i1 >>= 6;
            ac[l + 1] = base64Map[i1 & 0x3f];
            i1 >>= 6;
            ac[l] = base64Map[i1 & 0x3f];
            k += 3;
        }

        return ac;
    }

}
