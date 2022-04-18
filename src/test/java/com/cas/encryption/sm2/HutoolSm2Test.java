package com.cas.encryption.sm2;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.cas.util.HexConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.GMCipherSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 2:58 下午
 * @desc 国密2【SM2】是非对称加密
 */
public class HutoolSm2Test {


    /**
     * 自定义密钥对
     */
    @Test
    public void test4() {
        String content = "我是Hanley";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        final SM2 sm2 = new SM2(pair.getPrivate(), pair.getPublic());
        byte[] sign = sm2.sign(content.getBytes());
        // true
        boolean verify = sm2.verify(content.getBytes(), sign);
        System.out.println("验签结果：" + verify);

    }

    /**
     * SM2签名和验签
     */
    @Test
    public void test3() {
        System.out.println("开始时间：" + System.currentTimeMillis());
        String content = "821E90D4D35D02506F60F5E543F9C64D8FF08AAFA1ADA73A07D28DD02DD56E15C43DAE5CC53D0FB14A4D4B95F7AD43E06FC39BBE96990E2F89F2A6A20E6D0531B767CA19470330C1619F35C7EA1703125167B9DBE0C8401A697613696C5A9D710F6A00D0B612B50B6881C15F8B642311886A61031982012C308201283081D0A003020102021001008631310010866000000000000100300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323233375A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D034200047F61538BB0C8190AF97A35AA6CC072D3A10B958BE8B25750C7F8A5F68A806DC14EAE57E23017AD7B8304A84A452AF81101C9C39263571FC5E3AC41E791E931F5300A06082A811CCF5501837503470030440220584586BE3C4943CC9D453858970DF62EDE4C8EAF647AE7F5D96CF4F47D8DD115022033DD3D62AB274B944231DAAA99E1115F37A9D7721601A1462604FBD93018B3CF13045F204E091424003100310030003200320034003100390039003200300033003200350032003800310035152A0032003000310038002E00300035002E00300032002D0032003000320038002E00300035002E003000321B821C50002F0039006A002F00340041004100510053006B005A004A005200670041004200410067004100410041005100410042004100410044002F003200770042004400410042007700540046005200670056004500520077005900460078006700660048006800770068004B006B00590074004B00690059006D004B006C0059003900510054004E0047005A0056006C0072006100570052005A0059006D00420077006600710047004900630048006500590065005700420069006A004C002B004F006D004B006100720074004C0061003000620049006600470031004D0053007600300071004700780074004B0033002F0032007700420044004100520034006600480079006F006C004B006C00490074004C0056004B007400630032004A007A0072006100320074007200610032007400720061003200740072006100320074007200610032007400720061003200740072006100320074007200610032007400720061003200740072006100320074007200610032007400720061003200740072006100320074007200610032007400720061003200740072006100320074007200610033002F007700410041005200430041004400570041004B00630044004100530049004100410068004500420041007800450042002F00380051004100480077004100410041005100550042004100510045004200410051004500410041004100410041004100410041004100410041004500430041007700510046004200670063004900430051006F004C002F003800510041007400520041004100410067004500440041007700490045004100770055004600420041005100410041004100460039004100510049004400410041005100520042005200490068004D005500450047004500310046006800420079004A007800460044004B0042006B00610045004900490030004B00780077005200560053003000660041006B004D0032004A007900670067006B004B004600680063005900470052006F006C004A00690063006F004B0053006F0030004E005400590033004F0044006B003600510030005200460052006B0064004900530055007000540056004600560057005600310068005A0057006D004E006B005A0057005A006E00610047006C007100630033005200310064006E0064003400650058007100440068004900570047006800340069004A00690070004B0054006C004A00570057006C00350069005A006D0071004B006A0070004B0057006D007000360069007000710072004B007A0074004C00570032007400370069003500750073004C00440078004D0058004700780038006A004A00790074004C00540031004E0058005700310039006A005A00320075004800690034002B0054006C003500750066006F003600650072007800380076005000300039006600620033002B0050006E0036002F00380051004100480077004500410041007700450042004100510045004200410051004500420041005100410041004100410041004100410041004500430041007700510046004200670063004900430051006F004C002F0038005100410074005200450041004100670045004300420041005100440042004100630046004200410051004100410051004A003300410041004500430041007800450045004200530045007800420068004A0042005500510064006800630052004D0069004D006F0045004900460045004B0052006F00620048004200430053004D007A00550076004100560059006E004C0052004300680059006B004E004F0045006C003800520063005900470052006F006D004A007900670070004B006A00550032004E007A00670035004F006B004E004500520055005A004800530045006C004B00550031005200560056006C00640059005700560070006A005A00470056006D005A0032006800700061006E004E003000640058005A003300650048006C00360067006F004F0045006800590061004800690049006D004B006B0070004F0055006C005A00610058006D004A006D0061006F0071004F006B007000610061006E0071004B006D007100730072004F003000740062006100330075004C006D0036007700730050004500780063006200480079004D006E004B003000740050005500310064006200580032004E006E0061003400750050006B003500650062006E0036004F006E007100380076005000300039006600620033002B0050006E0036002F0039006F004100440041004D00420041004100490052004100780045004100500077004400560078002B0064004B004D0034006F00480057006C0070004400450042004600480066002B0074004C003200780052006700590035007000670049006500740042006F007A00320070006300340036004300670051006E0055006300300055006300640036004D003400360043006700420052006D006A006A0046004E004A0070004D00670041005500410050004A00340034004600470054005500540054004B004200790061006A00610037006A004200350059004300670052005A005000500065006D00650074004E006A006D00560031002B00550067002F006A005300350079006100410048004B004D00550059004100500061006B0042004E004C0033003600550041004B0076007200530067006E004E0041004F0054005100610042006900350035007000650032006100540069006A0069006700510045006300550055005A00390036004B00590043006200630044005000650069006C0037005500670034003400370031004A00510059007800370055006E0066006A00720053006B005A006F003900360041004500490078005300300031006A006A00760055004600780063007000450070004C0075004200370064003600590068003700790071007200590033005600560075004E00510057004A003900700072004F0075004C003500570062003500590038002F00370078007100690038006800640073006D006700440062002F007400530049006E006E004E0051007A0061006F00750077003700410063003400370031006A006B0030006D00610041004C004D006C003000370073005300530063006D006F002F004F00620047004B0069007A007A0053005A006F00410074004A00630075006A00410071006300660053007400430033003100500074004A002B00590072004700420070007700610067004400710034005800560078006B0048004E0053003900540058004E003200640036003800440041005A007900760070005700370042004F007300730059006300480069006700430077004600780030006F0041006F004200700061004100450046004F0039007100510039004B0058007400510041006800390061004B00580069006900670051006E0030003400460048003000460047005000570067006A00500057006B0055004A006E00500053006700340078007A0053006E00380071005900780034006F0041007200330055007700680069004A004A007800360056007A00300038007A00530050006B006D0072004F006F00540074004A004B0063006E00700078005600410030007800410054005400540052005300550041004C00320070004B004B004B0041004300690069006B006F004100570069006B00700061004100480041003100700036005A00640043004E0074006A006E0035005400570057004B0065007200590035006F0041003600350057004200410070002F00610073007600530037006B00790052003700470050007A0043007400520061004100460046004100460041004200700044006B00550041004C005200530063006E00740052005100490054004A002F00380041003100300034005500450041003900610054006F006100520051004800330071004B005900370056004A003900420055007000710043003500790059007900500055005500410063003100630048004D0068004E005100470070003500750048004E00510030007800440061004B006400520074004E00410044004D00550075004B0058004600480053006700420075004B004400540075004B00510034004E004100440061005700670069006A0032006F0041004B0063004B00620053006900670043003700700038006800530035005400420078006B003400720070006B003500460063006E00410063004F00440058005600570035007A00450070003900710041004A00650031004800420046004C00530059006F0041005100430069006A007600520051004100740049006500310046004900660076005500680069006D006F005A00660066003000710059003100460049004D0071005400510042007A0056007700420035006A00660057006F0051004D006D007000370067006600760047004800760054003400720063006E006B0069006D004300520057003800730034007000360051007300330061007200360052004B004F004B0073004B006900690070007500560079006D00630074006B007A006400610062004C005A004E002F004400570073004600460049005600700058004B0073006A00410065004A00300050004B006B0055007A006B0047007400390034006C00590066004D004D00310057006B00730059007900430051004D00550037006B0075004A006B005500740057005A0062004E003100360063003100570049004B006E004200460055005300300047004B0042005100440053006900670052005A00730030004C007A004B007600710061003600690049005900550043007300480052003000330054006200760037006F007200660058007000510041002F004F004B004B0053006C0042006F0041006100530052003000460046004F006F006F0041004200530059006F00500070005200320070004400450050007000360030007800780038007600460053006500390051007A004F00450048005000650067004C00470048004F006D00620073006A0074006D0070004A004400730058004100700038003200310037007300460052006A0069006E00730075006100470055006B0056004E0037006A006F004300610058007A00350068007800740050003500560059002B00560054007900610054007A006F0075006800590066006A00530048005900490062006F006E006700720056006B004E006B0064004B00720034005200750052006900700046007A0069006B005500690052006D0041004600560070006200700055004800760055006B006E00410039003600720065005700720048004C0064004B0041005A004500310033006E007000560057005800350079005400560034006D003300420078006C0063003100470077006A0059002F004C0056005800490061004D002F00610063003000340041006900720059006900350071004F005A00630059006F00460059003000640047005800370078007800570079004B00780074004E006D0045005500590055006A0072003300720059006A004F003400410030004300730050006F003400700061004B005900680042005200520052005100410076006100670038006D0067006A004E004C00530047004E005900310056007500680030007100300052006D006F005A00310033004C0051004F004F0035006D007500670045007700490037006900700064007500520054004A00410066004D0046005300720055006D006800560065004C004C005A0050005400300071004B006500330045006A0062006C004F00330031004600580079006F007000680069006F0075004600690043004B004D004C0067004C0030004600540072007700610055004B00460046004A0078006D006B004F00770079006300380063005600450071006A0041007A00300070003800760057006C006A00470056006F004800590070006D00310048006D00620074003300790035003600550030007800660050006C0065004B0076002B00570044003100700044004700420054007500540079006B004300700068006500610072007A00430072006A003800560057005A006400370059006F0051006D0069006100460041004100740062004E0075004300490031007A00360056006B0032006F004A0059004B0065003100620053004400410070006F005500390068003100460046004C00690071004D007800500077006F00700061004B00410044003800610054004800650069006C002F0043006B004D00540046004E005A00630069006E003000680050007400510042006E00540052003400590030003100540069007200640077006D005200780056004D0067007100610054004E004500780034004E004F003400700069006D006C00420071005300680073006800370055006900720054005A00430051006300340071004A005A003200350079007500420036003000440048007A004C00670056004600450032004700770061005A004C004F007800470046004700610045004A004C004400690067004C006C00310063005900700072003400780051004400380074004D0063003000430049004A0054005500530041006C00710064004A00310071005700330069004C00480070005600430036006C006D007A0069007900320063006300560070007200300071004B0043005000590067004600540064004B0061004D0035004F003400740046004A0053003000790051006F00700070004A006F006F00410055006400610057006B0047006100580046004900590055006C004C0053004100300041004E005900560055006E005800440064004B00750047006F005A006C00790070004E004100300079006A003300700077004E004900650074004A003200710047006100490047004900360056004700640074004D006C0064002B00690069006F003100560079004D006B0069006D00550053004D0046003700690067005900460052004D006A0034002B0038004B006100700063004E006A0074005100440052005A0044006300550031007A007800310070004B006100780035007000430047006A0035006D007200580074006F00770073005900340046005A00740073006D002B0055005600730049004D004300710052006E004A006A007800530034006F006F007100690041007000440037003000760061006B006F0041004300630030005500480046004600410043003000410069006D006A003600550036006B004D004B005000770070004E00330070005100530061004100410030007800730047006E006400710061003100410046004700340058006100530061006700330031006100760066007500650039005A0077006600440055006D006A0052004D006E005000490071004E0077007700360055003500570079004B00550073004D0056004A0056007900480044005500590078005400390077007000720073004F0031004100580045005A0071006A004C0030006A004E0069006F00670032005400540053004A0062004E0058005400780079005400330072005400570073007600540054006B0045003500720054005500310052006D0078003900460049004B004B00590067006F006F0070006100410045006F006F004E004600410042006E0046004A00310070006F004F005400530035007000440046003600550068004F004B0051006E0046004D004C00440047005300610041004A004E0031004E004A0071006D002B006F00510071005300430032005300500053006700330069003700630067005500440053004600760048003600410056006E00530072006E007000550030006B006D003900380030007900700062004C0053004B00340064006B003600300076006E005A0036006D007000480055004700710037006F00520032006F0041006B004D006F00390061006A00610058003000710049007100610055004A00540045004200590073006100650067006F00430063005500340055004100580039004F00620044006B00560071004B0061007700620061005100780053006800710031003400350030004900420079004F0061005A004C00520061004200700061006A0044005A007000510061005A0049002B0069006B007A0052006D00670042005400520053004700690067004300420070006B0051002F004D007700480031004E005100530036006A004100670050007A0067006E0030004600630039004C004D007A004D005300530061006A004C0045003900360042006D0078004C00710033005800610076005000760056004B0061002F006C006C00360073006300560053007A0052006D00670052004D006A0035006B0042004A0037003100700044006B00630064004B0078007700630047007400470032006E0044004A00670039005200310070004D0075004C004C0047004F004F006C0047004B0055004800490070003100510057004D003200340070004E006F007800540079004F00610058004800460049005A0043003000590070006D00770056004F0052005300590070006900490064006D00420054005700360031004D00770077004B00690049007A0032006F0075004100300056004C0047003500410071004D0043006E0044006900710045004F00580055004A00490058004B00350079004200360031006600740064005100530059005900590068005700720041006D0066006400490053004B0061007300680048005100310052006D007A0072006C006300480076005400730031007A00630046003700500047004100510054006900720038004F007100710066003900590075005000700051004900310051006300390036004B0070007200660077006B005A0044006A00380061004B0041004F006500750059006A0048004A00360067003800670030007800510045004700340034004A0037004300720066004D00300061006F0046007900560035007A0056004B0051006C006E004F006100410045004A007900630030006C00460046004D004100700079004F007900480049004F004B006200520053004100300049004C0067005000770065004400360056005A0055006D00730059005A004600570049007200740030004700440038007700390036006C006F007400530037006D006D0044006D006E00690071006B006400330045006500700032006E00330071005A005A006B0062006F0077005000340031004E00690037006B00700070004100500057006B0042007000520039006100420069004D0075005200550054004B0061006E004A0071004A0035005500580037007A0041005500570046006300680077006100670075004A0053006F0032006A00720052004E0064006A004F00450048003400310055005A006900780079006100700049006900540044004F0061006600350052003200420031004900590064003800640071006A007000380054007400470034004B003100520042005A0074004A007400670059004500630064006100610052003500690076004A0030003900420069007000570043007400450054004700750047004A002B005900560059006B0069004500630043006A00480047004F00610059006A004D004400470069006B00620068006A005200530047006100390074004700490077006F00480056002B0061007A00620079004D004C0063007500420077004D003000550055007700490063005900360055006D004B004B004B005100420069006A0046004600460041004300340070004D0055005500550041004A006A0046004C007A005200520051004100340053004F0076005200790050007800700033006E007900390050004D006200380036004B004B004100750049005A005A004400310063002F006E0054004F00540033006F006F006F0041004D00550075004D00300055005500410047004F00610073005200470049006B0046006F002B006E006F0061004B004B0041004C0071004A002B002B004C006A006F0034003500460053007A007100660049007800370059004E00460046004D0052006C0053004A00670035006F006F006F006F0041002F002F00320051003D003D8000008D67B668ED3B58F4317ECF353E7FAAF9C46CF42CA4159F4D0B6880F9C1B04906487828CD9BE04EAC5A26715CBF4ABDCA8A65CB1F7B6B083F101EC7979FD734A3";
        final SM2 sm2 = SmUtil.sm2();
        String sign = sm2.signHex(HexUtil.encodeHexStr(content));
//        boolean verifyHex = sm2.verifyHex(HexUtil.encodeHexStr(content), sign);
        System.out.println("sign= " + sign);
//        System.out.println("验签结果：" + verifyHex);
        System.out.println("结束时间：" + System.currentTimeMillis());
        // 1643081953417
        // 1643081952694
    }

