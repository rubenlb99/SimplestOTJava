package simplestOT;

import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

public class OT implements ObliviousTransfer{
	
	//Algoritmos
	public static final String SIMPLEST_OT = "SIMPLEST_OT";
	public static final String SIMPLEST_OT_EC = "SIMPLEST_OT_EC";
	
	public static final String b3072 = "FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1"
			+ "      29024E08 8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD"
			+ "      EF9519B3 CD3A431B 302B0A6D F25F1437 4FE1356D 6D51C245"
			+ "      E485B576 625E7EC6 F44C42E9 A637ED6B 0BFF5CB6 F406B7ED"
			+ "      EE386BFB 5A899FA5 AE9F2411 7C4B1FE6 49286651 ECE45B3D"
			+ "      C2007CB8 A163BF05 98DA4836 1C55D39A 69163FA8 FD24CF5F"
			+ "      83655D23 DCA3AD96 1C62F356 208552BB 9ED52907 7096966D"
			+ "      670C354E 4ABC9804 F1746C08 CA18217C 32905E46 2E36CE3B"
			+ "      E39E772C 180E8603 9B2783A2 EC07A28F B5C55DF0 6F4C52C9"
			+ "      DE2BCBF6 95581718 3995497C EA956AE5 15D22618 98FA0510\r\n"
			+ "      15728E5A 8AAAC42D AD33170D 04507A33 A85521AB DF1CBA64\r\n"
			+ "      ECFB8504 58DBEF0A 8AEA7157 5D060C7D B3970F85 A6E1E4C7\r\n"
			+ "      ABF5AE8C DB0933D7 1E8C94E0 4A25619D CEE3D226 1AD2EE6B\r\n"
			+ "      F12FFA06 D98A0864 D8760273 3EC86A64 521F2B18 177B200C\r\n"
			+ "      BBE11757 7A615D6C 770988C0 BAD946E2 08E24FA0 74E5AB31\r\n"
			+ "      43DB5BFC E0FD108E 4B82D120 A93AD2CA FFFFFFFF FFFFFFFF";

	public static final String b6144 = "FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1 29024E08\r\n"
			+ "   8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD EF9519B3 CD3A431B\r\n"
			+ "   302B0A6D F25F1437 4FE1356D 6D51C245 E485B576 625E7EC6 F44C42E9\r\n"
			+ "   A637ED6B 0BFF5CB6 F406B7ED EE386BFB 5A899FA5 AE9F2411 7C4B1FE6\r\n"
			+ "   49286651 ECE45B3D C2007CB8 A163BF05 98DA4836 1C55D39A 69163FA8\r\n"
			+ "   FD24CF5F 83655D23 DCA3AD96 1C62F356 208552BB 9ED52907 7096966D\r\n"
			+ "   670C354E 4ABC9804 F1746C08 CA18217C 32905E46 2E36CE3B E39E772C\r\n"
			+ "   180E8603 9B2783A2 EC07A28F B5C55DF0 6F4C52C9 DE2BCBF6 95581718\r\n"
			+ "   3995497C EA956AE5 15D22618 98FA0510 15728E5A 8AAAC42D AD33170D\r\n"
			+ "   04507A33 A85521AB DF1CBA64 ECFB8504 58DBEF0A 8AEA7157 5D060C7D\r\n"
			+ "   B3970F85 A6E1E4C7 ABF5AE8C DB0933D7 1E8C94E0 4A25619D CEE3D226\r\n"
			+ "   1AD2EE6B F12FFA06 D98A0864 D8760273 3EC86A64 521F2B18 177B200C\r\n"
			+ "   BBE11757 7A615D6C 770988C0 BAD946E2 08E24FA0 74E5AB31 43DB5BFC\r\n"
			+ "   E0FD108E 4B82D120 A9210801 1A723C12 A787E6D7 88719A10 BDBA5B26\r\n"
			+ "   99C32718 6AF4E23C 1A946834 B6150BDA 2583E9CA 2AD44CE8 DBBBC2DB\r\n"
			+ "   04DE8EF9 2E8EFC14 1FBECAA6 287C5947 4E6BC05D 99B2964F A090C3A2\r\n"
			+ "   233BA186 515BE7ED 1F612970 CEE2D7AF B81BDD76 2170481C D0069127\r\n"
			+ "   D5B05AA9 93B4EA98 8D8FDDC1 86FFB7DC 90A6C08F 4DF435C9 34028492\r\n"
			+ "   36C3FAB4 D27C7026 C1D4DCB2 602646DE C9751E76 3DBA37BD F8FF9406\r\n"
			+ "   AD9E530E E5DB382F 413001AE B06A53ED 9027D831 179727B0 865A8918\r\n"
			+ "   DA3EDBEB CF9B14ED 44CE6CBA CED4BB1B DB7F1447 E6CC254B 33205151\r\n"
			+ "   2BD7AF42 6FB8F401 378CD2BF 5983CA01 C64B92EC F032EA15 D1721D03\r\n"
			+ "   F482D7CE 6E74FEF6 D55E702F 46980C82 B5A84031 900B1C9E 59E7C97F\r\n"
			+ "   BEC7E8F3 23A97A7E 36CC88BE 0F1D45B7 FF585AC5 4BD407B2 2B4154AA\r\n"
			+ "   CC8F6D7E BF48E1D8 14CC5ED2 0F8037E0 A79715EE F29BE328 06A1D58B\r\n"
			+ "   B7C5DA76 F550AA3D 8A1FBFF0 EB19CCB1 A313D55C DA56C9EC 2EF29632\r\n"
			+ "   387FE8D7 6E3C0468 043E8F66 3F4860EE 12BF2D5B 0B7474D6 E694F91E\r\n"
			+ "   6DCC4024 FFFFFFFF FFFFFFFF";

