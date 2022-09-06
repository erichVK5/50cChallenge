/*
 *
 * Copyright Erich. S. Heinzle 2022
 *
 *
 */

import java.math.BigInteger;

/*
 *
 * Some code to tackle a topical decryption challenge
 *
 */

public class Cracker
{

	public static void histogram(String s)
	{
		int i,j,min,max,unique;
		min = 128;
		max = 0;
		unique = 0;
		int[] histo = new int[128];
		System.out.println("-- Histogram for " + s + " --");
		for (i = 0; i < 128; i++)
		{
			histo[i] = 0;
		}
		for (i = 0; i < s.length(); i++)
		{
			j = (int)(s.charAt(i));
			if(j < min) min = j;
			if(j > max) max = j;
			// System.out.println(s.charAt(i) + ", " + j);
			histo[j]++;
		}
		System.out.println("freq analysis:");
		for (i = 0; i < 128; i++)
		{
			if(histo[i] > 0)
			{
				unique++;
				System.out.println((char)(i) + ": " + histo[i]);
			}
		}
		System.out.println("Min char: " + min + ", Max char: " + max);
		System.out.println("Unique chars: " + unique);
		System.out.println("String length: " + s.length() + "\n");
	}

	public static void caeserCipher(String s, int base, int offset, int modulo)
	{
		String output = "";
		int i;
		for(i = 0; i < s.length(); i++)
		{
			output = output + (char)((((int)s.charAt(i) - base) + offset)%modulo + base);
		}
		//System.out.println(s);
		System.out.println(output);
	}

	public static void simpleScytale(String s, int hspan)
	{
		int i,j;
                System.out.println("\n--simple Scytale decode of " + s + " using span " + hspan + ": ");
                for(i = 0; i < hspan; i++)
                {
                        j = i + hspan;
                        System.out.print(s.charAt(i));
                        while (j < s.length())
                        {
                                System.out.print(s.charAt(j));
                                j += hspan;
                        }
			System.out.println();
                }
	}

	public static String scytale(String s, int span, int width, boolean first)
	{
		String sub;
		String result = "";
		if(span*width < s.length())
		{
			sub = s.substring(0,span*width);
		}
		else
		{
			sub = s;
		}
		int i,j;
		for(i = 0; i < span; i++)
		{
			j = i + span;
			result = result + sub.charAt(i);
			while (j < sub.length())
			{
				result = result + sub.charAt(j);
				j += span;
			}
			//System.out.println();
		}
		if(sub.length() < s.length())
		{
			result = result + scytale(s.substring(sub.length()), span, width, false);
		}
		if(first)
                {
                        System.out.println("\n-- Scytale decode " + span + "x" + width + " of: " + s + "\n" + result + "\n");
                }
		return result;
	}

	public static int charHexToInt(char c)
	{
		switch (c) 
		{
			case '0': return 0;
                        case '1': return 1;
                        case '2': return 2;
                        case '3': return 3;
                        case '4': return 4;
                        case '5': return 5;
			case '6': return 6;
                        case '7': return 7;
                        case '8': return 8;
                        case '9': return 9;
			case 'A':
			case 'a': return 10;
			case 'B':
			case 'b': return 11;
			case 'C':
			case 'c': return 12;
			case 'D':
			case 'd': return 13;
			case 'E':
			case 'e': return 14;
			case 'F':
			case 'f': return 15;
		}
		return 0; // return zero for garbage		
	}

	public static int hexByteToInt(String hb)
	{
		String sanitised = hb;
		if (hb.length() < 2)
		{
			sanitised = sanitised + "00";
		}
		return charHexToInt(sanitised.charAt(0))*16 + charHexToInt(sanitised.charAt(1));
	}

	public static String hexStringToASCII(String hs)
	{
		int i;
		String sanitised = hs;
		String result = "";
		if(sanitised.length()%2 != 0)
		{
			sanitised = sanitised + "0";
		}
		for(i = 0; i < hs.length(); i = i + 2)
		{	
			char c = (char)hexByteToInt(hs.substring(i,i+2));
			result = result + c;
		}
		return result;
	}

	public static String xorBabyYeah(String s, int[] mask)
	{
		int i,span;
		span = mask.length;
		int maskNibble,hexNibble,masked;
		String result = "";
		for(i = 0; i < s.length(); i++)
		{
			maskNibble = mask[i%span];
			hexNibble = charHexToInt(s.charAt(i));
			masked = hexNibble^maskNibble;
			result = result + String.format("%x", masked);
		}
		return result;
	}

	public static String beaufort(String s, String key, String label)
	{
		int i;
		String result = "";
		for(i = 0; i < s.length(); i++)
		{
			result = result + (char)((int)key.charAt(0) - (int)s.charAt(i) + 65);
		}
		System.out.println("-- Beaufort result for " + label + "\n" + s + "\nusing key " + key +  "--\n" + result + "\n");
		return result;
	}