    /**
     * SM2签名和验签
     */
    @Test
    public void test33() {
        String content = "你好";
        String privateKey = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKey = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        final SM2 sm2 = SmUtil.sm2(HexConverter.hexString2ByteArray(privateKey), HexConverter.hexString2ByteArray(publicKey));
        byte[] sign = sm2.sign(content.getBytes());
        boolean verifyHex = sm2.verify(content.getBytes(), sign);
        System.out.println("签名: " + HexConverter.byteArray2HexString(sign) +" -- 验签结果：" + verifyHex);
    }

    /**
     * 指定私钥签名测试
     * <i scr="https://i.goto327.top/CryptTools/SM2.aspx?tdsourcetag=s_pctim_aiomsg">秘钥验证</i>
     *
     * #32323232323232323232323232323232
     * #3082012A3081D0A003020102021001008631310010866000000000000200300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323235365A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D03420004A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034300A06082A811CCF55018375034900304602210089C7758D7D3CBD0F0703D14A42B9A91BC783660915CE2BF30D6D429CD4FB6DA9022100BD72E92B15B0060F2B84950CA153C958132769D5734EE9A1E5B069A1F070764F
     * #4F013E491A34B904F786336C931246A2788C5DB59D2E4EC8D081D2E8166BFB3ADA9D93860AB9F959230F7BD7A7061B5EB1F0CC37046099054D51FBE39B9E7181
     *  9fd0c8eaa1f5cb92065b7501ad1e3957b886905f6c7c6414df43961edca2fd0d3a9a33998b8c97afcb08cf24667392d4393cdc16d3051221102577d8035e78c1
     *
     *
     *
     * 303439353530433543393946353933453041433537394239433642344136393130303430324343313639434638303138383246433444343143433943314641413943344132393137344534393435303941343046334239324439414246454243333541373031414143453835334434463437393845363438313138393630393542463836393033424435394439424138443536453243393341314330334230453438433044303732323445343641383742334345433134464337323235384532384232443230424234304432373138414642443941363033343435423335393646313738424344304646383737364135373541303736414645324338323937464245
     */
    @Test
    public void signTest() {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        //需要加密的明文,得到明文对应的字节数组
        String data = "31313131313131313131313131313131";
        byte[] dataBytes = HexConverter.hexString2ByteArray(data);
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
//        byte[] encrypt = sm2.encrypt(dataBytes, KeyType.PublicKey);
//        byte[] encrypt = HexConverter.hexString2ByteArray("0C5ADD8755EED8A55B7A1A01315316EB5EB86D2E68243CE22B3501362FEDD62C562665703E41BF77F97B4ACDC80A24647B7B696C335FCD875915FD4F7C3B82EE74430181902961797D3D4986DA26C8DA3757DC9E4655369290CA456FBCBCE81CC57B4C972C2406FC9ACDFD1E186FC775");
        byte[] encrypt = HexConverter.hexString2ByteArray("045bce594725149735f24b8efb0c6aa046469d63c16c49b9f2bbc3deaf3c8f940df3f9455bc12b80f40745b7783954c3a4e820355671ce0073ddb8af4e579252574a741ecb2f7ee9520933644925b6cc9f3c7f011acfe668c6e8c3178c142c9fe820684062c36eb014ec1761527e2baa32");
        byte[] decryptStr = sm2.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("加密: " + HexUtil.encodeHexStr(encrypt));
        System.out.println("解密: " + HexConverter.byteArray2HexString(decryptStr));
        System.out.println("解密: " + decryptStr);
//        System.out.println("数据: " + HexUtil.encodeHexStr(dataBytes));
//        System.out.println("签名: " + HexUtil.encodeHexStr(sign));
    }