	public static final String b8192 = "FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1\r\n"
			+ "      29024E08 8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD\r\n"
			+ "      EF9519B3 CD3A431B 302B0A6D F25F1437 4FE1356D 6D51C245\r\n"
			+ "      E485B576 625E7EC6 F44C42E9 A637ED6B 0BFF5CB6 F406B7ED\r\n"
			+ "      EE386BFB 5A899FA5 AE9F2411 7C4B1FE6 49286651 ECE45B3D\r\n"
			+ "      C2007CB8 A163BF05 98DA4836 1C55D39A 69163FA8 FD24CF5F\r\n"
			+ "      83655D23 DCA3AD96 1C62F356 208552BB 9ED52907 7096966D\r\n"
			+ "      670C354E 4ABC9804 F1746C08 CA18217C 32905E46 2E36CE3B\r\n"
			+ "      E39E772C 180E8603 9B2783A2 EC07A28F B5C55DF0 6F4C52C9\r\n"
			+ "      DE2BCBF6 95581718 3995497C EA956AE5 15D22618 98FA0510\r\n"
			+ "      15728E5A 8AAAC42D AD33170D 04507A33 A85521AB DF1CBA64\r\n"
			+ "      ECFB8504 58DBEF0A 8AEA7157 5D060C7D B3970F85 A6E1E4C7\r\n"
			+ "      ABF5AE8C DB0933D7 1E8C94E0 4A25619D CEE3D226 1AD2EE6B\r\n"
			+ "      F12FFA06 D98A0864 D8760273 3EC86A64 521F2B18 177B200C\r\n"
			+ "      BBE11757 7A615D6C 770988C0 BAD946E2 08E24FA0 74E5AB31\r\n"
			+ "      43DB5BFC E0FD108E 4B82D120 A9210801 1A723C12 A787E6D7\r\n"
			+ "      88719A10 BDBA5B26 99C32718 6AF4E23C 1A946834 B6150BDA\r\n"
			+ "      2583E9CA 2AD44CE8 DBBBC2DB 04DE8EF9 2E8EFC14 1FBECAA6\r\n"
			+ "      287C5947 4E6BC05D 99B2964F A090C3A2 233BA186 515BE7ED\r\n"
			+ "      1F612970 CEE2D7AF B81BDD76 2170481C D0069127 D5B05AA9\r\n"
			+ "      93B4EA98 8D8FDDC1 86FFB7DC 90A6C08F 4DF435C9 34028492\r\n"
			+ "      36C3FAB4 D27C7026 C1D4DCB2 602646DE C9751E76 3DBA37BD\r\n"
			+ "      F8FF9406 AD9E530E E5DB382F 413001AE B06A53ED 9027D831\r\n"
			+ "      179727B0 865A8918 DA3EDBEB CF9B14ED 44CE6CBA CED4BB1B\r\n"
			+ "      DB7F1447 E6CC254B 33205151 2BD7AF42 6FB8F401 378CD2BF\r\n"
			+ "      5983CA01 C64B92EC F032EA15 D1721D03 F482D7CE 6E74FEF6\r\n"
			+ "      D55E702F 46980C82 B5A84031 900B1C9E 59E7C97F BEC7E8F3\r\n"
			+ "      23A97A7E 36CC88BE 0F1D45B7 FF585AC5 4BD407B2 2B4154AA\r\n"
			+ "      CC8F6D7E BF48E1D8 14CC5ED2 0F8037E0 A79715EE F29BE328\r\n"
			+ "      06A1D58B B7C5DA76 F550AA3D 8A1FBFF0 EB19CCB1 A313D55C\r\n"
			+ "      DA56C9EC 2EF29632 387FE8D7 6E3C0468 043E8F66 3F4860EE\r\n"
			+ "      12BF2D5B 0B7474D6 E694F91E 6DBE1159 74A3926F 12FEE5E4\r\n"
			+ "      38777CB6 A932DF8C D8BEC4D0 73B931BA 3BC832B6 8D9DD300\r\n"
			+ "      741FA7BF 8AFC47ED 2576F693 6BA42466 3AAB639C 5AE4F568\r\n"
			+ "      3423B474 2BF1C978 238F16CB E39D652D E3FDB8BE FC848AD9\r\n"
			+ "      22222E04 A4037C07 13EB57A8 1A23F0C7 3473FC64 6CEA306B\r\n"
			+ "      4BCBC886 2F8385DD FA9D4B7F A2C087E8 79683303 ED5BDD3A\r\n"
			+ "      062B3CF5 B3A278A6 6D2A13F8 3F44F82D DF310EE0 74AB6A36\r\n"
			+ "      4597E899 A0255DC1 64F31CC5 0846851D F9AB4819 5DED7EA1\r\n"
			+ "      B1D510BD 7EE74D73 FAF36BC3 1ECFA268 359046F4 EB879F92\r\n"
			+ "      4009438B 481C6CD7 889A002E D5EE382B C9190DA6 FC026E47\r\n"
			+ "      9558E447 5677E9AA 9E3050E2 765694DF C81F56E8 80B96E71\r\n"
			+ "      60C980DD 98EDD3DF FFFFFFFF FFFFFFFF";
	
	//Interfaz
	private ObliviousTransfer ObTransfer ;
	
	public OT(String algoritmo) {
		switch (algoritmo) {
		case SIMPLEST_OT: {
			
			BigInteger n = new BigInteger(b3072.replace(" ", "").replace("\r\n", ""), 16);			
			BigInteger g = new BigInteger("2");
			
			this.ObTransfer = new SimplestOT(g, n);
			
			break;
		}
		case SIMPLEST_OT_EC: {
			
			BigInteger n = new BigInteger(b3072.replace(" ", "").replace("\r\n", ""), 16);			
			
			this.ObTransfer = new SimplestOTEC(n);
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + algoritmo);
		}
	}

	@Override
	public String[] receive(byte[] choices, Random r, Socket channel) {
		// TODO Auto-generated method stub
		return ObTransfer.receive(choices, r, channel);
	}

	@Override
	public void send(String[] messages, Random r, Socket channel) {
		// TODO Auto-generated method stub
		ObTransfer.send(messages, r, channel);
	}
	
	
}
