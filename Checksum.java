/*
A checksum program, in the context of computer science and data integrity, is a software tool or algorithm used to verify the integrity of data. It calculates a checksum, which is a fixed-size string of characters generated from the data. This checksum is typically a relatively small and unique representation of the data. Checksums are used to detect errors that may occur during data transmission or storage, such as data corruption or tampering.

The basic idea behind a checksum program is to generate a checksum value before and after data transfer or storage. If the two checksums match, it indicates that the data has not been altered during the process. If they don't match, it suggests that the data has been corrupted or tampered with in some way.

Checksum programs are commonly used in various applications, including:

Data transmission: When sending data over a network or the internet, checksums are often used to ensure that the data arrives intact. The receiving end calculates the checksum of the received data and compares it to the original checksum. If they match, the data is considered valid.

File integrity: Checksums can be used to verify the integrity of files. Users can generate a checksum for a file and compare it to a reference checksum to check if the file has been modified.

Data storage: Checksums can be used to detect and correct errors in stored data, such as on hard drives or optical media.

Cryptographic security: In some cryptographic applications, checksums can be used as part of data authentication and verification processes to ensure data hasn't been tampered with.

Common checksum algorithms include CRC32, MD5, SHA-1, and SHA-256. However, it's important to note that some of these algorithms are no longer considered secure for cryptographic purposes due to vulnerabilities, so more secure algorithms like SHA-256 are recommended for cryptographic applications.

Overall, a checksum program helps ensure data integrity and provides a simple way to detect errors or tampering in data.

*/


import java.util.*;
 
class Checksum
{

    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("[•] Enter the string input:");
        String input = scan.next();
        int checksum = generateChecksum(input);
        // Call the method to create the checksum
        System.out.print("\n [○] The checksum generated is = "
                + Integer.toHexString(checksum));
        System.out.print("\n\n[♣] Enter the data to be sent:");
        input = scan.next();
        System.out.print("\n\n[♣] Enter the checksum to be sent:");
        checksum = Integer.parseInt((scan.next()), 16);
        // User inputs data as hexadecimal value but it will be stored as a
        // decimal value unless it is converted into hexadecimal first.
        receive(input, checksum);
        scan.close();
    }
 
    static int generateChecksum(String s)
    {
        String hex_value = new String();
        // 'hex_value' will be used to store various hex values as a string
        int x, i, checksum = 0;
        // 'x' will be used for general purpose storage of integer values
        // 'i' is used for loops
        // 'checksum' will store the final checksum
        for (i = 0; i < s.length() - 2; i = i + 2)
        {
            x = (int) (s.charAt(i));
            hex_value = Integer.toHexString(x);
            x = (int) (s.charAt(i + 1));
            hex_value = hex_value + Integer.toHexString(x);
            // Extract two characters and get their hexadecimal ASCII values
            System.out.println(s.charAt(i) + "" + s.charAt(i + 1) + " : "
                    + hex_value);
            x = Integer.parseInt(hex_value, 16);
            // Convert the hex_value into int and store it
            checksum += x;
            // Add 'x' into 'checksum'
        }
        if (s.length() % 2 == 0)
        {
            // If number of characters is even, then repeat above loop's steps
            // one more time.
            x = (int) (s.charAt(i));
            hex_value = Integer.toHexString(x);
            x = (int) (s.charAt(i + 1));
            hex_value = hex_value + Integer.toHexString(x);
            System.out.println(s.charAt(i) + "" + s.charAt(i + 1) + " : "
                    + hex_value);
            x = Integer.parseInt(hex_value, 16);
        }
        else
        {
            // If number of characters is odd, last 2 digits will be 00.
            x = (int) (s.charAt(i));
            hex_value = "00" + Integer.toHexString(x);
            x = Integer.parseInt(hex_value, 16);
            System.out.println(s.charAt(i) + " : " + hex_value);
        }
        checksum += x;
        // Add the generated value of 'x' from the if-else case into 'checksum'
        hex_value = Integer.toHexString(checksum);
        // Convert into hexadecimal string
        if (hex_value.length() > 4)
        {
            // If a carry is generated, then we wrap the carry
            int carry = Integer.parseInt(("" + hex_value.charAt(0)), 16);
            // Get the value of the carry bit
            hex_value = hex_value.substring(1, 5);
            // Remove it from the string
            checksum = Integer.parseInt(hex_value, 16);
            // Convert it into an int
            checksum += carry;
            // Add it to the checksum
        }
        checksum = generateComplement(checksum);
        // Get the complement
        return checksum;
    }
 
    static void receive(String s, int checksum)
    {
        int generated_checksum = generateChecksum(s);
        // Calculate checksum of received data
        generated_checksum = generateComplement(generated_checksum);
        // Then get its complement, since generated checksum is complemented
        int syndrome = generated_checksum + checksum;
        // Syndrome is addition of the 2 checksums
        syndrome = generateComplement(syndrome);
        // It is complemented
        System.out.print("\n[*] Syndrome = " + Integer.toHexString(syndrome));
        if (syndrome == 0)
        {
            System.out.print("\n\n[☺] Data is received without error.");
        }
        else
        {
            System.out.print("\n\n[!] There is an error in the received data.");
        }
    }
 
    static int generateComplement(int checksum)
    {
        // Generates 15's complement of a hexadecimal value
        checksum = Integer.parseInt("FFFF", 16) - checksum;
        return checksum;
    }
}