    @Test
    public void signTest2() throws UnsupportedEncodingException {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        String data = "046613CC1A403B20C77BFEE11708405DC9E1FADF51B2FE0CC9452DD370D16B9CC53206B083590FEB43F476CF5F15940E50F9870DD3990AA40D34741BC8D7CCA1FE18C5846CC96F41AF4D1743741B46105190A0861EDF326029B4E3A0492E17CF4BCA7C2CE19451D42CCEE3E5A3471FFF1CA95F9CB702F414F340A28868D5F8F02F9B3600E6086813AB7D95";
//        String data = "31313131313131313131313131313131";
        byte[] dataBytes = data.getBytes();
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
//        byte[] encrypt = sm2.encrypt(dataBytes, KeyType.PublicKey);
//        byte[] decrypt = sm2.decrypt(encrypt, KeyType.PrivateKey);
        byte[] decrypt = sm2.decrypt(HexUtil.decodeHex(data), KeyType.PrivateKey);
        System.out.println("加密: " + data);
        System.out.println("解密: " + HexUtil.encodeHexStr(decrypt));
    }

    /**
     * 指定私钥签名测试
     * <i scr="https://i.goto327.top/CryptTools/SM2.aspx?tdsourcetag=s_pctim_aiomsg">秘钥验证</i>
     */
    @Test
    public void verifyTest() {
        //指定的公钥
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        //需要加密的明文,得到明文对应的字节数组
        byte[] dataBytes = "323232323232323232323232323232323082012A3081D0A003020102021001008631310010866000000000000200300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323235365A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D03420004A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034300A06082A811CCF55018375034900304602210089C7758D7D3CBD0F0703D14A42B9A91BC783660915CE2BF30D6D429CD4FB6DA9022100BD72E92B15B0060F2B84950CA153C958132769D5734EE9A1E5B069A1F070764F".getBytes();
        //签名值
//        String signHex = "4F013E491A34B904F786336C931246A2788C5DB59D2E4EC8D081D2E8166BFB3ADA9D93860AB9F959230F7BD7A7061B5EB1F0CC37046099054D51FBE39B9E7181";
        String signHex = "7a93c482be3a1da007d34ebd5373d75e9f31ec40e79892928db2c4fff4307126593ec07faa340b4c39ff2200b20f9d979c9874c0e2b0fe73a6e0100cd651a42e";
        //这里需要根据公钥的长度进行加工
        if (publicKeyHex.length() == 130) {
            //这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyHex = publicKeyHex.substring(2);
        }
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        boolean verify = sm2.verify(dataBytes, HexUtil.decodeHex(signHex));
        System.out.println("数据: " + HexUtil.encodeHexStr(dataBytes));
        System.out.println("验签结果: " + verify);
    }



