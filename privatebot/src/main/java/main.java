import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class main {
    public static JDABuilder builder;
    public static JDA build;
    public static Statement st;

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        String token = "Discord 機器人金鑰";
        builder = JDABuilder.createDefault(token);

        builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);

        builder.setActivity(Activity.streaming("牧野白vtuber", "https://www.youtube.com/channel/UCbZcxNKrC0a6IZYBowvzAUg"));

        registerListeners();

        build = builder.build();

        Connection conn = null;
        try {
            //連接MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("連接成功MySQLToJava");
            //建立讀取資料庫 (test 為資料庫名稱; user 為MySQL使用者名稱; passwrod 為MySQL使用者密碼)
            String datasource = "jdbc:mysql://localhost/randomsentences?user=root&useUnicode=true&characterEncoding=UTF-8";
            //以下的資料庫操作請參考本blog中: "使用 Java 連結與存取 access 資料庫 (JDBC)"
            conn = DriverManager.getConnection(datasource);
            System.out.println("連接成功MySQL");
            st = conn.createStatement();
        } catch (Exception e) {
            System.out.println("?");
        }
    }

    public static void registerListeners() {
        builder.addEventListeners(new command());
    }
}