	public static void vigenere(String s, String key, int keyOffset, int direction)
	{
		int i;
		int dir = -1;
		int charA, charB;
		if(direction > 0)
		{
			dir = 1;
		}
		System.out.println("-- vigenere ciphertext: " + s + " and key: " + key + " using direction " + direction + " --");
		for(i = 0; i < s.length(); i++)
		{
			charA = (int)s.charAt(i) - 65;
			charB = (int)key.charAt((i+keyOffset)%key.length()) - 65;
			System.out.print((char)((charA + dir*charB + 26)%26 + 65));
		}
		System.out.println();
	}

	public static void substitution(String cipher, String subs)
	{
		int i;
		for(i = 0; i < cipher.length(); i++)
		{
			System.out.print(subs.charAt((int)(cipher.charAt(i)-65)));
		}
	}

	public static String substChars(String cipher, String in, String out, String label)
	{
		int i,j;
		String r = "";
		for(i=0; i < cipher.length(); i++)
		{
			char c = cipher.charAt(i);
			int k = in.indexOf(c);
			if(k >= 0)
			{
				r = r + out.charAt(k);
			}
			else
			{
				r = r + c;
			}
		}
		System.out.println("-- substition cipher using substitution list: " + in + " -> " + out + "\non " + label + ": " + cipher + ":\n\t" + r + "\n");
		return r;
	}

	public static String reverseString(String s)
	{
		String r = "";
		int i;
		for(i = s.length()-1; i >= 0; i--)
		{
			r = r + s.charAt(i);
		}
		return r;
	}

        public static String invertBinaryString(String s)
        {
                String r = "";
                int i;
                for(i = 0; i < s.length(); i++)
                {
			if(s.charAt(i) == '1')
			{
                        	r = r + '0';
			}
			else
			{
				r = r + '1';
			}
                }
                return r;
        }

	public static void printBinStringAsHex(String s, Boolean reverse, Boolean invert, String label)
	{
		String hexString;
		String v = s;
		if(invert)
		{
			v = invertBinaryString(s);
			System.out.println("Inverted string: " + v);
		}
		if(reverse) 
		{
			v = reverseString(v);
			hexString = new BigInteger(v, 2).toString(16);
			System.out.println("Reversed string: " + v);
		}
		else
		{
			hexString = new BigInteger(v, 2).toString(16);
		}
                System.out.println("\nbinary string " + label + " as hex:" + hexString);
	}

	public static int[] ASCIIhexToMask(String m)
	{
		// this obviously breaks for m.length() != 5 //
		int[] result = {charHexToInt(m.charAt(0)),
				charHexToInt(m.charAt(1)),
				charHexToInt(m.charAt(2)),
				charHexToInt(m.charAt(3)),
				charHexToInt(m.charAt(4))};
		return result;
	}

	public static void main(String[] args)
	{
		String hex = "E3B8287D4290F7233814D7A47A291DC0F71B2806D1A53B311CC4B97A0E1CC2B93B31068593332F10C6A3352F14D1B27A3514D6F7382F1AD0B0322955D1B83D3801CDB2287D05C0B82A311085A033291D85A3323855D6BC333119D6FB7A3C11C4A72E3C17CCBB33290C85B6343955CCBA3B3A1CCBB62E341ACBF72E3255CAA73F2F14D1B27A341B85A3323855D6BB333055C4A53F3C55C7B22E2A10C0B97A291DC0F73E3413C3BE392819D1F73B331185A3323855CCBA2A3206D6BE3831108B";
		String cipher1 = "DVZIVZFWZXRLFHRMXLMXVKGZMWNVGRXFOLFHRMVCVXFGRLM";
		String cipher2 = "URMWXOZIRGBRM7DRWGSC5WVKGS";
		String cipher3 = "BGOAMVOEIATSIRLNGTTNEOGRERGXNTEAIFCECAIEOALEKFNR5LWEFCHDEEAEEE7NMDRXX5";
		String c3bin =   "0111110010110001110110111100001110100011011001101100111110011011001101";
		String c3bininv ="1000001101001110001001000011110001011100100110010011000001100100110010";
		String c3binrev ="1011001101100111110011011001101100010111000011110110111000110100111110";
		String c3binrevinv = "0100110010011000001100100110010011101000111100001001000111001011000001";
		String c2bin =   "11010101000100101001010010";
		String c1bin =   "10000000001011110000111000110111001110100101100";
		histogram(cipher1);
		substChars(cipher1, "VGZLIRSOXFDBMUWCKHN", "ETAORIHLCUWYNFDXPSM", "cipher1");
		histogram(cipher2);
		substChars(cipher2, "VGZLIRSOXFDBMUWCK", "ETAORIHLCUWYNFDXP", "cipher2");
		String beaufortKey = "Z";
                beaufort(cipher1, beaufortKey, "cipher1");
                beaufort(cipher2, beaufortKey, "cipher2");
		simpleScytale(cipher3, 7);
		String cipher3decode = scytale(cipher3, 7, 5, true);
		String ASCIImask = cipher3decode.substring(cipher3decode.length()-5);
		System.out.println("Extracted XOR mask: " + ASCIImask);	
		String r = xorBabyYeah(hex, ASCIIhexToMask(ASCIImask));
		System.out.println("\n-- XOR'ed result --\n" + hexStringToASCII(r));
	}	
}