    /**
     * 使用自定义密钥对加密或解密
     */
    @Test
    public void test2() {
        String text = "我是一段测试aaaa";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        System.out.println("私钥： " + HexUtil.encodeHexStr(privateKey));
        System.out.println("公钥： " + HexUtil.encodeHexStr(publicKey));

        System.out.println("---- " + HexConverter.byteArray2HexString(privateKey));
        System.out.println("---- " + HexConverter.byteArray2HexString(publicKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密 私钥解密
        String encryptBcd = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptBcd, KeyType.PrivateKey));
        // 04EAEAD0D5B529009C01F75BD9964E1FD22E66FE035E59ACDFD779541CF7E2548F9C736B6E11F3B0799430E0964430EA6068A08323D42D46CF2B642E13CD789457F1E02BE5811E4A1ACACC5B0E3F04A6B764197C1170907ECBF5A11875D513DEFA703117A4EE7DF6CB8E9C83EC5A4DFFAB18F89CBC4983
        System.out.println("加密之后的数据： " + encryptBcd);
        // 我是一段测试aaaa
        System.out.println("解密之后的数据： " + decryptStr);
    }


    /**
     * 使用自定义密钥对加密或解密
     */
    @Test
    public void test22() {
        String text = "我是一段测试aaaa";
        byte[] privateKey = HexConverter.hexString2ByteArray("308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420e435d2097d0bdb91310380a35a24d215d18b5a8f3ac8f144dedb9e2bfdeaaca9a00a06082a811ccf5501822da1440342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");
        byte[] publicKey = HexConverter.hexString2ByteArray("3059301306072a8648ce3d020106082a811ccf5501822d0342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");

        System.out.println("---- " + HexConverter.byteArray2HexString(privateKey));
        System.out.println("---- " + HexConverter.byteArray2HexString(publicKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        byte[] sign = sm2.sign(text.getBytes());
        boolean verify = sm2.verify(text.getBytes(), sign);
        // 3044 02204F82EF6C6E036C91AB74D1203659C9BC4DE17E989E5C1E9D77F18D80ADDE16C302203B8792A64B712DE279CFEA63029C30F3FF2F3DF6194E71AC224EBD6972D04A89
        // 3044 02204A8B28377ACC24E6A19F9D1422B4F655FE7241889641DA3F0F185EFFCA7ED66402205D60BEE9C49E4B4BC29694333A0C6B9E191CBDBAF66E91A16774521D7A13951C
        // 3045 0220636DF69EC2D4B14E03FBD225044FAFAFA0413AAA5FADD707BEA55A98F54BCE8202210098C6796ED6D50158424FF52CB3D6B44B0C69636C85D9BCA767FC46B18077B854
        System.out.println(HexConverter.byteArray2HexString(sign));
        System.out.println("验证结果: " + verify);
    }

    /**
     * 使用随机生成的密钥对加密或解密
     */
    @Test
    public void test() {
        String text = "123456789"; // 106 - 96 = 10

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encrypt = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encrypt, KeyType.PrivateKey));
        // 045DB3B15840CFEE985F93AE6FEFB0CACDA63E6BF8DB3B2BF84D4CF3753320453BCAA426492098ADA321361E12454C2E24C1E42988F80827B02E18BBBAEC23EC73A0D064E52D7EF2689202F01AA16AED580F512BD88C7AAF09A6E90548C6CEAA2099C7D4C15A2E107539FF7E3AD954AF8C79EAFE05A980
        System.out.println("加密之后的数据： " + encrypt);
        // 我是一段测试aaaa
        System.out.println("解密之后的数据： " + decryptStr);
    }


    @Test
    public void sm2Test() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, DecoderException, BadPaddingException, IllegalBlockSizeException {
        /**
         * BC: Cipher.SM2 -> org.bouncycastle.jcajce.provider.asymmetric.ec.GMCipherSpi$SM2
         *   aliases: [SM2WITHSM3, 1.2.156.10197.1.301.3.2.1]
         *
         *   publicKey == MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEW73V9Nl6FUU63Lxs93AmJpfiPhFZCPqa5EYF/uUs2MyzGEKi+TJjEB3v3KuJzVCBC1Ilow6l1jvHGFEa/oijtw==
         * privateKey == MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgf+D/qe1w942MyuI5lB1lSk3zcD+WDeo3w+V0njAj+digCgYIKoEcz1UBgi2hRANCAARbvdX02XoVRTrcvGz3cCYml+I+EVkI+prkRgX+5SzYzLMYQqL5MmMQHe/cq4nNUIELUiWjDqXWO8cYURr+iKO3
         * BKZ8bs6POIjawn45BwmReSQqQ5Rk9LLGNhRNzQr4tEnJB6LcFzij8AvbqYJdr/D6isnJ4OMpalfWkbflbjIkeGeUiyxrLpSfw3oPwz2MJ4ivCzNsuE/HrBviRGNjZRedM2L7vVl6IkwZEOj4utQ5w0k=
         */
        byte[] privateKey = HexConverter.hexString2ByteArray("308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420e435d2097d0bdb91310380a35a24d215d18b5a8f3ac8f144dedb9e2bfdeaaca9a00a06082a811ccf5501822da1440342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");
        byte[] publicKey = HexConverter.hexString2ByteArray("3059301306072a8648ce3d020106082a811ccf5501822d0342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");

        Cipher cipher = Cipher.getInstance("SM2", new BouncyCastleProvider());
        Key sm2 = new SecretKeySpec(privateKey, "SM2");
        cipher.init(Cipher.ENCRYPT_MODE, sm2);
        byte[] decryptedData = cipher.doFinal(Hex.decodeHex("31313131313131313131313131313131"));
        System.out.println(HexConverter.byteArray2HexString(decryptedData));
        System.out.println(new String(decryptedData));
    }


    /**
     * 获取加密的密匙，传入的slatKey可以是任意长度的，作为SecureRandom的随机种子，
     * 而在KeyGenerator初始化时设置密匙的长度128bit(16位byte)
     */
    private static Key getSlatKey(String slatKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        kgen.init(128, random);
        Key key = kgen.generateKey();
        return key;
    }

    /**
     * 根据slatKey获取公匙，传入的slatKey作为SecureRandom的随机种子
     * 若使用new SecureRandom()创建公匙，则需要记录下私匙，解密时使用
     */
    private static byte[] getPublicKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        keyPairGenerator.initialize(1024, random);//or 2048
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 根据slatKey获取私匙，传入的slatKey作为SecureRandom的随机种子
     */
    private static byte[] getPrivateKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        keyPairGenerator.initialize(1024, random);// or 2048
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPrivate().getEncoded();
    }


}
